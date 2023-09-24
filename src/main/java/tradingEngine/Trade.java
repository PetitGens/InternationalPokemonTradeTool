package main.java.tradingEngine;

import main.java.tradingEngine.gameData.Language;
import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.SaveFile;
import main.java.tradingEngine.gameData.Specie;

import java.io.IOException;

/**
 * The class whose role is to exchange the Pokémon between the two save files.
 * If the two Pokémon need to be converted before the trade, this class will handle it.
 * @author Julien Ait azzouzene
 */
public class Trade {
    SaveFile saveFile1 = null;
    SaveFile saveFile2 = null;

    public Trade(){}

    /**
     * A parameterized constructor for Trade. It permits to specify the two save files directly.
     * @param saveFile1 -> the first save file involved in the trade
     * @param saveFile2 -> the second save file involved in the trade
     */
    public Trade(SaveFile saveFile1, SaveFile saveFile2){
        this.saveFile1 = saveFile1;
        this.saveFile2 = saveFile2;
    }

    /**
     * Sets one of the two save files.
     * @param index -> the index of the save file that must be set (either 0 or 1 since there are two of them)
     * @param saveFile -> the save file to store
     */
    public void setSaveFile(int index, SaveFile saveFile){
        switch (index) {
            case 0:
                saveFile1 = saveFile;
                break;
            case 1:
                saveFile2 = saveFile;
                break;
            default:
                throw new IllegalArgumentException("invalid save index");
        }
    }

    /**
     * Open a save file and keep to trade with it later.
     * @param index -> the index of the save file that must be set (either 0 or 1 since there are two of them)
     * @param path -> the path of the save file
     * @throws IllegalArgumentException -> if the index is invalid
     * @throws IOException -> if the save file opening fails
     */
    public void openSaveFile(int index, String path) throws IOException {
        switch (index) {
            case 0:
                saveFile1 = new SaveFile(path);
                break;
            case 1:
                saveFile2 = new SaveFile(path);
                break;
            default:
                throw new IllegalArgumentException("invalid save index");
        }
    }

    /**
     * Returns one of the two save files.
     * @param index -> the index of the save file that must be returned (either 0 or 1 since there are two of them)
     * @return -> the required save file
     */
    public SaveFile getSaveFile(int index){
        switch (index) {
            case 0:
                return  saveFile1;
            case 1:
                return saveFile2;
            default:
                throw new IllegalArgumentException("invalid save index");
        }
    }

    /**
     * Trades the specified Pokémon between the two save files.
     * @param pokemon1InParty -> true if the Pokémon of the first save file is in the party, false if it's in a box
     * @param pokemonIndex1 -> the first Pokémon's index in the party / in its box
     * @param boxIndex1 -> the first Pokémon's box number (only important if in a box)
     * @param pokemon2InParty -> true if the Pokémon of the second save file is in the party, false if it's in a box
     * @param pokemonIndex2 -> the second Pokémon's index in the party / in its box
     * @param boxIndex2 -> the second Pokémon's box number (only important if in a box)
     * @throws IllegalArgumentException -> if one of the indexes is invalid or if one of the two locations contains a
     * Blank Space (i.e. no Pokémon)
     * @throws IOException -> if the file writing fails
     */
    public void trade(boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                      boolean pokemon2InParty, int pokemonIndex2, int boxIndex2) throws IOException {

        if(saveFile1 == null ||saveFile2 == null){
            throw new NullPointerException("one of the two save file has not been set properly");
        }

        Language save1Language = saveFile1.getLanguage();
        Language save2Language = saveFile2.getLanguage();

        // Set the language to Western if it can apply to the save file (every Western games are compatible with each other)
        if(save1Language != Language.JAPANESE && save1Language != Language.KOREAN){
            save1Language = Language.WESTERN;
        }
        if(save2Language != Language.JAPANESE && save2Language != Language.KOREAN){
            save2Language = Language.WESTERN;
        }

        if(save1Language == save2Language){
            noConversionTrade(pokemon1InParty, pokemonIndex1, boxIndex1, pokemon2InParty, pokemonIndex2, boxIndex2);
        }
        else{
            conversionTrade(pokemon1InParty, pokemonIndex1, boxIndex1, pokemon2InParty, pokemonIndex2, boxIndex2);
        }
    }

    /**
     * Trades the two Pokémon without any conversion
     * @param pokemon1InParty -> true if the Pokémon of the first save file is in the party, false if it's in a box
     * @param pokemonIndex1 -> the first Pokémon's index in the party / in its box
     * @param boxIndex1 -> the first Pokémon's box number (only important if in a box)
     * @param pokemon2InParty -> true if the Pokémon of the second save file is in the party, false if it's in a box
     * @param pokemonIndex2 -> the second Pokémon's index in the party / in its box
     * @param boxIndex2 -> the second Pokémon's box number (only important if in a box)
     * @throws IllegalArgumentException -> if one of the indexes is invalid or if one of the two locations contains a
     * Blank Space (i.e. no Pokémon)
     * @throws IOException -> if the file writing fails
     */
    private void noConversionTrade(boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                              boolean pokemon2InParty, int pokemonIndex2, int boxIndex2) throws IOException {
        Pokemon[] tradedPokemon = getTradedPokemon(pokemon1InParty, pokemonIndex1, boxIndex1,
                pokemon2InParty, pokemonIndex2, boxIndex2);

        storeTradedPokemon(tradedPokemon, pokemon1InParty, pokemonIndex1, boxIndex1,
                pokemon2InParty, pokemonIndex2, boxIndex2);
    }

    /**
     * Get the two Pokémon from the save files and return them.
     * @param pokemon1InParty -> true if the Pokémon of the first save file is in the party, false if it's in a box
     * @param pokemonIndex1 -> the first Pokémon's index in the party / in its box
     * @param boxIndex1 -> the first Pokémon's box number (only important if in a box)
     * @param pokemon2InParty -> true if the Pokémon of the second save file is in the party, false if it's in a box
     * @param pokemonIndex2 -> the second Pokémon's index in the party / in its box
     * @param boxIndex2 -> the second Pokémon's box number (only important if in a box)
     * @throws IllegalArgumentException -> if one of the indexes is invalid or if one of the two locations contains a
     * Blank Space (i.e. no Pokémon)
     * @return an array containing the two Pokémon
     */
    private Pokemon[] getTradedPokemon(boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                                       boolean pokemon2InParty, int pokemonIndex2, int boxIndex2){
        Pokemon[] pokemon = new Pokemon[2];
        pokemon[0] = pokemon1InParty ?
                    saveFile1.getPartyPokemon(pokemonIndex1) :
                    saveFile1.getBoxPokemon(boxIndex1, pokemonIndex1);
        pokemon[1] = pokemon2InParty ?
                    saveFile2.getPartyPokemon(pokemonIndex2):
                    saveFile2.getBoxPokemon(boxIndex2, pokemonIndex2);

        if(pokemon[0].getSpecie() == Specie.BLANK_SPACE || pokemon[1].getSpecie() == Specie.BLANK_SPACE){
            throw new IllegalArgumentException("there is no Pokémon at this position");
        }

        return pokemon;
    }


    /**
     * Store the two Pokémon in their respective new save files.
     * @param pokemon -> an array containing the two Pokémon
     * @param pokemon1InParty -> true if the Pokémon of the first save file is in the party, false if it's in a box
     * @param pokemonIndex1 -> the first Pokémon's index in the party / in its box
     * @param boxIndex1 -> the first Pokémon's box number (only important if in a box)
     * @param pokemon2InParty -> true if the Pokémon of the second save file is in the party, false if it's in a box
     * @param pokemonIndex2 -> the second Pokémon's index in the party / in its box
     * @param boxIndex2 -> the second Pokémon's box number (only important if in a box)
     * @throws IllegalArgumentException -> if one of the indexes is invalid or if one of the two locations contains a
     * Blank Space (i.e. no Pokémon)
     * @throws IOException -> if the file writing fails
     */
    private void storeTradedPokemon(Pokemon[] pokemon, boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                                    boolean pokemon2InParty, int pokemonIndex2, int boxIndex2) throws IOException {
        if(pokemon1InParty){
            saveFile1.storePokemonInParty(pokemonIndex1, pokemon[1]);
        }
        else{
            saveFile1.storePokemonInBox(boxIndex1, pokemonIndex1, pokemon[1]);
        }

        if(pokemon2InParty){
            saveFile2.storePokemonInParty(pokemonIndex2, pokemon[0]);
        }
        else{
            saveFile2.storePokemonInBox(boxIndex2, pokemonIndex2, pokemon[0]);
        }
    }

    /**
     * Trades two Pokémon whose languages need to be converted.
     * @param pokemon1InParty -> true if the Pokémon of the first save file is in the party, false if it's in a box
     * @param pokemonIndex1 -> the first Pokémon's index in the party / in its box
     * @param boxIndex1 -> the first Pokémon's box number (only important if in a box)
     * @param pokemon2InParty -> true if the Pokémon of the second save file is in the party, false if it's in a box
     * @param pokemonIndex2 -> the second Pokémon's index in the party / in its box
     * @param boxIndex2 -> the second Pokémon's box number (only important if in a box)
     * @throws IllegalArgumentException -> if one of the indexes is invalid or if one of the two locations contains a
     * Blank Space (i.e. no Pokémon)
     * @throws IOException -> if the file writing fails
     */
    private void conversionTrade(boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                                 boolean pokemon2InParty, int pokemonIndex2, int boxIndex2) throws IOException {
        Pokemon[] tradedPokemon = getTradedPokemon(pokemon1InParty, pokemonIndex1, boxIndex1,
                pokemon2InParty, pokemonIndex2, boxIndex2);

        convertPokemonLanguage(tradedPokemon[0], saveFile2.getLanguage());
        convertPokemonLanguage(tradedPokemon[1], saveFile1.getLanguage());

        storeTradedPokemon(tradedPokemon, pokemon1InParty, pokemonIndex1, boxIndex1,
                pokemon2InParty, pokemonIndex2, boxIndex2);
    }

    /**
     * Convert a Pokémon's language so that it can be traded.
     * @param pokemon -> the Pokémon whose language needs to be converted
     * @param newLanguage -> the language to set the Pokémon's language to.
     */
    private void convertPokemonLanguage(Pokemon pokemon, Language newLanguage){
        //TODO ask the user a nickname if the Pokemon is nicknamed
        //TODO ask the user a new trainer Name
        boolean nicknamed = pokemon.isNicknamed();
        pokemon.setLanguage(newLanguage);

        if(! nicknamed){
            pokemon.setNickname(pokemon.getSpecie().getName(newLanguage));
        }
    }
}

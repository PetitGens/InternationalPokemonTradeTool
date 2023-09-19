package main.java.tradingEngine;

import main.java.tradingEngine.gameData.Language;
import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.SaveFile;

import java.io.IOException;

public class Trade {
    SaveFile saveFile1;
    SaveFile saveFile2;

    public void openSaveFile(int index, String path) throws IOException {
        switch (index) {
            case 0 -> saveFile1 = new SaveFile(path);
            case 1 -> saveFile2 = new SaveFile(path);
            default -> throw new IllegalArgumentException("invalid save index");
        }
    }

    public SaveFile getSaveFile(int index){
        return switch (index) {
            case 0 -> saveFile1;
            case 1 -> saveFile2;
            default -> throw new IllegalArgumentException("invalid save index");
        };
    }

    public void trade(boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                      boolean pokemon2InParty, int pokemonIndex2, int boxIndex2) throws IOException {
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
            throw new RuntimeException("not implemented yet");
        }
    }

    private void noConversionTrade(boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                              boolean pokemon2InParty, int pokemonIndex2, int boxIndex2) throws IOException {
        Pokemon[] tradedPokemon = getTradedPokemon(pokemon1InParty, pokemonIndex1, boxIndex1,
                pokemon2InParty, pokemonIndex2, boxIndex2);

        storeTradedPokemon(tradedPokemon, pokemon1InParty, pokemonIndex1, boxIndex1,
                pokemon2InParty, pokemonIndex2, boxIndex2);
    }

    private Pokemon[] getTradedPokemon(boolean pokemon1InParty, int pokemonIndex1, int boxIndex1,
                                       boolean pokemon2InParty, int pokemonIndex2, int boxIndex2){
        Pokemon[] pokemon = new Pokemon[2];
        pokemon[0] = pokemon1InParty ?
                    saveFile1.getPartyPokemon(pokemonIndex1) :
                    saveFile1.getBoxPokemon(boxIndex1, pokemonIndex1);
        pokemon[1] = pokemon2InParty ?
                    saveFile1.getPartyPokemon(pokemonIndex2):
                    saveFile2.getBoxPokemon(boxIndex2, pokemonIndex2);

        return pokemon;
    }

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
}

package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.InGameString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is used to read and write data from a Pokémon Red, Green, Blue, Yellow, Gold, Silver or Crystal save file.
 * It also detects the generation (RBY or GSC) and the region of the game the save file is from.
 * @author Julien Ait azzouzene
 */
public abstract class SaveFile {
    protected String path;

    private final Pokemon[] party = new Pokemon[6];

    protected int partyLength;

    protected Pokemon[][] boxes;

    protected int boxSize;

    protected int boxCount;

    protected int currentBox;

    protected int nameLength;

    protected int trainerId;
    protected InGameString trainerName;
    protected Language language;

    protected int checksum;

    /**
     * Returns one the Pokémon in the party at the given index (0 gives the 1st Pokémon, 5 gives the 6th).
     * @param index the index of the wanted party Pokémon
     * @return the Pokémon at this index in the party
     * @throws IllegalArgumentException if the index is negative or above 5
     */
    public Pokemon getPartyPokemon(int index) {
        if(index >= 6 || index < 0){
            throw new IllegalArgumentException("illegal party index : " + index);
        }

        return party[index];
    }

    /**
     * Returns the number of Pokémon in the party.
     * @return the party length
     */
    public int getPartyLength(){
        return partyLength;
    }

    /**
     * Returns a Pokémon in the specified box and at the specified index.
     * @param boxNumber the number of the box the Pokémon is (starts at 0).
     * @param pokemonIndex the index of the Pokémon in the box
     * @return the wanted Pokémon
     * @throws IllegalArgumentException if the boxNumber or pokemonIndex is negative or too big for the game / region
     */
    public Pokemon getBoxPokemon(int boxNumber, int pokemonIndex){
        if(boxNumber >= boxCount || boxNumber < 0){
            throw new IllegalArgumentException("illegal boxNumber : " + boxNumber);
        }
        if(pokemonIndex >= boxSize || pokemonIndex < 0){
            throw new IllegalArgumentException("illegal pokemonIndex : " + pokemonIndex);
        }

        return boxes[boxNumber][pokemonIndex];
    }

    /**
     * Returns the path where the save file is located.
     * @return the path
     */
    public String getPath(){
        return path;
    }

    /**
     * Sets the path where the data must be written.
     * This save file will not load data at that location, but will overwrite the potential file every time a new
     * Pokémon is stored.
     * @param path the new file path
     */
    public void setPath(String path){
        this.path = path;
    }

    /**
     * Returns the trainer ID of the save file.
     * @return the trainer ID
     */
    public int getTrainerId(){
        return trainerId;
    }

    /**
     * Returns the trainer name of the save file.
     * @return the trainer name
     */
    public InGameString getTrainerName(){
        return trainerName;
    }

    /**
     * Returns the save file's language.
     * @return the save file's language
     */
    public Language getLanguage(){return language;}

    /**
     * Sets the save file's language. This should only be used with a Western save file, since it's the only case where
     * the language cannot be detected.
     * @param language the save file's language
     */
    public void setLanguage(Language language){
        if(this.language == Language.JAPANESE){
            throw new IllegalArgumentException("you cannot change language if the save file is Japanese");
        }

        if(this.language == Language.KOREAN){
            throw new IllegalArgumentException("you cannot change language if the save file is Korean");
        }

        if(language == Language.JAPANESE || language == Language.KOREAN){
            throw new IllegalArgumentException("illegal language for a Western game");
        }

        this.language = language;
    }

    /**
     * Replace a Pokémon in the save file's party with the one given as parameter. Updates both the object and the file.
     * @param index the index in the party where the Pokémon should be placed (starts at 0).
     * @param pokemon the Pokémon to store
     * @throws IOException if the file writing fails
     * @throws IllegalArgumentException if the index is illegal, or if there is no Pokémon at that index or if the
     * Pokémon is not in the same format as the save file (it must be converted beforehand)
     */
    public void storePokemonInParty(int index, Pokemon pokemon) throws IOException {
        if(getPartyPokemon(index).getSpecie().equals(Specie.BLANK_SPACE)){
            throw new IllegalArgumentException("there is no Pokémon at that index");
        }

        if(! isLanguageCompatible(pokemon)){
            throw new IllegalArgumentException("this Pokémon's language is not compatible with this Save File's");
        }

        Path file = Paths.get(path);

        byte[] saveData = Files.readAllBytes(file);
        writePartyPokemon(saveData, index, pokemon);

        fixChecksum(saveData);

        Files.write(file, saveData);
        party[index] = pokemon;
    }

    /**
     * Replace a Pokémon in one of the save file's boxes with the one given as parameter. Updates both the object and
     * the file.
     * @param boxNumber the box where the Pokémon should be placed (starts at 0)
     * @param pokemonIndex the index in the box where the Pokémon should be placed (starts at 0)
     * @param pokemon the Pokémon to store
     * @throws IOException if the file writing fails
     * @throws IllegalArgumentException if the index is illegal, or if there is no Pokémon at that location or if the
     * Pokémon is not in the same format as the save file (it must be converted beforehand)
     */
    public void storePokemonInBox(int boxNumber, int pokemonIndex, Pokemon pokemon) throws IOException {
        if(getBoxPokemon(boxNumber, pokemonIndex).getSpecie().equals(Specie.BLANK_SPACE)){
            throw new IllegalArgumentException("there is no Pokémon at that index");
        }

        if(! isLanguageCompatible(pokemon)){
            throw new IllegalArgumentException("this Pokémon's language is not compatible with this Save File's");
        }

        Path file = Paths.get(path);
        byte[] saveData = Files.readAllBytes(file);

        if(boxNumber == currentBox){
            writeCurrentBoxPokemon(saveData, pokemonIndex, pokemon);
        }
        else{
            writeBoxPokemon(saveData, boxNumber, pokemonIndex, pokemon);
        }

        fixChecksum(saveData);

        Files.write(file, saveData);
        boxes[boxNumber][pokemonIndex] = pokemon;
    }

    private boolean isLanguageCompatible(Pokemon pokemon){
        Language saveLanguage = language;
        Language pokemonLanguage = pokemon.getLanguage();

        // If the language is not Japanese or Korean, we consider it Western for simplification
        if(saveLanguage != Language.JAPANESE && saveLanguage != Language.KOREAN){
            saveLanguage = Language.WESTERN;
        }
        if(pokemonLanguage != Language.JAPANESE && pokemonLanguage != Language.KOREAN){
            pokemonLanguage = Language.WESTERN;
        }

        return saveLanguage == pokemonLanguage;
    }

    /**
     * Copy the save file into the "backups" folder.
     * @throws IOException if the backup fail for some reason
     * @see SaveBackups#backup(SaveFile)
     */
    public void backup() throws IOException{
        SaveBackups.backup(this);
    }

    /**
     * Returns the save file's checksum.
     * @return the checksum
     */
    public int getChecksum(){
        return checksum;
    }

    protected abstract void fixChecksum(byte[] data);

    /**
     * Reads every Pokémon in the save file's party
     * @param saveData the save file's raw data
     * @param partyOffset the offset of the party in the save file
     * @throws IOException if any data is detected illegal (such as Pokémon count and characters)
     */
    protected void readPartyPokemon(byte[] saveData, int partyOffset) throws IOException {
        Specie[] partySpeciesList = new Specie[partyLength];

        for(int i = 0; i < partyLength; i++){
            // Reading list of species indexes
            int indexNumber = Bytes.byteToUnsignedByte(saveData[partyOffset + 1 + i]);
            partySpeciesList[i] = Specie.specieFromGen1Index(indexNumber);

            //Reading all party Pokémon
            int pokemonOffset = partyOffset + 8 + i * 44;
            byte[] pokemonData = new byte[44];
            System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 44);

            // Reading trainer name and nickname

            int trainerNameOffset = partyOffset + 0x110 + nameLength * i;
            byte[] trainerName = new byte[nameLength];
            System.arraycopy(saveData, trainerNameOffset, trainerName, 0, nameLength);

            int nicknameOffset = partyOffset + 0X110 + nameLength * (6 + i);
            byte[] nickname = new byte[nameLength];
            System.arraycopy(saveData, nicknameOffset, nickname, 0, nameLength);

            try{
                party[i] = new Pokemon(pokemonData, trainerName, nickname, language);
            }
            catch(IllegalArgumentException e){
                throw new IOException("illegal name : " + e.getMessage());
            }

            if(party[i].getSpecie() != partySpeciesList[i]){
                throw new IOException("a party's pokemon's specie doesn't match the species list");
            }
        }

        for(int i = partyLength; i < 6; i++){
            party[i] = Pokemon.BLANK_SPACE;
        }
    }

    /**
     * Write a Pokémon's data in the party.
     * @param saveData the save file's raw data
     * @param index the index where the Pokémon must be placed (starts at 0)
     * @param pokemon the Pokémon to write
     */
    protected abstract void writePartyPokemon(byte[] saveData, int index, Pokemon pokemon);

    /**
     * Reads every Pokémon in the save file current box (the lately opened one in the game).
     * @param saveData the save file's raw data
     * @param boxOffset the offset of the current box in the save file
     * @throws IOException if any data is detected illegal (such as Pokémon count and characters)
     */
    protected void readCurrentBoxPokemon(byte[] saveData, int boxOffset) throws IOException {
        int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);
        
        int firstPokemonOffset = language == Language.JAPANESE ? boxOffset + 0x20 : boxOffset + 0x16;

        for(int pokemonIndex = 0; pokemonIndex < pokemonCount; pokemonIndex++) {
            int pokemonOffset = firstPokemonOffset + pokemonIndex * 33;
            byte[] pokemonData = new byte[33];
            System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 33);

            // Reading trainer name and nickname

            int trainerNameOffset = firstPokemonOffset + 33 * boxSize + nameLength * pokemonIndex;
            byte[] trainerName = new byte[nameLength];
            System.arraycopy(saveData, trainerNameOffset, trainerName, 0, nameLength);

            int nicknameOffset = firstPokemonOffset + 33 * boxSize + nameLength * (boxSize + pokemonIndex);
            byte[] nickname = new byte[nameLength];
            System.arraycopy(saveData, nicknameOffset, nickname, 0, nameLength);

            try {
                boxes[currentBox][pokemonIndex] = new Pokemon(pokemonData, trainerName, nickname, language);
            } catch (IllegalArgumentException e) {
                throw new IOException("illegal name : " + e.getMessage());
            }
        }

        for(int i = pokemonCount; i < boxSize; i++){
            boxes[currentBox][i] = Pokemon.BLANK_SPACE;
        }
    }

    /**
     * Write a Pokémon's data in the current box.
     * @param saveData the save file's raw data
     * @param index the index where the Pokémon must be placed (starts at 0)
     * @param pokemon the Pokémon to write
     */
    protected abstract void writeCurrentBoxPokemon(byte[] saveData, int index, Pokemon pokemon);

    /**
     * Reads every Pokémon in the save file's boxes.
     * @param saveData the save file's raw data
     * @throws IOException if any data is detected illegal (such as Pokémon count and characters)
     */
    protected void readBoxesPokemon(byte[] saveData) throws IOException {
        int bank2Offset = 0x4000;

        int boxDiff;

        if(language == Language.JAPANESE) {
        	boxDiff = 0x566;
        }
        else {
        	boxDiff = 0x462;
        }

        for(int boxIndex = 0; boxIndex < boxCount / 2; boxIndex++){
            if(boxIndex == currentBox){
                continue;
            }

            int boxOffset = bank2Offset + boxIndex * boxDiff;
            int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);

            if(pokemonCount > boxSize){
                throw new IOException("illegal pokemon count");
            }

            for(int pokemonIndex = 0; pokemonIndex < pokemonCount; pokemonIndex++){
                int pokemonOffset = boxOffset + boxSize + 2 + pokemonIndex * 33;
                byte[] pokemonData = new byte[33];
                System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 33);

                // Reading trainer name and nickname

                int trainerNameOffset = boxOffset + 2 + 34 * boxSize + nameLength * pokemonIndex;
                byte[] trainerName = new byte[nameLength];
                System.arraycopy(saveData, trainerNameOffset, trainerName, 0, nameLength);

                int nicknameOffset = boxOffset + 2 + 34 * boxSize + nameLength * (boxSize + pokemonIndex);
                byte[] nickname = new byte[nameLength];
                System.arraycopy(saveData, nicknameOffset, nickname, 0, nameLength);

                try {
                    boxes[boxIndex][pokemonIndex] = new Pokemon(pokemonData, trainerName, nickname, language);
                } catch (IllegalArgumentException e) {
                    throw new IOException("illegal name : " + e.getMessage());
                }
            }

            for(int i = pokemonCount; i < boxSize; i++){
                boxes[boxIndex][i] = Pokemon.BLANK_SPACE;
            }
        }

        int bank3Offset = 0x6000;

        for(int boxIndex = 0; boxIndex < boxCount / 2; boxIndex++){
            if(boxIndex == currentBox){
                continue;
            }

            int boxOffset = bank3Offset + boxIndex * boxDiff;
            int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);

            for(int pokemonIndex = 0; pokemonIndex < pokemonCount; pokemonIndex++){
                int pokemonOffset = boxOffset + 2 + boxSize + pokemonIndex * 33;
                byte[] pokemonData = new byte[33];
                System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 33);

                // Reading trainer name and nickname

                int trainerNameOffset = boxOffset + 2 + 34 * boxSize + nameLength * pokemonIndex;
                byte[] trainerName = new byte[nameLength];
                System.arraycopy(saveData, trainerNameOffset, trainerName, 0, nameLength);

                int nicknameOffset = boxOffset + 2 + 34 * boxSize + nameLength * (boxSize + pokemonIndex);
                byte[] nickname = new byte[nameLength];
                System.arraycopy(saveData, nicknameOffset, nickname, 0, nameLength);

                try {
                    boxes[boxIndex + boxCount / 2][pokemonIndex] = new Pokemon(pokemonData, trainerName, nickname, language);
                } catch (IllegalArgumentException e) {
                    throw new IOException("illegal name : " + e.getMessage());
                }
            }

            for(int i = pokemonCount; i < boxSize; i++){
                boxes[boxIndex + boxCount / 2][i] = Pokemon.BLANK_SPACE;
            }
        }
    }

    /**
     * Write a Pokémon's data in the party.
     * @param saveData the save file's raw data
     * @param boxNumber the number of the box where the Pokémon must be placed (starts at 0)
     * @param pokemonIndex the index where the Pokémon must be placed (starts at 0)
     * @param pokemon the Pokémon to write
     */
    protected abstract void writeBoxPokemon(byte[] saveData, int boxNumber, int pokemonIndex, Pokemon pokemon);

    /**
     * Opens the save file located at the given path and returns a SaveFile instance based on it.
     * @param path the save file path
     * @return the created SaveFile object
     * @throws IOException if the file opening fails or if the save file has some problem
     * (for instance, bad checksums, illegal data, ...)
     */
    public static SaveFile openSaveFile(String path) throws IOException {
        byte[] saveData = Files.readAllBytes(Paths.get(path));

        //TODO check if save is gen1 or gen2

        return new Gen1SaveFile(path, saveData);
    }
}

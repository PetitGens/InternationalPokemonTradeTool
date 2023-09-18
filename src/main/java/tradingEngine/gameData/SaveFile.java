package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.InGameString;
import main.java.tradingEngine.gameData.strings.JapaneseString;
import main.java.tradingEngine.gameData.strings.WesternString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is used to read and write data from a Pokémon Red, Green, Blue, Yellow, Gold, Silver or Crystal save file.
 * It also detects the generation (RBY or GSC) and the region of the game the save file is from.
 * @author Julien Ait azzouzene
 */
public class SaveFile {
    private String path;

    private final Pokemon[] party = new Pokemon[6];

    private final int partyLength;

    private final Pokemon[][] boxes;

    private final int boxSize;

    private final int boxCount;

    private final int currentBox;

    private final int nameLength;

    private final int trainerId;
    private final InGameString trainerName;
    private Language language;

    /**
     * Reads the save file located at the given path.
     * @param path -> the save file path
     * @throws IOException -> if the file opening fails or if the save file has some problem
     * (for instance, bad checksums, illegal data, ...)
     */
    public SaveFile(String path) throws IOException {
        this.path = path;
        byte[] saveData = Files.readAllBytes(Path.of(path));

        if(saveData.length != 0x8000 && saveData.length != 0x802C){
            throw new IOException("invalid size");
        }

        if(isWestern(saveData)){
            language = Language.WESTERN;
        }
        else if(isJapanese(saveData)){
            language = Language.JAPANESE;
        }
        else{
            throw new IOException("invalid save file");
        }

        if(! verifyChecksum(saveData)){
            throw new IOException("invalid checksum");
        }

        int partyOffset;
        int currentBoxNumberOffset;
        int currentBoxOffset;

        if(language == Language.JAPANESE){
            partyOffset = 0x2ED5;
            currentBoxNumberOffset = 0x2842;
            currentBoxOffset = 0x302D;
            boxSize = 30;
            boxCount = 8;
            nameLength = 6;
        }
        else{
            partyOffset = 0x2F2C;
            currentBoxNumberOffset = 0x284C;
            currentBoxOffset = 0x30C0;
            boxSize = 20;
            boxCount = 12;
            nameLength = 11;
        }

        boxes = new Pokemon[boxCount][boxSize];

        // Reading party data

        partyLength = Bytes.byteToUnsignedByte(saveData[partyOffset]);
        if(partyLength > 6) {
            throw new IOException("party has a length above 6");
        }

        readPartyPokemon(saveData, partyOffset);

        // Reading boxes data

        currentBox = Bytes.byteToUnsignedByte(saveData[currentBoxNumberOffset]) & 0x7F;

        if(currentBox > 11){
            throw new IOException("illegal current box : " + currentBox);
        }

        readCurrentBoxPokemon(saveData, currentBoxOffset);

        readBoxesPokemon(saveData);

        // Reading trainer's name and ID number

        int idOffset = language == Language.JAPANESE ? 0x25FB : 0x2605;
        trainerId = Bytes.twoBytesToInt(saveData[idOffset], saveData[idOffset + 1]);

        int trainerNameOffset = 0x2598;
        byte[] rawName = new byte[nameLength];
        System.arraycopy(saveData, trainerNameOffset, rawName, 0, nameLength);
        trainerName = language == Language.JAPANESE ? new JapaneseString() : new WesternString();
        trainerName.addAll(rawName);
    }

    /**
     * Returns one the Pokémon in the party at the given index (0 gives the 1st Pokémon, 5 gives the 6th).
     * @param index -> the index of the wanted party Pokémon
     * @return the Pokémon at this index in the party
     * @throws IllegalArgumentException -> if the index is negative or above 5
     */
    public Pokemon getPartyPokemon(int index) {
        if(index >= 6 || index < 0){
            throw new IllegalArgumentException("illegal party index : " + index);
        }

        return party[index];
    }

    /**
     * Returns the number of Pokémon in the party.
     * @return -> the party length
     */
    public int getPartyLength(){
        return partyLength;
    }

    /**
     * Returns a Pokémon in the specified box and at the specified index.
     * @param boxNumber -> the number of the box the Pokémon is (starts at 0).
     * @param pokemonIndex -> the index of the Pokémon in the box
     * @return -> the wanted Pokémon
     * @throws IllegalArgumentException -> if the boxNumber or pokemonIndex is negative or too big for the game / region
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
     * @return -> the path
     */
    public String getPath(){
        return path;
    }

    /**
     * Sets the path where the data must be written.
     * This save file will not load data at that location, but will overwrite the potential file every time a new
     * Pokémon is stored.
     * @param path -> the new file path
     */
    public void setPath(String path){
        this.path = path;
    }

    /**
     * Returns the trainer ID of the save file.
     * @return -> the trainer ID
     */
    public int getTrainerId(){
        return trainerId;
    }

    /**
     * Returns the trainer name of the save file.
     * @return -> the trainer name
     */
    public InGameString getTrainerName(){
        return trainerName;
    }

    public Language getLanguage(){return language;}

    public void setLanguage(Language language){
        if(this.language == Language.JAPANESE){
            throw new IllegalArgumentException("you cannot change language if the save file is Japanese");
        }

        if(language == Language.JAPANESE || language == Language.KOREAN){
            throw new IllegalArgumentException("illegal language for a Western game");
        }

        this.language = language;
    }


    /**
     * Replace a Pokémon in the save file's party with the one given as parameter. Updates both the object and the file.
     * @param index -> the index in the party where the Pokémon should be placed (starts at 0).
     * @param pokemon -> the Pokémon to store
     * @throws IOException -> if the file writing fails
     * @throws IllegalArgumentException -> if the index is illegal, or if there is no Pokémon at that index or if the
     * Pokémon is not in the same format as the save file (it must be converted beforehand)
     */
    public void storePokemonInParty(int index, Pokemon pokemon) throws IOException {
        if(getPartyPokemon(index).getSpecie().equals(Specie.BLANK_SPACE)){
            throw new IllegalArgumentException("there is no Pokémon at that index");
        }

        if((pokemon.getLanguage() == Language.JAPANESE) != (language == Language.JAPANESE)){
            throw new IllegalArgumentException("this Pokémon's language is not compatible with this Save File's");
        }

        Path file = Path.of(path);

        byte[] saveData = Files.readAllBytes(file);
        writePartyPokemon(saveData, index, pokemon);

        fixChecksum(saveData);

        Files.write(file, saveData);
        party[index] = pokemon;
    }

    /**
     * Replace a Pokémon in one of the save file's boxes with the one given as parameter. Updates both the object and
     * the file.
     * @param boxNumber -> the box where the Pokémon should be placed (starts at 0)
     * @param pokemonIndex -> the index in the box where the Pokémon should be placed (starts at 0)
     * @param pokemon -> the Pokémon to store
     * @throws IOException -> if the file writing fails
     * @throws IllegalArgumentException -> if the index is illegal, or if there is no Pokémon at that location or if the
     * Pokémon is not in the same format as the save file (it must be converted beforehand)
     */
    public void storePokemonInBox(int boxNumber, int pokemonIndex, Pokemon pokemon) throws IOException {
        if(getBoxPokemon(boxNumber, pokemonIndex).getSpecie().equals(Specie.BLANK_SPACE)){
            throw new IllegalArgumentException("there is no Pokémon at that index");
        }

        if((pokemon.getLanguage() == Language.JAPANESE)!= (language == Language.JAPANESE)){
            throw new IllegalArgumentException("this Pokémon's language is not compatible with this Save File's");
        }

        Path file = Path.of(path);
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

    /**
     * Compute the save file's checksum and returns it.
     * @param data -> the save file's raw data
     * @return -> the checksum
     */
    private int calculateChecksum(byte[] data){
        int checksumOffset = language == Language.JAPANESE ? 0x3594 : 0x3523;
        int checksum = 0;

        for(int i = 0x2598; i <= checksumOffset - 1; i++){
            checksum += Bytes.byteToUnsignedByte(data[i]);
        }

        checksum = (checksum & 0xFF) ^ 0xFF;

        return checksum;
    }

    /**
     * Verify if the checksum is correct (if the stored checksum matches the computed one)
     * @param data -> the save file's raw data
     * @return -> true if the checksum is correct, false otherwise
     */
    private boolean verifyChecksum(byte[] data){
        int checksumOffset = language == Language.JAPANESE ? 0x3594 : 0x3523;
        return calculateChecksum(data) == Bytes.byteToUnsignedByte(data[checksumOffset]);
    }

    /**
     * Computes the checksum and store it at the right location in the save file.
     * @param data -> the save file's raw data
     */
    private void fixChecksum(byte[] data){
        int checksum = calculateChecksum(data);
        int checksumOffset = language == Language.JAPANESE ? 0x3594 : 0x3523;

        data[checksumOffset] = (byte) checksum;
    }

    /**
     * Reads every Pokémon in the save file's party
     * @param saveData -> the save file's raw data
     * @param partyOffset -> the offset of the party in the save file
     * @throws IOException -> if any data is detected illegal (such as Pokémon count and characters)
     */
    private void readPartyPokemon(byte[] saveData, int partyOffset) throws IOException {
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
     * @param saveData -> the save file's raw data
     * @param index -> the index where the Pokémon must be placed (starts at 0)
     * @param pokemon -> the Pokémon to write
     */
    private void writePartyPokemon(byte[] saveData, int index, Pokemon pokemon){
        int partyOffset = language == Language.JAPANESE ? 0x2ED5 : 0x2F2C;

        saveData[partyOffset + index + 1] = (byte) pokemon.getIndexNumber();

        int pokemonOffset = partyOffset + 8 + index * 44;
        byte[] pokemonData = pokemon.toPartyRawData();
        System.arraycopy(pokemonData, 0, saveData, pokemonOffset, 44);

        int trainerNameOffset = partyOffset + 0x110 + nameLength * index;
        byte[] trainerName = pokemon.getTrainerName().toArray();
        System.arraycopy(trainerName, 0, saveData, trainerNameOffset, nameLength);

        int nicknameOffset = partyOffset + 0x110 + nameLength * (6 + index);
        byte[] nickname = pokemon.getNickname().toArray();
        System.arraycopy(nickname, 0, saveData, nicknameOffset, nameLength);
    }
    /**
     * Reads every Pokémon in the save file current box (the lately opened one in the game).
     * @param saveData -> the save file's raw data
     * @param boxOffset -> the offset of the current box in the save file
     * @throws IOException -> if any data is detected illegal (such as Pokémon count and characters)
     */
    private void readCurrentBoxPokemon(byte[] saveData, int boxOffset) throws IOException {
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
     * @param saveData -> the save file's raw data
     * @param index -> the index where the Pokémon must be placed (starts at 0)
     * @param pokemon -> the Pokémon to write
     */
    private void writeCurrentBoxPokemon(byte[] saveData, int index, Pokemon pokemon){
        int currentBoxOffset = language == Language.JAPANESE ? 0x302D : 0x30C0;
        saveData[currentBoxOffset + index + 1] = (byte) pokemon.getIndexNumber();

        int firstPokemonOffset = language == Language.JAPANESE ? currentBoxOffset + 0x20 : currentBoxOffset + 0x16;

        int pokemonOffset = firstPokemonOffset + index * 33;
        byte[] pokemonData = pokemon.toBoxRawData();
        System.arraycopy(pokemonData, 0, saveData, pokemonOffset, 33);

        int trainerNameOffset = firstPokemonOffset + 33 * boxSize + nameLength * index;
        byte[] trainerName = pokemon.getTrainerName().toArray();
        System.arraycopy(trainerName, 0, saveData, trainerNameOffset, nameLength);

        int nicknameOffset = firstPokemonOffset + 33 * boxSize + nameLength * (boxSize + index);
        byte[] nickname = pokemon.getNickname().toArray();
        System.arraycopy(nickname, 0, saveData, nicknameOffset, nameLength);
    }

    /**
     * Reads every Pokémon in the save file's boxes.
     * @param saveData -> the save file's raw data
     * @throws IOException -> if any data is detected illegal (such as Pokémon count and characters)
     */
    private void readBoxesPokemon(byte[] saveData) throws IOException {
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
     * @param saveData -> the save file's raw data
     * @param boxNumber -> the number of the box where the Pokémon must be placed (starts at 0)
     * @param pokemonIndex -> the index where the Pokémon must be placed (starts at 0)
     * @param pokemon -> the Pokémon to write
     */
    private void writeBoxPokemon(byte[] saveData, int boxNumber, int pokemonIndex, Pokemon pokemon){
        int bankOffset = 0x4000;

        if(boxNumber >= boxCount / 2){
            bankOffset = 0x6000;
            boxNumber -= boxCount / 2;
        }

        int boxDiff = language == Language.JAPANESE ? 0x566 : 0x462;
        int boxOffset = bankOffset + boxNumber * boxDiff;
        saveData[boxOffset + 1 + pokemonIndex] = (byte) pokemon.getIndexNumber();

        int pokemonOffset = boxOffset + boxSize + 2 + pokemonIndex * 33;
        byte[] pokemonData = pokemon.toBoxRawData();
        System.arraycopy(pokemonData, 0, saveData, pokemonOffset, 33);

        int trainerNameOffset = boxOffset + 2 + 34 * boxSize + nameLength * pokemonIndex;
        byte[] trainerName = pokemon.getTrainerName().toArray();
        System.arraycopy(trainerName, 0, saveData, trainerNameOffset, nameLength);

        int nicknameOffset = boxOffset + 2 + 34 * boxSize + nameLength * (boxSize + pokemonIndex);
        byte[] nickname = pokemon.getNickname().toArray();
        System.arraycopy(nickname, 0, saveData, nicknameOffset, nameLength);
    }

    /**
     * Returns if the save file is from a western game (i.e., english, french, german, spanish or italian).
     * @param saveData -> the save file's raw data
     * @return -> true if the save file is western, false otherwise
     */
    public static boolean isWestern(byte[] saveData){
        return saveData[0x2F2C] <= 20 && Bytes.byteToUnsignedByte(saveData[0x2F2D + saveData[0x2F2C]]) == 0xFF &&
                saveData[0x30C0] <= 20 && Bytes.byteToUnsignedByte(saveData[0x30C1 + saveData[0x30C0]]) == 0xFF;
    }

    /**
     * Returns if the save file is from a japanese game.
     * @param saveData -> the save file's raw data
     * @return -> true if the save file is japanese, false otherwise
     */
    public static boolean isJapanese(byte[] saveData){
        return saveData[0x2ED5] <= 30 && Bytes.byteToUnsignedByte(saveData[0x2ED6 + saveData[0x2ED5]]) == 0xFF &&
                saveData[0x302D] <= 30 && Bytes.byteToUnsignedByte(saveData[0x302E + saveData[0x302D]]) == 0xFF;
    }
}

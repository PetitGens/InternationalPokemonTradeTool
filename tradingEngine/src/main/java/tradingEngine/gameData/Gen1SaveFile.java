package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.JapaneseString;
import main.java.tradingEngine.gameData.strings.WesternString;

import java.io.IOException;

public class Gen1SaveFile extends SaveFile{

    Gen1SaveFile(String path, byte[] saveData) throws IOException {
        this.path = path;

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
     * Compute the save file's checksum and returns it.
     * @param data the save file's raw data
     * @return the checksum
     */
    protected int calculateChecksum(byte[] data){
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
     * @param data the save file's raw data
     * @return true if the checksum is correct, false otherwise
     */
    private boolean verifyChecksum(byte[] data){
        int checksumOffset = language == Language.JAPANESE ? 0x3594 : 0x3523;
        checksum = Bytes.byteToUnsignedByte(data[checksumOffset]);
        return calculateChecksum(data) == checksum;
    }



    /**
     * Computes the checksum and store it at the right location in the save file.
     * @param data the save file's raw data
     */
    @Override
    protected void fixChecksum(byte[] data) {
        checksum = calculateChecksum(data);
        int checksumOffset = language == Language.JAPANESE ? 0x3594 : 0x3523;

        data[checksumOffset] = (byte) checksum;
    }

    @Override
    protected void writePartyPokemon(byte[] saveData, int index, Pokemon pokemon) {
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

    @Override
    protected void writeCurrentBoxPokemon(byte[] saveData, int index, Pokemon pokemon) {
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

    @Override
    protected void writeBoxPokemon(byte[] saveData, int boxNumber, int pokemonIndex, Pokemon pokemon) {
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
     * Returns if the save file is from a western game (i.e., english, French, german, spanish or italian).
     * @param saveData the save file's raw data
     * @return true if the save file is western, false otherwise
     */
    private static boolean isWestern(byte[] saveData){
        return saveData[0x2F2C] <= 20 && Bytes.byteToUnsignedByte(saveData[0x2F2D + saveData[0x2F2C]]) == 0xFF &&
                saveData[0x30C0] <= 20 && Bytes.byteToUnsignedByte(saveData[0x30C1 + saveData[0x30C0]]) == 0xFF;
    }

    /**
     * Returns if the save file is from a japanese game.
     * @param saveData the save file's raw data
     * @return true if the save file is japanese, false otherwise
     */
    private static boolean isJapanese(byte[] saveData){
        return saveData[0x2ED5] <= 30 && Bytes.byteToUnsignedByte(saveData[0x2ED6 + saveData[0x2ED5]]) == 0xFF &&
                saveData[0x302D] <= 30 && Bytes.byteToUnsignedByte(saveData[0x302E + saveData[0x302D]]) == 0xFF;
    }
}

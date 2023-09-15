package main.java.tradingEngine.gameData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveFile {
    private String path;

    private final boolean japanese;

    private final Pokemon[] party = new Pokemon[6];

    private final int partyLength;

    private final Pokemon[][] boxes;

    private final int boxSize;

    private final int boxCount;

    private final int currentBox;

    private final int nameLength;

    public SaveFile(String path) throws IOException {
        this.path = path;
        byte[] saveData = Files.readAllBytes(Path.of(path));

        if(saveData.length != 0x8000 && saveData.length != 0x802C){
            throw new IOException("invalid save file");
        }

        if(isWestern(saveData)){
            japanese = false;
        }
        else if(isJapanese(saveData)){
            japanese = true;
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

        if(japanese){
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
    }

    public Pokemon getPartyPokemon(int index){
        if(index >= 6 || index < 0){
            throw new IllegalArgumentException("illegal party index : " + index);
        }

        return party[index];
    }

    public int getPartyLength(){
        return partyLength;
    }

    public Pokemon getBoxPokemon(int boxNumber, int pokemonIndex){
        if(boxNumber >= boxCount || boxNumber < 0){
            throw new IllegalArgumentException("illegal boxNumber : " + boxNumber);
        }
        if(pokemonIndex >= boxSize || pokemonIndex < 0){
            throw new IllegalArgumentException("illegal pokemonIndex : " + pokemonIndex);
        }

        return boxes[boxNumber][pokemonIndex];
    }

    public void storePokemonInParty(int index, Pokemon pokemon) throws IOException {
        if(getPartyPokemon(index).getSpecie().equals(Specie.BLANK_SPACE)){
            throw new IllegalArgumentException("there is no Pokémon at that index");
        }

        if(pokemon.isJapanese() != japanese){
            throw new IllegalArgumentException("this Pokémon's language is not compatible with this Save File's");
        }

        Path file = Path.of(path);

        byte[] saveData = Files.readAllBytes(file);
        writePartyPokemon(saveData, index, pokemon);

        fixChecksum(saveData);

        Files.write(file, saveData);
        party[index] = pokemon;
    }

    public void storePokemonInBox(int boxNumber, int pokemonIndex, Pokemon pokemon) throws IOException {
        if(getBoxPokemon(boxNumber, pokemonIndex).getSpecie().equals(Specie.BLANK_SPACE)){
            throw new IllegalArgumentException("there is no Pokémon at that index");
        }

        if(pokemon.isJapanese() != japanese){
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

    private int calculateChecksum(byte[] data){
        int checksumOffset = japanese ? 0x3594 : 0x3523;
        int checksum = 0;

        for(int i = 0x2598; i <= checksumOffset - 1; i++){
            checksum += Bytes.byteToUnsignedByte(data[i]);
        }

        checksum = (checksum & 0xFF) ^ 0xFF;

        return checksum;
    }

    private boolean verifyChecksum(byte[] data){
        int checksumOffset = japanese ? 0x3594 : 0x3523;
        return calculateChecksum(data) == Bytes.byteToUnsignedByte(data[checksumOffset]);
    }

    private void fixChecksum(byte[] data){
        int checksum = calculateChecksum(data);
        int checksumOffset = japanese ? 0x3594 : 0x3523;

        data[checksumOffset] = (byte) checksum;
    }

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
                party[i] = new Pokemon(pokemonData, trainerName, nickname, japanese);
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

    private void writePartyPokemon(byte[] saveData, int index, Pokemon pokemon){
        int partyOffset = japanese ? 0x2ED5 : 0x2F2C;

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

    private void readCurrentBoxPokemon(byte[] saveData, int boxOffset) throws IOException {
        int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);
        
        int firstPokemonOffset = japanese ? boxOffset + 0x20 : boxOffset + 0x16;

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
                boxes[currentBox][pokemonIndex] = new Pokemon(pokemonData, trainerName, nickname, japanese);
            } catch (IllegalArgumentException e) {
                throw new IOException("illegal name : " + e.getMessage());
            }
        }

        for(int i = pokemonCount; i < boxSize; i++){
            boxes[currentBox][i] = Pokemon.BLANK_SPACE;
        }
    }

    private void writeCurrentBoxPokemon(byte[] saveData, int index, Pokemon pokemon){
        int currentBoxOffset = japanese ? 0x302D : 0x30C0;
        saveData[currentBoxOffset + index + 1] = (byte) pokemon.getIndexNumber();

        int firstPokemonOffset = japanese ? currentBoxOffset + 0x20 : currentBoxOffset + 0x16;

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

    private void readBoxesPokemon(byte[] saveData) throws IOException {
        int bank2Offset = 0x4000;

        int boxDiff;

        if(japanese) {
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
                    boxes[boxIndex][pokemonIndex] = new Pokemon(pokemonData, trainerName, nickname, japanese);
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
                    boxes[boxIndex + boxCount / 2][pokemonIndex] = new Pokemon(pokemonData, trainerName, nickname, japanese);
                } catch (IllegalArgumentException e) {
                    throw new IOException("illegal name : " + e.getMessage());
                }
            }

            for(int i = pokemonCount; i < boxSize; i++){
                boxes[boxIndex + boxCount / 2][i] = Pokemon.BLANK_SPACE;
            }
        }
    }

    private void writeBoxPokemon(byte[] saveData, int boxNumber, int pokemonIndex, Pokemon pokemon){
        int bankOffset = 0x4000;

        if(boxNumber >= boxCount / 2){
            bankOffset = 0x6000;
            boxNumber -= boxCount / 2;
        }

        int boxDiff = japanese ? 0x566 : 0x462;
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

    public static boolean isWestern(byte[] saveData){
        return saveData[0x2F2C] <= 20 && Bytes.byteToUnsignedByte(saveData[0x2F2D + saveData[0x2F2C]]) == 0xFF &&
                saveData[0x30C0] <= 20 && Bytes.byteToUnsignedByte(saveData[0x30C1 + saveData[0x30C0]]) == 0xFF;
    }

    public static boolean isJapanese(byte[] saveData){
        return saveData[0x2ED5] <= 30 && Bytes.byteToUnsignedByte(saveData[0x2ED6 + saveData[0x2ED5]]) == 0xFF &&
                saveData[0x302D] <= 30 && Bytes.byteToUnsignedByte(saveData[0x302E + saveData[0x302D]]) == 0xFF;
    }
}

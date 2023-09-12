package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;

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

    public SaveFile(String path) throws IOException {
        this.path = path;
        byte[] saveData = Files.readAllBytes(Path.of(path));

        //TODO verify file size

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
        }
        else{
            partyOffset = 0x2F2C;
            currentBoxNumberOffset = 0x284C;
            currentBoxOffset = 0x30C0;
            boxSize = 20;
            boxCount = 12;
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

    public void storePokemonInParty(int index, Pokemon pokemon){
        throw new RuntimeException("not implemented");
    }

    public void storePokemonInBox(int boxNumber, int pokemonIndex, Pokemon pokemon){
        throw new RuntimeException("not implemented");
    }

    private boolean verifyChecksum(byte[] data){
        int checksumOffset;
        int checksum = 0;

        if(japanese){
            checksumOffset = 0x3594;
        }
        else{
            checksumOffset = 0x3523;
        }

        for(int i = 0x2598; i <= checksumOffset - 1; i++){
            checksum += Bytes.byteToUnsignedByte(data[i]);
        }

        checksum = (checksum & 0xFF) ^ 0xFF;

        return checksum == Bytes.byteToUnsignedByte(data[checksumOffset]);
    }

    private void readPartyPokemon(byte[] saveData, int partyOffset) throws IOException {
        Specie[] partySpeciesList = new Specie[partyLength];

        for(int i = 0; i < partyLength; i++){
            // Reading list of species indexes
            int indexNumber = Bytes.byteToUnsignedByte(saveData[partyOffset + 1 + i]);
            partySpeciesList[i] = Specie.specieFromIndex(indexNumber);

            //Reading all party Pokémon
            int pokemonOffset = partyOffset + 8 + i * 44;
            byte[] pokemonData = new byte[44];
            System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 44);

            //TODO read nickname and ot name
            byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

            party[i] = new Pokemon(pokemonData, nickname, nickname, false);

            if(party[i].getSpecie() != partySpeciesList[i]){
                throw new IOException("a party's pokemon's specie doesn't match the species list");
            }
        }

        for(int i = partyLength; i < 5; i++){
            party[i] = Pokemon.BLANK_SPACE;
        }
    }

    private void readCurrentBoxPokemon(byte[] saveData, int boxOffset){
        int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);
        
        int firstPokemonOffset;
        
        if(japanese) {
        	firstPokemonOffset = boxOffset + 0x20;
        }
        else {
        	firstPokemonOffset = boxOffset + 0x16;
        }
        
        for(int pokemonIndex = 0; pokemonIndex < pokemonCount; pokemonIndex++){
            int pokemonOffset = firstPokemonOffset + pokemonIndex * 33;
            byte[] pokemonData = new byte[33];
            System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 33);

            //TODO read nickname and ot name
            byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

            boxes[currentBox][pokemonIndex] = new Pokemon(pokemonData, nickname, nickname, false);
        }

        for(int i = pokemonCount; i < 20; i++){
            boxes[currentBox][i] = Pokemon.BLANK_SPACE;
        }
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

                //TODO read nickname and ot name
                byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

                boxes[boxIndex][pokemonIndex] = new Pokemon(pokemonData, nickname, nickname, false);
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
                int pokemonOffset = boxOffset + 0x16 + pokemonIndex * 33;
                byte[] pokemonData = new byte[33];
                System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 33);

                //TODO read nickname and ot name
                byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

                boxes[boxIndex + boxCount / 2][pokemonIndex] = new Pokemon(pokemonData, nickname, nickname, false);
            }

            for(int i = pokemonCount; i < boxSize; i++){
                boxes[boxIndex + boxCount / 2][i] = Pokemon.BLANK_SPACE;
            }
        }
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

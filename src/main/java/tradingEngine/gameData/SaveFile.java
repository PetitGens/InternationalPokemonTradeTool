package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveFile {
    private String path;

    private final Pokemon[] party = new Pokemon[6];

    private final int partyLength;

    private final Pokemon[][] boxes = new Pokemon[12][20];

    private final int currentBox;

    public SaveFile(String path) throws IOException {
        this.path = path;
        byte[] saveData = Files.readAllBytes(Path.of(path));

        //TODO verify file size

        //TODO verify the checksum
        //TODO detect language

        // Reading party data

        partyLength = Bytes.byteToUnsignedByte(saveData[0x2F2C]);

        if(partyLength > 6) {
            throw new IOException("party has a length above 6");
        }

        Specie[] partySpeciesList = new Specie[partyLength];

        for(int i = 0; i < partyLength; i++){
            // Reading list of species indexes
            int indexNumber = Bytes.byteToUnsignedByte(saveData[0x2F2D + i]);
            partySpeciesList[i] = Specie.specieFromIndex(indexNumber);

            //Reading all party Pokémon
            int pokemonOffset = 0x2F34 + i * 44;
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

        // Reading boxes data

        currentBox = Bytes.byteToUnsignedByte(saveData[0x284C]) & 0x7F;

        if(currentBox > 11){
            throw new IOException("illegal current box : " + (currentBox + 1));
        }

        int bank2Offset = 0x4000;

        for(int boxIndex = 0; boxIndex < 6; boxIndex++){
            if(boxIndex == currentBox){
                continue;
            }

            int boxOffset = bank2Offset + boxIndex * 0x462;
            int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);

            for(int pokemonIndex = 0; pokemonIndex < pokemonCount; pokemonIndex++){
                int pokemonOffset = boxOffset + 0x16 + pokemonIndex * 33;
                byte[] pokemonData = new byte[33];
                System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 33);

                //TODO read nickname and ot name
                byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

                boxes[boxIndex][pokemonIndex] = new Pokemon(pokemonData, nickname, nickname, false);
            }

            for(int i = pokemonCount; i < 20; i++){
                boxes[boxIndex][i] = Pokemon.BLANK_SPACE;
            }
        }

        int bank3Offset = 0x6000;

        for(int boxIndex = 0; boxIndex < 6; boxIndex++){
            if(boxIndex == currentBox){
                continue;
            }

            int boxOffset = bank3Offset + boxIndex * 0x462;
            int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);

            for(int pokemonIndex = 0; pokemonIndex < pokemonCount; pokemonIndex++){
                int pokemonOffset = boxOffset + 0x16 + pokemonIndex * 33;
                byte[] pokemonData = new byte[33];
                System.arraycopy(saveData, pokemonOffset, pokemonData, 0, 33);

                //TODO read nickname and ot name
                byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

                boxes[boxIndex + 6][pokemonIndex] = new Pokemon(pokemonData, nickname, nickname, false);
            }

            for(int i = pokemonCount; i < 20; i++){
                boxes[boxIndex + 6][i] = Pokemon.BLANK_SPACE;
            }

            readCurrentBoxPokemon(saveData);
        }
    }

    public Pokemon getPartyPokemon(int index){
        return party[index];
    }

    public int getPartyLength(){
        return partyLength;
    }

    public Pokemon getBoxPokemon(int boxNumber, int pokemonIndex){
        return boxes[boxNumber][pokemonIndex];
    }

    public void storePokemonInParty(int index, Pokemon pokemon){
        throw new RuntimeException("not implemented");
    }

    public void storePokemonInBox(int boxNumber, int pokemonIndex, Pokemon pokemon){
        throw new RuntimeException("not implemented");
    }

    private boolean verifyChecksum(File file){
        throw new RuntimeException("not implemented");
    }

    private void readCurrentBoxPokemon(byte[] saveData){
        int boxOffset = 0x30C0;
        int pokemonCount = Bytes.byteToUnsignedByte(saveData[boxOffset]);

        for(int pokemonIndex = 0; pokemonIndex < pokemonCount; pokemonIndex++){
            int pokemonOffset = boxOffset + 0x16 + pokemonIndex * 33;
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
}

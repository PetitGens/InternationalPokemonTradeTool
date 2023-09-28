package test.tradingEngine.gameData;

import main.java.tradingEngine.gameData.Language;
import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.SaveFile;
import main.java.tradingEngine.gameData.Specie;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.*;

public class SaveFileTest {
    private static final String resourcesPath = "src/main/resources/tradingEngine/";
    
    @Test
    public void westernSaveTest() throws IOException {
        Files.copy(Paths.get(resourcesPath + "save.sav"), Paths.get(resourcesPath + "copy.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        SaveFile saveFile = new SaveFile(resourcesPath + "copy.sav");

        assertEquals(5, saveFile.getPartyLength());

        Pokemon venusaur = saveFile.getPartyPokemon(4);
        checkPartyPokemon(venusaur);

        Pokemon snorlax = saveFile.getBoxPokemon(0, 2);
        checkBoxPokemon(snorlax);

        Pokemon dragonite = saveFile.getBoxPokemon(11, 0);
        checkBoxPokemon2(dragonite);

        Pokemon dugtrio = saveFile.getBoxPokemon(1, 19);
        checkCurrentBoxPokemon(dugtrio);
        
        assertEquals(19076, saveFile.getTrainerId());
        assertEquals("PtJean" ,saveFile.getTrainerName().toString());
    }

    @Test
    public void japaneseSaveTest() throws IOException{
        Files.copy(Paths.get(resourcesPath + "jap.sav"), Paths.get(resourcesPath + "copy_jap.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        SaveFile saveFile = new SaveFile(resourcesPath + "copy_jap.sav");

        assertEquals(6, saveFile.getPartyLength());

        checkJapanesePartyPokemon(saveFile.getPartyPokemon(4));
        
        checkJapaneseCurrentBoxPokemon(saveFile.getBoxPokemon(1, 1));

        checkJapaneseBoxPokemon(saveFile.getBoxPokemon(0, 2));
        
        assertEquals(19983, saveFile.getTrainerId());
        assertEquals("ハケッ" ,saveFile.getTrainerName().toString());
    }

    @Test
    public void isWesternTest() throws IOException {
        byte[] jap = Files.readAllBytes(Paths.get(resourcesPath + "jap.sav"));
        byte[] fr = Files.readAllBytes(Paths.get(resourcesPath + "save.sav"));

        assertTrue(SaveFile.isWestern(fr));
        assertFalse(SaveFile.isWestern(jap));
    }

    @Test
    public void isJapaneseTest() throws IOException {
        byte[] jap = Files.readAllBytes(Paths.get(resourcesPath + "jap.sav"));
        byte[] fr = Files.readAllBytes(Paths.get(resourcesPath + "save.sav"));

        assertTrue(SaveFile.isJapanese(jap));
        assertFalse(SaveFile.isJapanese(fr));
    }

    @Test
    public void gettersExceptionsTest() throws IOException {
        SaveFile fr = new SaveFile(resourcesPath + "save.sav");
        SaveFile jap = new SaveFile(resourcesPath + "jap.sav");

        assertThrows(IllegalArgumentException.class, () -> fr.getPartyPokemon(-1));
        assertThrows(IllegalArgumentException.class, () -> fr.getPartyPokemon(6));
        assertThrows(IllegalArgumentException.class, () -> fr.getBoxPokemon(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> fr.getBoxPokemon(0, -1));
        assertThrows(IllegalArgumentException.class, () -> fr.getBoxPokemon(12, 0));
        assertThrows(IllegalArgumentException.class, () -> fr.getBoxPokemon(0, 20));

        assertThrows(IllegalArgumentException.class, () -> jap.getPartyPokemon(-1));
        assertThrows(IllegalArgumentException.class, () -> jap.getPartyPokemon(6));
        assertThrows(IllegalArgumentException.class, () -> jap.getBoxPokemon(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> jap.getBoxPokemon(0, -1));
        assertThrows(IllegalArgumentException.class, () -> jap.getBoxPokemon(8, 0));
        assertThrows(IllegalArgumentException.class, () -> jap.getBoxPokemon(0, 30));

        fr.getPartyPokemon(0);
        fr.getPartyPokemon(5);
        fr.getBoxPokemon(0, 0);
        fr.getBoxPokemon(11, 0);
        fr.getBoxPokemon(0, 19);
        
        jap.getPartyPokemon(0);
        jap.getPartyPokemon(5);
        jap.getBoxPokemon(0, 0);
        jap.getBoxPokemon(7, 0);
        jap.getBoxPokemon(0, 29);
    }

    @Test
    public void westernPartyPokemonWritingTest() throws IOException{
    	Files.copy(Paths.get(resourcesPath + "save.sav"), Paths.get(resourcesPath + "copy.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        SaveFile saveFile = new SaveFile(resourcesPath + "copy.sav");
        
        Pokemon newPartyPokemon = new Pokemon(PokemonTest.boxPokemonData(), new byte[]{(byte)0x80},
                new byte[]{
                        (byte) 0x83,
                        (byte) 0x8E,
                        (byte) 0x83,
                        (byte) 0x8E,
                }, Language.WESTERN);

        saveFile.storePokemonInParty(4, newPartyPokemon);

        SaveFile saveFile2 = new SaveFile(resourcesPath + "copy.sav");

        Pokemon storedPokemon = saveFile2.getPartyPokemon(4);

        checkStoredWesternPartyPokemon(storedPokemon);
        
        assertEquals(newPartyPokemon, storedPokemon);
    }

    @Test
    public void westernBoxPokemonWritingTest() throws IOException{
        Files.copy(Paths.get(resourcesPath + "save.sav"), Paths.get(resourcesPath + "copy.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        SaveFile saveFile = new SaveFile(resourcesPath + "copy.sav");

        Pokemon newBoxPokemon = new Pokemon(PokemonTest.partyPokemonData(), new byte[]{(byte)0x80},
                new byte[]{
                        (byte) 0x8B,
                        (byte) 0x84,
                        (byte) 0x80,
                        (byte) 0x85,
                }, Language.WESTERN);

        saveFile.storePokemonInBox(0, 3, newBoxPokemon);

        SaveFile saveFile2 = new SaveFile(resourcesPath + "copy.sav");

        Pokemon storedPokemon = saveFile2.getBoxPokemon(0, 3);

        //checkPartyPokemon(storedPokemon);
        
        assertEquals(newBoxPokemon, storedPokemon);
    }
    
    @Test
    public void westernCurrentBoxPokemonWritingTest() throws IOException{
        Files.copy(Paths.get(resourcesPath + "save.sav"), Paths.get(resourcesPath + "copy.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        SaveFile saveFile = new SaveFile(resourcesPath + "copy.sav");

        Pokemon newBoxPokemon = new Pokemon(PokemonTest.partyPokemonData(), new byte[]{(byte)0x80},
                new byte[]{
                        (byte) 0x8B,
                        (byte) 0x84,
                        (byte) 0x80,
                        (byte) 0x85,
                }, Language.WESTERN);

        saveFile.storePokemonInBox(1, 19, newBoxPokemon);

        SaveFile saveFile2 = new SaveFile(resourcesPath + "copy.sav");

        Pokemon storedPokemon = saveFile2.getBoxPokemon(1, 19);

        //checkPartyPokemon(storedPokemon);
        
        assertEquals(newBoxPokemon, storedPokemon);
    }
    
    private void checkPartyPokemon(Pokemon pokemon){
        assertEquals(0x9A, pokemon.getIndexNumber());
        assertEquals(Specie.VENUSAUR, pokemon.getSpecie());
        assertEquals(81, pokemon.getCurrentHp());
        assertEquals(32, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(22, pokemon.getTypes()[0]);
        assertEquals(3, pokemon.getTypes()[1]);
        assertEquals(45, pokemon.getCatchRate());
        assertEquals(75, pokemon.getMoves()[0]);
        assertEquals(45, pokemon.getMoves()[1]);
        assertEquals(73, pokemon.getMoves()[2]);
        assertEquals(22, pokemon.getMoves()[3]);
        assertEquals(4618, pokemon.getTrainerId());
        assertEquals(27021, pokemon.getExp());
        assertEquals(109, pokemon.getEvs()[0]);
        assertEquals(153, pokemon.getEvs()[1]);
        assertEquals(118, pokemon.getEvs()[2]);
        assertEquals(193, pokemon.getEvs()[3]);
        assertEquals(110, pokemon.getEvs()[4]);

        assertEquals(13, pokemon.getIvs()[0]);
        assertEquals(9, pokemon.getIvs()[1]);
        assertEquals(7, pokemon.getIvs()[2]);
        assertEquals(6, pokemon.getIvs()[3]);
        assertEquals(5, pokemon.getIvs()[4]);

        assertEquals(25, pokemon.getMovesPps()[0]);
        assertEquals(40, pokemon.getMovesPps()[1]);
        assertEquals(10, pokemon.getMovesPps()[2]);
        assertEquals(10, pokemon.getMovesPps()[3]);

        assertEquals(102, pokemon.getStats()[0]);
        assertEquals(64, pokemon.getStats()[1]);
        assertEquals(63, pokemon.getStats()[2]);
        assertEquals(61, pokemon.getStats()[3]);
        assertEquals(72, pokemon.getStats()[4]);

        assertEquals("PtJean", pokemon.getTrainerName().toString());
        assertEquals("FLORIZARRE", pokemon.getNickname().toString());
    }

    private void checkBoxPokemon(Pokemon pokemon){
        assertEquals(0x84, pokemon.getIndexNumber());
        assertEquals(Specie.SNORLAX, pokemon.getSpecie());
        assertEquals(208, pokemon.getCurrentHp());
        assertEquals(45, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(0, pokemon.getTypes()[0]);
        assertEquals(0, pokemon.getTypes()[1]);
        assertEquals(200, pokemon.getCatchRate());
        assertEquals(34, pokemon.getMoves()[0]);
        assertEquals(156, pokemon.getMoves()[1]);
        assertEquals(133, pokemon.getMoves()[2]);
        assertEquals(38, pokemon.getMoves()[3]);
        assertEquals(19076, pokemon.getTrainerId());
        assertEquals(113906, pokemon.getExp());
        assertEquals(117, pokemon.getEvs()[0]);
        assertEquals(134, pokemon.getEvs()[1]);
        assertEquals(187, pokemon.getEvs()[2]);
        assertEquals(69, pokemon.getEvs()[3]);
        assertEquals(67, pokemon.getEvs()[4]);

        assertEquals(10, pokemon.getIvs()[0]);
        assertEquals(1, pokemon.getIvs()[1]);
        assertEquals(4, pokemon.getIvs()[2]);
        assertEquals(15, pokemon.getIvs()[3]);
        assertEquals(12, pokemon.getIvs()[4]);

        assertEquals(15, pokemon.getMovesPps()[0]);
        assertEquals(10, pokemon.getMovesPps()[1]);
        assertEquals(20, pokemon.getMovesPps()[2]);
        assertEquals(15, pokemon.getMovesPps()[3]);

        assertEquals("PtJean", pokemon.getTrainerName().toString());
        assertEquals("SNORLAX", pokemon.getNickname().toString());
    }

    private void checkBoxPokemon2(Pokemon pokemon){
        assertEquals(0x42, pokemon.getIndexNumber());
        assertEquals(Specie.DRAGONITE, pokemon.getSpecie());
        assertEquals(179, pokemon.getCurrentHp());
        assertEquals(55, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(26, pokemon.getTypes()[0]);
        assertEquals(2, pokemon.getTypes()[1]);
        assertEquals(45, pokemon.getCatchRate());
        assertEquals(21, pokemon.getMoves()[0]);
        assertEquals(63, pokemon.getMoves()[1]);
        assertEquals(86, pokemon.getMoves()[2]);
        assertEquals(97, pokemon.getMoves()[3]);
        assertEquals(19076, pokemon.getTrainerId());
        assertEquals(207968, pokemon.getExp());
        assertEquals(0, pokemon.getEvs()[0]);
        assertEquals(0, pokemon.getEvs()[1]);
        assertEquals(0, pokemon.getEvs()[2]);
        assertEquals(0, pokemon.getEvs()[3]);
        assertEquals(0, pokemon.getEvs()[4]);

        assertEquals(13, pokemon.getIvs()[0]);
        assertEquals(13, pokemon.getIvs()[1]);
        assertEquals(11, pokemon.getIvs()[2]);
        assertEquals(6, pokemon.getIvs()[3]);
        assertEquals(7, pokemon.getIvs()[4]);

        assertEquals(20, pokemon.getMovesPps()[0]);
        assertEquals(5, pokemon.getMovesPps()[1]);
        assertEquals(20, pokemon.getMovesPps()[2]);
        assertEquals(30, pokemon.getMovesPps()[3]);

        assertEquals("PtJean", pokemon.getTrainerName().toString());
        assertEquals("DRACOLOSSE", pokemon.getNickname().toString());
    }

    private void checkCurrentBoxPokemon(Pokemon pokemon){
        assertEquals(118, pokemon.getIndexNumber());
        assertEquals(Specie.DUGTRIO, pokemon.getSpecie());
        assertEquals(54, pokemon.getCurrentHp());
        assertEquals(26, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(4, pokemon.getTypes()[0]);
        assertEquals(4, pokemon.getTypes()[1]);
        assertEquals(255, pokemon.getCatchRate());
        assertEquals(10, pokemon.getMoves()[0]);
        assertEquals(45, pokemon.getMoves()[1]);
        assertEquals(91, pokemon.getMoves()[2]);
        assertEquals(28, pokemon.getMoves()[3]);
        assertEquals(19076, pokemon.getTrainerId());
        assertEquals(17576, pokemon.getExp());
        assertEquals(0, pokemon.getEvs()[0]);
        assertEquals(0, pokemon.getEvs()[1]);
        assertEquals(0, pokemon.getEvs()[2]);
        assertEquals(0, pokemon.getEvs()[3]);
        assertEquals(0, pokemon.getEvs()[4]);

        assertEquals(0, pokemon.getIvs()[0]);
        assertEquals(0, pokemon.getIvs()[1]);
        assertEquals(0, pokemon.getIvs()[2]);
        assertEquals(4, pokemon.getIvs()[3]);
        assertEquals(6, pokemon.getIvs()[4]);

        assertEquals(35, pokemon.getMovesPps()[0]);
        assertEquals(40, pokemon.getMovesPps()[1]);
        assertEquals(10, pokemon.getMovesPps()[2]);
        assertEquals(15, pokemon.getMovesPps()[3]);

        assertEquals("PtJean", pokemon.getTrainerName().toString());
        assertEquals("TRIOPIKEUR", pokemon.getNickname().toString());
    }
    
    private void checkJapanesePartyPokemon(Pokemon pokemon) {
        assertEquals(178, pokemon.getIndexNumber());
        assertEquals(Specie.CHARMELEON, pokemon.getSpecie());
        assertEquals(96, pokemon.getCurrentHp());
        assertEquals(33, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(20, pokemon.getTypes()[0]);
        assertEquals(20, pokemon.getTypes()[1]);
        assertEquals(45, pokemon.getCatchRate());
        assertEquals(10, pokemon.getMoves()[0]);
        assertEquals(99, pokemon.getMoves()[1]);
        assertEquals(52, pokemon.getMoves()[2]);
        assertEquals(163, pokemon.getMoves()[3]);
        assertEquals(19983, pokemon.getTrainerId());
        assertEquals(31550, pokemon.getExp());
        assertEquals(8700, pokemon.getEvs()[0]);
        assertEquals(10138, pokemon.getEvs()[1]);
        assertEquals(9538, pokemon.getEvs()[2]);
        assertEquals(11027, pokemon.getEvs()[3]);
        assertEquals(8196, pokemon.getEvs()[4]);

        assertEquals(12, pokemon.getIvs()[0]);
        assertEquals(1, pokemon.getIvs()[1]);
        assertEquals(3, pokemon.getIvs()[2]);
        assertEquals(6, pokemon.getIvs()[3]);
        assertEquals(6, pokemon.getIvs()[4]);

        assertEquals(35, pokemon.getMovesPps()[0]);
        assertEquals(20, pokemon.getMovesPps()[1]);
        assertEquals(25, pokemon.getMovesPps()[2]);
        assertEquals(20, pokemon.getMovesPps()[3]);

        assertEquals(96, pokemon.getStats()[0]);
        assertEquals(56, pokemon.getStats()[1]);
        assertEquals(53, pokemon.getStats()[2]);
        assertEquals(70, pokemon.getStats()[3]);
        assertEquals(59, pokemon.getStats()[4]);

        assertEquals("ハケッ", pokemon.getTrainerName().toString());
        assertEquals("カキ", pokemon.getNickname().toString());
    }

    private void checkJapaneseCurrentBoxPokemon(Pokemon pokemon){
        assertEquals(21, pokemon.getIndexNumber());
        assertEquals(Specie.MEW, pokemon.getSpecie());
        assertEquals(179, pokemon.getCurrentHp());
        assertEquals(50, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(24, pokemon.getTypes()[0]);
        assertEquals(24, pokemon.getTypes()[1]);
        assertEquals(0, pokemon.getCatchRate());
        assertEquals(1, pokemon.getMoves()[0]);
        assertEquals(144, pokemon.getMoves()[1]);
        assertEquals(63, pokemon.getMoves()[2]);
        assertEquals(14, pokemon.getMoves()[3]);
        assertEquals(19983, pokemon.getTrainerId());
        assertEquals(117360, pokemon.getExp());
        assertEquals(20236, pokemon.getEvs()[0]);
        assertEquals(52180, pokemon.getEvs()[1]);
        assertEquals(45750, pokemon.getEvs()[2]);
        assertEquals(60394, pokemon.getEvs()[3]);
        assertEquals(25231, pokemon.getEvs()[4]);

        assertEquals(2, pokemon.getIvs()[0]);
        assertEquals(8, pokemon.getIvs()[1]);
        assertEquals(8, pokemon.getIvs()[2]);
        assertEquals(3, pokemon.getIvs()[3]);
        assertEquals(12, pokemon.getIvs()[4]);

        assertEquals(35, pokemon.getMovesPps()[0]);
        assertEquals(10, pokemon.getMovesPps()[1]);
        assertEquals(5, pokemon.getMovesPps()[2]);
        assertEquals(30, pokemon.getMovesPps()[3]);

        assertEquals("ハケッ", pokemon.getTrainerName().toString());
        assertEquals("ミュウ", pokemon.getNickname().toString());
    }
    
    private void checkJapaneseBoxPokemon(Pokemon pokemon) {
        assertEquals(109, pokemon.getIndexNumber());
        assertEquals(Specie.PARAS, pokemon.getSpecie());
        assertEquals(24, pokemon.getCurrentHp());
        assertEquals(8, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(7, pokemon.getTypes()[0]);
        assertEquals(22, pokemon.getTypes()[1]);
        assertEquals(190, pokemon.getCatchRate());
        assertEquals(10, pokemon.getMoves()[0]);
        assertEquals(0, pokemon.getMoves()[1]);
        assertEquals(0, pokemon.getMoves()[2]);
        assertEquals(0, pokemon.getMoves()[3]);
        assertEquals(19983, pokemon.getTrainerId());
        assertEquals(512, pokemon.getExp());
        assertEquals(0, pokemon.getEvs()[0]);
        assertEquals(0, pokemon.getEvs()[1]);
        assertEquals(0, pokemon.getEvs()[2]);
        assertEquals(0, pokemon.getEvs()[3]);
        assertEquals(0, pokemon.getEvs()[4]);

        assertEquals(8, pokemon.getIvs()[0]);
        assertEquals(1, pokemon.getIvs()[1]);
        assertEquals(14, pokemon.getIvs()[2]);
        assertEquals(10, pokemon.getIvs()[3]);
        assertEquals(14, pokemon.getIvs()[4]);

        assertEquals(35, pokemon.getMovesPps()[0]);
        assertEquals(0, pokemon.getMovesPps()[1]);
        assertEquals(0, pokemon.getMovesPps()[2]);
        assertEquals(0, pokemon.getMovesPps()[3]);

        assertEquals("ハケッ", pokemon.getTrainerName().toString());
        assertEquals("パラス", pokemon.getNickname().toString());
    }

    private void checkStoredWesternPartyPokemon(Pokemon pokemon){
        assertEquals(0x84, pokemon.getIndexNumber());
        assertEquals(Specie.SNORLAX, pokemon.getSpecie());
        assertEquals(208, pokemon.getCurrentHp());
        assertEquals(45, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(0, pokemon.getTypes()[0]);
        assertEquals(0, pokemon.getTypes()[1]);
        assertEquals(200, pokemon.getCatchRate());
        assertEquals(34, pokemon.getMoves()[0]);
        assertEquals(156, pokemon.getMoves()[1]);
        assertEquals(133, pokemon.getMoves()[2]);
        assertEquals(38, pokemon.getMoves()[3]);
        assertEquals(19076, pokemon.getTrainerId());
        assertEquals(113906, pokemon.getExp());
        assertEquals(200, pokemon.getEvs()[0]);
        assertEquals(300, pokemon.getEvs()[1]);
        assertEquals(400, pokemon.getEvs()[2]);
        assertEquals(600, pokemon.getEvs()[3]);
        assertEquals(500, pokemon.getEvs()[4]);

        assertEquals(10, pokemon.getIvs()[0]);
        assertEquals(1, pokemon.getIvs()[1]);
        assertEquals(4, pokemon.getIvs()[2]);
        assertEquals(15, pokemon.getIvs()[3]);
        assertEquals(12, pokemon.getIvs()[4]);

        assertEquals(15, pokemon.getMovesPps()[0]);
        assertEquals(10, pokemon.getMovesPps()[1]);
        assertEquals(20, pokemon.getMovesPps()[2]);
        assertEquals(15, pokemon.getMovesPps()[3]);
    }
}
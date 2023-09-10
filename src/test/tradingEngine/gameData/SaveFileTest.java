package test.tradingEngine.gameData;

import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.SaveFile;
import main.java.tradingEngine.gameData.Specie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveFileTest {
    private static final String resourcesPath = "src/main/resources/tradingEngine/gameData/";
    @Test
    void testConstructor() throws IOException {
        Files.copy(Path.of(resourcesPath + "save.sav"), Path.of(resourcesPath + "copy.sav"),
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
    }
}
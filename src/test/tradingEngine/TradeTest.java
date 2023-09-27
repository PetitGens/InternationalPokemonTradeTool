package test.tradingEngine;

import main.java.tradingEngine.Trade;
import main.java.tradingEngine.gameData.Language;
import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.SaveFile;
import main.java.tradingEngine.gameData.Specie;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.*;

public class TradeTest {

    private static final String resourcesPath = "src/main/resources/tradingEngine/";

    @Test
    public void tradeTest() throws IOException {
        Path baseSavePath = Paths.get(resourcesPath + "save.sav");
        Files.copy(baseSavePath, Paths.get(resourcesPath + "copy.sav"),
                StandardCopyOption.REPLACE_EXISTING);
        Files.copy(baseSavePath, Paths.get(resourcesPath + "copy2.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        Trade trade = new Trade();
        trade.openSaveFile(0, resourcesPath + "copy.sav");
        trade.openSaveFile(1, resourcesPath + "copy2.sav");

        assertThrows(IllegalArgumentException.class, () -> trade.getSaveFile(2));
        assertThrows(IllegalArgumentException.class, () -> trade.openSaveFile(2, resourcesPath + "copy.sav"));

        trade.trade(true, 1, 0, true, 0, 0);

        SaveFile saveFile1 = trade.getSaveFile(0);
        SaveFile saveFile2 = trade.getSaveFile(1);

        Pokemon tradedPokemon1 = saveFile1.getPartyPokemon(1);
        Pokemon tradedPokemon2 = saveFile2.getPartyPokemon(0);
        checkWesternPartyPokemon1(tradedPokemon1);
        checkWesternPartyPokemon2(tradedPokemon2);
    }

    @Test
    public void internationalTradeTest() throws IOException {
        Files.copy(Paths.get(resourcesPath + "save.sav"), Paths.get(resourcesPath + "copy.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        Files.copy(Paths.get(resourcesPath + "jap.sav"), Paths.get(resourcesPath + "copy_jap.sav"),
                StandardCopyOption.REPLACE_EXISTING);

        Trade trade = new Trade();
        trade.openSaveFile(0, resourcesPath + "copy.sav");
        trade.openSaveFile(1, resourcesPath + "copy_jap.sav");

        SaveFile saveFile1 = trade.getSaveFile(0);
        SaveFile saveFile2 = trade.getSaveFile(1);
        saveFile1.setLanguage(Language.FRENCH);

        trade.trade(true, 0, 0,
                false, 2, 0);

        Pokemon tradedPokemon1 = saveFile1.getPartyPokemon(0);
        Pokemon tradedPokemon2 = saveFile2.getBoxPokemon(0, 2);
        checkWesternPartyPokemon3(tradedPokemon1);
        checkJapaneseBoxPokemon(tradedPokemon2);
    }

    void checkWesternPartyPokemon1(Pokemon pokemon){
        assertEquals(Specie.HYPNO, pokemon.getSpecie());
        assertEquals(83, pokemon.getCurrentHp());
        assertEquals(26, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(190, pokemon.getCatchRate());
        assertEquals(19076, pokemon.getTrainerId());
        assertEquals(17576, pokemon.getExp());
        assertEquals("PtJean", pokemon.getTrainerName().toString());
        assertEquals("HYPNOMADE", pokemon.getNickname().toString());
    }
    void checkWesternPartyPokemon2(Pokemon pokemon){
        assertEquals(Specie.VAPOREON, pokemon.getSpecie());
        assertEquals(207, pokemon.getCurrentHp());
        assertEquals(50, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(45, pokemon.getCatchRate());
        assertEquals(19076, pokemon.getTrainerId());
        assertEquals(125616, pokemon.getExp());
        assertEquals("PtJean", pokemon.getTrainerName().toString());
        assertEquals("AQUALI", pokemon.getNickname().toString());
    }

    void checkWesternPartyPokemon3(Pokemon pokemon){
        assertEquals(Specie.PARAS, pokemon.getSpecie());
        assertEquals(8, pokemon.getLevel());
        assertEquals(190, pokemon.getCatchRate());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(7, pokemon.getTypes()[0]);
        assertEquals(22, pokemon.getTypes()[1]);
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
        assertEquals(24, pokemon.getCurrentHp());
        assertEquals(24, pokemon.getStats()[0]);
        assertEquals(16, pokemon.getStats()[1]);
        assertEquals(16, pokemon.getStats()[2]);
        assertEquals(10, pokemon.getStats()[3]);
        assertEquals(16, pokemon.getStats()[4]);
        assertEquals("PARAS", pokemon.getNickname().toString());
    }

    void checkJapaneseBoxPokemon(Pokemon pokemon){
        assertEquals(Specie.HYPNO, pokemon.getSpecie());
        assertEquals(83, pokemon.getCurrentHp());
        assertEquals(26, pokemon.getLevel());
        assertEquals(0, pokemon.getStatusCondition());
        assertEquals(24, pokemon.getTypes()[0]);
        assertEquals(24, pokemon.getTypes()[1]);
        assertEquals(29, pokemon.getMoves()[0]);
        assertEquals(95, pokemon.getMoves()[1]);
        assertEquals(50, pokemon.getMoves()[2]);
        assertEquals(93, pokemon.getMoves()[3]);
        assertEquals(190, pokemon.getCatchRate());
        assertEquals(19076, pokemon.getTrainerId());
        assertEquals(17576, pokemon.getExp());
        assertEquals(0, pokemon.getEvs()[0]);
        assertEquals(0, pokemon.getEvs()[1]);
        assertEquals(0, pokemon.getEvs()[2]);
        assertEquals(0, pokemon.getEvs()[3]);
        assertEquals(0, pokemon.getEvs()[4]);
        assertEquals(6, pokemon.getIvs()[0]);
        assertEquals(14, pokemon.getIvs()[1]);
        assertEquals(1, pokemon.getIvs()[2]);
        assertEquals(13, pokemon.getIvs()[3]);
        assertEquals(8, pokemon.getIvs()[4]);
        assertEquals(15, pokemon.getMovesPps()[0]);
        assertEquals(20, pokemon.getMovesPps()[1]);
        assertEquals(20, pokemon.getMovesPps()[2]);
        assertEquals(25, pokemon.getMovesPps()[3]);
        assertEquals(83, pokemon.getStats()[0]);
        assertEquals(50, pokemon.getStats()[1]);
        assertEquals(41, pokemon.getStats()[2]);
        assertEquals(46, pokemon.getStats()[3]);
        assertEquals(68, pokemon.getStats()[4]);
        assertEquals("スリーパー", pokemon.getNickname().toString());
    }
}

package test.tradingEngine;

import main.java.tradingEngine.Trade;
import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.SaveFile;
import main.java.tradingEngine.gameData.Specie;
import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.*;

public class TradeTest {

    private static final String resourcesPath = "src/main/resources/tradingEngine/";

    @Test
    public void tradeTest() throws IOException {
        Path baseSavePath = Path.of(resourcesPath + "save.sav");
        Files.copy(baseSavePath, Path.of(resourcesPath + "copy.sav"),
                StandardCopyOption.REPLACE_EXISTING);
        Files.copy(baseSavePath, Path.of(resourcesPath + "copy2.sav"),
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
}

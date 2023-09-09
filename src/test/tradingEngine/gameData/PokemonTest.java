package test.tradingEngine.gameData;

import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.Specie;
import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void constructorTest(){
        byte[] data = partyPokemonData();

        byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

        Pokemon partyPokemon = new Pokemon(data, nickname, nickname, false);

        assertEquals(0x9A, partyPokemon.getIndexNumber());
        assertEquals(Specie.VENUSAUR, partyPokemon.getSpecie());
        assertEquals(81, partyPokemon.getCurrentHp());
        assertEquals(32, partyPokemon.getLevel());
        assertEquals(0, partyPokemon.getStatusCondition());
        assertEquals(22, partyPokemon.getTypes()[0]);
        assertEquals(3, partyPokemon.getTypes()[1]);
        assertEquals(45, partyPokemon.getCatchRate());
        assertEquals(75, partyPokemon.getMoves()[0]);
        assertEquals(45, partyPokemon.getMoves()[1]);
        assertEquals(73, partyPokemon.getMoves()[2]);
        assertEquals(22, partyPokemon.getMoves()[3]);
        assertEquals(4618, partyPokemon.getTrainerId());
        assertEquals(27021, partyPokemon.getExp());
        assertEquals(109, partyPokemon.getEvs()[0]);
        assertEquals(153, partyPokemon.getEvs()[1]);
        assertEquals(118, partyPokemon.getEvs()[2]);
        assertEquals(193, partyPokemon.getEvs()[3]);
        assertEquals(110, partyPokemon.getEvs()[4]);

        // TODO test IVs and PPs values

        assertEquals(102, partyPokemon.getStats()[0]);
        assertEquals(64, partyPokemon.getStats()[1]);
        assertEquals(63, partyPokemon.getStats()[2]);
        assertEquals(61, partyPokemon.getStats()[3]);
        assertEquals(72, partyPokemon.getStats()[4]);
    }

    byte[] partyPokemonData(){
        return new byte[]{
                (byte) 0x9A,
                (byte) 0x00,
                (byte) 0x51,
                (byte) 0x07,
                (byte) 0x00,
                (byte) 0x16,
                (byte) 0x03,
                (byte) 0x2D,
                (byte) 0x4B,
                (byte) 0x2D,
                (byte) 0x49,
                (byte) 0x16,
                (byte) 0x12,
                (byte) 0x0A,
                (byte) 0x00,
                (byte) 0x69,
                (byte) 0x8D,
                (byte) 0x00,
                (byte) 0x6D,
                (byte) 0x00,
                (byte) 0x99,
                (byte) 0x00,
                (byte) 0x76,
                (byte) 0x00,
                (byte) 0xC1,
                (byte) 0x00,
                (byte) 0x6E,
                (byte) 0x97,
                (byte) 0x65,
                (byte) 0x19,
                (byte) 0x28,
                (byte) 0x0A,
                (byte) 0x0A,
                (byte) 0x20,
                (byte) 0x00,
                (byte) 0x66,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x3F,
                (byte) 0x00,
                (byte) 0x3D,
                (byte) 0x00,
                (byte) 0x48
        };
    }
}
package test.tradingEngine.gameData;

import main.java.tradingEngine.gameData.Language;
import main.java.tradingEngine.gameData.Pokemon;
import main.java.tradingEngine.gameData.Specie;
import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PokemonTest {

    @Test
    public void inPartyConstructorTest(){
        byte[] data = partyPokemonData();

        byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

        Pokemon partyPokemon = new Pokemon(data, nickname, nickname, Language.WESTERN);

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

        assertEquals(13, partyPokemon.getIvs()[0]);
        assertEquals(9, partyPokemon.getIvs()[1]);
        assertEquals(7, partyPokemon.getIvs()[2]);
        assertEquals(6, partyPokemon.getIvs()[3]);
        assertEquals(5, partyPokemon.getIvs()[4]);

        assertEquals(25, partyPokemon.getMovesPps()[0]);
        assertEquals(40, partyPokemon.getMovesPps()[1]);
        assertEquals(10, partyPokemon.getMovesPps()[2]);
        assertEquals(10, partyPokemon.getMovesPps()[3]);

        assertEquals(102, partyPokemon.getStats()[0]);
        assertEquals(64, partyPokemon.getStats()[1]);
        assertEquals(63, partyPokemon.getStats()[2]);
        assertEquals(61, partyPokemon.getStats()[3]);
        assertEquals(72, partyPokemon.getStats()[4]);
    }

    @Test
    public void inBoxConstructorTest(){
        byte[] data = boxPokemonData();

        byte[] nickname = {InGameWesternCharacter.CAPITAL_A.value};

        Pokemon boxPokemon = new Pokemon(data, nickname, nickname, Language.WESTERN);

        assertEquals(0x84, boxPokemon.getIndexNumber());
        assertEquals(Specie.SNORLAX, boxPokemon.getSpecie());
        assertEquals(208, boxPokemon.getCurrentHp());
        assertEquals(45, boxPokemon.getLevel());
        assertEquals(0, boxPokemon.getStatusCondition());
        assertEquals(0, boxPokemon.getTypes()[0]);
        assertEquals(0, boxPokemon.getTypes()[1]);
        assertEquals(200, boxPokemon.getCatchRate());
        assertEquals(34, boxPokemon.getMoves()[0]);
        assertEquals(156, boxPokemon.getMoves()[1]);
        assertEquals(133, boxPokemon.getMoves()[2]);
        assertEquals(38, boxPokemon.getMoves()[3]);
        assertEquals(19076, boxPokemon.getTrainerId());
        assertEquals(113906, boxPokemon.getExp());
        assertEquals(200, boxPokemon.getEvs()[0]);
        assertEquals(300, boxPokemon.getEvs()[1]);
        assertEquals(400, boxPokemon.getEvs()[2]);
        assertEquals(600, boxPokemon.getEvs()[3]);
        assertEquals(500, boxPokemon.getEvs()[4]);

        assertEquals(10, boxPokemon.getIvs()[0]);
        assertEquals(1, boxPokemon.getIvs()[1]);
        assertEquals(4, boxPokemon.getIvs()[2]);
        assertEquals(15, boxPokemon.getIvs()[3]);
        assertEquals(12, boxPokemon.getIvs()[4]);

        assertEquals(15, boxPokemon.getMovesPps()[0]);
        assertEquals(10, boxPokemon.getMovesPps()[1]);
        assertEquals(20, boxPokemon.getMovesPps()[2]);
        assertEquals(15, boxPokemon.getMovesPps()[3]);
    }

    @Test
    public void toPartyDataTest(){
        byte[] boxData = boxPokemonData();
        Pokemon snorlax = new Pokemon(boxData, new byte[]{InGameWesternCharacter.CAPITAL_A.value}, new byte[]{InGameWesternCharacter.CAPITAL_A.value}, Language.WESTERN);
        byte[] expectedData = new byte[44];
        System.arraycopy(boxData, 0, expectedData, 0, 33);

        expectedData[0x21] = 45;
        expectedData[0x23] = (byte)209;
        expectedData[0x25] = 106;
        expectedData[0x27] = 69;
        expectedData[0x29] = 48;
        expectedData[0x2B] = 76;
        
        byte[] actualData = snorlax.toPartyRawData();

        assertArrayEquals(expectedData, actualData);
    }

    @Test
    public void toBoxDataTest(){
        byte[] partyData = partyPokemonData();
        Pokemon venusaur = new Pokemon(partyData, new byte[]{InGameWesternCharacter.CAPITAL_A.value}, new byte[]{InGameWesternCharacter.CAPITAL_H.value}, Language.WESTERN);
        byte[] expectedData = new byte[33];
        System.arraycopy(partyData, 0, expectedData, 0, 33);

        expectedData[3] = 32;
        assertArrayEquals(expectedData, venusaur.toBoxRawData());
    }

    public static byte[] partyPokemonData(){
        return new byte[]{
                (byte) 0x9A,
                (byte) 0x00,
                (byte) 0x51,
                (byte) 0x00,
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

    public static byte[] boxPokemonData(){
        return new byte[]{
                (byte) 0x84,
                (byte) 0x00,
                (byte) 0xD0,
                (byte) 0x2D,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0xC8,
                (byte) 0x22,
                (byte) 0x9C,
                (byte) 0x85,
                (byte) 0x26,
                (byte) 0x4A,
                (byte) 0x84,
                (byte) 0x01,
                (byte) 0xBC,
                (byte) 0xF2,
                (byte) 0x00,
                (byte) 0xC8,
                (byte) 0x01,
                (byte) 0x2C,
                (byte) 0x01,
                (byte) 0x90,
                (byte) 0x02,
                (byte) 0x58,
                (byte) 0x01,
                (byte) 0xF4,
                (byte) 0x14,
                (byte) 0xFC,
                (byte) 0x0F,
                (byte) 0x0A,
                (byte) 0x14,
                (byte) 0x0F
        };
    }
}
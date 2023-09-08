package test.tradingEngine.gameData.strings;

import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;
import main.java.tradingEngine.gameData.strings.WesternString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WesternStringTest {

    @Test
    void addTest(){
        WesternString westernString = new WesternString();
        westernString.add(InGameWesternCharacter.CAPITAL_A.value);

        assertEquals(InGameWesternCharacter.CAPITAL_A.value, westernString.get(0));
        assertEquals(InGameWesternCharacter.CAPITAL_A, InGameWesternCharacter.characterFromValue(westernString.get(0)));

        assertThrows(IllegalArgumentException.class, ()->westernString.add((byte)0x40));
    }

    @Test
    void isCharacterValidTest(){
        WesternString westernString = new WesternString();

        assertTrue(westernString.isCharacterValid(InGameWesternCharacter.CAPITAL_A.value));
        assertTrue(westernString.isCharacterValid((byte) 0x9E));

        for(InGameWesternCharacter westernCharacter : InGameWesternCharacter.values()){
            assertTrue(westernString.isCharacterValid(westernCharacter.value));
        }

        assertFalse(westernString.isCharacterValid((byte)0));
        assertFalse(westernString.isCharacterValid((byte)255));
        assertFalse(westernString.isCharacterValid((byte)0x42));
        assertFalse(westernString.isCharacterValid((byte)0x36));
    }

    @Test
    void addAll(){
        byte[] validArray = {
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_B.value,
                InGameWesternCharacter.CAPITAL_C.value,
                InGameWesternCharacter.CAPITAL_D.value,
                InGameWesternCharacter.CAPITAL_E.value,
                InGameWesternCharacter.CAPITAL_F.value,
                InGameWesternCharacter.CAPITAL_G.value,
                InGameWesternCharacter.CAPITAL_H.value,
                InGameWesternCharacter.CAPITAL_I.value,
                InGameWesternCharacter.CAPITAL_J.value,
                InGameWesternCharacter.DELIMITER.value
        };

//        assertEquals(11, validArray.length);

        WesternString westernString = new WesternString();

        westernString.addAll(validArray);

        assertEquals(10, westernString.size());

        assertEquals(InGameWesternCharacter.CAPITAL_A.value, westernString.get(0));
        assertEquals(InGameWesternCharacter.CAPITAL_B.value, westernString.get(1));
        assertEquals(InGameWesternCharacter.CAPITAL_C.value, westernString.get(2));
        assertEquals(InGameWesternCharacter.CAPITAL_D.value, westernString.get(3));
        assertEquals(InGameWesternCharacter.CAPITAL_E.value, westernString.get(4));
        assertEquals(InGameWesternCharacter.CAPITAL_F.value, westernString.get(5));
        assertEquals(InGameWesternCharacter.CAPITAL_G.value, westernString.get(6));
        assertEquals(InGameWesternCharacter.CAPITAL_H.value, westernString.get(7));
        assertEquals(InGameWesternCharacter.CAPITAL_I.value, westernString.get(8));
        assertEquals(InGameWesternCharacter.CAPITAL_J.value, westernString.get(9));

        validArray[10] = InGameWesternCharacter.CAPITAL_K.value;

        westernString = new WesternString();
        westernString.addAll(validArray);
        assertEquals(10, westernString.size());

        byte[] smallArray = {InGameWesternCharacter.CAPITAL_A.value};
        westernString.addAll(smallArray);
        assertEquals(1, westernString.size());

        int[] invalidArrayInt = {0x80, 0xb0, 0xff};

        byte[] invalidArray = new byte[3];

        for(int i = 0; i < invalidArrayInt.length; i++){
            invalidArray[i] = (byte) invalidArrayInt[i];
        }

        WesternString finalString = westernString;

        assertThrows(IllegalArgumentException.class, ()->finalString.addAll(invalidArray));

        byte[] tooBigArray = {
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_A.value
        };
//        assertEquals(12, tooBigArray.length);

        assertThrows(IllegalArgumentException.class, ()->finalString.addAll(tooBigArray));
    }

    @Test
    void removeTest(){
        byte[] validArray = {
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_B.value,
                InGameWesternCharacter.CAPITAL_C.value,
                InGameWesternCharacter.CAPITAL_D.value,
                InGameWesternCharacter.CAPITAL_E.value,
                InGameWesternCharacter.CAPITAL_F.value,
                InGameWesternCharacter.CAPITAL_G.value,
                InGameWesternCharacter.CAPITAL_H.value,
                InGameWesternCharacter.CAPITAL_I.value,
                InGameWesternCharacter.CAPITAL_J.value,
                InGameWesternCharacter.DELIMITER.value
        };
        WesternString westernString = new WesternString();
        westernString.addAll(validArray);

        westernString.remove(9);
        assertEquals(9, westernString.size());
        assertEquals("ABCDEFGHI", westernString.toString());

        westernString.remove(0);
        assertEquals(8, westernString.size());
        assertEquals("BCDEFGHI", westernString.toString());

        westernString.remove(4);
        assertEquals(7, westernString.size());
        assertEquals("BCDEGHI", westernString.toString());

        assertThrows(IllegalArgumentException.class, ()->westernString.remove(westernString.size()));
    }

    @Test
    void setTest(){
        byte[] validArray = {
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_B.value,
                InGameWesternCharacter.CAPITAL_C.value,
                InGameWesternCharacter.CAPITAL_D.value,
                InGameWesternCharacter.CAPITAL_E.value,
                InGameWesternCharacter.CAPITAL_F.value,
                InGameWesternCharacter.CAPITAL_G.value,
                InGameWesternCharacter.CAPITAL_H.value,
                InGameWesternCharacter.CAPITAL_I.value,
                InGameWesternCharacter.CAPITAL_J.value,
                InGameWesternCharacter.DELIMITER.value
        };

        WesternString westernString = new WesternString();
        westernString.addAll(validArray);

        assertEquals(10, westernString.size());

        westernString.set(InGameWesternCharacter.CAPITAL_Z.value, 0);
        assertEquals(InGameWesternCharacter.CAPITAL_Z.value, westernString.get(0));

        westernString.set(InGameWesternCharacter.CAPITAL_U.value, 5);
        assertEquals(InGameWesternCharacter.CAPITAL_U.value, westernString.get(5));

        westernString.set(InGameWesternCharacter.CAPITAL_A.value, 9);
        assertEquals(InGameWesternCharacter.CAPITAL_A.value, westernString.get(9));

        assertThrows(IllegalArgumentException.class, ()->westernString.set((byte)0, 0));

        assertThrows(IllegalArgumentException.class, ()->westernString.set((byte)0x80, 10));
    }

    @Test
    void toStringTest(){
        WesternString machopName = new WesternString();
        byte[] machopCharacters = {
                InGameWesternCharacter.CAPITAL_M.value,
                InGameWesternCharacter.CAPITAL_A.value,
                InGameWesternCharacter.CAPITAL_C.value,
                InGameWesternCharacter.CAPITAL_H.value,
                InGameWesternCharacter.CAPITAL_O.value,
                InGameWesternCharacter.CAPITAL_P.value,
        };
        machopName.addAll(machopCharacters);

        assertEquals("MACHOP", machopName.toString());

        WesternString golemName = new WesternString();
        byte[] golemCharacters = {
                (byte) 0x86,
                (byte) 0x8E,
                (byte) 0x8B,
                (byte) 0x84,
                (byte) 0x8C
        };
        golemName.addAll(golemCharacters);

        assertEquals("GOLEM", golemName.toString());

        WesternString hypnoName = new WesternString();
        // Writing Hypno's French name here because I want to test a long name
        byte[] hypnoCharacters = {
                (byte) 0x87,
                (byte) 0x98,
                (byte) 0x8F,
                (byte) 0x8D,
                (byte) 0x8E,
                (byte) 0x8C,
                (byte) 0x80,
                (byte) 0x83,
                (byte) 0x84
        };
        hypnoName.addAll(hypnoCharacters);

        assertEquals("HYPNOMADE", hypnoName.toString());
    }
}
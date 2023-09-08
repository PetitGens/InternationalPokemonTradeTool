package test.tradingEngine.gameData.strings;

import main.java.tradingEngine.gameData.strings.InGameJapaneseCharacter;
import main.java.tradingEngine.gameData.strings.JapaneseString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JapaneseStringTest {

    @Test
    void addTest() {
        JapaneseString japaneseString = new JapaneseString();
        japaneseString.add(InGameJapaneseCharacter.KATAKANA_A.value);

        assertEquals(InGameJapaneseCharacter.KATAKANA_A.value, japaneseString.get(0));
        assertEquals(InGameJapaneseCharacter.KATAKANA_A, InGameJapaneseCharacter.characterFromValue(japaneseString.get(0)));

        assertThrows(IllegalArgumentException.class, () -> japaneseString.add((byte) 0X51));
    }

    @Test
    void isCharacterValidTest() {
        JapaneseString japaneseString = new JapaneseString();

        assertTrue(japaneseString.isCharacterValid(InGameJapaneseCharacter.KATAKANA_A.value));
        assertTrue(japaneseString.isCharacterValid((byte) 0x9E));

        for (InGameJapaneseCharacter westernCharacter : InGameJapaneseCharacter.values()) {
            assertTrue(japaneseString.isCharacterValid(westernCharacter.value));
        }

        assertFalse(japaneseString.isCharacterValid((byte) 0));
        assertFalse(japaneseString.isCharacterValid((byte) 255));
        assertFalse(japaneseString.isCharacterValid((byte) 0X53));
        assertFalse(japaneseString.isCharacterValid((byte) 0x36));
    }

    @Test
    void addAll() {
        byte[] validArray = {
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_I.value,
                InGameJapaneseCharacter.KATAKANA_U.value,
                InGameJapaneseCharacter.KATAKANA_E.value,
                InGameJapaneseCharacter.KATAKANA_O.value,
                InGameJapaneseCharacter.KATAKANA_KA.value,
                InGameJapaneseCharacter.KATAKANA_KI.value,
                InGameJapaneseCharacter.KATAKANA_KU.value,
                InGameJapaneseCharacter.KATAKANA_KE.value,
                InGameJapaneseCharacter.KATAKANA_KO.value,
                InGameJapaneseCharacter.DELIMITER.value
        };

//        assertEquals(11, validArray.length);

        JapaneseString japaneseString = new JapaneseString();

        japaneseString.addAll(validArray);

        assertEquals(10, japaneseString.size());

        assertEquals(InGameJapaneseCharacter.KATAKANA_A.value, japaneseString.get(0));
        assertEquals(InGameJapaneseCharacter.KATAKANA_I.value, japaneseString.get(1));
        assertEquals(InGameJapaneseCharacter.KATAKANA_U.value, japaneseString.get(2));
        assertEquals(InGameJapaneseCharacter.KATAKANA_E.value, japaneseString.get(3));
        assertEquals(InGameJapaneseCharacter.KATAKANA_O.value, japaneseString.get(4));
        assertEquals(InGameJapaneseCharacter.KATAKANA_KA.value, japaneseString.get(5));
        assertEquals(InGameJapaneseCharacter.KATAKANA_KI.value, japaneseString.get(6));
        assertEquals(InGameJapaneseCharacter.KATAKANA_KU.value, japaneseString.get(7));
        assertEquals(InGameJapaneseCharacter.KATAKANA_KE.value, japaneseString.get(8));
        assertEquals(InGameJapaneseCharacter.KATAKANA_KO.value, japaneseString.get(9));

        validArray[10] = InGameJapaneseCharacter.KATAKANA_SA.value;

        japaneseString = new JapaneseString();
        japaneseString.addAll(validArray);
        assertEquals(10, japaneseString.size());

        byte[] smallArray = {InGameJapaneseCharacter.KATAKANA_A.value};
        japaneseString.addAll(smallArray);
        assertEquals(1, japaneseString.size());

        int[] invalidArrayInt = {0x80, 0x10, 0xff};

        byte[] invalidArray = new byte[3];

        for (int i = 0; i < invalidArrayInt.length; i++) {
            invalidArray[i] = (byte) invalidArrayInt[i];
        }

        JapaneseString finalString = japaneseString;

        assertThrows(IllegalArgumentException.class, () -> finalString.addAll(invalidArray));

        byte[] tooBigArray = {
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_A.value
        };
//        assertEquals(12, tooBigArray.length);

        assertThrows(IllegalArgumentException.class, () -> finalString.addAll(tooBigArray));
    }

    @Test
    void removeTest() {
        byte[] validArray = {
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_I.value,
                InGameJapaneseCharacter.KATAKANA_U.value,
                InGameJapaneseCharacter.KATAKANA_E.value,
                InGameJapaneseCharacter.KATAKANA_O.value,
                InGameJapaneseCharacter.KATAKANA_KA.value,
                InGameJapaneseCharacter.KATAKANA_KI.value,
                InGameJapaneseCharacter.KATAKANA_KU.value,
                InGameJapaneseCharacter.KATAKANA_KE.value,
                InGameJapaneseCharacter.KATAKANA_KO.value,
                InGameJapaneseCharacter.DELIMITER.value
        };
        JapaneseString japaneseString = new JapaneseString();
        japaneseString.addAll(validArray);

        japaneseString.remove(9);
        assertEquals(9, japaneseString.size());
        assertEquals("アイウエオカキクケ", japaneseString.toString());

        japaneseString.remove(0);
        assertEquals(8, japaneseString.size());
        assertEquals("イウエオカキクケ", japaneseString.toString());

        japaneseString.remove(4);
        assertEquals(7, japaneseString.size());
        assertEquals("イウエオキクケ", japaneseString.toString());

        assertThrows(IllegalArgumentException.class, () -> japaneseString.remove(japaneseString.size()));
    }

    @Test
    void setTest() {
        byte[] validArray = {
                InGameJapaneseCharacter.KATAKANA_A.value,
                InGameJapaneseCharacter.KATAKANA_I.value,
                InGameJapaneseCharacter.KATAKANA_U.value,
                InGameJapaneseCharacter.KATAKANA_E.value,
                InGameJapaneseCharacter.KATAKANA_O.value,
                InGameJapaneseCharacter.KATAKANA_KA.value,
                InGameJapaneseCharacter.KATAKANA_KI.value,
                InGameJapaneseCharacter.KATAKANA_KU.value,
                InGameJapaneseCharacter.KATAKANA_KE.value,
                InGameJapaneseCharacter.KATAKANA_KO.value,
                InGameJapaneseCharacter.DELIMITER.value
        };

        JapaneseString japaneseString = new JapaneseString();
        japaneseString.addAll(validArray);

        assertEquals(10, japaneseString.size());

        japaneseString.set(InGameJapaneseCharacter.HIRAGANA_N.value, 0);
        assertEquals(InGameJapaneseCharacter.HIRAGANA_N.value, japaneseString.get(0));

        japaneseString.set(InGameJapaneseCharacter.HIRAGANA_A.value, 5);
        assertEquals(InGameJapaneseCharacter.HIRAGANA_A.value, japaneseString.get(5));

        japaneseString.set(InGameJapaneseCharacter.KATAKANA_A.value, 9);
        assertEquals(InGameJapaneseCharacter.KATAKANA_A.value, japaneseString.get(9));

        assertThrows(IllegalArgumentException.class, () -> japaneseString.set((byte) 0, 0));

        assertThrows(IllegalArgumentException.class, () -> japaneseString.set((byte) 0x80, 10));
    }

    @Test
    void toStringTest() {
        JapaneseString meowthName = new JapaneseString();
        byte[] meowthCharacters = {
                InGameJapaneseCharacter.KATAKANA_NI.value,
                InGameJapaneseCharacter.KATAKANA_SMALL_YA.value,
                InGameJapaneseCharacter.CHOONPU.value,
                InGameJapaneseCharacter.KATAKANA_SU.value
        };
        meowthName.addAll(meowthCharacters);

        assertEquals("ニャース", meowthName.toString());

        JapaneseString dratiniName = new JapaneseString();
        byte[] dratiniCharacters = {
                (byte) 0x9E,
                (byte) 0x95,
                (byte) 0xD8,
                (byte) 0xAE,
                (byte) 0x82
        };
        dratiniName.addAll(dratiniCharacters);

        assertEquals("ミニリュウ", dratiniName.toString());

        JapaneseString goldeenName = new JapaneseString();
        byte[] goldeenCharacters = {
                (byte) 0x93,
                (byte) 0x8A,
                (byte) 0x86,
                (byte) 0xAB,
                (byte) 0x93
        };
        goldeenName.addAll(goldeenCharacters);

        assertEquals("トサキント", goldeenName.toString());
    }
}
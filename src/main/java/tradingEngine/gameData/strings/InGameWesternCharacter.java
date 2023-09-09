package main.java.tradingEngine.gameData.strings;

/**
 * An enumeration whose purpose is to convert the characters used in Western Pokémon games into characters that can be
 * used in Java strings.
 * value -> the numeric value corresponding to the character in the in-game encoding
 * character -> the character in its "regular" version.
 * @author Julien Ait azzouzene
 */
public enum InGameWesternCharacter {
    DELIMITER((byte) 0x50, '\0'),
    SPACE((byte) 0x7F, ' '),
    CAPITAL_A((byte) 0x80, 'A'),
    CAPITAL_B((byte) 0x81, 'B'),
    CAPITAL_C((byte) 0x82, 'C'),
    CAPITAL_D((byte) 0x83, 'D'),
    CAPITAL_E((byte) 0x84, 'E'),
    CAPITAL_F((byte) 0x85, 'F'),
    CAPITAL_G((byte) 0x86, 'G'),
    CAPITAL_H((byte) 0x87, 'H'),
    CAPITAL_I((byte) 0x88, 'I'),
    CAPITAL_J((byte) 0x89, 'J'),
    CAPITAL_K((byte) 0x8A, 'K'),
    CAPITAL_L((byte) 0x8B, 'L'),
    CAPITAL_M((byte) 0x8C, 'M'),
    CAPITAL_N((byte) 0x8D, 'N'),
    CAPITAL_O((byte) 0x8E, 'O'),
    CAPITAL_P((byte) 0x8F, 'P'),
    CAPITAL_Q((byte) 0x90, 'Q'),
    CAPITAL_R((byte) 0x91, 'R'),
    CAPITAL_S((byte) 0x92, 'S'),
    CAPITAL_T((byte) 0x93, 'T'),
    CAPITAL_U((byte) 0x94, 'U'),
    CAPITAL_V((byte) 0x95, 'V'),
    CAPITAL_W((byte) 0x96, 'W'),
    CAPITAL_X((byte) 0x97, 'X'),
    CAPITAL_Y((byte) 0x98, 'Y'),
    CAPITAL_Z((byte) 0x99, 'Z'),
    LEFT_PARENTHESIS((byte) 0x9A, '('),
    RIGHT_PARENTHESIS((byte) 0x9B, ')'),
    COLON((byte) 0x9C, ':'),
    SEMICOLON((byte) 0x9D, ';'),
    LEFT_BRACKET((byte) 0x9E, '['),
    RIGHT_BRACKET((byte) 0x9F, ']'),
    LOWER_A((byte) 0xA0, 'a'),
    LOWER_B((byte) 0xA1, 'b'),
    LOWER_C((byte) 0xA2, 'c'),
    LOWER_D((byte) 0xA3, 'd'),
    LOWER_E((byte) 0xA4, 'e'),
    LOWER_F((byte) 0xA5, 'f'),
    LOWER_G((byte) 0xA6, 'g'),
    LOWER_H((byte) 0xA7, 'h'),
    LOWER_I((byte) 0xA8, 'i'),
    LOWER_J((byte) 0xA9, 'j'),
    LOWER_K((byte) 0xAA, 'k'),
    LOWER_L((byte) 0xAB, 'l'),
    LOWER_M((byte) 0xAC, 'm'),
    LOWER_N((byte) 0xAD, 'n'),
    LOWER_O((byte) 0xAE, 'o'),
    LOWER_P((byte) 0xAF, 'p'),
    LOWER_Q((byte) 0xB0, 'q'),
    LOWER_R((byte) 0xB1, 'r'),
    LOWER_S((byte) 0xB2, 's'),
    LOWER_T((byte) 0xB3, 't'),
    LOWER_U((byte) 0xB4, 'u'),
    LOWER_V((byte) 0xB5, 'v'),
    LOWER_W((byte) 0xB6, 'w'),
    LOWER_X((byte) 0xB7, 'x'),
    LOWER_Y((byte) 0xB8, 'y'),
    LOWER_Z((byte) 0xB9, 'z'),
    PK((byte) 0xE1, '*'),
    MN((byte) 0xE2, '*'),
    DASH((byte) 0xE3, '-'),
    QUESTION_MARK((byte) 0xE6, '?'),
    EXCLAMATION_POINT((byte) 0xE7, '!'),
    POINT((byte) 0xE8, '.'),
    MALE((byte) 0xEF, '♂'),
    MULTIPLICATION_SIGN((byte) 0xF1, 'x'),
    POINT2((byte) 0xF2, '.'),
    SLASH((byte) 0xF3, '/'),
    COMMA((byte) 0xF4, ','),
    FEMALE((byte) 0xF5, '♀'),
    ;

    public final byte value;
    public final char character;

    InGameWesternCharacter(byte value, char character){
        this.value = value;
        this.character = character;
    }

    /**
     * Returns one of the enumeration's elements corresponding to a numeric value.
     * @param value -> the value of the wanted character in the in-game encoding
     * @return the corresponding enumeration element, null if none is found
     */
    public static InGameWesternCharacter characterFromValue(byte value){
        for(InGameWesternCharacter character : values()){
            if(character.value == value){
                return character;
            }
        }
        return null;
    }
}

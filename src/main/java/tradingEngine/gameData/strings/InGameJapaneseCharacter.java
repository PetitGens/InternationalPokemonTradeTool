package main.java.tradingEngine.gameData.strings;

/**
 * An enumeration whose purpose is to convert the characters used in Japanese Pokémon games into characters that can be
 * used in Java strings.
 * value -> the numeric value corresponding to the character in the in-game encoding.
 * character -> the character in its "regular" version.
 * @author Julien Ait azzouzene
 */
public enum InGameJapaneseCharacter {
    DELIMITER((byte) 0x50, '\0'),
    HIRAGANA_A((byte) 0xB1, 'あ'),
    HIRAGANA_I((byte) 0xB2, 'い'),
    HIRAGANA_U((byte) 0xB3, 'う'),
    HIRAGANA_E((byte) 0xB4, 'え'),
    HIRAGANA_O((byte) 0xB5, 'お'),
    HIRAGANA_KA((byte) 0xB6, 'か'),
    HIRAGANA_KI((byte) 0xB7, 'き'),
    HIRAGANA_KU((byte) 0xB8, 'く'),
    HIRAGANA_KE((byte) 0xB9, 'け'),
    HIRAGANA_KO((byte) 0xBA, 'こ'),
    HIRAGANA_SA((byte) 0xBB, 'さ'),
    HIRAGANA_SHI((byte) 0xBC, 'し'),
    HIRAGANA_SU((byte) 0xBD, 'す'),
    HIRAGANA_SE((byte) 0xBE, 'せ'),
    HIRAGANA_SO((byte) 0xBF, 'そ'),
    HIRAGANA_TA((byte) 0xC0, 'た'),
    HIRAGANA_CHI((byte) 0xC1, 'ち'),
    HIRAGANA_TSU((byte) 0xC2, 'つ'),
    HIRAGANA_TE((byte) 0xC3, 'て'),
    HIRAGANA_TO((byte) 0xC4, 'と'),
    HIRAGANA_NA((byte) 0xC5, 'な'),
    HIRAGANA_NI((byte) 0xC6, 'に'),
    HIRAGANA_NU((byte) 0xC7, 'ぬ'),
    HIRAGANA_NE((byte) 0xC8, 'ね'),
    HIRAGANA_NO((byte) 0xC9, 'の'),
    HIRAGANA_HA((byte) 0xCA, 'は'),
    HIRAGANA_HI((byte) 0xCB, 'ひ'),
    HIRAGANA_FU((byte) 0xCC, 'ふ'),
    HIRAGANA_HE((byte) 0xCD, 'へ'),
    HIRAGANA_HO((byte) 0xCE, 'ほ'),
    HIRAGANA_MA((byte) 0xCF, 'ま'),
    HIRAGANA_MI((byte) 0xD0, 'み'),
    HIRAGANA_MU((byte) 0xD1, 'む'),
    HIRAGANA_ME((byte) 0xD2, 'め'),
    HIRAGANA_MO((byte) 0xD3, 'も'),
    HIRAGANA_YA((byte) 0xD4, 'や'),
    HIRAGANA_YU((byte) 0xD5, 'ゆ'),
    HIRAGANA_YO((byte) 0xD6, 'よ'),
    HIRAGANA_RA((byte) 0xD7, 'ら'),
    HIRAGANA_RI((byte) 0xD8, 'リ'),
    HIRAGANA_RU((byte) 0xD9, 'る'),
    HIRAGANA_RE((byte) 0xDA, 'れ'),
    HIRAGANA_RO((byte) 0xDB, 'ろ'),
    HIRAGANA_WA((byte) 0xDC, 'わ'),
    HIRAGANA_WO((byte) 0xDD, 'を'),
    HIRAGANA_N((byte) 0xDE, 'ん'),

    HIRAGANA_GA((byte) 0x26, 'が'),
    HIRAGANA_GI((byte) 0x27, 'か'),
    HIRAGANA_GU((byte) 0x28, 'ぐ'),
    HIRAGANA_GE((byte) 0x29, 'げ'),
    HIRAGANA_GO((byte) 0x2A, 'ご'),
    HIRAGANA_ZA((byte) 0x2B, 'ざ'),
    HIRAGANA_ZI((byte) 0x2C, 'じ'),
    HIRAGANA_ZU((byte) 0x2D, 'う'),
    HIRAGANA_ZE((byte) 0x2E, 'ぜ'),
    HIRAGANA_ZO((byte) 0x2F, 'ぞ'),
    HIRAGANA_DA((byte) 0x30, 'だ'),
    HIRAGANA_DI((byte) 0x31, 'ぢ'),
    HIRAGANA_DU((byte) 0x32, 'づ'),
    HIRAGANA_DE((byte) 0x33, 'で'),
    HIRAGANA_DO((byte) 0x34, 'ど'),
    HIRAGANA_BA((byte) 0x3A, 'ば'),
    HIRAGANA_BI((byte) 0x3B, 'び'),
    HIRAGANA_BU((byte) 0x3C, 'ぶ'),
    HIRAGANA_BE((byte) 0x3D, 'べ'),
    HIRAGANA_BO((byte) 0x3E, 'ぼ'),
    HIRAGANA_PA((byte) 0x44, 'ぱ'),
    HIRAGANA_PI((byte) 0x45, 'ぴ'),
    HIRAGANA_PU((byte) 0x46, 'ぷ'),
    HIRAGANA_PE((byte) 0x47, 'ぺ'),
    HIRAGANA_PO((byte) 0x48, 'ぽ'),
    HIRAGANA_SMALL_TSU((byte) 0xDF, 'っ'),
    HIRAGANA_SMALL_YA((byte) 0xE0, 'ゃ'),
    HIRAGANA_SMALL_YU((byte) 0xE1, 'ゅ'),
    HIRAGANA_SMALL_YO((byte) 0XE2, 'ょ'),

    KATAKANA_A((byte) 0x80, 'ア'),
    KATAKANA_I((byte) 0x81, 'イ'),
    KATAKANA_U((byte) 0x82, 'ウ'),
    KATAKANA_E((byte) 0x83, 'エ'),
    KATAKANA_O((byte) 0x84, 'オ'),
    KATAKANA_KA((byte) 0x85, 'カ'),
    KATAKANA_KI((byte) 0x86, 'キ'),
    KATAKANA_KU((byte) 0x87, 'ク'),
    KATAKANA_KE((byte) 0x88, 'ケ'),
    KATAKANA_KO((byte) 0x89, 'コ'),
    KATAKANA_SA((byte) 0x8A, 'サ'),
    KATAKANA_SHI((byte) 0x8B, 'シ'),
    KATAKANA_SU((byte) 0x8C, 'ス'),
    KATAKANA_SE((byte) 0x8D, 'セ'),
    KATAKANA_SO((byte) 0x8E, 'ソ'),
    KATAKANA_TA((byte) 0x8F, 'タ'),
    KATAKANA_CHI((byte) 0x90, 'チ'),
    KATAKANA_TSU((byte) 0x91, 'ツ'),
    KATAKANA_TE((byte) 0x92, 'テ'),
    KATAKANA_TO((byte) 0x93, 'ト'),
    KATAKANA_NA((byte) 0x94, 'ナ'),
    KATAKANA_NI((byte) 0x95, 'ニ'),
    KATAKANA_NU((byte) 0x96, 'ヌ'),
    KATAKANA_NE((byte) 0x97, 'ネ'),
    KATAKANA_NO((byte) 0x98, 'ノ'),
    KATAKANA_HA((byte) 0x99, 'ハ'),
    KATAKANA_HI((byte) 0x9A, 'ヒ'),
    KATAKANA_FU((byte) 0x9B, 'フ'),
    KATAKANA_HE((byte) 0xCD, 'ヘ'),
    KATAKANA_HO((byte) 0x9C, 'ホ'),
    KATAKANA_MA((byte) 0x9D, 'マ'),
    KATAKANA_MI((byte) 0x9E, 'ミ'),
    KATAKANA_MU((byte) 0x9F, 'ム'),
    KATAKANA_ME((byte) 0xA0, 'メ'),
    KATAKANA_MO((byte) 0xA1, 'モ'),
    KATAKANA_YA((byte) 0xA2, 'ヤ'),
    KATAKANA_YU((byte) 0xA3, 'ユ'),
    KATAKANA_YO((byte) 0xA4, 'ヨ'),
    KATAKANA_RA((byte) 0xA5, 'ラ'),
    KATAKANA_RU((byte) 0xA6, 'ル'),
    KATAKANA_RE((byte) 0xA7, 'レ'),
    KATAKANA_RO((byte) 0xA8, 'ロ'),
    KATAKANA_WA((byte) 0xA9, 'ワ'),
    KATAKANA_WO((byte) 0xAA, 'ヲ'),
    KATAKANA_N((byte) 0xAB, 'ン'),

    KATAKANA_GA((byte) 0x5, 'ガ'),
    KATAKANA_GI((byte) 0x6, 'ギ'),
    KATAKANA_GU((byte) 0x7, 'グ'),
    KATAKANA_GE((byte) 0x8, 'ゲ'),
    KATAKANA_GO((byte) 0x9, 'ゴ'),
    KATAKANA_ZA((byte) 0xA, 'ザ'),
    KATAKANA_ZI((byte) 0xB, 'ジ'),
    KATAKANA_ZU((byte) 0xC, 'ズ'),
    KATAKANA_ZE((byte) 0xD, 'ゼ'),
    KATAKANA_ZO((byte) 0xE, 'ゾ'),
    KATAKANA_DA((byte) 0xF, 'ダ'),
    KATAKANA_DI((byte) 0x10, 'ヂ'),
    KATAKANA_DU((byte) 0x11, 'ヅ'),
    KATAKANA_DE((byte) 0x12, 'デ'),
    KATAKANA_DO((byte) 0x13, 'ド'),
    KATAKANA_BA((byte) 0x19, 'バ'),
    KATAKANA_BI((byte) 0x1A, 'ビ'),
    KATAKANA_BU((byte) 0x1B, 'ブ'),
    KATAKANA_BE((byte) 0x3D, 'ベ'),
    KATAKANA_BO((byte) 0x1C, 'ボ'),
    KATAKANA_PA((byte) 0x40, 'パ'),
    KATAKANA_PI((byte) 0x41, 'ピ'),
    KATAKANA_PU((byte) 0x42, 'プ'),
    KATAKANA_PE((byte) 0x47, 'ペ'),
    KATAKANA_PO((byte) 0x43, 'ポ'),
    KATAKANA_SMALL_TSU((byte) 0xAC, 'ッ'),
    KATAKANA_SMALL_YA((byte) 0xAD, 'ャ'),
    KATAKANA_SMALL_YU((byte) 0xAE, 'ュ'),
    KATAKANA_SMALL_YO((byte) 0xAF, 'ョ'),
    CHOONPU((byte) 0xE3, 'ー'),

    ;
    public final byte value;
    public final char character;

    InGameJapaneseCharacter(byte value, char character){
        this.value = value;
        this.character = character;
    }

    /**
     * Returns one of the enumeration's elements corresponding to a numeric value.
     * @param value -> the value of the wanted character in the in-game encoding
     * @return the corresponding enumeration element, null if none is found
     */
    public static InGameJapaneseCharacter characterFromValue(byte value){
        for(InGameJapaneseCharacter character : values()){
            if(character.value == value){
                return character;
            }
        }
        return null;
    }
}

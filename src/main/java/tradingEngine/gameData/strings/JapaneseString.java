package main.java.tradingEngine.gameData.strings;

/**
 * A character string in a special encoding used in Japanese Gen 1/2 Pokémon games.
 * @see InGameString
 * @author Julien Ait azzouzene
 */
public class JapaneseString extends InGameString{
    @Override
    public boolean isCharacterValid(byte character) {
        InGameJapaneseCharacter japChar = InGameJapaneseCharacter.characterFromValue(character);
        return japChar != null;
    }

    @Override
    public void addAll(byte[] array) {
        if(array.length > 11){
            throw new IllegalArgumentException("array size is too big, it should not be greater than 11");
        }
        
        int arraySize = array.length;

        int i = 0;
        while(i < arraySize && array[i] != InGameJapaneseCharacter.DELIMITER.value){
            InGameJapaneseCharacter japChar = InGameJapaneseCharacter.characterFromValue(array[i]);
            if (japChar == null){
                throw new IllegalArgumentException(String.format("illegal character for Japanese game game : %x", array[i]));
            }

            this.array[i] = array[i];
            i++;
        }

        if(i == 11){
            i = 10;
        }

        this.array[i] = 0x50;
        length = i;
    }

    @Override
    public String toString() {
        StringBuilder string= new StringBuilder();

        for(int i = 0; i < length; i++){
            InGameJapaneseCharacter japChar = InGameJapaneseCharacter.characterFromValue(array[i]);

            if(japChar == null){
                throw new IllegalArgumentException("InGameChar contain illegal character");
            }

            string.append(japChar.character);
        }

        return string.toString();
    }

    @Override
    public void fromString(String string) {
        if(string.length() > InGameString.MAX_SIZE - 1){
            throw new IllegalArgumentException("string too long");
        }
        if(string.length() == 0){
            throw new IllegalArgumentException("string must not be empty");
        }
        char[] charArray = string.toCharArray();

        int i;
        for( i = 0; i < string.length(); i++){
            InGameJapaneseCharacter inGameCharacter = InGameJapaneseCharacter.fromRegularCharacter(charArray[i]);
            if (inGameCharacter == null){
                throw new IllegalArgumentException("illegal character in string");
            }
            array[i] = inGameCharacter.value;
        }
        length = i;
        array[i] = InGameJapaneseCharacter.DELIMITER.value;
    }
}

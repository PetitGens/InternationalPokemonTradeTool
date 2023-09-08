package tradingEngine.gameData.strings;

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

        int i = 0;
        while(array[i] != InGameJapaneseCharacter.DELIMITER.value){
            InGameJapaneseCharacter japChar = InGameJapaneseCharacter.characterFromValue(array[i]);
            if (japChar == null){
                throw new IllegalArgumentException(String.format("illegal character for Japanese game game : %x", array[i]));
            }

            this.array[i] = array[i];
            i++;
        }
        this.array[i] = 0x50;
        length = i - 1;
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
        //TODO build InGameString from regular String
    }
}

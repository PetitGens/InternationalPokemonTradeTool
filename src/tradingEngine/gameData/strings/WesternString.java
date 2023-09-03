package tradingEngine.gameData.strings;

public class WesternString extends InGameString{
    @Override
    public void add(byte character) {
        InGameWesternCharacter westChar = InGameWesternCharacter.characterFromValue(character);
        if (westChar == null){
            throw new IllegalArgumentException(String.format("illegal character for Western game : %x", character));
        }
        array[length] = character;
        array[length + 1] = 0x50;
        length++;
    }

    @Override
    public void addAll(byte[] array) {
        if(array.length > 11){
            throw new IllegalArgumentException("array size is too big, it should not be greater than 11");
        }

        int i = 0;
        while(array[i] != 0x50){
            InGameWesternCharacter westChar = InGameWesternCharacter.characterFromValue(array[i]);
            if (westChar == null){
                throw new IllegalArgumentException(String.format("illegal character for Western game : %x", array[i]));
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
            InGameWesternCharacter westChar = InGameWesternCharacter.characterFromValue(array[i]);

            if(westChar == null){
                throw new IllegalArgumentException("InGameChar contain illegal character");
            }

            string.append(westChar.character);
        }

        return string.toString();
    }

    @Override
    public void fromString(String string) {
        //TODO build InGameString from regular String
    }

    public static String hexToString(String hexString){
        int stringLength = hexString.length();
        if(stringLength % 2 != 0){
            throw new IllegalArgumentException("hex string should have an even number of character");
        }

        StringBuilder returnString = new StringBuilder();

        for(int i = 0; i < stringLength; i += 2){
            String parsable = "0x" + hexString.substring(i, i + 2);
            byte value = Integer.decode(parsable).byteValue();

            InGameWesternCharacter westChar = InGameWesternCharacter.characterFromValue(value);

            if(westChar == null){
                throw new IllegalArgumentException("illegal character : " + parsable);
            }

            returnString.append(westChar.character);
        }

        return returnString.toString();
    }
}

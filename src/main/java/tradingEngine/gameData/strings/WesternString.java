package main.java.tradingEngine.gameData.strings;

/**
 * A character string in a special encoding used in Western Gen 1/2 Pokémon games.
 * @see InGameString
 * @author Julien Ait azzouzene
 */
public class WesternString extends InGameString{

    @Override
    public boolean isCharacterValid(byte character) {
        InGameWesternCharacter westChar = InGameWesternCharacter.characterFromValue(character);
        return westChar != null;
    }

    @Override
    public void addAll(byte[] array) {
        if(array.length > 11){
            throw new IllegalArgumentException("array size is too big, it should not be greater than 11");
        }

        int i = 0;
        while(i < array.length && array[i] != 0x50){
            InGameWesternCharacter westChar = InGameWesternCharacter.characterFromValue(array[i]);
            if (westChar == null){
                throw new IllegalArgumentException(String.format("illegal character for Western game : %x", array[i]));
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

    /**
     * Convert a hexadecimal encoded in-game string to a translated String
     * @param hexString -> the encoded in-game string as a hexadecimal String
     * @return the converted String
     */
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
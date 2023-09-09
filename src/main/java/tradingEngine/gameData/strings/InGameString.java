package main.java.tradingEngine.gameData.strings;

/**
 * A character string in a special encoding depending on the language of the game.
 * It has a size limit of 10 useful characters since no in-game strings used in this program should be longer than that.
 * This string always ends with an eleventh character with the value 0x50 as a delimiter, like the one used in the game.
 * "Characters" refer to 8 bits values (stored as byte here) like it's coded in the game.
 * @see WesternString
 * @see JapaneseString
 * @author Julien Ait azzouzene
 */
public abstract class InGameString {
    public static final int MAX_SIZE = 11;

    protected byte[] array = new byte[MAX_SIZE];
    protected int length = 0;

    /**
     * Adds the specified encoded character to the string.
     * @param character -> the character to add, in the right encoding depending on the game
     * @throws IllegalArgumentException if the character is illegal for the game or if there is no room for a new
     * character
     */
    public void add(byte character){
        if(length == 10){
            throw new IllegalArgumentException("in-game strings cannot exceed 11 characters");
        }

        if(isCharacterValid(character)){
            array[length] = character;
            array[length + 1] = 0x50;
            length++;
        }
        else {
            throw new IllegalArgumentException(String.format("illegal character for this game : %x", character));
        }
    }

    /**
     * Checks if the specified character is legal game-wise
     * @param character -> the character to check, in its numeric value
     * @return true if the character is legal, false otherwise
     */
    public abstract boolean isCharacterValid(byte character);

    /**
     * Writes every character in the string from the specified array.
     * The array can have a 0x50 delimiter, but that's not mandatory ; if it does, the characters beyond the delimiter
     * won't be written.
     * The method puts a delimiter after the written area, so this effectively erase all characters present
     * before the method call.
     * @param array -> the array from which the character are read
     */
    public abstract void addAll(byte[] array);

    /**
     * Removes the character at the specified index and shifts all the character after it.
     * @param index -> the index where the character to remove is located
     * @throws IllegalArgumentException if the specified index is out of bounds
     */
    public void remove(int index){
        if(index >= length){
            throw new IllegalArgumentException("index out of string's bounds");
        }

        for(int i = index; i < length - 1; i++){
            array[i] = array[i+1];
        }
        
        length--;
    }

    /**
     * Returns the character located at the specified index.
     * @param index -> the index where the wanted character is located
     * @return the character in question
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public byte get(int index){
        if(index >= length){
            throw new IllegalArgumentException("index out of string's bounds : " + index);
        }
        return array[index];
    }

    /**
     * Sets the character located at the specified index to the specified value
     * @param character -> the value to write at the index location
     * @param index -> the location of the character to write
     * @throws IllegalArgumentException if the index is out of bounds or if the specified character is illegal for the
     * game
     */
    public void set(byte character, int index){
        if(index >= MAX_SIZE - 1){
            throw new IllegalArgumentException("index out of string's bounds");
        }
        
        if(! isCharacterValid(character)) {
        	throw new IllegalArgumentException(String.format("illegal character for this game : %x", character));
        }
        
        array[index] = character;
    }

    /**
     * @return the number of useful characters (that means NOT including the 0x50 delimiter).
     */
    public int size(){
        return length;
    }

    /**
     * @return the string converted to a regular Java String
     */
    public abstract String toString();

    /**
     * (currently unimplemented) Replaces all the string's characters from the regular Java String given as parameter
     * @param string -> the String to read from
     * @throws IllegalArgumentException if the given string contains characters that aren't in the game
     */
    public abstract void fromString(String string);

    /**
     * Convert a byte value (which is signed) to an unsigned 8 bits value stored in an int.
     * (mostly for displaying, maths or comparisons)
     * @param value -> the signed value to convert
     * @return the converted unsigned value
     */
    public static int byteToUnsignedByte(byte value){
        return (int) value & 0xff;
    }

    /**
     * Returns the number of characters in the given array (excluding delimiter). It's mostly made for strings that
     * contains a 0x50 delimiter, as otherwise it just returns the length of the array.
     * @param string -> An array of encoded characters that the method will count.
     * @return the number of characters
     */
    public static int stringLength(byte[] string){
        int i;
        for(i = 0; i < string.length; i++){
            if(byteToUnsignedByte(string[i]) == 0x50){
                return i;
            }
        }
        return i;
        //throw new IllegalArgumentException("this string has no delimiter character");
    }

    /**
     * @return A byte array that based on this string, ended with a 0x50 delimiter.
     */
    public byte[] toArray(){
        byte[] newArray = new byte[array.length];
        int i = 0;
        while(i < array.length && array[i] != 0x50){
            newArray[i] = array[i];
            i++;
        }
        newArray[i] = 0x50;
        return newArray;
    }
}

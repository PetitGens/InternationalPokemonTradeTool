package main.java.tradingEngine.gameData.strings;

public abstract class InGameString {
    public static final int MAX_SIZE = 11;

    protected byte[] array = new byte[MAX_SIZE];
    protected int length = 0;

    public void add(byte character){
        if(isCharacterValid(character)){
            array[length] = character;
            array[length + 1] = 0x50;
            length++;
        }
        else {
            throw new IllegalArgumentException(String.format("illegal character for this game : %x", character));
        }
    }

    public abstract boolean isCharacterValid(byte character);

    public abstract void addAll(byte[] array);

    public void remove(int index){
        if(index >= length){
            throw new IllegalArgumentException("index out of string's bounds");
        }

        for(int i = index; i < length - 1; i++){
            array[i] = array[i+1];
        }
        
        length--;
    }

    public byte get(int index){
        if(index >= length){
            throw new IllegalArgumentException("index out of string's bounds : " + index);
        }
        return array[index];
    }

    public void set(byte character, int index){
        if(index >= MAX_SIZE - 1){
            throw new IllegalArgumentException("index out of string's bounds");
        }
        
        if(! isCharacterValid(character)) {
        	throw new IllegalArgumentException(String.format("illegal character for this game : %x", character));
        }
        
        array[index] = character;
    }

    public int size(){
        return length;
    }

    public abstract String toString();
    public abstract void fromString(String string);

    public static int byteToUnsignedByte(byte value){
        return (int) value & 0xff;
    }

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

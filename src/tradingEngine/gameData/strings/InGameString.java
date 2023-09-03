package tradingEngine.gameData.strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class InGameString  {
    public static final int MAX_SIZE = 11;

    protected byte[] array = new byte[MAX_SIZE];
    protected int length = 0;

    public abstract void add(byte character);
    public abstract void addAll(byte[] array);

    public void remove(int index){
        if(index >= length){
            throw new IllegalArgumentException("index out of string's bounds");
        }

        for(int i = index; i < length - 1; i++){
            array[i] = array[i+1];
        }
    }

    public byte get(int index){
        if(index >= length){
            throw new IllegalArgumentException("index out of string's bounds");
        }
        return array[index];
    }

    public void set(byte character, int index){
        if(index >= MAX_SIZE){
            throw new IllegalArgumentException("index out of string's bounds");
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
        for(int i = 0; i < string.length; i++){
            if(byteToUnsignedByte(string[i]) == 0x50){
                return i;
            }
        }
        throw new IllegalArgumentException("this string has no delimiter character");
    }
}

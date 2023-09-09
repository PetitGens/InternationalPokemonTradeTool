package main.java.tradingEngine.gameData;

import main.java.tradingEngine.gameData.strings.InGameString;

public abstract class Bytes {

    /**
     * Convert a byte value (which is signed) to an unsigned 8 bits value stored in an int.
     * (mostly for displaying, maths or comparisons)
     * @param value -> the signed value to convert
     * @return the converted unsigned value
     */
    public static int byteToUnsignedByte(byte value){
        return (int) value & 0xff;
    }

    public static int twoBytesToInt(byte high_byte, byte low_byte){
        return 0x100 * byteToUnsignedByte(high_byte) + byteToUnsignedByte(low_byte);
    }

    public static int threeBytesToInt(byte high_byte, byte mid_byte, byte low_byte){
        return 0x10000 * byteToUnsignedByte(high_byte) + twoBytesToInt(mid_byte, low_byte);
    }
}

package main.java.tradingEngine.gameData;

/**
 * A small utility for byte variables.
 * @author Julien Ait azzouzene
 */
public abstract class Bytes {

    /**
     * Converts a byte value (which is signed) to an unsigned 8-bits value stored in an int
     * (mostly for displaying, maths or comparisons).
     * @param value the signed value to convert
     * @return the converted unsigned value
     */
    public static int byteToUnsignedByte(byte value){
        return (int) value & 0xff;
    }

    /**
     * Converts two bytes to a 16 bits unsigned value.
     * @param high_byte the eight leftmost bits
     * @param low_byte the eight rightmost bits
     * @return the converted 16-bits value
     */
    public static int twoBytesToInt(byte high_byte, byte low_byte){
        return 0x100 * byteToUnsignedByte(high_byte) + byteToUnsignedByte(low_byte);
    }

    /**
     * Converts three bytes to a 24 bits unsigned value.
     * @param high_byte the eight leftmost bits
     * @param mid_byte the eight bits at the middle
     * @param low_byte the eight rightmost bits
     * @return the converted 16-bits value
     */
    public static int threeBytesToInt(byte high_byte, byte mid_byte, byte low_byte){
        return 0x10000 * byteToUnsignedByte(high_byte) + twoBytesToInt(mid_byte, low_byte);
    }

    /**
     * Returns the upper byte of a 16-bits value.
     * @param value the 16-bits value to read from
     * @return the 8 first bits of the given 16-bits value.
     */
    public static byte getHighByteFrom2BytesValue(int value){
        return (byte)((value >> 8) & 0xFF);
    }

    /**
     * Returns the lower byte of a 16-bits value.
     * @param value the 16-bits value to read from
     * @return the 8 last bits of the given 16-bits value.
     */
    public static byte getLowByteFrom2BytesValue(int value){
        return (byte)(value & 0xFF);
    }
}

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DltStream {

    private byte[] buffer;
    private int pos = 0;
    private int end = 0;

    public DltEndianness endianness;

    public DltStream(byte[] buf, int pos, int len, DltEndianness endianness) {
        buffer = buf;
        this.pos = pos;
        this.end = pos + len;
        this.endianness = endianness;
    }

    /**
     * Reads a string (not null terminated, with fixed length)
     */
    public String readString(int size) {
        int i = size;
        int p = pos;
        while (buffer[p + i - 1] == 0 && i > 0)
            --i;
        pos += size;
        return new String(buffer, p, i, StandardCharsets.UTF_8);
    }

    /**
     * Reads an integer (up to 64 bits)<br>
     * An unsigned 64-bit values will get negative if
     * the range is exceeded. Use "Long.toUnsignedString"
     * for such types if output is needed.
     *
     * @param size   The size in bytes.
     * @param signed If signed or unsigned.
     * @return The parsed long
     */
    public long readInteger(int size, boolean signed) {
        if (endianness == DltEndianness.LittleEndian)
            return readLongLittleEndian(size, signed);
        else
            return readLongBigEndian(size, signed);
    }

    public long readLongLittleEndian(int size, boolean signed) {
        long v = 0;
        if (signed) {
            for (int i = 0; i < size - 1; ++i) {
                v |= (((long) buffer[pos++]) & 0x00FF) << (i << 3);
            }
            v |= ((long) buffer[pos++]) << ((size - 1) << 3);
        } else {
            for (int i = 0; i < size; ++i) {
                v |= (((long) buffer[pos++]) & 0x00FF) << (i << 3);
            }
        }
        return v;
    }

    public long readLongBigEndian(int size, boolean signed) {
        long v = 0;
        if (signed) {
            for (int i = 1; i < size; ++i) {
                v |= (((long) buffer[pos + i]) & 0x00FF) << ((size - 1 - i) << 3);
            }
            v |= ((long) buffer[pos]) << ((size - 1) << 3);
            pos += size;
        } else {
            for (int i = 0; i < size; ++i) {
                v |= (((long) buffer[pos++]) & 0x00FF) << ((size - 1 - i) << 3);
            }
        }
        return v;
    }

    public int getAvailable() {
        return end - pos;
    }

    public byte[] mid(int length) {
        byte[] d = Arrays.copyOfRange(buffer, pos, pos + length);
        pos += length;
        return d;
    }

    public float readFloat() {
        return Float.intBitsToFloat((int) readInteger(4, false));
    }

    public double readDouble() {
        return Double.longBitsToDouble(readInteger(8, false));
    }
}

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class DltUtil {

    static boolean debug = false;

    static final char[] hexmap = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toAscii(byte[] data, ToAsciiMode type) {
        return toAscii(data, type, data.length);
    }

    public static String toAscii(byte[] data) {
        return toAscii(data, ToAsciiMode.HEX);
    }

    public static String toHexDigits(byte[] data, int len) {
        StringBuilder sb = new StringBuilder(len * 3);
        for (int i = 0; i < len; ++i) {
            sb.append(hexmap[data[i] & 0x0F]);
            sb.append(hexmap[(data[i] >> 4) & 0x0F]);

            if ((i % 2) == 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static String toAscii(byte[] data, ToAsciiMode type, int size_bytes) {
        if (type == ToAsciiMode.ASCII) {
            // ascii
            return new String(data, 0, size_bytes, StandardCharsets.ISO_8859_1);
        } else if (type == ToAsciiMode.BINARY) {
            // binary
            int size = data.length;
            if (0 == size) {
                //Let's return an empty string.
                return "";
            }

            if (1 == size_bytes) {
                // width 8, base 2
                StringBuilder encoded = new StringBuilder(10);
                encoded.append("0b");
                encoded.append(Integer.toBinaryString(data[0]));
                while (encoded.length() < 10) {
                    encoded.insert(2, '0');
                }
                // insert a space after 0bxxxx
                return encoded.append(' ').toString();
            } else // has to be: (2 == size_bytes)
            {
                int toEncode = (((int) data[0]) & 0x00FF) | ((((int) (data[1])) << 8) & 0x00FF00);

                StringBuilder encoded = new StringBuilder(25);
                encoded.append("0b");
                encoded.append(Integer.toBinaryString(toEncode));
                while (encoded.length() < 18) {
                    encoded.insert(2, '0');
                }
                encoded.insert(14, ' '); // insert spaces
                encoded.insert(10, ' ');
                encoded.insert(6, ' ');
                return encoded.toString();
            }
        } else {
            // hex
            return "0x" + toHexDigits(data, data.length);
        }
    }

    public enum ToAsciiMode {
        ASCII,
        HEX,
        BINARY
    }

    public static void extractString(StringBuilder text, byte[] data, Charset charSet) {
        if (data != null && data.length > 0) {
            int i = data.length;
            while (i > 0 && data[i - 1] == 0)
                --i;
            text.append(new String(data, 0, i, charSet));
        }
    }

}

public class DltStorageHeader {

    public static final int sizeOf = 4 + 4 + 4 + 4;

    /**
     * This pattern should be DLT0x01
     */
    public String pattern;

    /**
     * 32 bit, seconds since 1.1.1970
     */
    public long seconds;

    /**
     * 32 bit signed, Microseconds
     */
    public int microseconds;

    /**
     * The ECU id is added, if it is not already in the DLT message itself
     */
    public String ecu;

    DltStorageHeader(DltStream s) {

        pattern = s.readString(4);
        seconds = s.readInteger(4, false);
        microseconds = (int) s.readInteger(4, true);

        ecu = s.readString(4);
    }
}

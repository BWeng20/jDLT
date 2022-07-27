public class DltExtendedHeader {

    public static int sizeOf = 1 + 1 + 4 + 4;


    /**
     * 8 bit, unsigned. message info
     */
    public short msin;

    /**
     * 8 bit, unsigned. number of arguments
     */
    public short noar;

    /**
     * 4 chars application id
     */
    public String apid;

    /**
     * 4 chars context id
     */
    public String ctid;

    public DltExtendedHeader(DltStream s) {
        msin = (short) s.readInteger(1, false);
        noar = (short) s.readInteger(1, false);
        apid = s.readString(4);
        ctid = s.readString(4);
    }

}

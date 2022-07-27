public class DltStandardHeaderExtra {

    public final static int sizeOf = 4 + 4 + 4;

    /**
     * 4 chars, ECU id
     */
    public String ecu;

    /**
     * 32 bit, Session number
     */
    public long seid;

    /**
     * 32 bit, Timestamp since system start in 0.1 milliseconds
     */
    public long tmsp;

    public DltStandardHeaderExtra(DltStream s, int htyp) {

        if (0 != (DltConstants.DLT_HTYP_WEID & htyp)) {
            ecu = s.readString(4);
        }
        if (0 != (DltConstants.DLT_HTYP_WSID & htyp)) {
            seid = (long) s.readInteger(4, false);
        }
        if (0 != (DltConstants.DLT_HTYP_WTMS & htyp)) {
            tmsp = (long) s.readInteger(4, false);
        }
    }

}

public class DltStandardHeader {
    public static int sizeOf = 1 + 1 + 2;

    /**
     * 8 bit, This parameter contains some information, see definitions below
     */
    public int htyp;

    /**
     * 8 bit,  The message counter is increased with each sent DLT message
     */
    public int mcnt;

    /**
     * 16bit, Length of the complete message, without storage header
     */
    public int len;

    public DltStandardHeader(DltStream s) {
        htyp = (int) s.readInteger(1, false);
        mcnt = (int) s.readInteger(1, false);
        len = (int) s.readInteger(2, false);
    }
}



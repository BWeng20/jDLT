public class DltServiceConnectionInfo {

    public static final int sizeOf = 4 + 1 + 1 + 4;

    /**
     * service ID. unsigned 4 byte.
     */
    public long service_id;
    /**
     * reponse status. unsigned 1 byte
     */
    public int status;
    /**
     * new state. unsigned 1 byte
     */
    public int state;
    /**
     * communication interface, String 4 byte
     */
    public String comid;

    public DltServiceConnectionInfo(DltStream s) {
        service_id = s.readInteger(4, false);
        status = (int) s.readInteger(1, false);
        state = (int) s.readInteger(1, false);
        comid = s.readString(4);
    }

}

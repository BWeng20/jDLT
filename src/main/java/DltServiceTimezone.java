public class DltServiceTimezone {


    public static final int sizeOf = 4 + 1 + 4 + 1;

    /**
     * service ID, unsigned 4 byte
     */
    public long service_id;

    /**
     * response status, unsigned 1 byte
     */
    public int status;
    /**
     * Timezone in seconds. signed 4 byte
     */
    public int timezone;
    /**
     * Is daylight saving time, unsigned 1 byte
     */
    public boolean isdst;

    public DltServiceTimezone(DltStream s) {
        service_id = s.readInteger(4, false);
        status = (int) s.readInteger(1, false);
        timezone = (int) s.readInteger(4, true);
        isdst = 0 != s.readInteger(1, false);
    }

}

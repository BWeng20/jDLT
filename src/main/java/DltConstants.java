public class DltConstants {
    /**
     * Boolean data
     */
    public static final int DLT_TYPE_INFO_BOOL = 0x00000010;
    /**
     * Signed integer data
     */
    public static final int DLT_TYPE_INFO_SINT = 0x00000020;
    /**
     * Unsigned integer data
     */
    public static final int DLT_TYPE_INFO_UINT = 0x00000040;
    /**
     * Float data
     */
    public static final int DLT_TYPE_INFO_FLOA = 0x00000080;
    /**
     * Array of standard types
     */
    public static final int DLT_TYPE_INFO_ARAY = 0x00000100;
    /**
     * String
     */
    public static final int DLT_TYPE_INFO_STRG = 0x00000200;
    /**
     * Raw data
     */
    public static final int DLT_TYPE_INFO_RAWD = 0x00000400;
    /**
     * Set, if additional information to a variable is available
     */
    public static final int DLT_TYPE_INFO_VARI = 0x00000800;
    /**
     * Set, if quantization and offset are added
     */
    public static final int DLT_TYPE_INFO_FIXP = 0x00001000;
    /**
     * Set, if additional trace information is added
     */
    public static final int DLT_TYPE_INFO_TRAI = 0x00002000;
    /**
     * Struct
     */
    public static final int DLT_TYPE_INFO_STRU = 0x00004000;
    /**
     * coding of the type string: 0 = ASCII, 1 = UTF-8
     */
    public static final int DLT_TYPE_INFO_SCOD = 0x00038000;
    public static final int DLT_TYLE_8BIT = 0x00000001;
    public static final int DLT_TYLE_16BIT = 0x00000002;
    public static final int DLT_TYLE_32BIT = 0x00000003;
    public static final int DLT_TYLE_64BIT = 0x00000004;
    public static final int DLT_TYLE_128BIT = 0x00000005;

    /**
     * Length of standard data: 1 = 8bit, 2 = 16bit, 3 = 32 bit, 4 = 64 bit, 5 = 128 bit
     */
    public static final int DLT_TYPE_INFO_TYLE = 0x0000000f;

    public static final int DLT_SCOD_ASCII = 0x00000000;
    public static final int DLT_SCOD_UTF8 = 0x00008000;
    public static final int DLT_SCOD_HEX = 0x00010000;
    public static final int DLT_SCOD_BIN = 0x00018000;
    /**
     * Service ID: Set log level
     */
    public static final int DLT_SERVICE_ID_SET_LOG_LEVEL = 0x01;
    /**
     * Service ID: Set trace status
     */
    public static final int DLT_SERVICE_ID_SET_TRACE_STATUS = 0x02;
    /**
     * Service ID: Get log info
     */
    public static final int DLT_SERVICE_ID_GET_LOG_INFO = 0x03;
    /**
     * Service ID: Get dafault log level
     */
    public static final int DLT_SERVICE_ID_GET_DEFAULT_LOG_LEVEL = 0x04;
    /**
     * Service ID: Store configuration
     */
    public static final int DLT_SERVICE_ID_STORE_CONFIG = 0x05;
    /**
     * Service ID: Reset to factory defaults
     */
    public static final int DLT_SERVICE_ID_RESET_TO_FACTORY_DEFAULT = 0x06;
    /**
     * Service ID: Set communication interface status
     */
    public static final int DLT_SERVICE_ID_SET_COM_INTERFACE_STATUS = 0x07;
    /**
     * Service ID: Set communication interface maximum bandwidth
     */
    public static final int DLT_SERVICE_ID_SET_COM_INTERFACE_MAX_BANDWIDTH = 0x08;
    /**
     * Service ID: Set verbose mode
     */
    public static final int DLT_SERVICE_ID_SET_VERBOSE_MODE = 0x09;
    /**
     * Service ID: Set message filtering
     */
    public static final int DLT_SERVICE_ID_SET_MESSAGE_FILTERING = 0x0A;
    /**
     * Service ID: Set timing packets
     */
    public static final int DLT_SERVICE_ID_SET_TIMING_PACKETS = 0x0B;
    /**
     * Service ID: Get local time
     */
    public static final int DLT_SERVICE_ID_GET_LOCAL_TIME = 0x0C;
    /**
     * Service ID: Use ECU id
     */
    public static final int DLT_SERVICE_ID_USE_ECU_ID = 0x0D;
    /**
     * Service ID: Use session id
     */
    public static final int DLT_SERVICE_ID_USE_SESSION_ID = 0x0E;
    /**
     * Service ID: Use timestamp
     */
    public static final int DLT_SERVICE_ID_USE_TIMESTAMP = 0x0F;
    /**
     * Service ID: Use extended header
     */
    public static final int DLT_SERVICE_ID_USE_EXTENDED_HEADER = 0x10;
    /**
     * Service ID: Set default log level
     */
    public static final int DLT_SERVICE_ID_SET_DEFAULT_LOG_LEVEL = 0x11;
    /**
     * Service ID: Set default trace status
     */
    public static final int DLT_SERVICE_ID_SET_DEFAULT_TRACE_STATUS = 0x12;
    /**
     * Service ID: Get software version
     */
    public static final int DLT_SERVICE_ID_GET_SOFTWARE_VERSION = 0x13;
    /**
     * Service ID: Message buffer overflow
     */
    public static final int DLT_SERVICE_ID_MESSAGE_BUFFER_OVERFLOW = 0x14;
    /**
     * Service ID: Message unregister context
     */
    public static final int DLT_SERVICE_ID_UNREGISTER_CONTEXT = 0xf01;
    /**
     * Service ID: Message connection info
     */
    public static final int DLT_SERVICE_ID_CONNECTION_INFO = 0xf02;
    /**
     * Service ID: Timezone
     */
    public static final int DLT_SERVICE_ID_TIMEZONE = 0xf03;
    /**
     * Service ID: Timezone
     */
    public static final int DLT_SERVICE_ID_MARKER = 0xf04;
    /**
     * Service ID: Message Injection (minimal ID)
     */
    public static final int DLT_SERVICE_ID_CALLSW_CINJECTION = 0xFFF;
    /**
     * DLT service connection state - Client is disconnected
     */
    public static final int DLT_CONNECTION_STATUS_DISCONNECTED = 0x01;
    /**
     * DLT service connection state - Client is connected
     */
    public static final int DLT_CONNECTION_STATUS_CONNECTED = 0x02;
    public static final int DLT_ID_SIZE = 4;
    public static final int DLT_SIZE_WEID = DLT_ID_SIZE;
    public static final int DLT_SIZE_WSID = 4;
    public static final int DLT_SIZE_WTMS = 4;

    /**
     * MSB first
     */
    static int DLT_HTYP_MSBF = 0x02;
    /**
     * with ECU ID
     */
    static int DLT_HTYP_WEID = 0x04;
    /**
     * with session ID
     */
    static int DLT_HTYP_WSID = 0x08;
    /**
     * < with timestamp
     */
    static int DLT_HTYP_WTMS = 0x10;
    /**
     * < version number, 0x1
     */
    static int DLT_HTYP_VERS = 0xe0;
    /**
     * verbose
     */
    static int DLT_MSIN_VERB = 0x01;
    /**
     * message type
     */
    static int DLT_MSIN_MSTP = 0x0e;
    /**
     * message type info
     */
    static int DLT_MSIN_MTIN = 0xf0;

    /**
     * shift right offset to get mstp value
     */
    static int DLT_MSIN_MSTP_SHIFT = 1;
    /**
     * shift right offset to get mtin value
     */
    static int DLT_MSIN_MTIN_SHIFT = 4;
    /**
     * use extended header
     */
    static int DLT_HTYP_UEH = 0x01;
}

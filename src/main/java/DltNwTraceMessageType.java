public enum DltNwTraceMessageType {

    Unknown,

    /**
     * 0x1 - Inter-Process-Communication
     */
    Ipc,

    /**
     * 0x2 - CAN Communications bus
     */
    Can,

    /**
     * 0x3 - FlexRay Communications bus
     */
    Flexray,

    /**
     * 0x4 - Most Communications bus
     */
    Most,

    /**
     * 0x5  - Ethernet Communications bus
     */
    Ethernet,
    /**
     * 0x6 - SOME/IP Communication
     */
    SomeIP,

    /**
     * 0x7-0x15 - User Defined
     */
    User
}

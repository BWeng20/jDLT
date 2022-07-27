public enum DltLogMessageLevel {

    Unknown,

    /**
     * 0x1 - Fatal system error
     */
    Fatal,
    /**
     * 0x2 - SWC error)
     */
    Error,
    /**
     * 0x3 - Correct behavior cannot be ensured.
     */
    Warn,

    /**
     * 0x4 - Message of LogLevel type “Information”
     */
    Info,

    /**
     * 0x5 - Message of LogLevel type “Debug”
     */
    Debug,
    
    /**
     * 0x6 - Message of LogLevel type “Verbose”
     */
    Verb

}

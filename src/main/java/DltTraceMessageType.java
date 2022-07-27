public enum DltTraceMessageType {

    Unknown,

    /**
     * 0x1 - Value of variable
     */
    Variable,
    /**
     * 0x2 - Call of a function
     */
    FunctionIn,
    /**
     * 0x3 - Return of a function
     */
    FunctionOut,
    /**
     * 0x4 - State of a State Machine
     */
    State,

    /**
     * 0x5 - RTE events
     */
    Vfb
}

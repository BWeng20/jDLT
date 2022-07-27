import java.nio.charset.StandardCharsets;

public class DltArgument {
    public int dltType;
    public DltTypeInfoDef typeInfo;
    public String name;
    public byte[] data;

    public DltEndianness endianness;

    boolean setArgument(DltStream payload) {
        short length = 0;
        short length2;
        short length3 = 0;

        endianness = payload.endianness;

        /* get type info */
        if (payload.getAvailable() < 4)
            return false;

        dltType = (int) payload.readInteger(4, false);

        if (0 != (dltType & DltConstants.DLT_TYPE_INFO_STRG)) {
            if ((dltType & DltConstants.DLT_TYPE_INFO_SCOD) == DltConstants.DLT_SCOD_UTF8) {
                typeInfo = DltTypeInfoDef.Utf8;
            } else {
                typeInfo = DltTypeInfoDef.Strg;
            }
        } else if (0 != (dltType & DltConstants.DLT_TYPE_INFO_BOOL)) {
            typeInfo = DltTypeInfoDef.Bool;
        } else if (0 != (dltType & DltConstants.DLT_TYPE_INFO_SINT)) {
            typeInfo = DltTypeInfoDef.SInt;
        } else if (0 != (dltType & DltConstants.DLT_TYPE_INFO_UINT)) {
            typeInfo = DltTypeInfoDef.UInt;
        } else if (0 != (dltType & DltConstants.DLT_TYPE_INFO_FLOA)) {
            typeInfo = DltTypeInfoDef.Floa;
        } else if (0 != (dltType & DltConstants.DLT_TYPE_INFO_RAWD)) {
            typeInfo = DltTypeInfoDef.Rawd;
        } else if (0 != (dltType & DltConstants.DLT_TYPE_INFO_TRAI)) {
            typeInfo = DltTypeInfoDef.Trai;
        } else {
            typeInfo = DltTypeInfoDef.Unknown;
            return false;
        }

        /* get length of string, raw data or trace info */
        if (typeInfo == DltTypeInfoDef.Strg || typeInfo == DltTypeInfoDef.Rawd || typeInfo == DltTypeInfoDef.Trai || typeInfo == DltTypeInfoDef.Utf8) {
            if (payload.getAvailable() < 2)
                return false;
            length = (short) payload.readInteger(2, false);
        }

        /* get variable info */
        if (0 != (dltType & DltConstants.DLT_TYPE_INFO_VARI)) {
            if (payload.getAvailable() < 2)
                return false;
            length2 = (short) payload.readInteger(2, false);

            if (typeInfo == DltTypeInfoDef.SInt || typeInfo == DltTypeInfoDef.UInt || typeInfo == DltTypeInfoDef.Floa) {
                if (payload.getAvailable() < 2)
                    return false;
                length3 = (short) payload.readInteger(2, false);
            }
            name = payload.readString(length2);
            if (typeInfo == DltTypeInfoDef.SInt || typeInfo == DltTypeInfoDef.UInt || typeInfo == DltTypeInfoDef.Floa) {
                name = payload.readString(length3);
            }
        }

        /* get fix point quantisation and offset */
        if (0 != (dltType & DltConstants.DLT_TYPE_INFO_FIXP)) {
            /* not supported yet */
            return false;
        }

        /* get data */
        if (typeInfo == DltTypeInfoDef.Strg || typeInfo == DltTypeInfoDef.Rawd || typeInfo == DltTypeInfoDef.Trai || typeInfo == DltTypeInfoDef.Utf8) {
            if (payload.getAvailable() < length)
                return false;
            data = payload.mid(length);
        } else if (typeInfo == DltTypeInfoDef.Bool) {
            data = payload.mid(1);
        } else if (typeInfo == DltTypeInfoDef.SInt || typeInfo == DltTypeInfoDef.UInt) {
            switch (dltType & DltConstants.DLT_TYPE_INFO_TYLE) {
                case DltConstants.DLT_TYLE_8BIT -> data = payload.mid(1);
                case DltConstants.DLT_TYLE_16BIT -> data = payload.mid(2);
                case DltConstants.DLT_TYLE_32BIT -> data = payload.mid(4);
                case DltConstants.DLT_TYLE_64BIT -> data = payload.mid(8);
                case DltConstants.DLT_TYLE_128BIT -> data = payload.mid(16);
                default -> {
                    return false;
                }
            }

        } else if (typeInfo == DltTypeInfoDef.Floa) {
            switch (dltType & DltConstants.DLT_TYPE_INFO_TYLE) {
                case DltConstants.DLT_TYLE_8BIT -> data = payload.mid(1);
                case DltConstants.DLT_TYLE_16BIT -> data = payload.mid(2);
                case DltConstants.DLT_TYLE_32BIT -> data = payload.mid(4);
                case DltConstants.DLT_TYLE_64BIT -> data = payload.mid(8);
                case DltConstants.DLT_TYLE_128BIT -> data = payload.mid(16);
                default -> {
                    return false;
                }
            }

        }

        return true;
    }

    @Override
    public String toString() {
        return toString(false);
    }


    public String toString(boolean binary) {

        if (binary) {
            return DltUtil.toAscii(data);
        } else {
            StringBuilder text = new StringBuilder(50);

            switch (typeInfo) {
                case Unknown:
                    text.append("?");
                    break;
                case Strg:
                    if (data.length > 0) {
                        DltUtil.extractString(text, data, StandardCharsets.ISO_8859_1);
                    }
                    break;
                case Utf8:
                    if (data.length > 0) {
                        DltUtil.extractString(text, data, StandardCharsets.UTF_8);
                    }
                    break;
                case Bool:
                    if (data.length > 0) {
                        if (data[0] != 0)
                            text.append("true");
                        else
                            text.append("false");
                    } else
                        text.append('?');
                    break;
                case SInt: {
                    DltStream intS = new DltStream(data, 0, data.length, endianness);
                    if (data.length <= 8)
                        text.append(intS.readInteger(data.length, true));
                    else
                        text.append('?');
                }
                break;
                case UInt:
                    if ((dltType & DltConstants.DLT_TYPE_INFO_SCOD) == DltConstants.DLT_SCOD_BIN) {
                        if ((dltType & DltConstants.DLT_TYPE_INFO_TYLE) == DltConstants.DLT_TYLE_8BIT)
                            text.append(DltUtil.toAscii(data, DltUtil.ToAsciiMode.BINARY, 1)); // show binary
                        else if ((dltType & DltConstants.DLT_TYPE_INFO_TYLE) == DltConstants.DLT_TYLE_16BIT)
                            text.append(DltUtil.toAscii(data, DltUtil.ToAsciiMode.BINARY, 2)); // show binary
                    } else if ((dltType & DltConstants.DLT_TYPE_INFO_SCOD) == DltConstants.DLT_SCOD_HEX) {
                        if ((dltType & DltConstants.DLT_TYPE_INFO_TYLE) == DltConstants.DLT_TYLE_8BIT)
                            text.append(DltUtil.toAscii(data, DltUtil.ToAsciiMode.HEX, 1)); // show 8 bit hex
                        else if ((dltType & DltConstants.DLT_TYPE_INFO_TYLE) == DltConstants.DLT_TYLE_16BIT)
                            text.append(DltUtil.toAscii(data, DltUtil.ToAsciiMode.HEX, 2)); // show 16 bit hex
                        else if ((dltType & DltConstants.DLT_TYPE_INFO_TYLE) == DltConstants.DLT_TYLE_32BIT)
                            text.append(DltUtil.toAscii(data, DltUtil.ToAsciiMode.HEX, 4)); // show 32 bit hex
                        else if ((dltType & DltConstants.DLT_TYPE_INFO_TYLE) == DltConstants.DLT_TYLE_64BIT)
                            text.append(DltUtil.toAscii(data, DltUtil.ToAsciiMode.HEX, 8)); // show 64 bit hex
                    } else {
                        DltStream intS = new DltStream(data, 0, data.length, endianness);
                        if (data.length <= 8)
                            text.append(Long.toUnsignedString(intS.readInteger(data.length, false)));
                        else
                            text.append('?');

                    }
                    break;
                case Floa: {
                    DltStream intF = new DltStream(data, 0, data.length, endianness);
                    switch (data.length) {
                        case 4 -> text.append(intF.readFloat());
                        case 8 -> text.append(intF.readDouble());
                        default -> text.append('?');
                    }
                }
                break;
                case Rawd:
                    text.append(DltUtil.toHexDigits(data, data.length)); // show raw format (no leading 0x)
                    break;
                case Trai:
                default:
                    text.append('?');
                    break;
            }
            return text.toString();
        }
    }

}

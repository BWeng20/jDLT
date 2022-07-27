import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DltReceiver {

    public static final int DLT_DAEMON_TCP_PORT = 3490;
    public static final int DLT_DAEMON_UDP_PORT = 3490;
    public static final int DLT_MAX_MESSAGE_LEN = 1024 * 64;

    long bytesReceived;
    long bytesError;
    long syncFound;

    boolean sendSerialHeader;
    boolean syncSerialHeader = false;

    int messageCounter;


    public DltReceiver(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        stream = socket.getInputStream();
    }

    Socket socket;
    InputStream stream;

    byte buffer[] = new byte[10240];
    int end = 0;
    int pos = 0;

    public DltMessage readMessage() throws IOException {
        int len = stream.read(buffer, end, buffer.length - end);

        end += len;

        if (len < 0) {
			//@TODO
            System.out.println("No connection");
            System.exit(-1);
        }
        if (DltUtil.debug)
            System.out.println("Read " + len + " (avail " + end + ")");

        DltMessage msg = new DltMessage();
        if (parseMessage(msg)) {
            return msg;
        }
        return null;
    }

    public void clear() {
        pos = 0;
        end = 0;
        bytesReceived = 0;
        bytesError = 0;
        syncFound = 0;
        messageCounter = 0;
    }


    boolean parseMessage(DltMessage msg) {

		//@TODO: The original DLT-Viewer code is awfull...
		//       How ever should skip this garbage?
		//       I tried here to throw everything away if we got too much garbage.
		//       but no hint how this behaves in real world enviroments.


        int found = 0;
        int firstPos = 0;
        int secondPos = 0;
        char lastFound = 0;

        for (int num = 0; num < (end - pos); num++) {
            if (buffer[pos + num] == 'D') {
                lastFound = 'D';
            } else if (lastFound == 'D' && buffer[pos + num] == 'L') {
                lastFound = 'L';
            } else if (lastFound == 'L' && buffer[pos + num] == 'S') {
                lastFound = 'S';
            } else if (lastFound == 'S' && buffer[pos + num] == 0x01) {
                /* header found */
                found++;
                if (found == 1) {
                    firstPos = num + 1;
                    syncFound++;
                }
                if (found == 2) {
                    secondPos = num + 1;
                    break;
                }
                lastFound = 0;
            } else {
                lastFound = 0;
            }
            if ((!syncSerialHeader) && num == 3)
                break;
        }

        if (syncSerialHeader && found == 0) {
            if (0 == lastFound) {
                /*@TODO: ??? clear buffer if even not start of sync header found */
                bytesError += end - pos;
                end = 0;
                pos = 0;
            }
            return false;
        }
        if (found > 0 && DltUtil.debug)
            System.out.println(">>>> sync found #" + found + " firstPos " + firstPos + " secondPos " + secondPos);

        if (found == 2) {
            if (!msg.setMsg(buffer, pos + firstPos, pos + secondPos - firstPos - 4, false)) {
                advance(secondPos - 4);
                bytesError += secondPos - 4;
                return false;
            } else {
                advance(secondPos - 4);
                if (firstPos > 4)
                    bytesError += firstPos - 4;
                return true;
            }
        }

        if (firstPos > 4)
            bytesError += firstPos - 4;

        if (!msg.setMsg(buffer, pos + firstPos, end - firstPos - pos, false)) {

            if (end > (buffer.length / 2)) {
                //@TODO: clear buffer if we have garbage
                bytesError += end - pos;
                end = 0;
                pos = 0;
                System.err.println("Buffer cleared because of errors");
            }
            return false;
        }

        advance(firstPos + msg.getHeaderSize() + msg.getPayloadSize());
        return true;
    }

    private void advance(int offset) {
        if (DltUtil.debug)
            System.out.println("Advance " + offset);
        pos += offset;
        if (pos > (buffer.length / 2)) {
            System.arraycopy(buffer, pos, buffer, 0, end - pos);
            end -= pos;
            pos = 0;
        }
    }


    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder(1024);
        DltReceiver receiver = new DltReceiver("localhost", 3490);
        do {
            DltMessage msg = receiver.readMessage();
            if (msg != null) {
                sb.setLength(0);
                sb.append("Msg[").append(msg.mode.toString()).append("] ");

                sb.append(msg.type.toString());
                switch (msg.type) {
                    case Unknown -> {
                    }
                    case Control -> sb.append('-').append(msg.controlSubtype);
                    case Log -> sb.append('-').append(msg.logLevel);
                    case NwTrace -> {
                        if (msg.nwTraceSubtype == DltNwTraceMessageType.User)
                            sb.append("-User#").append(msg.mtin);
                        else
                            sb.append('-').append(msg.nwTraceSubtype);
                    }
                    case AppTrace -> sb.append('-').append(msg.traceSubtype);
                }

                sb.append('-').append(msg.ecuid).append(':')
                        .append(msg.apid).append(':').append(msg.ctid);
                sb.append(" #").append(msg.headerSize).append('/').append(msg.payload.length);
                sb.append(": ").append(msg.toStringPayload());

                System.out.println(sb);
            }
        } while (true);
    }

}

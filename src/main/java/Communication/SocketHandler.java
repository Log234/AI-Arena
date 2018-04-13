package Communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketHandler {
    Socket socket;
    Logger log;
    OutputStream out;
    InputStream in;

    SocketHandler(Socket socket, Logger log) {
        this.socket = socket;
        this.log = log;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException e) {
            log.severe(e.getLocalizedMessage());
        }
    }

    public boolean disconnect() {
        try {
            socket.close();
            log.info("Client disconnected: " + socket.getInetAddress());
            return true;
        } catch (IOException e) {
            log.severe(e.getLocalizedMessage());
        }
        return false;
    }

    public boolean write(String msg) {
        try {
            byte[] data = msg.getBytes("UTF-8");
            out.write(data.length);
            out.write(data);
            return true;
        } catch (IOException e) {
            log.severe(e.getLocalizedMessage());
        }
        return false;
    }

    public String read(long timeout) {
        try {
            int length = in.read();
            byte[] data = new byte[length];
            int read = in.read(data);
            if (read != length) {
                return null;
            } else {
                return new String(data, "UTF-8");
            }
        } catch (IOException e) {
            log.severe(e.getLocalizedMessage());
        }
        return null;
    }
}

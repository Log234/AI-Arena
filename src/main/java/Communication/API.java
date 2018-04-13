package Communication;

import java.net.Socket;
import java.util.logging.Logger;

public class API {
    MessageHandler client;
    Logger log;

    API(Socket socket, Logger log) {
        this.client = new MessageHandler(socket, log);
        this.log = log;
    }
}

package Communication;

import Concurrency.ThreadPool;

import java.net.Socket;
import java.util.logging.Logger;

public class SessionHandler {
    Logger log;
    private ThreadPool clients;

    public SessionHandler(int min, int max) {
         clients = new ThreadPool(1, 30);
    }

    void register(Socket socket) {

    }

}

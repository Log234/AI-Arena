package Communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ConnectionHandler implements Runnable {
    private int port;
    private ServerSocket socket;
    private SessionHandler sessionHandler;

    private Logger log;
    private boolean running;

    public ConnectionHandler(int port, SessionHandler sessionHandler, Logger log) {
        this.port = port;
        this.sessionHandler = sessionHandler;
        this.log = log;
        this.running = false;
    }

    public void run() {
        log.info("Starting the connection handler.");

        try {
            socket = new ServerSocket(port);


        } catch (IOException e) {
            log.severe("An error occurred while trying to start the connection handler.");
            log.severe(e.getLocalizedMessage());
        }

        running = true;
        log.info("Connection handler has started.");
        while (running) {
            try {
                Socket client = socket.accept();
                log.info("Accepting connection request from: " + client.getInetAddress());
                sessionHandler.register(client);
            } catch (IOException e) {}
        }
        log.info("Connection handler has stopped.");
    }

    public void stop() {
        log.info("Stopping the connection handler.");
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}

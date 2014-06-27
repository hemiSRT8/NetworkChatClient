package ua.khvorov.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.khvorov.gui.chatcarcass.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Client {

    private static PrintWriter writer;
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public Client() {
        run();
    }

    public void run() {
        String ip = Chat.askServerIp();
        int port = Integer.valueOf(Chat.askServerPort());

        Socket socket = null;

        try {
            socket = new Socket(ip, port);
            LOGGER.info("Socket was successfully created ({}:{})", ip, port);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                Chat.appendToChat(reader.readLine());
            }

        } catch (ConnectException connectException) {
            Chat.showServerIsOfflineMessage();
            LOGGER.info("Server is offline", connectException);
        } catch (IOException e1) {
            LOGGER.error("IO exception", e1);

            try {
                if (socket != null) {
                    socket.close();
                }
                LOGGER.info("Socket was closed due to IO exception,(ip {}) , (port {})", ip, port, e1);
            } catch (IOException e2) {
                LOGGER.error("IO exception while socket closing", e1);
            }
        }
    }

    public static void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
            LOGGER.debug("Message `{}` was sent", message);
        }
    }
}
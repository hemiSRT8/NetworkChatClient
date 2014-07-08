package ua.khvorov.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.khvorov.api.message.Message;
import ua.khvorov.bean.IPAndPort;
import ua.khvorov.exception.ServerOfflineException;
import ua.khvorov.service.ServerUpdateService;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class NetworkClient {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private ObjectOutputStream writer;
    private String ip;
    private int port;
    private ServerUpdateService serverUpdateService;


    public NetworkClient(IPAndPort ipAndPort, ServerUpdateService serverUpdateService) {
        ip = ipAndPort.getIp();
        port = ipAndPort.getPort();
        this.serverUpdateService = serverUpdateService;
    }

    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(ip, port);
            LOGGER.info("Socket was successfully created ({}:{})", ip, port);
            writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    serverUpdateService.verifyInputObject(reader.readObject());
                } catch (ClassNotFoundException e) {
                    LOGGER.error("ClassNotFoundException", e);
                }
            }

        } catch (SocketException connectException) {
            LOGGER.info("Server is offline", connectException);
            throw new ServerOfflineException("Server is offline", connectException);
        } catch (IOException e1) {
            LOGGER.error("IO exception", e1);
            try {
                if (socket != null) {
                    socket.close();
                }
                LOGGER.info(String.format("Socket was closed due to IO exception,(ip %s) , (port %d)", ip, port), e1);
            } catch (IOException e2) {
                LOGGER.error("IO exception while socket closing", e1);
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            writer.writeObject(message);
        } catch (IOException e) {
            LOGGER.error("IO exception", e);
        }

        LOGGER.debug("Message `{}` was sent", message);
    }
}

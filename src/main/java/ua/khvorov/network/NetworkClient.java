package ua.khvorov.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.khvorov.bean.IPAndPort;
import ua.khvorov.exception.ServerOfflineException;
import ua.khvorov.service.ServerUpdateService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class NetworkClient {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private PrintWriter writer;
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
            final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                serverUpdateService.incomingMessageUpdate(reader.readLine());
            }

        } catch (ConnectException connectException) {
            LOGGER.info("Server is offline", connectException);
            throw new ServerOfflineException("Server is offline", connectException);
        } catch (IOException e1) {
            LOGGER.error("IO exception", e1);
            try {
                socket.close();
                LOGGER.info(String.format("Socket was closed due to IO exception,(ip %s) , (port %d)", ip, port), e1);
            } catch (IOException e2) {
                LOGGER.error("IO exception while socket closing", e1);
            }
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
        LOGGER.debug("Message `{}` was sent", message);
    }
}

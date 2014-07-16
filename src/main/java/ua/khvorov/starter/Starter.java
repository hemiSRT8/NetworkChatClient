package ua.khvorov.starter;


import org.slf4j.*;
import ua.khvorov.service.ServerUpdateService;

public class Starter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        new ServerUpdateService().initUserUI();
        LOGGER.info("Client runs");
    }
}

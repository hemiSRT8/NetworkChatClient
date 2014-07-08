package ua.khvorov.service;

import org.slf4j.*;
import ua.khvorov.api.message.*;
import ua.khvorov.api.util.NickAndPassword;
import ua.khvorov.bean.IPAndPort;
import ua.khvorov.exception.ServerOfflineException;
import ua.khvorov.gui.window.*;
import ua.khvorov.network.NetworkClient;

import static ua.khvorov.api.message.MessageType.*;

public class ServerUpdateService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private Chat chat;
    private NetworkClient networkClient;

    public void initUserUI() {
        chat = new Chat(this);
        chat.showIpAndPortInputDialog();
        chat.showSignInInputDialog();
    }

    public void initNetworkClient(IPAndPort ipAndPort) {
        networkClient = new NetworkClient(ipAndPort, this);
        new Thread() {
            public void run() {
                try {
                    networkClient.run();
                } catch (ServerOfflineException e) {
                    InfoMessages.showErrorMessage("server is offline");
                }
            }
        }.start();
    }

    public void verifyInputObject(Object object) {
        Message message = (Message) object;
        Object messageType = message.getType();
        Object messageValue = message.getValue();
        if (messageType == TEXT_MESSAGE) {
            incomingMessageUpdate((String) messageValue);
        } else if (messageType == SIGN_IN) {
            InfoMessages.showSignInResult((Boolean) messageValue);
        }
    }

    private void incomingMessageUpdate(String msg) {
        chat.appendToChat(msg);
    }

    public void sendMessage(String text) {
        Message message = new Message<MessageType, String>(TEXT_MESSAGE, text);
        networkClient.sendMessage(message);

        LOGGER.debug("message `{}` was sent", text);
    }

    public void sendMessage(NickAndPassword nickAndPassword) {
        Message message = new Message<MessageType, NickAndPassword>(SIGN_IN, nickAndPassword);
        networkClient.sendMessage(message);
    }
}

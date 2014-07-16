package ua.khvorov.service;

import org.slf4j.*;
import ua.khvorov.api.entity.User;
import ua.khvorov.api.message.*;
import ua.khvorov.api.util.NickAndPassword;
import ua.khvorov.bean.IPAndPort;
import ua.khvorov.exception.ServerOfflineException;
import ua.khvorov.gui.window.Chat;
import ua.khvorov.network.NetworkClient;

import static ua.khvorov.api.message.MessageType.*;
import static ua.khvorov.gui.window.Chat.*;

public class ServerUpdateService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private Chat chat;
    private NetworkClient networkClient;

    public void initUserUI() {
        chat = new Chat(this);
        chat.showIpAndPortInputDialog();
    }

    public void initNetworkClient(IPAndPort ipAndPort) {
        networkClient = new NetworkClient(ipAndPort, this);

        new Thread() {
            public void run() {
                try {
                    networkClient.run();
                } catch (ServerOfflineException e) {
                    showInfoMessage("Server is offline", 0);
                    chat.getIpAndPortDialog().getDialog().setVisible(true);
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
        } else {
            boolean success = (Boolean) messageValue;

            if (messageType == SIGN_IN) {
                showInfoMessage(success,
                        "You are online!", "Not valid login/password");
                if (success) {
                    chat.getFrame().setVisible(true);
                } else {
                    chat.getSignInDialog().getDialog().setVisible(true);
                }
            } else if (messageType == REGISTRATION) {
                showInfoMessage(success,
                        "You was successfully registered,now you can log in !", "Nickname was already registered");

                if (success) {
                    chat.getSignInDialog().getDialog().setVisible(true);
                } else {
                    chat.getRegistrationDialog().getDialog().setVisible(true);
                }
            }
        }
    }

    private void incomingMessageUpdate(String msg) {
        chat.appendToChat(msg);
    }

    public void sendMessage(String text) {
        Message message = new Message<MessageType, String>(TEXT_MESSAGE, text.trim());
        networkClient.sendMessage(message);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("message `{}` was sent", text);
        }
    }

    public void sendMessage(NickAndPassword nickAndPassword) {
        Message message = new Message<MessageType, NickAndPassword>(SIGN_IN, nickAndPassword);
        networkClient.sendMessage(message);
    }

    public void sendMessage(User user) {
        Message message = new Message<MessageType, User>(REGISTRATION, user);
        networkClient.sendMessage(message);
    }

    public Chat getChat() {
        return chat;
    }
}

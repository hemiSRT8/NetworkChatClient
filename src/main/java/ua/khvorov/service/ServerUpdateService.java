package ua.khvorov.service;

import ua.khvorov.bean.IPAndPort;
import ua.khvorov.exception.ServerOfflineException;
import ua.khvorov.gui.window.Chat;
import ua.khvorov.gui.window.IpAndPortInputDialog;
import ua.khvorov.network.NetworkClient;

public class ServerUpdateService {
    private Chat chat;
    private NetworkClient networkClient;

    public void initUserUI() {
        chat = new Chat(this);
        chat.showInputDialog();

    }

    public void initNetworkClient(IPAndPort ipAndPort) {
        networkClient = new NetworkClient(ipAndPort, this);
        new Thread() {
            public void run() {
                try {
                    networkClient.run();
                } catch (ServerOfflineException e) {
                    chat.showServerIsOfflineMessage();
                }
            }
        }.start();
    }

    public void incomingMessageUpdate(String msg) {
        chat.appendToChat(msg);
    }

    public void sendMessage(String msg) {
        networkClient.sendMessage(msg);
    }
}

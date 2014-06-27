package ua.khvorov.gui.listeners;


import ua.khvorov.network.NetworkClient;
import ua.khvorov.service.ServerUpdateService;

import javax.swing.*;
import java.awt.event.*;

public class SendButtonListener {

    public static ActionListener constructListener(final JTextArea input, final ServerUpdateService serverUpdateService) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverUpdateService.sendMessage(input.getText());
                input.setText("");
            }
        };
    }
}

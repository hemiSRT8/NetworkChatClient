package ua.khvorov.gui.listeners;


import ua.khvorov.client.Client;

import javax.swing.*;
import java.awt.event.*;

public class SendButtonListener {

    public static ActionListener constructListener(final JTextArea input) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage(input.getText());
                input.setText("");
            }
        };
    }
}

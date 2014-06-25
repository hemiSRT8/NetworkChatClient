package ua.khvorov.gui.chatcarcass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.khvorov.gui.fonts.InputFont;
import ua.khvorov.gui.listeners.SendButtonListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;

public class Chat {

    private static JTextArea chatTextArea;
    private static JTextArea inputTextField;
    private static final Logger LOGGER = LoggerFactory.getLogger(Chat.class);

    public Chat() {
        mainFrame();
    }

    private JFrame mainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.add(mainPanel());

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setIconImage(new ImageIcon("resources\\NetworkChatIcon.png").getImage());
        mainFrame.pack();
        mainFrame.setSize(800, 400);
        mainFrame.setLocation(screenSize.width / 2 - (mainFrame.getWidth() / 2), //Center of the screen
                screenSize.height / 2 - (mainFrame.getHeight() / 2));
        mainFrame.setVisible(true);

        return mainFrame;
    }

    private JPanel mainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(new JScrollPane(chatTextArea()), BorderLayout.CENTER);
        mainPanel.add(inputTextField(), BorderLayout.PAGE_END);

        return mainPanel;
    }

    private JPanel inputTextField() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        inputTextField = new JTextArea();
        inputTextField.setCaretColor(RED);
        inputTextField.setFont(InputFont.getInputFont());

        panel.add(new JScrollPane(inputTextField), BorderLayout.CENTER);
        panel.add(sendButton(), BorderLayout.LINE_END);

        return panel;
    }

    private JTextArea chatTextArea() {
        chatTextArea = new JTextArea();
        chatTextArea.setEnabled(false);
        chatTextArea.setDisabledTextColor(BLACK);

        return chatTextArea;
    }

    private JButton sendButton() {
        JButton sendButton = new JButton("SEND");
        sendButton.addActionListener(SendButtonListener.constructListener(inputTextField));
        sendButton.setPreferredSize(new Dimension(100, 65));

        return sendButton;
    }

    public static void appendToChat(String message) {
        chatTextArea.append(message + "\n");
        LOGGER.debug("Message `{}` was successfully appended to chat", message);
    }
}
package ua.khvorov.gui.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.khvorov.gui.listeners.SendButtonListener;
import ua.khvorov.service.ServerUpdateService;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import static javax.swing.JOptionPane.showMessageDialog;
import static ua.khvorov.gui.util.CenterOfDisplay.getXPosition;
import static ua.khvorov.gui.util.CenterOfDisplay.getYPosition;

public class Chat {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JTextArea chatTextArea;
    private ServerUpdateService serverUpdateService;
    private JFrame frame;
    private JButton sendButton;
    private IpAndPortDialog ipAndPortDialog;
    private SignInDialog signInDialog;
    private RegistrationDialog registrationDialog;

    public Chat(ServerUpdateService serverUpdateService) {
        this.serverUpdateService = serverUpdateService;
        frame = initFrame();
    }

    private JFrame initFrame() {
        frame = new JFrame();
        frame.add(mainPanel());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("resources\\networkChatIcon.png").getImage());
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocation(getXPosition(frame), getYPosition(frame));
        frame.getRootPane().setDefaultButton(sendButton);
        frame.setVisible(false);

        return frame;
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

        JTextArea inputTextField = new JTextArea();
        inputTextField.setCaretColor(RED);

        panel.add(new JScrollPane(inputTextField), BorderLayout.CENTER);
        constructSendButton(inputTextField);
        panel.add(sendButton, BorderLayout.LINE_END);

        return panel;
    }

    private JTextArea chatTextArea() {
        chatTextArea = new JTextArea();
        chatTextArea.setEnabled(false);
        chatTextArea.setDisabledTextColor(BLACK);

        return chatTextArea;
    }

    private void constructSendButton(JTextArea inputTextField) {
        sendButton = new JButton("SEND");
        sendButton.addActionListener(SendButtonListener.constructListener(inputTextField, serverUpdateService));
        sendButton.setPreferredSize(new Dimension(100, 65));
    }

    public void appendToChat(String message) {
        chatTextArea.append(message + "\n");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Message `{}` was successfully appended to chat", message);
        }
    }

    public void showIpAndPortInputDialog() {
        ipAndPortDialog = new IpAndPortDialog(frame, serverUpdateService);
    }

    public void showSignInInputDialog() {
        signInDialog = new SignInDialog(frame, serverUpdateService, this);
    }

    public void showRegistrationInputDialog() {
        registrationDialog = new RegistrationDialog(frame, serverUpdateService);
    }

    /**
     * 0 - Info icon
     * 1 - Error icon
     */
    public static void showInfoMessage(String text, int icon) {
        showMessageDialog(null,
                text,
                "",
                icon);
    }

    public static void showInfoMessage(boolean success, String successText, String failText) {
        if (success) {
            showInfoMessage(successText, 1);
        } else {
            showInfoMessage(failText, 0);
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public IpAndPortDialog getIpAndPortDialog() {
        return ipAndPortDialog;
    }

    public SignInDialog getSignInDialog() {
        return signInDialog;
    }

    public RegistrationDialog getRegistrationDialog() {
        return registrationDialog;
    }
}
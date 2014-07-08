package ua.khvorov.gui.window;

import org.slf4j.*;
import ua.khvorov.gui.fonts.InputFont;
import ua.khvorov.gui.listeners.SendButtonListener;
import ua.khvorov.service.ServerUpdateService;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;

public class Chat {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JTextArea chatTextArea;
    private ServerUpdateService serverUpdateService;
    private JFrame frame;
    private JButton sendButton;

    public Chat(ServerUpdateService serverUpdateService) {
        this.serverUpdateService = serverUpdateService;
        frame = initFrame();
        frame.getRootPane().setDefaultButton(sendButton);
    }

    private JFrame initFrame() {
        JFrame frame = new JFrame();
        frame.add(mainPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setIconImage(new ImageIcon("resources\\networkChatIcon.png").getImage());
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocation(screenSize.width / 2 - (frame.getWidth() / 2), //Center of the screen
                screenSize.height / 2 - (frame.getHeight() / 2));

        frame.setVisible(true);
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
        inputTextField.setFont(InputFont.INPUT_FONT);

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
        LOGGER.debug("Message `{}` was successfully appended to chat", message);
    }

    public void showIpAndPortInputDialog() {
        new IpAndPortInputDialog(frame, serverUpdateService);
    }

    public void showSignInInputDialog() {
        new SignInInputDialog(frame, serverUpdateService);
    }
}
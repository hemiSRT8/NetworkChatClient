package ua.khvorov.gui.window;

import ua.khvorov.api.util.NickAndPassword;
import ua.khvorov.service.ServerUpdateService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ua.khvorov.gui.util.CenterOfDisplay.*;

public class SignInDialog {

    private JDialog dialog;

    public SignInDialog(final JFrame frame, final ServerUpdateService serverUpdateService, final Chat chat) {
        dialog = new JDialog(frame, "Sign in", true);
        dialog.setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2));
        JTextField nickname = new JTextField();
        JPasswordField password = new JPasswordField();
        fieldsPanel.add(new JLabel("Nickname"));
        fieldsPanel.add(nickname);
        fieldsPanel.add(new JLabel("Password"));
        fieldsPanel.add(password);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton signInButton = new JButton("Sign in");
        JButton exitButton = new JButton("Exit");
        signInButton.addActionListener(new SignInListener(nickname, password,
                serverUpdateService));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(signInButton);
        buttonPanel.add(exitButton);

        final JButton registrationButton = new JButton("Registration");
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                chat.showRegistrationInputDialog();
            }
        });

        dialog.add(fieldsPanel, BorderLayout.NORTH);
        dialog.add(buttonPanel, BorderLayout.CENTER);
        dialog.add(registrationButton, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setPreferredSize(new Dimension(600, 200));
        dialog.setResizable(false);
        dialog.setLocation(getXPosition(dialog), getYPosition(dialog));
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.getRootPane().setDefaultButton(signInButton);

        dialog.setVisible(true);
    }

    public JDialog getDialog() {
        return dialog;
    }

    /**
     * Listener
     */
    private class SignInListener implements ActionListener {

        private JTextField nickname;
        private JTextField password;
        private ServerUpdateService serverUpdateService;

        public SignInListener(JTextField nickname, JTextField password,
                              ServerUpdateService serverUpdateService) {
            this.nickname = nickname;
            this.password = password;
            this.serverUpdateService = serverUpdateService;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String nickname = this.nickname.getText();
            String password = this.password.getText();

            serverUpdateService.sendMessage(new NickAndPassword(nickname, password));

            dialog.setVisible(false);
        }
    }
}

package ua.khvorov.gui.window;


import ua.khvorov.api.util.NickAndPassword;
import ua.khvorov.service.ServerUpdateService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInInputDialog {

    public SignInInputDialog(JFrame frame, ServerUpdateService serverUpdateService) {
        JDialog dialog = new JDialog(frame, true);
        dialog.setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2));
        JTextField ipInputField = new JTextField();
        JTextField portInputField = new JTextField();
        fieldsPanel.add(new JLabel("Nickname"));
        fieldsPanel.add(ipInputField);
        fieldsPanel.add(new JLabel("Password"));
        fieldsPanel.add(portInputField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton nextButton = new JButton("Sign in");
        JButton exitButton = new JButton("Exit");
        nextButton.addActionListener(new GetIpAndPortListener(ipInputField, portInputField, dialog, serverUpdateService));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(nextButton);
        buttonPanel.add(exitButton);

        dialog.add(fieldsPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setPreferredSize(new Dimension(200,150));
        dialog.pack();
        dialog.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(screenSize.width / 2 - (dialog.getWidth() / 2), screenSize.height / 2 - (dialog.getHeight() / 2));
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.getRootPane().setDefaultButton(nextButton);

        dialog.setVisible(true);
    }

    public static class GetIpAndPortListener implements ActionListener {

        private JTextField nickname;
        private JTextField password;
        private JDialog dialog;
        private ServerUpdateService serverUpdateService;

        public GetIpAndPortListener(JTextField nickname, JTextField password, JDialog dialog, ServerUpdateService serverUpdateService) {
            this.nickname = nickname;
            this.password = password;
            this.dialog = dialog;
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

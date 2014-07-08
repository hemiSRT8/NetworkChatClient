package ua.khvorov.gui.window;


import ua.khvorov.bean.IPAndPort;
import ua.khvorov.gui.listeners.IPKeyListener;
import ua.khvorov.gui.listeners.PortKeyListener;
import ua.khvorov.service.ServerUpdateService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IpAndPortInputDialog {

    public IpAndPortInputDialog(JFrame frame, ServerUpdateService serverUpdateService) {
        JDialog dialog = new JDialog(frame, true);
        dialog.setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2));
        JTextField ipInputField = new JTextField();
        final JTextField portInputField = new JTextField();
        fieldsPanel.add(new JLabel("IP"));
        fieldsPanel.add(ipInputField);
        fieldsPanel.add(new JLabel("PORT"));
        fieldsPanel.add(portInputField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton nextButton = new JButton("Next");
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
        dialog.setPreferredSize(new Dimension(200, 130));
        dialog.pack();
        dialog.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(screenSize.width / 2 - (dialog.getWidth() / 2), screenSize.height / 2 - (dialog.getHeight() / 2));
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.getRootPane().setDefaultButton(nextButton);

        ipInputField.addKeyListener(new IPKeyListener(ipInputField, nextButton));
        portInputField.addKeyListener(new PortKeyListener(portInputField, nextButton));

        nextButton.setEnabled(false);

        dialog.setVisible(true);
    }

    public static class GetIpAndPortListener implements ActionListener {

        private JTextField ipField;
        private JTextField portField;
        private JDialog dialog;
        private ServerUpdateService serverUpdateService;

        public GetIpAndPortListener(JTextField ipField, JTextField portField, JDialog dialog, ServerUpdateService serverUpdateService) {
            this.ipField = ipField;
            this.portField = portField;
            this.dialog = dialog;
            this.serverUpdateService = serverUpdateService;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String ip = ipField.getText();
            Integer port = Integer.valueOf(portField.getText());
            serverUpdateService.initNetworkClient(new IPAndPort(ip, port));
            dialog.setVisible(false);
        }
    }
}

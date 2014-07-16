package ua.khvorov.gui.window;

import net.sourceforge.jdatepicker.impl.*;
import ua.khvorov.api.entity.User;
import ua.khvorov.service.ServerUpdateService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import static java.awt.BorderLayout.CENTER;
import static ua.khvorov.gui.util.CenterOfDisplay.*;

public class RegistrationDialog {

    private JDialog dialog;

    public RegistrationDialog(JFrame frame, ServerUpdateService serverUpdateService) {
        dialog = new JDialog(frame, "Registration", true);

        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(300, 200));
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocation(getXPosition(dialog), getYPosition(dialog));

        /**
         * Fields panel
         */
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(6, 2));

        fieldsPanel.add(new JLabel("Nickname"));
        JTextField nicknameInputField = new JTextField(15);
        fieldsPanel.add(nicknameInputField);

        fieldsPanel.add(new JLabel("Password"));
        JPasswordField passwordInputField = new JPasswordField(15);
        fieldsPanel.add(passwordInputField);

        fieldsPanel.add(new JLabel("City"));
        JTextField cityInputField = new JTextField(15);
        fieldsPanel.add(cityInputField);

        fieldsPanel.add(new JLabel("Date of birth"));
        JDatePickerImpl datePickerInputField = dateOfBirthPicker();
        fieldsPanel.add(datePickerInputField);

        fieldsPanel.add(new JLabel("Additional info"));
        JTextField additionalInfoInputField = new JTextField(15);
        fieldsPanel.add(additionalInfoInputField);

        /**
         * Buttons
         */
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new RegistrationListener(
                nicknameInputField, passwordInputField, cityInputField, datePickerInputField, additionalInfoInputField,
                serverUpdateService));

        fieldsPanel.add(confirmButton);
        fieldsPanel.add(exitButton());

        /**
         * Filling
         */
        dialog.add(fieldsPanel, CENTER);
        dialog.setVisible(true);
    }

    private JButton exitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return exitButton;
    }

    private JDatePickerImpl dateOfBirthPicker() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl dateOfBirthImpl = new JDatePanelImpl(model);

        return new JDatePickerImpl(dateOfBirthImpl);
    }

    public JDialog getDialog() {
        return dialog;
    }

    /**
     * Listener
     */
    private class RegistrationListener implements ActionListener {

        private JTextField nick;
        private JPasswordField password;
        private JTextField city;
        private JDatePickerImpl dateOfBirth;
        private JTextField additionalInfo;
        private ServerUpdateService serverUpdateService;

        private RegistrationListener(
                JTextField nick, JPasswordField password, JTextField city, JDatePickerImpl dateOfBirth,
                JTextField additionalInfo, ServerUpdateService serverUpdateService) {

            this.nick = nick;
            this.password = password;
            this.city = city;
            this.dateOfBirth = dateOfBirth;
            this.additionalInfo = additionalInfo;
            this.serverUpdateService = serverUpdateService;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String nick = this.nick.getText();
            String password = new String(this.password.getPassword());
            String city = this.city.getText();
            Date dateOfBirth = (Date) this.dateOfBirth.getModel().getValue();
            String additionalInfo = this.additionalInfo.getText();

            serverUpdateService.sendMessage(new User(nick, password, city, dateOfBirth, additionalInfo));

            dialog.setVisible(false);
        }
    }
}

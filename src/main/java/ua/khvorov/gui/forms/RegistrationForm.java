package ua.khvorov.gui.forms;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.GREEN;


public class RegistrationForm implements ActionListener {

    public RegistrationForm() {
        createFrame();
    }

    private JFrame frame;

    private JFrame createFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(300, 250);
        frame.setResizable(false);
        frame.setTitle("Registration");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - (frame.getWidth() / 2), //Center of the screen
                screenSize.height / 2 - (frame.getHeight() / 2));

        frame.add(mainPanel());

        frame.setVisible(true);
        return frame;
    }

    private JPanel mainPanel() {
        JPanel mainPanel = new JPanel();

        mainPanel.add(nicknamePanel());
        mainPanel.add(passwordPanel());
        mainPanel.add(cityPanel());
        mainPanel.add(infoPanel());
        mainPanel.add(dateOfBirthPanel());

        mainPanel.add(confirmRegistrationButton());

        return mainPanel;
    }

    private JPanel nicknamePanel() {
        JPanel nicknamePanel = new JPanel();

        JLabel nicknameLabel = new JLabel("Nickname");
        JTextField nicknameInput = new JTextField(15);

        nicknamePanel.add(nicknameLabel);
        nicknamePanel.add(nicknameInput);

        return nicknamePanel;
    }

    private JPanel passwordPanel() {
        JPanel passwordPanel = new JPanel();

        JLabel passwordLabel = new JLabel("Password");
        JTextField passwordInput = new JTextField(15);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);

        return passwordPanel;
    }

    private JPanel dateOfBirthPanel() {
        JPanel dateOfBirthPanel = new JPanel();

        JLabel dateOfBirthLabel = new JLabel("Date of birth");

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        dateOfBirthPanel.add(dateOfBirthLabel);
        dateOfBirthPanel.add(datePicker);

        return dateOfBirthPanel;
    }

    private JPanel cityPanel() {
        JPanel cityPanel = new JPanel();

        JLabel cityLabel = new JLabel("City");
        JTextField cityInput = new JTextField(15);

        cityPanel.add(cityLabel);
        cityPanel.add(cityInput);

        return cityPanel;
    }

    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel();

        JLabel infoLabel = new JLabel("Additional info");
        JTextField infoInput = new JTextField(15);

        infoPanel.add(infoLabel);
        infoPanel.add(infoInput);

        return infoPanel;
    }

    private JButton confirmRegistrationButton() {
        JButton signInButton = new JButton("Confirm");
        signInButton.setPreferredSize(new Dimension(100, 25));
        signInButton.setBackground(GREEN);
        signInButton.addActionListener(this);

        return signInButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
    }
}

package ua.khvorov.gui.listeners;

import ua.khvorov.validator.Validator;

import javax.swing.*;
import java.awt.event.*;

import static java.awt.Color.*;

public class PortKeyListener extends KeyAdapter {

    private static boolean enabled;
    private JTextField ipInputField;
    private JButton nextButton;

    public PortKeyListener(JTextField ipInputField, JButton nextButton) {
        this.ipInputField = ipInputField;
        this.nextButton = nextButton;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!Validator.portValidation(ipInputField.getText())) {
            ipInputField.setBorder(BorderFactory.createLineBorder(RED));
            enabled = false;
            nextButton.setEnabled(false);
        } else {
            ipInputField.setBorder(BorderFactory.createLineBorder(DARK_GRAY));
            enabled = true;
            if (IPKeyListener.isEnabled()) {
                nextButton.setEnabled(true);
            }
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }
}

package ua.khvorov.gui.window;

import static javax.swing.JOptionPane.*;

public class InfoMessages {

    public static void showErrorMessage(String text) {
        showMessageDialog(null,
                text,
                "",
                ERROR_MESSAGE);
    }

    public static void showInfoMessage(String text) {
        showMessageDialog(null,
                text,
                "",
                INFORMATION_MESSAGE);
    }

    public static void showSignInResult(boolean success) {
        if (success) {
            showInfoMessage("You are online!");
        } else {
            showErrorMessage("Not valid login/password");
        }
    }
}

package ua.khvorov.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern IP_PATTERN = Pattern.compile(
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private static final Pattern PORT_PATTERN = Pattern.compile("^\\d{4}$");

    public static boolean ipInputValidation(String ip) {
        if (ip.equals("localhost")) {
            return true;
        } else {
            Matcher matcher = IP_PATTERN.matcher(ip);
            return matcher.matches();
        }
    }

    public static boolean portValidation(String port) {
        Matcher matcher = PORT_PATTERN.matcher(port);

        return matcher.matches();
    }
}

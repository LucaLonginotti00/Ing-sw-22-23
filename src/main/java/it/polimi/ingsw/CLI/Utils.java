package it.polimi.ingsw.CLI;

import java.util.regex.Pattern;

/**
 * This class is used for some utils.
 */
public class Utils {

    private static final String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private static final String IP_REGEXP = "^(" + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + ")$";
    private static final Pattern IP_PATTERN = Pattern.compile(IP_REGEXP);

    /**
     * This method checks if the IP address is valid.
     * @param IP The IP address.
     * @return {@code True} if the IP address is valid, {@code False} if not.
     */
    public static boolean IPAddressIsValid(String IP){
        return IP != null && IP_PATTERN.matcher(IP).matches();
    }

    /**
     * This method checks if the server port is valid.
     * @param port The server port.
     * @return {@code True} if the server port is valid, {@code False} if not.
     */
    public static boolean portIsValid(int port){
        return port >= 1024 && port <= 65535;
    }
}

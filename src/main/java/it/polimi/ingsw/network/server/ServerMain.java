package it.polimi.ingsw.network.server;

import java.util.*;

/**
 * Class used to start the server on a given port.
 */
public class ServerMain {
    private static final int DefaultPort = 1250;
    private static final int MinPort = 1024;
    private static final int MaxPort = 65535;

    //port is followed by the number of desired port
    private static final String PortInfo = "port";
    private static final String LogInfo = "log";
    private static boolean IsLog = false;

    public static void main(String[] args){
        boolean error = false;
        int port = DefaultPort;
        List<String> info = new ArrayList<>(Arrays.asList(args));
        if (info.size() > 0) {
                if (info.contains(LogInfo))
                    IsLog = true;

            if (info.contains(PortInfo)){
                String portString = "";
                try {
                    portString = info.get(info.indexOf(PortInfo) + 1);
                } catch (Exception e) {
                    error = true;
                }

                try {
                    int tempPort = Integer.parseInt(portString);
                    if (tempPort >= MinPort && tempPort <= MaxPort) {
                        port = tempPort;
                    }
                    else {
                        error = true;
                    }
                    } catch (NumberFormatException e) {
                    error = true;
                }

                if (error) {
                    System.out.println("Port number is not valid, please check the available port numbers");
                    return;
                }
            }
        }
        Server server = new Server(port, IsLog);
        server.startServer();
    }
}
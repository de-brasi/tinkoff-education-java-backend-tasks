package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task6 {
    private Task6() {}

    public static void scanPorts() {
        final Map<Integer, String> busyPorts = getSomeServicesWithPort();

        ServerSocket serverSocket;
        DatagramSocket datagramSocket;

        LOGGER.info("Протокол\tПорт\tСервис");
        for (int port = PORT_MIN; port < PORT_MAX; port++) {

            try {
                serverSocket = new ServerSocket(port);
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.info("TCP\t" + port + "\t" + busyPorts.getOrDefault(port, ""));
            }

            try {
                datagramSocket = new DatagramSocket(port);
                datagramSocket.close();
            } catch (IOException e) {
                LOGGER.info("UDP\t" + port + "\t" + busyPorts.getOrDefault(port, ""));
            }

        }
    }

    @SuppressWarnings("MagicNumber")
    private static Map<Integer, String> getSomeServicesWithPort() {
        Map<Integer, String> res = new HashMap<>();

        res.put(3306, "MySQL Database");
        res.put(5432, "PostgreSQL Database");
        res.put(3389, "RDP");
        res.put(27017, "MongoDB Database");
        res.put(1521, "Oracle Database");
        res.put(23, "Telnet");
        res.put(110, "POP3");
        res.put(143, "IMAP");
        res.put(123, "NTP");
        res.put(445, "SMB");
        res.put(548, "AFP");
        res.put(8080, "HTTP proxy");
        res.put(1080, "SOCKS proxy");
        res.put(1701, "L2TP");
        res.put(5900, "VNC");

        return res;
    }

    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 49151;

    private final static Logger LOGGER = LogManager.getLogger();
}

package de.uulm.verteilte_systeme;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EchoClient {
    private final String host;
    private final int port;
    private Socket socket;


    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
    }

    public String sendMessage(String message) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketInputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            if (message != null) {
                output.println(message);
                return socketInputReader.readLine();
            }

        } catch (IOException exception) {
            System.out.println("Exception: " + Arrays.toString(exception.getStackTrace()));
        }
        return "";
    }
}
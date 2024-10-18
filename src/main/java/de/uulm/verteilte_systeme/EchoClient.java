package de.uulm.verteilte_systeme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class EchoClient {
    private final String host;
    private final int port;
    private Socket socket;
    private int timeoutDurationInMilliseconds = Integer.MAX_VALUE;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public EchoClient(String host, int port, int timoutDurationInMilliseconds) {
        this(host, port);
        this.timeoutDurationInMilliseconds = timoutDurationInMilliseconds;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(timeoutDurationInMilliseconds);
        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
        }
    }

    public String sendMessage(String message) {
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

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException exception) {
            System.out.println("Exception: " + Arrays.toString(exception.getStackTrace()));
        }
    }
}
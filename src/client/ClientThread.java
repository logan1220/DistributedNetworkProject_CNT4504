package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread implements Runnable {
    @Override
    public void run() {
        // Connect to the server and wait
        int port = 5002;
        String ip = "192.168.100.111";

        Socket sock;
        try {
            sock = new Socket(ip, port);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String message;

            while (!(message = in.readLine()).equals("BYE")) {	}

            long startTime = System.currentTimeMillis();
            out.println("1");
            out.println("BYE");
            while (!(message = in.readLine()).equals("BYE")) {	}
            long stopTime = System.currentTimeMillis();

            out.println("7");
            RunClientThread.time = (stopTime - startTime);

        } catch (UnknownHostException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}

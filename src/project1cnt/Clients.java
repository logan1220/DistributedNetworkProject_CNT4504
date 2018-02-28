/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1cnt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author logan
 */
public class Clients implements Runnable {
    
    String hostName = "127.0.0.1";
    int portNumber = 5001;
    String cmd;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    BufferedReader stdIn;
    
    public Clients(String cmd) throws IOException {
        try (Socket socket= new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            this.socket = socket;
            this.out = out;
            this.in = in;
            this.stdIn = stdIn;

        } catch (UnknownHostException e) { //display host error
            System.err.println(e);
            System.out.println("Don't know host");
            System.exit(8);
        } catch (IOException e) { //display IOE error
            System.err.println(e);
            System.out.println("Error creating socket");
            System.exit(9);
        }
    }
    
    @Override
    public void run() {
        long startTime = System.nanoTime();
        this.out.println(cmd);
        long endTime = System.nanoTime();
        
        Project1cnt.threadTimer((endTime-startTime));
    }
}

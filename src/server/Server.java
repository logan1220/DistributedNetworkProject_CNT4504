/*
 * Project 1
 * CNT 4504- Networks
 * Professor Sanjay Ahuja
 * Group 5- Logan Sirdevan, Wesley (Barrett) Tucker, Wafaa Safar, Chloe Cruz, Reggie Jackson, Madison Gourde
 * Due: Feburary 28, 2018
 * This is the server-side program which validates the connection of the host and accepts the command line argument requested and returns the response
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 */
public class Server {
    public static void main(String[] args) throws IOException { 
        
        if (args.length < 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(0);
        }
        
        int portNumber = Integer.parseInt(args[0]);
        
        System.out.println("Server started. Listening on port " + portNumber);
        
        //create server sockets 
        try (ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            
            System.out.println("Client connected on port " + portNumber + ". Servicing requests.");
            
            String inputLine;
            
            while((inputLine = in.readLine()) != null) {
                System.out.println("Recieved message: " + inputLine + " from " + clientSocket.toString());
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port" +
                    portNumber + " or listening for a connection.");
        } 
    }
}

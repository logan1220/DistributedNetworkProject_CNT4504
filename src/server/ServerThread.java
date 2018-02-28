/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author logan
 */
public class ServerThread implements Runnable {
    
    private Socket client;

    ServerThread(Socket clientSocket) {
        client = clientSocket;
    }
    
    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) 
        {
            System.out.println("Client connected..");

            String inputLine;

            while((inputLine = in.readLine()) != null) {
                System.out.println("Recieved message: " + inputLine + " from " + client.toString());
                out.println(runCmd(inputLine));
                //run command here
            }
            
            out.close();
            in.close();
            client.close();
            
        } catch (IOException io) {
            System.out.println(io);
            System.exit(99);
        }
    }
    
    public static String runCmd(String cmd) throws IOException {
        Process proc = Runtime.getRuntime().exec(cmd);
        String s = null;
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        while ((s = stdInput.readLine()) != null) {
            return s;
        }

        // read any errors from the attempted command
        while ((s = stdError.readLine()) != null) {
            return s;
        }
        
        return "";
    }//end of displayOutput method
    
}

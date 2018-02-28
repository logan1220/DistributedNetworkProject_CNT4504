/*
 * Project 1
 * CNT 4504- Networks
 * Professor Sanjay Ahuja
 * Group 5- Logan Sirdevan, Wesley (Barrett) Tucker, Wafaa Safar, Chloe Cruz, Reggie Jackson, Madison Gourde
 * Due: Feburary 28, 2018
 * This is a client-server programming project. This is the client side where a text menu is displayed to the user for information from the server
including the host current date and time, uptime, memory use, netstat, current users, and running processes. 
The user enters the server hostname as a command line argument when this client program is started. If no argument is given, 
the program will print an error and exit. Then the program will enter a loop to display the text manu, prompt the user for a command, test if the command that the user entered is valid, 
send that command request to the server on the host, get the response back from the server, and display the response to the user. 
 *The server should print out diagnostic messages about what it is doing. 
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String args[]) {
        //Must have command line args
        if (args.length < 2) {
            System.out.println(args.length);
            System.out.println("Invalid hostname! Aborting...");
            System.out.println("Usage: java Client.jar <host name> <port number>");
            System.exit(111);
        }

        System.out.println("CNT4504: Network Management Application using the Sockets API Project 1");

        System.out.println("Group 5: Wesley Tucker "
        + "| Logan Sirdevan "
        + "| Wafaa Safar |\n "
        + "Reggie Jackson "
        + "| Chloe Cruz "
        + "| Madison Gourde");

        int index = args.length - 1;

        String hostName = args[0];
        
        int portNumber = Integer.parseInt(args[1]);

        // Connect to the server
        System.out.println("Connecting to server...");
        try (Socket sock = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));) 
        {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String sMessage;
            String cMessage;

            // Main program loop
            while (true) {
                // Get server message
                while (!(sMessage = in.readLine()).equals("BYE")) {
                    if (!sMessage.equals("null")) {
                        System.out.println(sMessage);
                    }
                } // end while

                showMenu();

                // Send a message to server
                if (args.length > 2) {
                    out.println(args[index]);
                    System.out.println("cmd: " + (args[index]));
                    out.println("BYE");
                    index--;
                    if (index < 2) {
                        break;
                    }
                } else {
                    cMessage = stdIn.readLine();
                    if (cMessage != null) {
                        out.println(cMessage);
                        out.println("BYE");

                        // If message is 7 then quit app
                        if (cMessage.equals("7")) {
                            System.out.println("Thanks for playing");
                            break;
                        }
                    } // end if
                }
            } // end main program loop
        } catch (UnknownHostException e) {
                System.err.println("Unable to connect to host at: " + hostName + ":" + portNumber);
                System.exit(1);
        } catch (IOException e) {
                System.err.print(e.getMessage());
                System.exit(1);
        } // end try/catch
    }// end Main

    // Display the main menu
    private static void showMenu() {
        System.out.println("Enter Selection:");
        System.out.println("1.   Host current Date and Time");
        System.out.println("2.   Host uptime");
        System.out.println("3.   Host memory use");
        System.out.println("4.   Host Netstat");
        System.out.println("5.   Host current users");
        System.out.println("6.   Host running processes");
        System.out.println("7.   Quit");
    }// end displayMenu
}// end MainClient

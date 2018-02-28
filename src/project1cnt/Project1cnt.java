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
package project1cnt;

import java.io.*;
import java.util.Scanner;
import java.net.Socket;
import java.net.UnknownHostException;

public class Project1cnt {
    public static double startTime;
    public static double finishTime;
    public static double totalTime;
    public static double meanTime;
    public static Scanner scan;
    public static BufferedReader stdIn;
    
   
    public static void main(String[] args) throws IOException {
        
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("CNT4504: Network Management Application using the Sockets API Project 1");
        
        System.out.println("Group 5: Wesley Tucker "
                + "| Logan Sirdevan "
                + "| Wafaa Safar |\n "
                + "Reggie Jackson "
                + "| Chloe Cruz "
                + "| Madison Gourde");
        
        if (args.length != 2 ) {
            System.out.println("Usage: java Project1cnt <hostname> <portnumber>");
            System.exit(1);
        }
        
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try (Socket socket= new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) 
        {
            //creates the socket with the hostname and port number
            boolean yes = true;
            
            while (true) {
                String input;
                String userInput;
                menuDisplay();
                
                while((userInput = stdIn.readLine()) != null) {
                    //send info to server
                    String choice = userChoice(userInput);
                    System.out.println("here1");
                    if (choice != null) {
                        out.println(choice);
                    } else {
                        break;
                    }
                    System.out.println("here2");
                    while((input = in.readLine()) != null) {
                        System.out.println(input);
                        
                        break;
                    }
                    break;
                }
            }
        } catch (UnknownHostException e) { //display host error
            System.err.println(e);
            System.out.println("Don't know host");
            System.exit(2);
        } catch (IOException e) { //display IOE error
            System.err.println(e);
            System.out.println("io error");
            System.exit(3);
        }
    }//end of main method
    
    /*
    * This method takes in the choice of command of the user into the variable 'choice' and performs that command based on the case of the choice
    */
    public static String userChoice(String choice) throws IOException {
        switch (choice) {
            case "1":
                //if date then ask for clients
                createThreads("date");
                return "date";
            case "2":
                return "uptime";
            case "3":
                return "free -m";
            case "4":
                 //if netstat then ask for clients
                createThreads("netstat -a");
                return "netstat -a";
            case"5":
                return "users";
            case"6":
                return "ps -aux | less";
            case"7":
                System.exit(0);
                break;
            default:
                //if the user enters the value 0 or anything greater than 7 or any letters
                System.out.print("\nInvalid choice! Please enter a number 1-7: \n");
                return null;
        }//end switch
        
        return "";
                
    }//end of userChoice method
    
    public static void menuDisplay() {
        System.out.print("\n1.Host current Date and Time\n2.Host uptime\n3.Host memory use\n4.Host Netstat\n5.Host current users\n6.Host running processes\n7.Quit");
        System.out.print("\nEnter your choice: \n\n");
    }//menuDisplay
    
    public static void createThreads(String cmd) throws IOException {
        //Scanner input = new Scanner(System.in);
       
        System.out.println("How many clients should run this command?");
        
        String number = stdIn.readLine();
        
        try {
            int clientAgents = Integer.parseInt(number);
            
            for(int i=0; i < clientAgents; i++) {
                Clients thread = new Clients(cmd);
                thread.run();
            }
            
            System.out.println(number + " clients ran with a mean response time of " + (totalTime/clientAgents) + " nanoseconds");
        } catch (NumberFormatException e) {
            System.out.println("Number not recognized, returning..");
            return;
        }
    }
    
    public static void threadTimer(long totalTimeCA) {
        totalTime += totalTimeCA;
        System.out.println(totalTimeCA);
    }
    
}//end of project
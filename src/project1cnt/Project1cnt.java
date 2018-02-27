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
   
    public static void main(String[] args) throws IOException {
        System.out.println("CNT4504: Network Management Application using the Sockets API Project 1");
        
        System.out.println("Group 5: Wesley Tucker | Logan Sirdevan | Wafaa Safar\n Reggie Jackson | Chloe Cruz | Madison Gourde");
        
        //if (args.length != 2 ) {
           // System.out.println("Usage: java Project1cnt <hostname> <portnumber>");
            //System.exit(1);
        //4}
        
        displayPrompt();
        
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try (Socket socket= new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) { 
            //creates the socket with the hostname and port number
            boolean yes = true;
            
            do{
            
                displayPrompt();
            
            }while(yes);
            
            String userInput;
            while((userInput = stdIn.readLine()) != null) {
                //send info to server
                out.println(userInput);
            }
        } catch (UnknownHostException e) { //display host error
            System.err.println(e);
            System.out.println("Don't know host");
            System.exit(2);
        } catch (IOException e) { //display IOE error
            System.err.println(e);
            System.out.println("Error creating socket");
            System.exit(3);
        }
    }//end of main method
    
    /*
    * This method displays the prompt and waits for the user to pick the command
    */
    public static void displayPrompt() throws IOException {
        menuDisplay();
        scan = new Scanner(System.in);
        
        Boolean invalid = true;
        
        while(invalid) {
            System.out.print("\n1.Host current Date and Time\n2.Host uptime\n3.Host memory use\n4.Host Netstat\n5.Host current users\n6.Host running processes\n7.Quit");
            System.out.print("\nEnter your choice: \n\n");
            String choice = scan.next();
            int length= choice.length();
            
            if (choice.isEmpty()) { //if user enters nothing, the program will stop running
                System.out.print("Error: No command line argument chosen");
                System.exit(0);
            }
            
            if (length == 1){
                startTime= System.nanoTime(); //starts the timer for how long it takes for the command to run
                userChoice(choice);
            } else { //if user tries to enter a double-digit number
                System.out.println("\nInvalid command line argument choice. Try again!");
                invalid=true;
            }
        }//end while loop
    }//end of displayPrompt method
    
    /*
    * This method takes in the choice of command of the user into the variable 'choice' and performs that command based on the case of the choice
    */
    public static void userChoice (String choice) throws IOException {
		
        switch (choice){

        case"1":
            displayOutput("date");
            //display time respoonse to the user
            finishTime = System.nanoTime(); //calculates the time the program ran for
            totalTime = (finishTime - startTime); //calculates the total by taking the finish time and subtracting the start time from it
            System.out.printf("Time Spent: " + totalTime + " nanoseconds\n"); //prints the total time to the user
            menuDisplay();
            break;
        case"2":
            displayOutput("uptime");
            menuDisplay();
            break;
        case"3":
            displayOutput("free -m");
            menuDisplay();
            break;
        case"4":
            displayOutput("netstat -a");
             //display time respoonse to the user
            finishTime = System.nanoTime(); //calculates the time the program ran for
            totalTime = (finishTime - startTime); //calculates the total by taking the finish time and subtracting the start time from it
            System.out.printf("Time Spent: " + "%.2f\n" + totalTime + " seconds"); //prints the total time to the user
            menuDisplay();
            break;
        case"5":
            displayOutput("users"); //or cmd "who" ???
            menuDisplay();
            break;
        case"6":
            displayOutput("ps -aux | less");
            menuDisplay();
            break;
        case"7":
            System.exit(0);
            break;
        default:
            //if the user enters the value 0 or anything greater than 7 or any letters
            System.out.print("\nInvalid choice! Please enter a number 1-7: \n");
            choice = scan.next();
            userChoice(choice);
        }//end switch
                
    }//end of userChoice method
    
    public static void displayOutput(String cmd) throws IOException {
        Process proc = Runtime.getRuntime().exec(cmd);
        String s = null;
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
        }

        // read any errors from the attempted command
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }//end of displayOutput method

    public static void menuDisplay() {
     
        System.out.print("\n1.Host current Date and Time\n2.Host uptime\n3.Host memory use\n4.Host Netstat\n5.Host current users\n6.Host running processes\n7.Quit");
        System.out.print("\nEnter your choice: \n\n");
    
    }//menuDisplay
    
}//end of project
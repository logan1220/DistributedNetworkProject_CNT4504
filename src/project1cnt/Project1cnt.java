package project1cnt;

import java.io.*;
import java.util.Scanner;

public class Project1cnt {

    public static Scanner scan;
   
    public static void main(String[] args) throws IOException {
        	
        scan = new Scanner(System.in);
        System.out.print("Group 5\n\n");
        System.out.print("Wesley Tucker | Reggie Jackson | Logan Sirdevan\nChloe Cruz | Madison Gourde | Wafaa Safar\n\n");
        
        while(true){
            
            System.out.print("\n1.Host current Date and Time\n2.Host uptime\n3.Host memory use\n4.Host Netstat\n5.Host current users\n6.Host running processes\n7.Quit");
            System.out.print("\nEnter your choice: ");
            String choice = scan.next();
            userChoice(choice);
        
        }//end while loop

    }//end main
	
    public static void userChoice (String choice){
		
		switch (choice){
                    
		case"1": 		
			break;
		case"2":
			break;
		case"3":
			break;
		case"4":
			break;
		case"5":	
			break;
		case"6":
			break;
		case"7":
			break;
		default:
			System.out.print("Invalid choice! Enter a number 1-7: ");
                        
		}//end switch
                
	}//end userChoice
    
}//end project1cnt

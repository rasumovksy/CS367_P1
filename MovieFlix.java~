// TODO *** add comments as specified in the commenting guide ***

import java.util.*;

public class MovieFlix {

    public static void main(String[] args) {

        // TODO *** steps 1 - 3 of the main method ***

        Scanner stdin = new Scanner(System.in);  //for console input

        boolean done = false;
        while (!done) {
            System.out.print("Enter option (cdprswx): ");
            String input = stdin.nextLine();

            //only do something if the user enters at least one character
            if (input.length() > 0) {
                char choice = input.charAt(0); //strip off option character
                String remainder = "";         //will hold the remaining input
                if (input.length() > 1) {      //if there is an argument
                    //trim off any leading or trailing spaces
                    remainder = input.substring(1).trim(); 

                    switch (choice) { //the commands that have arguments
    
                    case 'c':
                        // TODO *** implement this option ***
                        break;
    
                    case 'p':
                        // TODO *** implement this option ***
                        break;
    
                    case 'r':
                        // TODO *** implement this option ***
                        break;
    
                    case 's':
                        // The following code reads in a comma-separated sequence 
                        // of strings.  If there are exactly two strings in the 
                        // sequence, the strings are assigned to name1 and name2.
                        // Otherwise, an error message is printed.
                        String[] tokens = remainder.split("[,]+");
                        if (tokens.length != 2) {
                            System.out.println("need to provide exactly two names");
                        }
                        else {
                            String name1 = tokens[0].trim();
                            String name2 = tokens[1].trim();
                            
                            // TODO code to implement this option ***
                        }
                        break;
    
                    case 'w':
                        // TODO *** implement this option ***
                        break;
    
                    default: //ignore invalid commands
                        System.out.println("Incorrect command.");
                        break;
                    
                    } // end switch
                } // end if
                else { //if there is no argument
                    switch (choice) { //the commands without arguments
                    
                    case 'd': 
                        // TODO to implement this option ***
                        break;
                        
                    case 'x':
                        done = true;
                        System.out.println("exit");
                        break;
                        
                    default:  //a command with no argument
                        System.out.println("Incorrect command.");
                        break;
                    } //end switch
                } //end else  
           } //end if
        } //end while
    } //end main

}

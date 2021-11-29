/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gradebook {
	
	//some static variables to use in my functions at the bottom
	public static List<Assignment> gradebook = new ArrayList<>();
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		System.out.println("Welcome to the Gradebook");
		
		int menuChoice = 0;
		String menu = "0 - Add grades" + "\n" +
						"1 - Remove grades" + "\n" +
						"2 - Print grades" + "\n" +
						"3 - Print to File\n" +
						"4 - Read from File\n" +
						"5 - To MySQL\n" +
						"6 - From MySQL\n" +
						"7 - MySQL Search\n" +
						"8 - Exit program" + "\n";
		
		//give the user menu options until they make a valid selection from the options
		do {
			
			try {
				System.out.println("Please select an option from below: ");
				System.out.println(menu);
				menuChoice = Integer.parseInt(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println("You must enter a valid number for the selection.");
				continue;
			}
			
			
			switch(menuChoice) {
			case 0:
				System.out.println("You selected add grade");
				GradebookHelper.addGrade();
				break;
			case 1:
				System.out.println("You selected remove grade");
				GradebookHelper.removeGrade();
				break;
			case 2:
				System.out.println("You selected print grades");
				GradebookHelper.printGrades();
				System.out.println("");
				break;
			case 3:
				System.out.println("You selected you selected print to file");
				System.out.println("What would you like to name the file? (do not include the file extension)");
				String filename = sc.nextLine();
				
				//call the function to save the gradebook to file
				try {
					GradebookIO.save(filename);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("");
				break;
			case 4:
				System.out.println("You selected read from file");
				System.out.println("Which file would you like to read from? (do not include the file extension)");
				String readFilename = sc.nextLine();
				
				//call function to read file to gradebook
				try {
					GradebookIO.readFile(readFilename);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("");
				break;
			case 5:
				System.out.println("You selected to MySQL");
				GradebookHelper.printQuizAverage();
				System.out.println("");
				break;
			case 6:
				System.out.println("You selected from MySQL");
				GradebookHelper.printReadings();
				System.out.println("");
				break;
			case 7:
				System.out.println("You selected MySQL search");
				GradebookHelper.printConcepts();
				System.out.println("");
				break;
			}
		} while(menuChoice != 8);
		
		sc.close();
	}
}
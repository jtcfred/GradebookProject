/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gradebook {
	
	//some static variables to use in my functions at the bottom
	static List<Assignment> gradebook = new ArrayList<>();
	static int maxSize = 0;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		System.out.println("Welcome to the Gradebook");
		//make sure the size of the gradebook from user input is valid
		do {
			try {
				System.out.println("Enter the size of the gradebook (max of 20, min of 1): ");
				maxSize = Integer.parseInt(sc.nextLine());
				if(maxSize > 20 || maxSize < 1) {
					System.out.println("Please enter a size in the range of [1,20]");
					continue;
				}
			} catch(NumberFormatException e) {
				System.out.println("You must enter a valid number for the size.");
				continue;
			}
			//if it makes it here, there were no problems and the input was valid, so break
			//out of the loop
			break;
		} while(true);
		
		
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
				GradebookHelper.printAverage();
				System.out.println("");
				break;
			case 4:
				System.out.println("You selected read from file");
				GradebookHelper.printExtrema();
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
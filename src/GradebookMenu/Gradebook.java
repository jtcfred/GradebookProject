/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookMenu;

import java.util.Scanner;

public class Gradebook {
	
	//some static variables to use in my functions at the bottom
	static Assignment[] gradebook;
	static int index = 0;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		System.out.println("Welcome to the Gradebook");
		int size = 0;
		//make sure the size of the gradebook from user input is valid
		do {
			try {
				System.out.println("Enter the size of the gradebook (max of 20, min of 1): ");
				size = Integer.parseInt(sc.nextLine());
				if(size > 20 || size < 1) {
					System.out.println("Pleaes enter a size in the range of [1,20]");
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
		
		//initialize the gradebook with the specified size
		gradebook = new Assignment[size];
		
		int menuChoice = 5;
		String menu = "0 - Add grades" + "\n" +
						"1 - Remove grades" + "\n" +
						"2 - Print grades" + "\n" +
						"3 - Print score average" +"\n" +
						"4 - Print highest/lowest score" + "\n" +
						"5 - Print quiz question average" + "\n" +
						"6 - Print discussion associated readings" + "\n" +
						"7 - Print program concepts" + "\n" + 
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
				GradebookHelper.addGrade(gradebook, index, sc);
				break;
			case 1:
				System.out.println("You selected remove grade");
				GradebookHelper.removeGrade(gradebook, index, sc);
				break;
			case 2:
				System.out.println("You selected print grades");
				GradebookHelper.printGrades(gradebook, index);
				System.out.println("");
				break;
			case 3:
				System.out.println("You selected you selected print average");
				GradebookHelper.printAverage(gradebook, index);
				System.out.println("");
				break;
			case 4:
				System.out.println("You selected print extrema");
				GradebookHelper.printExtrema(gradebook, index);
				System.out.println("");
				break;
			case 5:
				System.out.println("You selected print quiz average");
				GradebookHelper.printQuizAverage(gradebook, index);
				System.out.println("");
				break;
			case 6:
				System.out.println("You selected print readings");
				GradebookHelper.printReadings(gradebook, index);
				System.out.println("");
				break;
			case 7:
				System.out.println("You selected print concepts");
				GradebookHelper.printConcepts(gradebook, index);
				System.out.println("");
				break;
			}
		} while(menuChoice != 8);
		
		sc.close();
	}
}
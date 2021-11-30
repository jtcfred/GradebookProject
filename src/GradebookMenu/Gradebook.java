/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookMenu;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import DBManagement.*;

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
				GradebookDB.insertAll();
				System.out.println("Grades successfully inserted");
				break;
			case 6:
				System.out.println("You selected from MySQL");
				GradebookDB.getAll();
				System.out.println("Grades successfully retrieved from DB");
				break;
			case 7:
				System.out.println("You selected MySQL search, what would you like to search for?");
				int subMenuChoice = 0;
				String subMenu = "0 - All quizzes" + "\n" +
								"1 - All programs" + "\n" +
								"2 - All discussions" + "\n" +
								"3 - Range of score\n" +
								"4 - Range of due dates\n" +
								"5 - All grades with even score\n";
				
				//give the user menu options until they make a valid selection from the options
				do {
					try {
						System.out.println("Please select an option from below: ");
						System.out.println(subMenu);
						subMenuChoice = Integer.parseInt(sc.nextLine());
					}catch(NumberFormatException e) {
						System.out.println("You must enter a valid number for the selection.");
						continue;
					}
				System.out.println("");
				
				} while(subMenuChoice > 5 || subMenuChoice < 0);
				
				switch(subMenuChoice) {
				case 0:
					GradebookDB.allQuizzes();
					break;
				case 1:
					GradebookDB.allPrograms();
					break;
				case 2:
					GradebookDB.allDiscussions();
					break;
				case 3:
					double lower = -1;
					do {
						try {
							System.out.println("Enter the lower score for the range (inclusive): ");
							lower = Double.parseDouble(sc.nextLine());
						}catch(NumberFormatException e) {
							System.out.println("You must enter a valid number for the selection.");
							continue;
						}
					System.out.println("");
					
					} while(lower < 0 && lower > 100);
					
					double upper = -1;
					do {
						try {
							System.out.println("Enter the upper score for the range (inclusive): ");
							upper = Double.parseDouble(sc.nextLine());
						}catch(NumberFormatException e) {
							System.out.println("You must enter a valid number for the selection.");
							continue;
						}
					System.out.println("");
					
					} while(upper < lower && upper > 100);
					
					GradebookDB.rangedScore(lower, upper);
					break;
				case 4:
					LocalDate lowerDate;
					do {
						try {
							System.out.println("Enter the lower date for the range (yyyy-mm-dd)(inclusive): ");
							lowerDate = LocalDate.parse(sc.nextLine());
						}catch(DateTimeParseException e) {
							System.out.println("You must enter a valid date for the selection.");
							continue;
						}
					System.out.println("");
					break;
					} while(true);
					
					LocalDate upperDate = null;
					do {
						try {
							System.out.println("Enter the upper date for the range (yyyy-mm-dd)(inclusive): ");
							upperDate = LocalDate.parse(sc.nextLine());
						}catch(DateTimeParseException e) {
							System.out.println("You must enter a valid date for the selection.");
							continue;
						}
					System.out.println("");
					break;
					} while(lowerDate.compareTo(upperDate) > 0);
					
					GradebookDB.rangedDate(lowerDate, upperDate);
					break;
				case 5:
					GradebookDB.evenScore();
					break;
				}
				break;
			}
		} while(menuChoice != 8);
		
		sc.close();
	}
}
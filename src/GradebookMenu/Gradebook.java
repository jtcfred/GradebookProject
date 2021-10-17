package GradebookMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Assignments.*;
import GradebookExceptions.*;

public class Gradebook {
	
	static Assignment[] gradebook;
	static int index = 0;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		System.out.println("Welcome to the Gradebook");
		int size = 0;
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
				addGrade();
				break;
			case 1:
				System.out.println("You selected remove grade");
				removeGrade();
				break;
			case 2:
				System.out.println("You selected print grades");
				printGrades();
				System.out.println("");
				break;
			case 3:
				System.out.println("You selected you selected print average");
				printAverage();
				System.out.println("");
				break;
			case 4:
				System.out.println("You selected print extrema");
				printExtrema();
				System.out.println("");
				break;
			case 5:
				System.out.println("You selected print quiz average");
				printQuizAverage();
				System.out.println("");
				break;
			case 6:
				System.out.println("You selected print readings");
				printReadings();
				System.out.println("");
				break;
			case 7:
				System.out.println("You selected print concepts");
				printConcepts();
				System.out.println("");
				break;
			}
		} while(menuChoice != 8);
		
		sc.close();
	}
	
	static double getScoreInput() {
		double score;
		do {
			try {
				System.out.println("Enter the score as a double: ");
				score = Double.parseDouble(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println("You must enter a valid score.");
				continue;
			}
			if(score < 0 || score > 100) {
				System.out.println("Please make sure the score is within valid range.");
				continue;
			}
			break;
		}while(true);
		return score;
		
	}
	
	static char getLetter(double score) {
		char letter;
		if(score >= 90) {
			letter = 'A';
		} else if(score >= 80) {
			letter = 'B';
		} else if(score >= 70) {
			letter = 'C';
		} else if(score >= 60) {
			letter = 'D';
		} else {
			letter = 'F';
		}
		return letter;
	}
	
	static String getNameInput() {
		String name;
		System.out.println("Enter the name of the assignment");
		name = sc.nextLine();
		return name;
	}
	
	static LocalDate getDueDateInput() {
		LocalDate dueDate;
		do {
			try {
				System.out.println("Enter the due date in the format: " + "\n" +
						"yyyy-mm-dd");
				dueDate = LocalDate.parse(sc.nextLine());
			}catch(DateTimeParseException e) {
				System.out.println("You must enter a valid due date.");
				continue;
			}
			break;
		}while(true);
		return dueDate;
	}
	
	//Adds a grade at index in the gradebook
	static void addGrade() {
		try {
			if(index == gradebook.length) {
				throw new GradebookFullException();
			}
		} catch(GradebookFullException e) {
			System.out.println("The gradebook is full! You cannot add any more grades.");
			return;
		}
		int input = -1;
		do {
			try {
				System.out.println("Which type of grade would you like to add: ");
				System.out.println("0 - Quiz" + "\n" +
									"1 - Discussion" + "\n" +
									"2 - Program");
				input = Integer.parseInt(sc.nextLine());
			} catch(NumberFormatException e) {
				System.out.println("You must enter a valid number for the selection.");
				continue;
			}
			if(input == 0) {
				
				//Get the score
				double score = Gradebook.getScoreInput();
				
				//get the letter grade
				char letter = Gradebook.getLetter(score);
				
				//get the name
				String name = Gradebook.getNameInput();
				
				//get the due date
				LocalDate dueDate = Gradebook.getDueDateInput();
				
				//get the question count
				int questionCount;
				do {
					try {
						System.out.println("Enter the number of questions in the quiz:");
						questionCount = Integer.parseInt(sc.nextLine());
					}catch(NumberFormatException e) {
						System.out.println("You must enter a valid question amount.");
						continue;
					}
					if(questionCount < 0) {
						System.out.println("Please make sure the question amount is within valid range.");
						continue;
					}
					break;
				}while(true);
				
				//Make the quiz object and add to the gradebook
				Quiz quiz = new Quiz(score, letter, name, dueDate, questionCount);
				gradebook[index] = quiz;
				index++;
				break;
			}
			if(input == 1) {
				
				//Get the score
				double score = Gradebook.getScoreInput();
				
				//get the letter grade
				char letter = Gradebook.getLetter(score);
				
				//get the name
				String name = Gradebook.getNameInput();
				
				//get the due date
				LocalDate dueDate = Gradebook.getDueDateInput();
				
				//get the reading
				System.out.println("Enter the name of the reading:");
				String reading = sc.nextLine();
				
				Discussion discussion = new Discussion(score, letter, name, dueDate, reading);
				gradebook[index] = discussion;
				index++;
				break;
			}
			if(input == 2) {
				
				//Get the score
				double score = Gradebook.getScoreInput();
				
				//get the letter grade
				char letter = Gradebook.getLetter(score);
				
				//get the name
				String name = Gradebook.getNameInput();
				
				//get the due date
				LocalDate dueDate = Gradebook.getDueDateInput();
				
				//get the concept
				System.out.println("Enter the name of the concept:");
				String concept = sc.nextLine();
				
				Program program = new Program(score, letter, name, dueDate, concept);
				gradebook[index] = program;
				index++;
				break;
			}
			//if it makes it here, they did not make a valid selection
			System.out.println("Please make a valid selection for which grade you would like to add.");
		} while(true);
	}
	
	static void removeGrade() {
		
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch (GradebookEmptyException e) {
			System.out.println("The gradebook is currently empty. There are no grades to remove");
			return;
		}
		System.out.println("Enter the name of the grade you want to remove: ");
		String name = sc.nextLine();
		int i;
		for(i = 0; i < index; i++) {
			if(name.equals(gradebook[i].getName())) {
				//shift all grades after the one we want to remove, 1 to the left
				for(int j = i; j < index; j++) {
					gradebook[j] = gradebook[j + 1];
				}
				break;
			}
		}
		try {
			if(i == index) {
				throw new InvalidGradeException();
			}
		} catch(InvalidGradeException e) {
			System.out.println("A grade with that name is not in the gradebook.");
			return;
		}
	}
	
	static void printGrades() {
		System.out.println("\n");
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no grades to print.");
			return;
		}
		for(int i = 0; i < index; i++) {
			System.out.println(gradebook[i].toString() + "\n");
		}
	}
	
	static void printAverage() {
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no grades to average.");
			return;
		}
		double total = 0;
		for(int i = 0; i < index; i++) {
			total += gradebook[i].getScore();
		}
		System.out.println("Average: " + (total / (index)));
	}
	
	static void printExtrema() {
		
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		double max, min;
		max = min = gradebook[0].getScore();
		for(int i = 1; i < index; i++) {
			if(gradebook[i].getScore() > max) {
				max = gradebook[i].getScore();
			}
			if(gradebook[i].getScore() < min) {
				min = gradebook[i].getScore();
			}
		}
		System.out.println("The maximum score in the gradebook is: " + max);
		System.out.println("The minimum score in the gradebook is: " + min);
	}
	
	static void printQuizAverage() {
		double avg = 0;
		int total = 0;
		for(int i = 0; i < index; i++) {
			if(gradebook[i] instanceof Quiz) {
				total++;
				Quiz temp = (Quiz)gradebook[i];
				avg += temp.getQuestionCount();	
			}
		}
		if(total == 0) {
			System.out.println("There are no quizzes yet.");
		} else {
			avg /= total;
			System.out.println("The average number of quiz questions is:" + avg);
		}
		
	}
	
	static void printReadings() {
		int total = 0;
		for(int i = 0; i < index; i++) {
			if(gradebook[i] instanceof Discussion) {
				Discussion temp = (Discussion)gradebook[i];
				System.out.println("" + total + ". " + temp.getReading());
				total++;
			}
		}
		if(total == 0) {
			System.out.println("There are no Discussions yet.");
		}
	}
	
	static void printConcepts() {
		int total = 0;
		for(int i = 0; i < index; i++) {
			if(gradebook[i] instanceof Program) {
				Program temp = (Program)gradebook[i];
				System.out.println("" + total + ". " + temp.getConcept());
				total++;
			}
		}
		if(total == 0) {
			System.out.println("There are no Programs yet.");
		}
	}
}
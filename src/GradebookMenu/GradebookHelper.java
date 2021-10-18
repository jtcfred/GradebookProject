/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Assignments.*;
import GradebookExceptions.*;

public class GradebookHelper {

	//This function prompts the user for a score and returns it as a double
	static double getScoreInput(Scanner sc) {
		double score;
		//only allow input that can be parsed into a double
		do {
			try {
				System.out.println("Enter the score as a double: ");
				score = Double.parseDouble(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println("You must enter a valid score.");
				continue;
			}
			//only allow the score to be between 0 and 100
			if(score < 0 || score > 100) {
				System.out.println("Please make sure the score is within valid range [0,100].");
				continue;
			}
			break;
		}while(true);
		return score;
		
	}
	
	//this function calculates the letter grade based on the score and returns the letter
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
	
	//this function prompts the user for the name of the grade and returns it
	static String getNameInput(Scanner sc) {
		String name;
		System.out.println("Enter the name of the assignment");
		name = sc.nextLine();
		return name;
	}
	
	//this function prompts the user for a date in a specified format and returns it
	static LocalDate getDueDateInput(Scanner sc) {
		//only all input that is in the specified format
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
	
	//Adds a grade at the current index in the gradebook
	static void addGrade(Assignment[]gradebook, int index, Scanner sc) {
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
				double score = getScoreInput(sc);
				
				//get the letter grade
				char letter = getLetter(score);
				
				//get the name
				String name = getNameInput(sc);
				
				//get the due date
				LocalDate dueDate = getDueDateInput(sc);
				
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
				double score = getScoreInput(sc);
				
				//get the letter grade
				char letter = getLetter(score);
				
				//get the name
				String name = getNameInput(sc);
				
				//get the due date
				LocalDate dueDate = getDueDateInput(sc);
				
				//get the reading
				System.out.println("Enter the name of the reading:");
				String reading = sc.nextLine();
				
				//make the discussion object and add to the gradebook
				Discussion discussion = new Discussion(score, letter, name, dueDate, reading);
				gradebook[index] = discussion;
				index++;
				break;
			}
			if(input == 2) {
				
				//Get the score
				double score = getScoreInput(sc);
				
				//get the letter grade
				char letter = getLetter(score);
				
				//get the name
				String name = getNameInput(sc);
				
				//get the due date
				LocalDate dueDate = getDueDateInput(sc);
				
				//get the concept
				System.out.println("Enter the name of the concept:");
				String concept = sc.nextLine();
				
				//make the program object and add to the gradebook
				Program program = new Program(score, letter, name, dueDate, concept);
				gradebook[index] = program;
				index++;
				break;
			}
			//if it makes it here, they did not make a valid selection
			System.out.println("Please make a valid selection for which grade you would like to add.");
		} while(true);
	}
	
	//this function removes a grade specified by the user by name
	static void removeGrade(Assignment[]gradebook, int index, Scanner sc) {
		
		//don't let the user remove a grade if the gradebook is empty
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
				//handle edge case where the element being removed is at the position
				if(i == gradebook.length - 1) {
					gradebook[i] = null;
					index--;
					return;
				}
				//shift all grades after the one we want to remove, 1 position to the left
				for(int j = i; j < index - 1; j++) {
					gradebook[j] = gradebook[j + 1];
				}
				index--;
				return;
			}
		}
		//if the grade wasn't found, throw exception
		try {
			if(i == index) {
				throw new InvalidGradeException();
			}
		} catch(InvalidGradeException e) {
			System.out.println("A grade with that name is not in the gradebook.");
			return;
		}
	}
	
	//this function prints out all of the grades currently in the gradebook
	static void printGrades(Assignment[]gradebook, int index) {
		
		//throw exception if the gradebook is empty
		System.out.println("\n");
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no grades to print.");
			return;
		}
		
		//print all of the grades to console
		for(int i = 0; i < index; i++) {
			System.out.println(gradebook[i].toString() + "\n");
		}
		
	}
	
	//this function prints the average score of all of the grades in the gradebook
	static void printAverage(Assignment[]gradebook, int index) {
		
		//throw exception if gradebook is empty
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no grades to average.");
			return;
		}
		
		//calculate the average
		double total = 0;
		for(int i = 0; i < index; i++) {
			total += gradebook[i].getScore();
		}
		System.out.println("Average: " + (total / (index)));
	}
	
	//this function prints the max and min scores of the gradebook
	static void printExtrema(Assignment[]gradebook, int index) {
		
		//throw exception if the gradebook is empty
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		//calculate the max and min
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
		
		//print to console
		System.out.println("The maximum score in the gradebook is: " + max);
		System.out.println("The minimum score in the gradebook is: " + min);
	}
	
	//prints the average number of questions on all of the current quizzes
	static void printQuizAverage(Assignment[]gradebook, int index) {
		
		//throw exception if the gradebook is empty
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		
		double avg = 0;
		int total = 0;
		for(int i = 0; i < index; i++) {
			//if the grade is a quiz, add its question count to total
			if(gradebook[i] instanceof Quiz) {
				total++;
				Quiz temp = (Quiz)gradebook[i];
				avg += temp.getQuestionCount();	
			}
		}
		
		//print the average to console if there are quizzes
		if(total == 0) {
			System.out.println("There are no quizzes yet.");
		} else {
			avg /= total;
			System.out.println("The average number of quiz questions is:" + avg);
		}
		
	}
	
	//prints all of the readings from discussions to console
	static void printReadings(Assignment[]gradebook, int index) {
		
		//throw exception if the gradebook is empty
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		
		int total = 0;
		for(int i = 0; i < index; i++) {
			//if the grade is a discussion, print its reading to console
			if(gradebook[i] instanceof Discussion) {
				Discussion temp = (Discussion)gradebook[i];
				System.out.println("" + total + ". " + temp.getReading());
				total++;
			}
		}
		
		//if there are no discussions, let user know
		if(total == 0) {
			System.out.println("There are no Discussions yet.");
		}
	}
	
	//prints all of the concepts from programs to console
	static void printConcepts(Assignment[]gradebook, int index) {
		
		//throw exception if the gradebook is empty
		try {
			if(index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		int total = 0;
		for(int i = 0; i < index; i++) {
			//if the grade is a program, print its concept to console
			if(gradebook[i] instanceof Program) {
				Program temp = (Program)gradebook[i];
				System.out.println("" + total + ". " + temp.getConcept());
				total++;
			}
		}
		
		//if there are no programs, let the user know
		if(total == 0) {
			System.out.println("There are no Programs yet.");
		}
	}
}

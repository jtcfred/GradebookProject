/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import Assignments.*;
import GradebookExceptions.*;

public class GradebookHelper {

	//This function prompts the user for a score and returns it as a double
	static double getScoreInput() {
		double score;
		//only allow input that can be parsed into a double
		do {
			try {
				System.out.println("Enter the score as a double: ");
				score = Double.parseDouble(Gradebook.sc.nextLine());
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
	static String getNameInput() {
		String name;
		System.out.println("Enter the name of the assignment");
		name = Gradebook.sc.nextLine();
		return name;
	}
	
	//this function prompts the user for a date in a specified format and returns it
	static LocalDate getDueDateInput() {
		//only all input that is in the specified format
		LocalDate dueDate;
		do {
			try {
				System.out.println("Enter the due date in the format: " + "\n" +
						"yyyy-mm-dd");
				dueDate = LocalDate.parse(Gradebook.sc.nextLine());
			}catch(DateTimeParseException e) {
				System.out.println("You must enter a valid due date.");
				continue;
			}
			break;
		}while(true);
		return dueDate;
	}
	
	//Adds a grade at the current index in the gradebook
	static void addGrade() {
		try {
			if(Gradebook.index == Gradebook.gradebook.length) {
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
				input = Integer.parseInt(Gradebook.sc.nextLine());
			} catch(NumberFormatException e) {
				System.out.println("You must enter a valid number for the selection.");
				continue;
			}
			if(input == 0) {
				
				//Get the score
				double score = getScoreInput();
				
				//get the letter grade
				char letter = getLetter(score);
				
				//get the name
				String name = getNameInput();
				
				//get the due date
				LocalDate dueDate = getDueDateInput();
				
				//get the question count
				int questionCount;
				do {
					try {
						System.out.println("Enter the number of questions in the quiz:");
						questionCount = Integer.parseInt(Gradebook.sc.nextLine());
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
				Gradebook.gradebook[Gradebook.index] = quiz;
				Gradebook.index++;
				break;
			}
			if(input == 1) {
				
				//Get the score
				double score = getScoreInput();
				
				//get the letter grade
				char letter = getLetter(score);
				
				//get the name
				String name = getNameInput();
				
				//get the due date
				LocalDate dueDate = getDueDateInput();
				
				//get the reading
				System.out.println("Enter the name of the reading:");
				String reading = Gradebook.sc.nextLine();
				
				//make the discussion object and add to the gradebook
				Discussion discussion = new Discussion(score, letter, name, dueDate, reading);
				Gradebook.gradebook[Gradebook.index] = discussion;
				Gradebook.index++;
				break;
			}
			if(input == 2) {
				
				//Get the score
				double score = getScoreInput();
				
				//get the letter grade
				char letter = getLetter(score);
				
				//get the name
				String name = getNameInput();
				
				//get the due date
				LocalDate dueDate = getDueDateInput();
				
				//get the concept
				System.out.println("Enter the name of the concept:");
				String concept = Gradebook.sc.nextLine();
				
				//make the program object and add to the gradebook
				Program program = new Program(score, letter, name, dueDate, concept);
				Gradebook.gradebook[Gradebook.index] = program;
				Gradebook.index++;
				break;
			}
			//if it makes it here, they did not make a valid selection
			System.out.println("Please make a valid selection for which grade you would like to add.");
		} while(true);
	}
	
	//this function removes a grade specified by the user by name
	static void removeGrade() {
		
		//don't let the user remove a grade if the gradebook is empty
		try {
			if(Gradebook.index == 0) {
				throw new GradebookEmptyException();
			}
		} catch (GradebookEmptyException e) {
			System.out.println("The gradebook is currently empty. There are no grades to remove");
			return;
		}
		
		System.out.println("Enter the name of the grade you want to remove: ");
		String name = Gradebook.sc.nextLine();
		int i;
		for(i = 0; i < Gradebook.index; i++) {
			if(name.equals(Gradebook.gradebook[i].getName())) {
				//handle edge case where the element being removed is at the position
				if(i == Gradebook.gradebook.length - 1) {
					Gradebook.gradebook[i] = null;
					Gradebook.index--;
					return;
				}
				//shift all grades after the one we want to remove, 1 position to the left
				for(int j = i; j < Gradebook.index - 1; j++) {
					Gradebook.gradebook[j] = Gradebook.gradebook[j + 1];
				}
				Gradebook.index--;
				return;
			}
		}
		//if the grade wasn't found, throw exception
		try {
			if(i == Gradebook.index) {
				throw new InvalidGradeException();
			}
		} catch(InvalidGradeException e) {
			System.out.println("A grade with that name is not in the gradebook.");
			return;
		}
	}
	
	//this function prints out all of the grades currently in the gradebook
	static void printGrades() {
		
		//throw exception if the gradebook is empty
		System.out.println("\n");
		try {
			if(Gradebook.index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no grades to print.");
			return;
		}
		
		//print all of the grades to console
		for(int i = 0; i < Gradebook.index; i++) {
			System.out.println(Gradebook.gradebook[i].toString() + "\n");
		}
		
	}
	
	//this function prints the average score of all of the grades in the gradebook
	static void printAverage() {
		
		//throw exception if gradebook is empty
		try {
			if(Gradebook.index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no grades to average.");
			return;
		}
		
		//calculate the average
		double total = 0;
		for(int i = 0; i < Gradebook.index; i++) {
			total += Gradebook.gradebook[i].getScore();
		}
		System.out.println("Average: " + (total / (Gradebook.index)));
	}
	
	//this function prints the max and min scores of the gradebook
	static void printExtrema() {
		
		//throw exception if the gradebook is empty
		try {
			if(Gradebook.index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		//calculate the max and min
		double max, min;
		max = min = Gradebook.gradebook[0].getScore();
		for(int i = 1; i < Gradebook.index; i++) {
			if(Gradebook.gradebook[i].getScore() > max) {
				max = Gradebook.gradebook[i].getScore();
			}
			if(Gradebook.gradebook[i].getScore() < min) {
				min = Gradebook.gradebook[i].getScore();
			}
		}
		
		//print to console
		System.out.println("The maximum score in the gradebook is: " + max);
		System.out.println("The minimum score in the gradebook is: " + min);
	}
	
	//prints the average number of questions on all of the current quizzes
	static void printQuizAverage() {
		
		//throw exception if the gradebook is empty
		try {
			if(Gradebook.index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		
		double avg = 0;
		int total = 0;
		for(int i = 0; i < Gradebook.index; i++) {
			//if the grade is a quiz, add its question count to total
			if(Gradebook.gradebook[i] instanceof Quiz) {
				total++;
				Quiz temp = (Quiz)Gradebook.gradebook[i];
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
	static void printReadings() {
		
		//throw exception if the gradebook is empty
		try {
			if(Gradebook.index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		
		int total = 0;
		for(int i = 0; i < Gradebook.index; i++) {
			//if the grade is a discussion, print its reading to console
			if(Gradebook.gradebook[i] instanceof Discussion) {
				Discussion temp = (Discussion)Gradebook.gradebook[i];
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
	static void printConcepts() {
		
		//throw exception if the gradebook is empty
		try {
			if(Gradebook.index == 0) {
				throw new GradebookEmptyException();
			}
		} catch(GradebookEmptyException e) {
			System.out.println("The gradebook is empty. There are no extrema to print.");
			return;
		}
		
		int total = 0;
		for(int i = 0; i < Gradebook.index; i++) {
			//if the grade is a program, print its concept to console
			if(Gradebook.gradebook[i] instanceof Program) {
				Program temp = (Program)Gradebook.gradebook[i];
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

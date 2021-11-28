package GradebookMenu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import Assignments.*;
import GradebookExceptions.GradebookEmptyException;

public class GradebookIO {
	public static void save(String fileString) throws IOException {
		
		//don't try to print any grades if the gradebook is empty
		try {
			if(Gradebook.gradebook.isEmpty()) {
				throw new GradebookEmptyException();
			}
		} catch (GradebookEmptyException e) {
			System.out.println("The gradebook is currently empty. There are no grades to remove");
			return;
		}
		
		//create the file if it is not already there
		Path filePath = Paths.get("./GradeTextFiles/" + fileString + ".txt");
		File output = filePath.toFile();
		PrintWriter out;
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
			out = new PrintWriter(new BufferedWriter(new FileWriter(output)));
		} else {
			out = new PrintWriter(new BufferedWriter(new FileWriter(output, true)));
		}
		
		//write the specific information delimited by tabs
		for(Assignment a : Gradebook.gradebook) {
			if(a instanceof Quiz) {
				out.print("Quiz" + "\t");
				out.print(a.getName() + "\t");
				out.print(a.getScore() + "\t");
				out.print(a.getLetter() + "\t");
				out.print(a.getDate() + "\t");
				out.print(((Quiz) a).getQuestionCount() + "\n");
			} else if(a instanceof Discussion) {
				out.print("Discussion" + "\t");
				out.print(a.getName() + "\t");
				out.print(a.getScore() + "\t");
				out.print(a.getLetter() + "\t");
				out.print(a.getDate() + "\t");
				out.print(((Discussion) a).getReading() + "\n");
			} else if(a instanceof Program) {
				out.print("Program" + "\t");
				out.print(a.getName() + "\t");
				out.print(a.getScore() + "\t");
				out.print(a.getLetter() + "\t");
				out.print(a.getDate() + "\t");
				out.print(((Program) a).getConcept() + "\n");
			}
		}
		
		System.out.println("Grades successfully printed to file");
		out.close();
	}
	
	
	public static void readFile(String fileString) throws IOException {
		
		if(new File("./GradeTextFiles/" + fileString + ".txt").isFile() == false) {
			System.out.println("Could not find a gradebook file with that name.");
			return;
		}
		
		//open the file for reading
		BufferedReader in = new BufferedReader(new FileReader("./GradeTextFiles/" + fileString + ".txt"));
		String line;
		while((line = in.readLine()) != null) {
			
			//read the line of information and create an assignment
			//then add the assignment to the gradebook
			String[] columns = line.split("\t");
			String assignment = columns[0];
			
			//if its a quiz
			if(assignment.equals("Quiz")) {
				String name = columns[1];
				double score = Double.parseDouble(columns[2]);
				char letter = columns[3].charAt(0);
				LocalDate date = LocalDate.parse(columns[4]);
				int questionAmount = Integer.parseInt(columns[5]);
				Quiz quiz = new Quiz(score, letter, name, date, questionAmount);
				Gradebook.gradebook.add(quiz);
				
				//if its a discussion
			} else if(assignment.equals("Discussion")) {
				String name = columns[1];
				double score = Double.parseDouble(columns[2]);
				char letter = columns[3].charAt(0);
				LocalDate date = LocalDate.parse(columns[4]);
				String reading = columns[5];
				Discussion discussion = new Discussion(score, letter, name, date, reading);
				Gradebook.gradebook.add(discussion);
				
				//if its a program
			} else if(assignment.equals("Program")) {
				String name = columns[1];
				double score = Double.parseDouble(columns[2]);
				char letter = columns[3].charAt(0);
				LocalDate date = LocalDate.parse(columns[4]);
				String concept = columns[5];
				Program program = new Program(score, letter, name, date, concept);
				Gradebook.gradebook.add(program);
			}
			
		}
		in.close();
		
	}
}

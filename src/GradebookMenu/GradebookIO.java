package GradebookMenu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import Assignments.*;
import GradebookExceptions.GradebookFullException;

public class GradebookIO {
	public static void save(Assignment a, String fileString) throws IOException {
		//create the file if it is not already there
		Path filePath = Paths.get("GradeTextFiles/" + fileString);
		PrintWriter out;
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileString)));
		} else {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileString, true)));
		}
		
		
		//write the specific information delimited by tabs
		if(a instanceof Quiz) {
			out.print("Quiz" + "\t");
			out.print(a.getName() + "\t");
			out.print(a.getLetter() + "\t");
			out.print(a.getDate() + "\t");
			out.print(((Quiz) a).getQuestionCount() + "\t");
		} else if(a instanceof Discussion) {
			out.print("Discussion" + "\t");
			out.print(a.getName() + "\t");
			out.print(a.getLetter() + "\t");
			out.print(a.getDate() + "\t");
			out.print(((Discussion) a).getReading() + "\t");
		} else if(a instanceof Program) {
			out.print("Program" + "\t");
			out.print(a.getName() + "\t");
			out.print(a.getLetter() + "\t");
			out.print(a.getDate() + "\t");
			out.print(((Program) a).getConcept() + "\t");
		}
		
		
		out.close();
	}
	
	
	public static void ReadFile(String fileString) throws IOException {
		//open the file for reading
		BufferedReader in = new BufferedReader(new FileReader(fileString));
		String line;
		while((line = in.readLine()) != null) {
			
			//don't let the gradebook size exceed its limit
			try {
				if(Gradebook.gradebook.size() == Gradebook.maxSize) {
					throw new GradebookFullException();
				}
			} catch(GradebookFullException e) {
				System.out.println("The gradebook is full! You cannot add any more grades.");
				return;
			}
			
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

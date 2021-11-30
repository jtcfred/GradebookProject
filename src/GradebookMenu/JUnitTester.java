package GradebookMenu;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Assignments.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class JUnitTester {
	
	private List<Assignment> expected;
	
	@BeforeEach
	public void setup() {
		expected = new ArrayList<>();
	}
	
	@Test
	public void testPrintToFile() {
		//load up gradebook
		Gradebook.gradebook.add(new Quiz(100, 'A', "quiz1", LocalDate.parse("2020-12-02"), 20));
		Gradebook.gradebook.add(new Discussion(85, 'B', "Pragmatic Programmer", LocalDate.parse("2021-12-02"), "Clean Code"));
		Gradebook.gradebook.add(new Program(75, 'C', "Gradebook", LocalDate.parse("2021-12-02"), "ArrayList"));
		expected = Gradebook.gradebook;
		
		//function being tested
		try {
			GradebookIO.save("testing");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//fail if the file doesnt exist
		if(new File("./GradeTextFiles/" + "testing" + ".txt").isFile() == false) {
			fail();
		}
		
		List<Assignment> actual = new ArrayList<>();
		//add the lines from the file into actual
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("./GradeTextFiles/" + "testing" + ".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		try {
			while((line = in.readLine()) != null) {
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
					actual.add(quiz);
					
					//if its a discussion
				} else if(assignment.equals("Discussion")) {
					String name = columns[1];
					double score = Double.parseDouble(columns[2]);
					char letter = columns[3].charAt(0);
					LocalDate date = LocalDate.parse(columns[4]);
					String reading = columns[5];
					Discussion discussion = new Discussion(score, letter, name, date, reading);
					actual.add(discussion);
					
					//if its a program
				} else if(assignment.equals("Program")) {
					String name = columns[1];
					double score = Double.parseDouble(columns[2]);
					char letter = columns[3].charAt(0);
					LocalDate date = LocalDate.parse(columns[4]);
					String concept = columns[5];
					Program program = new Program(score, letter, name, date, concept);
					actual.add(program);
				}
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//check if the retrieved information matches the sent information
		for(int i = 0; i < actual.size(); i++) {
			assertEquals(expected.get(i).getName(), actual.get(i).getName());
			assertEquals(expected.get(i).getScore(), actual.get(i).getScore(), .0001);
			assertEquals(expected.get(i).getLetter(), actual.get(i).getLetter());
			assertTrue(expected.get(i).getDate().compareTo(actual.get(i).getDate()) == 0);
			if(actual.get(i) instanceof Quiz) {
				assertSame(((Quiz) expected.get(i)).getQuestionCount(), ((Quiz) actual.get(i)).getQuestionCount());
			}
			if(actual.get(i) instanceof Discussion) {
				assertEquals(((Discussion) expected.get(i)).getReading(), ((Discussion) actual.get(i)).getReading());
			}
			if(actual.get(i) instanceof Program) {
				assertEquals(((Program) expected.get(i)).getConcept(), ((Program) actual.get(i)).getConcept());
			}
		}
		
		//delete the file that was used for testing
		if(new File("./GradeTextFiles/testing.txt").delete()) {
			System.out.println("Successfully cleared out test file");
		}
	}
	
	@Test
	public void testReadFromFile() throws IOException {
		//load up gradebook
		expected.add(new Quiz(100, 'A', "quiz1", LocalDate.parse("2020-12-02"), 20));
		expected.add(new Discussion(85, 'B', "Pragmatic Programmer", LocalDate.parse("2021-12-02"), "Clean Code"));
		expected.add(new Program(75, 'C', "Gradebook", LocalDate.parse("2021-12-02"), "ArrayList"));
		
		//create dummy file with information
		Path filePath = Paths.get("./GradeTextFiles/" + "testing" + ".txt");
		File output = filePath.toFile();
		PrintWriter out;
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
			out = new PrintWriter(new BufferedWriter(new FileWriter(output)));
		} else {
			out = new PrintWriter(new BufferedWriter(new FileWriter(output, true)));
		}
		
		//write the specific information delimited by tabs
		for(Assignment a : expected) {
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
		out.close();
		
		//function to test
		GradebookIO.readFile("testing");
		List<Assignment> actual = Gradebook.gradebook;
		
		//check if the retrieved information matches the sent information
		for(int i = 0; i < actual.size(); i++) {
			assertEquals(expected.get(i).getName(), actual.get(i).getName());
			assertEquals(expected.get(i).getScore(), actual.get(i).getScore(), .0001);
			assertEquals(expected.get(i).getLetter(), actual.get(i).getLetter());
			assertTrue(expected.get(i).getDate().compareTo(actual.get(i).getDate()) == 0);
			if(actual.get(i) instanceof Quiz) {
				assertSame(((Quiz) expected.get(i)).getQuestionCount(), ((Quiz) actual.get(i)).getQuestionCount());
			}
			if(actual.get(i) instanceof Discussion) {
				assertEquals(((Discussion) expected.get(i)).getReading(), ((Discussion) actual.get(i)).getReading());
			}
			if(actual.get(i) instanceof Program) {
				assertEquals(((Program) expected.get(i)).getConcept(), ((Program) actual.get(i)).getConcept());
			}
		}
		
		//delete the file that was used for testing
		if(new File("./GradeTextFiles/testing.txt").delete()) {
			System.out.println("Successfully cleared out test file");
		}
	}
}

/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package Assignments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import GradebookMenu.Assignment;

public class Program implements Assignment{

	private double score;
	private char letterGrade;
	private String name;
	private LocalDate dueDate;
	private String concept;
	
	//default constructor
		public Program() {
			score = 0;
			letterGrade = 'F';
			name = "";
			dueDate = java.time.LocalDate.now();
			concept = "";
		}
		
	//parameterized constructor
	public Program(double score, char letterGrade, String name, LocalDate dueDate, String concept) {
		this.score = score;
		this.letterGrade = letterGrade;
		this.name = name;
		this.dueDate = dueDate;
		this.concept = concept;
	}
	
	@Override
	public double getScore() {
		return score;
	}

	@Override
	public char getLetter() {
		return letterGrade;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocalDate getDate() {
		return dueDate;
	}
	
	public String getConcept() {
		return concept;
	}
	
	@Override
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public void setLetter(char letter) {
		letterGrade = letter;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDueDate(LocalDate date) {
		dueDate = date;
	}
	
	public void setConcept(String concept) {
		this.concept = concept;
	}
	
	@Override
	public String toString() {
		String date = dueDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
		String message = "Name: " + name + "\n" +
						"Score: " + score + "\n" +
						"Letter: " + letterGrade + "\n" +
						"Due: " + date + "\n" +
						"Concept: " + concept;
		return message;
	}
}

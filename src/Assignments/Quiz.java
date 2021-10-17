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

public class Quiz implements Assignment{

	private double score;
	private char letterGrade;
	private String name;
	private LocalDate dueDate;
	private int questionCount;
	
	//default constructor
	public Quiz() {
		score = 0;
		letterGrade = 'F';
		name = "";
		dueDate = java.time.LocalDate.now();
		questionCount = 0;
	}
	
	//parameterized constructor
	public Quiz(double score, char letterGrade, String name, LocalDate dueDate, int questionCount) {
		this.score = score;
		this.letterGrade = letterGrade;
		this.name = name;
		this.dueDate = dueDate;
		this.questionCount = questionCount;
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
	
	public int getQuestionCount() {
		return questionCount;
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
	
	public void setQuestionCount(int count) {
		questionCount = count;
	}
	
	@Override
	public String toString() {
		String date = dueDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
		String message = "Name: " + name + "\n" +
						"Score: " + score + "\n" +
						"Letter: " + letterGrade + "\n" +
						"Due: " + date + "\n" +
						"Number of Questions: " + questionCount;
		return message;
	}
}

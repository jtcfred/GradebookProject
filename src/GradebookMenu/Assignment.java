/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookMenu;
import java.time.LocalDate;

public interface Assignment {
	double getScore();
	char getLetter();
	String getName();
	LocalDate getDate();
	void setScore(double score);
	void setLetter(char letter);
	void setName(String name);
	void setDueDate(LocalDate date);
	String toString();
}

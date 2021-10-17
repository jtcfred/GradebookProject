/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookExceptions;

@SuppressWarnings("serial")
public class InvalidGradeException extends Exception{
	
	public InvalidGradeException() {
		
	}
	
	public InvalidGradeException(String message) {
		super(message);
	}
}
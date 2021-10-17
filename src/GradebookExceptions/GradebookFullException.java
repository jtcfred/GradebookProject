/*
 * Jackson Cozzi
 * jcozzi
 * Gradebook Project
 */
package GradebookExceptions;

@SuppressWarnings("serial")
public class GradebookFullException extends Exception{

	public GradebookFullException() {
		
	}
	
	public GradebookFullException(String message) {
		super(message);
	}
}

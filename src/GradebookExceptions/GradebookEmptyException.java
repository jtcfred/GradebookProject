package GradebookExceptions;

@SuppressWarnings("serial")
public class GradebookEmptyException extends Exception{

	public GradebookEmptyException() {
		
	}
	
	public GradebookEmptyException(String message) {
		super(message);
	}
}

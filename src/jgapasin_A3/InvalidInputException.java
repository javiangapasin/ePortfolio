package jgapasin_A3;

/**
 * Class Name: InvalidInputException
 * 
 * Description: A custom exception class that extends the built-in `Exception` class. It is used to signal 
 * invalid user input in the application. This exception is thrown when the user enters data that does not 
 * meet the required format or constraints, helping to catch input errors and prompt the user for valid data.
 */
public class InvalidInputException extends Exception {

	public InvalidInputException(String message) {
		super(message);
	}
	
	//No-argument constructor to preserve getMessage
	public InvalidInputException() {
		super("Invalid attribute exception.");
	}
}
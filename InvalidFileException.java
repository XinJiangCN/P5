/**
 * This exception should be thrown when the file format is invalid
 * 
 * @author CS367
 */

public class InvalidFileException extends Exception {
	/**
	 * Constructs an InvalidFileException object with a message
	 * 
	 * @param message
	 *            exception message
	 */
	public InvalidFileException(String message) {
		super(message);
	}
}

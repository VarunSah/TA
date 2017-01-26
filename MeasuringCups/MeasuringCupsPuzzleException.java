/**
 * A special (unchecked) exception class used only by methods in this project.
 */
public class MeasuringCupsPuzzleException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize the exception with a more specific error message
	 *
	 * @param message
	 *            the error message
	 */
	public MeasuringCupsPuzzleException(String message) {
		super(message);
	}
}

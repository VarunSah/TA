/**
 * A representation of a measuring cup.
 */
public class Cup {

	private int capacity;
	private int currentAmount;

	/**
	 * Construct a measuring cup
	 * 
	 * @param capacity
	 *            the maximum volume of the measuring cup
	 * @param currentAmount
	 *            the current volume of fluid in the measuring cup
	 * @throws IllegalArgumentException
	 *             when any of these conditions are true: capacity < 0,
	 *             currentAmount < 0, currentAmount > capacity
	 */
	public Cup(int capacity, int currentAmount) {
		if (currentAmount > capacity || capacity < 0 || currentAmount < 0)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		this.currentAmount = currentAmount;
	}

	/**
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return currentAmount
	 */
	public int getCurrentAmount() {
		return currentAmount;
	}

	/**
	 * Compare this cup against another cup
	 * 
	 * @param cup
	 *            an other cup to compare against this cup
	 * @return true if the other cup has the same capacity and currentAmount as
	 *         this cup and false otherwise
	 */
	public boolean equals(Cup cup) {
		if (this.capacity == cup.capacity && this.currentAmount == cup.currentAmount) {
			return true;
		}
		return false;
	}

	/**
	 * @return a string containing the currentAmount
	 */
	public String toString() {
		return String.valueOf(this.currentAmount);
	}
}

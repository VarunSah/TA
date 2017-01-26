import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * An abstraction representing a list of {@link MeasuringCupPuzzleState} graph
 * nodes
 */
public class MeasuringCupsPuzzleStateList implements
		Collection<MeasuringCupsPuzzleState> {

	ArrayList<MeasuringCupsPuzzleState> cupPuzzleStateList;

	/**
	 * Construct a new list
	 */
	public MeasuringCupsPuzzleStateList() {
		this.cupPuzzleStateList = new ArrayList<MeasuringCupsPuzzleState>();
	}

	/**
	 * @return an iterator over this list's members
	 */
	@Override
	public Iterator<MeasuringCupsPuzzleState> iterator() {
		return this.cupPuzzleStateList.iterator();
	}

	/**
	 * @param e
	 *            a {@link MeasuringCupsPuzzleState} state to add to this list
	 * @return true if the state has been added and false otherwise
	 */
	@Override
	public boolean add(MeasuringCupsPuzzleState e) {
		return this.cupPuzzleStateList.add(e);
	}

	/**
	 * @param c
	 *            a collection of MeasuringCupsPuzzleState to add to this list
	 * @return true if the elements of c have been added to this list
	 */
	@Override
	public boolean addAll(Collection<? extends MeasuringCupsPuzzleState> c) {
		return this.cupPuzzleStateList.addAll(c);
	}

	/**
	 * Update member cupPuzzleStateList to remove all nodes in this list
	 */
	@Override
	public void clear() {
		this.cupPuzzleStateList.clear();
	}

	/**
	 * @param o
	 *            an object to find in the list
	 * @return true if the list contains o and false otherwise
	 */
	@Override
	public boolean contains(Object o) {
		return this.cupPuzzleStateList.contains(o);
	}

	/**
	 * @param c
	 *            a collection of objects
	 * @return true if all objects in c are present in this list and false
	 *         otherwise
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return this.cupPuzzleStateList.containsAll(c);
	}

	/**
	 * @return true if this list is empty and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.cupPuzzleStateList.isEmpty();
	}

	/**
	 * @param o
	 *            the object to remove
	 * @return true if o has been removed from the list and false otherwise
	 */
	@Override
	public boolean remove(Object o) {
		return this.cupPuzzleStateList.remove(o);
	}

	/**
	 * @param c
	 *            a collection of objects to remove
	 * @return true if no object in c is present in the modified list and false
	 *         otherwise
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return this.cupPuzzleStateList.removeAll(c);
	}

	/**
	 * @param c
	 *            a collection of objects to retain
	 * @return true if the list now only contains objects that are present in c
	 *         (and no other objects) and false otherwise
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return this.cupPuzzleStateList.retainAll(c);
	}

	/**
	 * @return the number of elements in the list
	 */
	@Override
	public int size() {
		return this.cupPuzzleStateList.size();
	}

	/**
	 * @return an array containing members of this list
	 */
	@Override
	public Object[] toArray() {
		return this.cupPuzzleStateList.toArray();
	}

	/**
	 * @return an array containing members of this list
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return this.cupPuzzleStateList.toArray(a);
	}

	/**
	 * reverse the order of this list
	 */
	public void reverse() {
		Collections.reverse(this.cupPuzzleStateList);
	}

	/**
	 * @return a string formed by appending each member's toString value   
	 *         to the member's getOperation return value after a tab (\t).
	 *         Each member appears in a new line
	 */
	public String toString() {
		Iterator<MeasuringCupsPuzzleState> CupPuzzleStateListIterator = this.cupPuzzleStateList
				.iterator();
		String result = "";
		while (CupPuzzleStateListIterator.hasNext()) {
			MeasuringCupsPuzzleState currentState = CupPuzzleStateListIterator.next();
			result += getOperation(currentState) + "\t" +currentState.toString();
			if (CupPuzzleStateListIterator.hasNext()) {
				result += "\n";
			}
		}
		return result;
	}

	/**
	 * @param cupPuzzleState  
	 * @return a string indicating which operation resulted in cupPuzzleState from its parent state 
	 */
	public String getOperation(MeasuringCupsPuzzleState cupPuzzleState) {
		MeasuringCupsPuzzleState parentState = cupPuzzleState.getParentState();

		if (parentState == null) {
			return Config.START;
		} else if (!cupPuzzleState.getCupA().equals(parentState.getCupA())
				&& !cupPuzzleState.getCupB().equals(parentState.getCupB())) {
			if (cupPuzzleState.getCupA().getCurrentAmount() > parentState
					.getCupA().getCurrentAmount()) {
				return Config.B_To_A;
			} else {
				return Config.A_To_B;
			}
		} else if (cupPuzzleState.getCupA().equals(parentState.getCupA())
				&& !cupPuzzleState.getCupB().equals(parentState.getCupB())) {
			if (cupPuzzleState.getCupB().getCurrentAmount() > parentState
					.getCupB().getCurrentAmount()) {
				return Config.FILL_B;
			} else {
				return Config.EMPTY_B;
			}			
		} else if (!cupPuzzleState.getCupA().equals(parentState.getCupA())
				&& cupPuzzleState.getCupB().equals(parentState.getCupB())) {
			if (cupPuzzleState.getCupA().getCurrentAmount() > parentState
					.getCupA().getCurrentAmount()) {
				return Config.FILL_A;
			} else {
				return Config.EMPTY_A;
			}
		} else {
			return Config.INVALID_OPERATION;
		}
	}
}

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue of MeasuringCupsPuzzleState nodes
 */
public class MeasuringCupsPuzzleQueue implements MeasuringCupsPuzzleADT {

	Queue<MeasuringCupsPuzzleState> queue;

	/**
	 * Construct a new queue
	 */
	public MeasuringCupsPuzzleQueue() {
		this.queue = new LinkedList<MeasuringCupsPuzzleState>();
	}

	/**
	 * Add a node to the queue
	 * 
	 * @param state
	 *            the node to add
	 */
	@Override
	public void add(MeasuringCupsPuzzleState state) {
		this.queue.add(state);
	}

	/**
	 * Remove the last (FIFO) node from the queue
	 * 
	 * @return the least recent node that has been inserted into the queue;
	 *         which is now removed from the queue as a result of this function
	 *         call
	 */
	@Override
	public MeasuringCupsPuzzleState remove() {
		return queue.poll();
	}

	/**
	 * @return true if the queue is empty and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}

	/**
	 * Update the queue by removing all of its members.
	 */
	@Override
	public void clear() {
		this.queue.clear();
	}

	/**
	 * @return a String representation of the queue by visiting each member in
	 *         FIFO order, calling its toString, and joining the resulting
	 *         String to the return string by separating member Strings with a
	 *         space character
	 */
	public String toString() {
		Iterator<MeasuringCupsPuzzleState> queueIterator = this.queue.iterator();
		String result = "";
		while (queueIterator.hasNext()) {
			result += queueIterator.next().toString();
			if (queueIterator.hasNext()) {
				result += " ";
			}
		}
		return result;
	}
}

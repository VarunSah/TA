/**
 * Define the inferface shared by {@link MeasuringCupsPuzzleStack} and
 * {@link MeasuringCupsPuzzleQueue}
 */
public interface MeasuringCupsPuzzleADT {
	void add(MeasuringCupsPuzzleState state);

	MeasuringCupsPuzzleState remove();

	boolean isEmpty();

	void clear();
}

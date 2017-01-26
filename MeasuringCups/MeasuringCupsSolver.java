/**
 * The main class. Has a main method which solves the Measuring Cups problem
 * described in MeasuringCupsProblem.docx.
 */
public class MeasuringCupsSolver {
	/**
	 * Command line interface: MeasuringCupsSolver
	 * <cupACapacity> <cupBCapacity> <targetVolume> where cupACapacity,
	 * cupBCapacity, and targetVolume are all positive integers.
	 * <p>
	 * Prints a list of node states representing a path through measuring cup
	 * states in the form (<volumeA0>, <volumeB0>) (<volumeA1>, <volumeB1>) ...
	 * (<targetVolume>, 0) for two graph traversal algorithms, BFS and DFS, or
	 * prints "UNSOLVABLE" if the targetVolume cannot be reached.
	 * <p>
	 * Example 1: MeasuringCupsSolver 5 3 2 
	 * BFS
	 * Start State	(0, 0)
	 * Fill CupA	(5, 0)
	 * CupA to CupB	(2, 3)
	 * Empty CupB	(2, 0)
	 * 
	 * DFS
	 * Start State	(0, 0)
	 * Fill CupB	(0, 3)
	 * CupB to CupA	(3, 0)
	 * Fill CupB	(3, 3)
	 * CupB to CupA	(5, 1)
	 * Empty CupB	(5, 0)
	 * CupA to CupB	(2, 3)
	 * Empty CupB	(2, 0)
	 * <p>
	 * Example 2: MeasuringCupsSolver 88 42 13 
	 * BFS
	 * Unsolvable
	 * 
	 * DFS
	 * Unsolvable
	 */
	public static void main(String[] args) {
		int capacityCupA = Integer.parseInt(args[0]);
		int capacityCupB = Integer.parseInt(args[1]);
		int goalAmount = Integer.parseInt(args[2]);

		Cup startCupA = new Cup(capacityCupA, 0);
		Cup startCupB = new Cup(capacityCupB, 0);

		MeasuringCupsPuzzleState startState = new MeasuringCupsPuzzleState(startCupA, startCupB, null);

		Cup goalCupA = new Cup(capacityCupA, goalAmount);
		Cup goalCupB = new Cup(capacityCupB, 0);

		MeasuringCupsPuzzleState goalState = new MeasuringCupsPuzzleState(goalCupA, goalCupB, null);

		MeasuringCupsPuzzle measuringCupsPuzzle = new MeasuringCupsPuzzle(startState, goalState);

		System.out.println(Config.BFS);
		if (measuringCupsPuzzle.findPathIfExists(Config.BFS)) {
			System.out.println(measuringCupsPuzzle.retrievePath().toString());
		} else {
			System.out.println(Config.UNSOLVABLE);
		}

		System.out.println();
		
		System.out.println(Config.DFS);
		if (measuringCupsPuzzle.findPathIfExists(Config.DFS)) {
			System.out.println(measuringCupsPuzzle.retrievePath().toString());
		} else {
			System.out.println(Config.UNSOLVABLE);
		}
	}
}

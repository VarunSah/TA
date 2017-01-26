import java.util.Iterator;

/**
 * A class describing the measuring cups puzzle with a startState
 * {@link MeasuringCupsPuzzleState} and a goalState
 * {@link MeasuringCupsPuzzleState}
 */
public class MeasuringCupsPuzzle {

	private MeasuringCupsPuzzleState startState;
	private MeasuringCupsPuzzleState goalState;

	private MeasuringCupsPuzzleADT measuringCupsPuzzleADT;

	private MeasuringCupsPuzzleStateList pathFromStartToGoal;
	private MeasuringCupsPuzzleStateList processedStates;
	private MeasuringCupsPuzzleState foundGoalState;

	/**
	 * Construct a puzzle object by describing the startState and goalState
	 * 
	 * @param startState
	 *            a state describing the capacities and initial volumes of
	 *            measuring cups {@link MeasuringCupsPuzzleState}
	 * @param goalState
	 *            a state describing the desired end volumes of measuring cups
	 *            {@link MeasuringCupsPuzzleState}
	 */
	public MeasuringCupsPuzzle(MeasuringCupsPuzzleState startState, MeasuringCupsPuzzleState goalState) {
		this.startState = startState;
		this.goalState = goalState;

		this.pathFromStartToGoal = new MeasuringCupsPuzzleStateList();
		this.processedStates = new MeasuringCupsPuzzleStateList();
		this.foundGoalState = null;
		this.measuringCupsPuzzleADT = null;
	}

	/**
	 * Solve the measuring cups puzzle if it can be solved. Set processedStates
	 * by adding a {@link MeasuringCupsPuzzleState} graph node to the list as
	 * the algorithm visits that node. Set foundGoalState to a
	 * {@link MeasuringCupsPuzzleState} if the graph traversal algorithm labeled
	 * by *algorithm* visits a node with the same values as the desired
	 * goalState
	 * 
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 * @return true if the puzzle can be solved (and has been solved, see
	 *         {@link retrievePath} to obtain the solution stored in this
	 *         object) and false otherwise
	 */
	public boolean findPathIfExists(String algorithm) {
		chooseADT(algorithm);
		resetCupPuzzle();

		this.measuringCupsPuzzleADT.add(this.startState);

		boolean goalFound = false;
		MeasuringCupsPuzzleStateList currentSuccessors = null;

		while (!this.measuringCupsPuzzleADT.isEmpty()) {
			MeasuringCupsPuzzleState currentState = this.measuringCupsPuzzleADT.remove();

			if (isProcessed(currentState))
				continue;
			// while (isProcessed(currentState)) {
			// if (!this.measuringCupsPuzzleADT.isEmpty()) {
			// currentState = this.measuringCupsPuzzleADT.remove();
			// } else {
			// return goalFound;
			// }
			// }
			if (this.goalState.equals(currentState)) {
				this.foundGoalState = currentState;
				goalFound = true;
				break;
			} else {
				processedStates.add(currentState);

				currentSuccessors = getSuccessors(currentState);

				Iterator<MeasuringCupsPuzzleState> iterator = currentSuccessors.iterator();
				while (iterator.hasNext()) {
					MeasuringCupsPuzzleState currentSuccessor = iterator.next();
					this.measuringCupsPuzzleADT.add(currentSuccessor);
				}
			}
		}
		return goalFound;
	}

	/**
	 * Set member measuringCupsPuzzleADT {@link MeasuringCupsPuzzleADT} with a
	 * data type that will be used to solve the puzzle.
	 * 
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 */
	private void chooseADT(String algorithm) {
		if (Config.BFS.equals(algorithm)) {
			measuringCupsPuzzleADT = new MeasuringCupsPuzzleQueue();
		} else if (Config.DFS.equals(algorithm)) {
			measuringCupsPuzzleADT = new MeasuringCupsPuzzleStack();
		} else {
			throw new MeasuringCupsPuzzleException(Config.INVALID_ALGORITHM);
		}
	}

	/**
	 * Reset the puzzle by erasing all member variables which store some aspect
	 * of the solution (pathFromStartToGoal, processedStates, and
	 * foundGoalState) and setting them to their initial values
	 */
	private void resetCupPuzzle() {
		pathFromStartToGoal.clear();
		processedStates.clear();
		foundGoalState = null;
	}

	/**
	 * Mark the graph node represented by currentState as visited for the
	 * purpose of the graph traversal algorithm being used to solve the puzzle
	 * (set by {@link chooseADT})
	 * 
	 * @param currentState
	 *            {@link MeasuringCupsPuzzleState}
	 * @return true if the currentState has been visited and false otherwise
	 */
	private boolean isProcessed(MeasuringCupsPuzzleState currentState) {
		MeasuringCupsPuzzleState closedState = null;
		boolean isProcessed = false;
		Iterator<MeasuringCupsPuzzleState> closedIterator = this.processedStates.iterator();
		if (currentState != null) {
			while (closedIterator.hasNext()) {
				closedState = closedIterator.next();
				isProcessed = closedState.equals(currentState);
				if (isProcessed == true) {
					break;
				}
			}
		}
		return isProcessed;
	}

	/**
	 * Assuming {@link findPathIfExists} returns true, return the solution that
	 * was found. Set pathFromStartToGoal by starting at the foundGoalState and
	 * accessing/setting the current node to the parentState
	 * {@link MeasuringCupsPuzzleState#getParentState} until reaching the
	 * startState
	 * 
	 * @return a list of states {@link MeasuringCupsPuzzleStateList}
	 *         representing the changes in volume of cupA and cupB from the
	 *         initial state to the goal state.
	 */
	public MeasuringCupsPuzzleStateList retrievePath() {
		MeasuringCupsPuzzleState foundGoalState = this.foundGoalState;
		this.pathFromStartToGoal.add(foundGoalState);
		MeasuringCupsPuzzleState currentState = foundGoalState;
		while (currentState.getParentState() != null) {
			this.pathFromStartToGoal.add(currentState.getParentState());
			currentState = currentState.getParentState();
		}
		pathFromStartToGoal.reverse();
		return this.pathFromStartToGoal;
	}

	/**
	 * Enumerate all possible states that can be reached from the currentState
	 * 
	 * @param currentState
	 *            the current volumes of cupA and cupB
	 * @return a list of states {@link MeasuringCupsPuzzleStateList} that can be
	 *         reached by emptying cupA or cupB, pouring from cupA to cupB and
	 *         vice versa, and filling cupA or cupB to its max capacity
	 */
	public MeasuringCupsPuzzleStateList getSuccessors(MeasuringCupsPuzzleState currentState) {
		MeasuringCupsPuzzleStateList successors = new MeasuringCupsPuzzleStateList();

		if (currentState == null) {
			return successors;
		}

		// Fill bigger Cup
		successors.add(fillCupA(currentState));

		// Fill smaller Cup
		successors.add(fillCupB(currentState));

		// Empty larger Cup
		successors.add(emptyCupA(currentState));

		// Empty smaller Cup
		successors.add(emptyCupB(currentState));

		// Pour from larger to smaller (reduce larger amount by min(larger,
		// difference b/w smaller capacity and current smaller); increase
		// smaller amount to capacity
		successors.add(pourCupAToCupB(currentState));

		// Pour from smaller to larger (reduce smaller by min(smaller,
		// difference between larger capacity and smaller capacity)
		successors.add(pourCupBToCupA(currentState));

		Iterator<MeasuringCupsPuzzleState> successorIterator = successors.iterator();
		while (successorIterator.hasNext()) {
			MeasuringCupsPuzzleState successor = successorIterator.next();
			if (currentState.equals(successor)) {
				successorIterator.remove();
			}
		}
		return successors;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupA to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupA(MeasuringCupsPuzzleState currentState) {
		Cup cupA = new Cup(currentState.getCupA().getCapacity(), currentState.getCupA().getCapacity());
		Cup cupB = new Cup(currentState.getCupB().getCapacity(), currentState.getCupB().getCurrentAmount());
		MeasuringCupsPuzzleState nextState = new MeasuringCupsPuzzleState(cupA, cupB, currentState);
		return nextState;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupB to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupB(MeasuringCupsPuzzleState currentState) {
		Cup cupA = new Cup(currentState.getCupA().getCapacity(), currentState.getCupA().getCurrentAmount());
		Cup cupB = new Cup(currentState.getCupB().getCapacity(), currentState.getCupB().getCapacity());
		MeasuringCupsPuzzleState nextState = new MeasuringCupsPuzzleState(cupA, cupB, currentState);
		return nextState;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupA
	 */
	public MeasuringCupsPuzzleState emptyCupA(MeasuringCupsPuzzleState currentState) {
		Cup cupA = new Cup(currentState.getCupA().getCapacity(), 0);
		Cup cupB = new Cup(currentState.getCupB().getCapacity(), currentState.getCupB().getCurrentAmount());
		MeasuringCupsPuzzleState nextState = new MeasuringCupsPuzzleState(cupA, cupB, currentState);
		return nextState;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupB
	 */
	public MeasuringCupsPuzzleState emptyCupB(MeasuringCupsPuzzleState currentState) {
		Cup cupA = new Cup(currentState.getCupA().getCapacity(), currentState.getCupA().getCurrentAmount());
		Cup cupB = new Cup(currentState.getCupB().getCapacity(), 0);
		MeasuringCupsPuzzleState nextState = new MeasuringCupsPuzzleState(cupA, cupB, currentState);
		return nextState;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupA into cupB until either cupA is empty or cupB is full
	 */
	public MeasuringCupsPuzzleState pourCupAToCupB(MeasuringCupsPuzzleState currentState) {
		Integer transferAmount = 0;
		Integer currentDifference = Math
				.abs(currentState.getCupB().getCapacity() - currentState.getCupB().getCurrentAmount());
		transferAmount = Math.min(currentState.getCupA().getCurrentAmount(), currentDifference);

		Cup cupA = new Cup(currentState.getCupA().getCapacity(),
				currentState.getCupA().getCurrentAmount() - transferAmount);
		Cup cupB = new Cup(currentState.getCupB().getCapacity(),
				currentState.getCupB().getCurrentAmount() + transferAmount);
		MeasuringCupsPuzzleState nextState = new MeasuringCupsPuzzleState(cupA, cupB, currentState);
		return nextState;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupB into cupA until either cupB is empty or cupA is full
	 */
	public MeasuringCupsPuzzleState pourCupBToCupA(MeasuringCupsPuzzleState currentState) {
		Integer transferAmount = 0;
		Integer currentDifference = Math
				.abs(currentState.getCupA().getCapacity() - currentState.getCupA().getCurrentAmount());
		transferAmount = Math.min(currentState.getCupB().getCurrentAmount(), currentDifference);

		Cup cupA = new Cup(currentState.getCupA().getCapacity(),
				currentState.getCupA().getCurrentAmount() + transferAmount);
		Cup cupB = new Cup(currentState.getCupB().getCapacity(),
				currentState.getCupB().getCurrentAmount() - transferAmount);
		MeasuringCupsPuzzleState nextState = new MeasuringCupsPuzzleState(cupA, cupB, currentState);
		return nextState;
	}
}

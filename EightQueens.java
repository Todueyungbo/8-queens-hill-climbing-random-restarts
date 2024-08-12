import java.util.Random;

public class EightQueens {

    private static final int SIZE = 8;
    private static final int MAX_RESTARTS = 1000;
    private int[] board; // This represents the positions of queens in each row

    public EightQueens() {
        board = new int[SIZE];
    }

    // Randomly initialize the board
    private void initializeBoard() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            board[i] = rand.nextInt(SIZE);
        }
    }

    // Calculate the number of conflicts (pairs of queens attacking each other)
    private int calculateConflicts(int[] state) {
        int conflicts = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = i + 1; j < SIZE; j++) {
                if (state[i] == state[j] || Math.abs(state[i] - state[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    // Perform the Hill Climbing algorithm with a random restart
    private int[] hillClimbWithRandomRestart() {
        int restartCount = 0;
        int[] currentState = new int[SIZE];
        int[] nextState;
        int currentConflicts;
        int nextConflicts;

        while (restartCount < MAX_RESTARTS) {
            initializeBoard();
            currentState = board.clone();
            currentConflicts = calculateConflicts(currentState);

            boolean localOptimumReached = false;
            while (!localOptimumReached) {
                nextState = currentState.clone();
                int bestConflicts = currentConflicts;

                // Explore the neighborhood
                for (int col = 0; col < SIZE; col++) {
                    for (int row = 0; row < SIZE; row++) {
                        if (currentState[col] == row) {
                            continue;
                        }
                        nextState[col] = row;
                        nextConflicts = calculateConflicts(nextState);

                        // If the new state has fewer conflicts, move to that state
                        if (nextConflicts < bestConflicts) {
                            bestConflicts = nextConflicts;
                            currentState[col] = row;
                        }

                        // Reset nextState to currentState for the next iteration
                        nextState[col] = currentState[col];
                    }
                }

                if (bestConflicts == currentConflicts) {
                    // No better neighbor found; local optimum reached
                    localOptimumReached = true;
                } else {
                    currentConflicts = bestConflicts;
                }

                // Check if a solution has been found
                if (currentConflicts == 0) {
                    return currentState;
                }
            }
            restartCount++;
        }
        return null; // Return null if no solution is found within MAX_RESTARTS
    }

    // Print the board
    private void printBoard(int[] state) {
        if (state == null) {
            System.out.println("No solution found.");
            return;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (state[col] == row) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EightQueens eightQueens = new EightQueens();
        int[] solution = eightQueens.hillClimbWithRandomRestart();
        eightQueens.printBoard(solution);
    }
}
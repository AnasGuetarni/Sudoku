import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contain the "main"
 *
 * Class Solver who contains the algorithms of research
 */

public class Solver {

    int numberOfBackTracking = 0;

    /**
     * Main function who do the calculation of the time to resolve the function
     * @param args contain the argument
     */
    public static void main(String[] args) {

        State initialState = new State(readFile("sudoku.txt"));
        System.out.println(initialState);
        boolean optimize;

        switch (args[0]){
            case "oui":
                optimize = true;
                break;
            case "non":
                optimize = false;
                break;
            default:
                optimize = true;
                break;
        }

        Solver su = new Solver();
        long startTime = System.nanoTime();;
        State finalState = su.Backtracking(initialState, optimize);
        long endTime = System.nanoTime();
        Double timeInSeconds = ((endTime - startTime) / Math.pow(10, 9));
        System.out.println("Resolution: ");

        if(finalState == null)
            System.out.println("No resolution to your Sudoku");
        else{
            System.out.println(finalState);
            System.out.println(timeInSeconds + " seconds to solve the Sudoku");
        }
    }

    /**
     * Function who permit to read the file
     * @param f correspond to the file to read
     * @return the initial state of the sudoku
     */
    public static int[][] readFile(String f) {
        // Initialize a tab of 9x9 elements
        int[][] state = new int[9][9];

        // We create a BufferedReader to reads text from a character-input stream
        BufferedReader buffer = null;

        // We try to put all the caracteres of the file into the state (2d array)
        try {
            buffer = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String line;
            int j = 0;
            int i = 0;
            // We read the buffer if it isn't empty
            while ((line = buffer.readLine()) != null) {
                // We split at each line
                String[] data = line.split(" ");
                // Loop on the data
                for (String s : data) {
                    // We parse each numer to a Integer
                    state[i][j] = Integer.parseInt(s);
                    j++;
                }
                // We travel into the sudoku column by column
                i++;
                j = 0;
            }
            // We close the buffer
            buffer.close();
        // We catch any Exception of type IOException
        } catch(IOException e) {
            e.printStackTrace();
        }

        // We return the final state
        return state;
    }

    /**
     *
     * @param currentState correspond to the current state of type State
     * @return the current state if the sudoku is completed
     * @return the
     */

    public State Backtracking(State currentState, boolean optimize) {

        numberOfBackTracking++;

        // If the sudoku is completed, we return the current state
        if(currentState.isComplete()){
            System.out.println("Numbers of backtracking: "+(numberOfBackTracking-1));
            return currentState;
        }

        // We create a new State
        State newState;
        Position X;
        // We create a Position with the MostConstraintVar in the currentState if we want to do an heuristic version
        // Either we create a Position with the current value
        if (optimize)
            X = currentState.getMostConstraintVar();
        else
            X = currentState.getAllVar();

        // We create a list of Integer
        List<Integer> D = new ArrayList<>();
        // We add the possible Values of the sudoku in the List
        D.addAll(X.getPossibleValues());

        // Loop into each element of the List
        for (int i : D) {
            // Set the Most constraitVarValue() of the element of the list
            currentState.setMostConstraitVarValue(i);
            // If the currentState is valid
            if(currentState.isValid()) {
                // We return into the newState the Backtracking algorithm (recursivity)
                newState = Backtracking(new State(currentState), optimize);
                // If the new state is null, we return it
                if(newState != null) return newState;
            }
        }
        return null;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Solver {

    public static void main(String[] args) {

        State initalState = new State(readFile("sudoku.txt"));
        System.out.println(initalState);

        Solver su = new Solver();
        long t1 = System.currentTimeMillis();
        State s = su.PSCBacktracking(initalState);
        System.out.println(System.currentTimeMillis()-t1);
        System.out.println(s);
    }

    public static int[][] readFile(String f) {
        int[][] state = new int[9][9];
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String line;
            int j = 0;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                for (String s : data) {
                    state[i][j] = Integer.parseInt(s);
                    i++;
                }
                i = 0;
                j++;
            }

            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    public State PSCBacktracking(State A) {
        if(A.isComplete()) return A;
        State r;
        Position X = A.getMostConstraintVar();

        List<Integer> D = new ArrayList<>();
        D.addAll(X.getPossibleValues());
        for (int i : D) {
            A.setMostConstraitVarValue(i);
            if(A.isValid()) {
                r = PSCBacktracking(new State(A));
                if(r != null) return r;
            }
        }
        return null;
    }
}

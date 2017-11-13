import java.util.ArrayList;
import java.util.List;

/**
 * Class State who contains the modifications of the state of the Sudoku
 */

public class State {

    private Position[][] tiles;
    private boolean valid = false;
    private int mcx, mcy;

    public State(int[][] tiles) {
        this.tiles = new Position[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tiles[i][j] = new Position();
                this.tiles[i][j].setValue(tiles[i][j]);
            }
        }
        update();
    }

    public State(State sudokuState) {
        this.tiles = new Position[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tiles[i][j] = new Position();
                this.tiles[i][j].setValue(sudokuState.tiles[i][j].getValue());
            }
        }
        update();
    }

    public boolean isComplete() {
        // If the sudoku is finish
        if(isValid()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if(tiles[i][j].getValue() == 0) return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isValid() {
        // We call the update function
        update();
        return valid;
    }

    public boolean update() {
        // For each tiles remove the forbiden values
        // We loop into the sudoku
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tiles[i][j].resetValues();
                // We set into the list the occupiedSquare and the occupied Line and Row
                List<Integer> occupiedSquare = getSquareNumbers(i, j);
                List<Integer> occupiedLR = getLineRowNumbers(i, j);

                for (Integer v : occupiedSquare) {
                    tiles[i][j].getPossibleValues().remove(v);
                    if(v == tiles[i][j].getValue()) {
                        valid = false;
                        return false;
                    }
                }

                for (Integer v : occupiedLR) {
                    tiles[i][j].getPossibleValues().remove(v);
                    if(v == tiles[i][j].getValue()) {
                        valid = false;
                        return false;
                    }
                }
            }
        }
        valid = true;
        return true;
    }

    /**
     *
     * @param x is the value x of the position
     * @param y is the value y of the position
     * @return a list of integer
     */
    private List<Integer> getSquareNumbers(int x, int y) {
        // We create a list of Integer values
        List<Integer> values = new ArrayList<>(9);

        // We create the x1 and y1 values
        int x1 = (int)Math.floor(x/3)*3;
        int y1 = (int)Math.floor(y/3)*3;

        // We loop into 3x3 part of the sudoku
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // We get the values
                int t = tiles[x1+i][y1+j].getValue();
                if(t != 0 && x1+i != x && y1+j != y) {
                    // We add the t into the list
                    values.add(t);
                }
            }
        }
        // We return the list
        return values;
    }

    private List<Integer> getLineRowNumbers(int k, int l) {
        // We create a list of Integer
        List<Integer> values = new ArrayList<>(9);

        // We loop into the row
        for (int i = 0; i < 9; i++) {
            if(tiles[k][i].getValue() != 0 && !values.contains(tiles[k][i].getValue()) && i != l) {
                values.add(tiles[k][i].getValue());
            }

            if(tiles[i][l].getValue() != 0 && !values.contains(tiles[i][l].getValue()) && i != k) {
                values.add(tiles[i][l].getValue());
            }
        }

        // We return the list "values"
        return values;
    }

    public Position getMostConstraintVar() {
        // We create a position called "most"
        Position most = null;

        // Loop into the 2d array of the sudoku
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // We set the most contraint var to the Position "most"
                if(most == null || (tiles[i][j].getPossibleValues().size() <= most.getPossibleValues().size() && tiles[i][j].getValue() == 0)) {
                    most = tiles[i][j];
                    mcx = i;
                    mcy = j;
                }
                //System.out.print("Tiles in getMostContraintVar: "+tiles[i][j].getPossibleValues().size()+"\n");
            }
        }
        // We return the Position "most"
        return most;
    }

    public Position getAllVar(){
        // We create a position called allPositions
        Position allPositions = null;
        // Loop into the 2d array of the sudoku
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // We set the current value to the Position "allPositions"
                if(allPositions == null || (tiles[i][j].getValue() == 0)) {
                    allPositions = tiles[i][j];
                    mcx = i;
                    mcy = j;
                }
            }
        }
        // We return the Position "allPositions"
        return allPositions;
    }

    /**
     * Set to the good position the most constraint value
     * @param v is the most constraint value
     */

    public void setMostConstraitVarValue(int v) {
        tiles[mcx][mcy].setValue(v);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s.append(tiles[i][j].getValue()+" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;

public class Sudoku {
    private final int SIZE = 9;
    private int[][] board = new int[SIZE][SIZE];
    private int emptyCases = (int) Math.pow(SIZE, 2);
    private Domain[][] domains = new Domain[SIZE][SIZE];


    /**
     * Empty constructor, initialize an empty board
     */
    public Sudoku () {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
                this.domains[i][j] = new Domain();
            }
        }
    }

    /**
     * Copy constructor
     *
     * @param sudoku	The sudoku we want to copy
     */
    public Sudoku (Sudoku sudoku) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = sudoku.board[i][j];
            }
        }
        this.emptyCases = sudoku.emptyCases;
        this.domains = new Domain[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.domains[i][j] = new Domain(sudoku.domains[i][j]);
            }
        }
    }

    /**
     * Constructor from a filepath, parser
     *
     * @param filepath					The path of the file
     * @throws FileNotFoundException	If the file doesn't exist
     * @throws FileFormatException		If the file is not correctly formatted
     * @throws InvalidSudokuException	If the sudoku generated is not valid
     */
    public Sudoku (String filepath) throws FileNotFoundException, FileFormatException, InvalidSudokuException {
        LineNumberReader reader = new LineNumberReader(new FileReader(filepath));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.domains[i][j] = new Domain();
            }
        }

        String line;
        int lineCnt = 0;
        try {
            while ((line = reader.readLine()) != null) {
                String[] strArray = line.split(" ");
                if (strArray.length < 9) throw new FileFormatException();
                for (int i = 0; i < strArray.length; i++) {
                    this.board[lineCnt][i] = strArray[i].equals("-") ? 0 : Integer.valueOf(strArray[i]);
                    if (this.board[lineCnt][i] != 0) {
                        emptyCases--;
                        addConstraint(this.board[lineCnt][i], lineCnt, i);
                    }
                }
                lineCnt++;
            }
            reader.close();
        } catch (Exception E) {
            throw new FileFormatException();
        }

        if (lineCnt < 8) throw new FileFormatException();

        if (!this.isValid())
            throw new InvalidSudokuException();
    }

    /**
     *
     * @return	True if the sudoku is valid, false otherwise
     */
    public boolean isValid () {
        boolean returnValue = true;
        for (int i = 0; i < this.board.length; i++) {
            returnValue = returnValue && this.lineCheck(i);
            returnValue = returnValue && this.colCheck(i);
            if (i % 3 == 0) {
                for (int j = 0; j < this.board.length; j += 3) {
                    returnValue = returnValue && this.subArrayCheck(i, j);
                }
            }
        }
        return returnValue;
    }

    /**
     *
     * @param line	The line we want to check
     * @return		True if the line is valid
     */
    private boolean lineCheck (int line) {
        Domain checkDomain = new Domain();
        for (int j = 0; j < this.board[line].length; j++) {
            Integer temp = this.board[line][j];
            if (temp != 0) {
                if (!checkDomain.contains(temp)) {
                    return false;
                }
                checkDomain.remove(temp);
            }
        }
        return true;
    }

    /**
     *
     * @param column	The column we want to check
     * @return			True if the column is valid
     */
    private boolean colCheck (int column) {
        Domain checkDomain = new Domain();
        for (int i = 0; i < this.board.length; i++) {
            Integer temp = this.board[i][column];
            if (temp != 0) {
                if (!checkDomain.contains(temp)) {
                    return false;
                }
                checkDomain.remove(temp);
            }
        }
        return true;
    }

    /**
     *
     * @param line		The line of the top left corner of the subarray
     * @param column	The column of the top left of the subarray
     * @return			True if the subarray is valid
     */
    private boolean subArrayCheck (int line, int column) {
        Domain checkDomain = new Domain();

        for (int i = line; i < line + 3; i++) {
            for (int j = column; j < column + 3; j++) {
                Integer temp = this.board[i][j];
                if (temp != 0) {
                    if (!checkDomain.contains(temp)) {
                        return false;
                    }
                    checkDomain.remove(temp);
                }
            }
        }
        return true;
    }

    /**
     *
     * @return	The number of empty cases
     */
    private int getEmptyCases () {
        return emptyCases;
    }

    /**
     *
     * @return	True if the sudoku is complete, false otherwise
     */
    public boolean isFull () {
        return getEmptyCases() == 0;
    }

    /**
     * Function that updates the domain accordance to the new constraints added by our insertion
     *
     * @param value		The value we inserted
     * @param line		The line at which we inserted it
     * @param column	The column at which we inserted it
     */
    private void addConstraint (int value, int line, int column) {
        domains[line][column].clearAll();
        for (int i = 0; i < this.domains.length; i++) {
            for (int j = 0; j < this.domains[i].length; j++) {
                if (i == line || j == column || (i / 3 == line / 3 && j / 3 == column / 3)) {
                    domains[i][j].remove(Integer.valueOf(value));
                }
            }
        }
    }

    /**
     *
     * @return	The string output of the sudoku
     */
    @Override
    public String toString () {
        String out = "";
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                out += this.board[i][j] == 0 ? "-" : String.valueOf(this.board[i][j]);
                out += j % 3 == 2 ? "\t" : " ";
            }
            out += i % 3 == 2 ? "\n\n" : "\n";
        }
        return out;
    }

    /**
     *
     * @return	The position of the most constrained case, that is the one for which the domain is the smallest
     */
    public Position getMostConstrainedCase () {
        int smallest = Integer.MAX_VALUE;
        Position boardCase = new Position(-1, -1);
        for (int i = 0; i < this.domains.length; i++) {
            for (int j = 0; j < this.domains[i].length; j++) {
                if (this.domains[i][j].size() < smallest && this.domains[i][j].size() > 0) {
                    boardCase.setLine(i);
                    boardCase.setColumn(j);
                    smallest = this.domains[i][j].size();
                }
            }
        }
        return (boardCase.getLine() == -1 && boardCase.getColumn() == -1) ? null : boardCase;
    }

    /**
     * Function to fill a case, it will update the constraints accordingly, as well as the emptyCases value
     *
     * @param line		The line at which we want to insert
     * @param column	The column at which we want to insert
     * @param value		The value we want to insert
     */
    public void fillCase (int line, int column, int value) {
        this.board[line][column] = value;
        addConstraint(Integer.valueOf(value), line, column);
        emptyCases--;
    }

    /**
     *
     * @param line		The line of the case
     * @param column	The column of the case
     * @return			The domain of validity of that case
     */
    public Domain getDomainOfCase (int line, int column) {
        return this.domains[line][column];
    }

    /**
     *
     * @return	The 2d array of the sudoku
     */
    public int[][] getBoard () {
        return board;
    }
}

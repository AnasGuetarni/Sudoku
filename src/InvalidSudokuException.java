public class InvalidSudokuException extends Exception {
    public InvalidSudokuException () {
        super("Your entered sudoku is not valid");
    }
}
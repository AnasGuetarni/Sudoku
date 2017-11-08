public class FileFormatException extends Exception{
    public FileFormatException () {
        super("The sudoku file was not formatted correctly");
    }

    public FileFormatException (String message) {
        super(message);
    }
}

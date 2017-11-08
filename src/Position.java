public class Position {
    private int line;
    private int column;

    /**
     * Main constructor
     *
     * @param line		The line of the position
     * @param column	The column of the position
     */
    public Position (int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     *
     * @return	The line
     */
    public int getLine () {
        return line;
    }

    /**
     *
     * @param line	The new value for line
     */
    public void setLine (int line) {
        this.line = line;
    }

    /**
     *
     * @return	The column
     */
    public int getColumn () {
        return column;
    }

    /**
     *
     * @param column	The new value for column
     */
    public void setColumn (int column) {
        this.column = column;
    }

    /**
     *
     * @return	String representation of the Position
     */
    @Override
    public String toString () {
        return "Position{" +
                "line=" + line +
                ", column=" + column +
                '}';
    }
}

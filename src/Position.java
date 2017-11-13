import java.util.ArrayList;
import java.util.List;

/**
 * Class Position who permit to acceed to the default values
 */
public class Position{

    private static final List<Integer> DEFAULT_VALUES = new ArrayList<>();

    // We set the possible values of the sudoku
    static {
        DEFAULT_VALUES.add(1);
        DEFAULT_VALUES.add(2);
        DEFAULT_VALUES.add(3);
        DEFAULT_VALUES.add(4);
        DEFAULT_VALUES.add(5);
        DEFAULT_VALUES.add(6);
        DEFAULT_VALUES.add(7);
        DEFAULT_VALUES.add(8);
        DEFAULT_VALUES.add(9);
    }

    private int value;
    private List<Integer> possibleValues;

    public Position() {
        this.possibleValues = new ArrayList<>();
        this.possibleValues.addAll(DEFAULT_VALUES);
    }

    // Reset all the valued to the default values
    public void resetValues() {
        this.possibleValues.clear();
        this.possibleValues.addAll(DEFAULT_VALUES);
    }

    // We get the Value
    public int getValue() {
        return value;
    }

    // We set the value
    public void setValue(int value) {
        this.value = value;
    }

    // We get into a list the possibleValues
    public List<Integer> getPossibleValues() {
        return possibleValues;
    }
}

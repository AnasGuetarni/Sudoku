import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 09.11.16.
 */
public class Position{

    private static final List<Integer> DEFAULT_VALUES = new ArrayList<>();

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

    public void resetValues() {
        this.possibleValues.clear();
        this.possibleValues.addAll(DEFAULT_VALUES);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }
}

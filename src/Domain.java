import java.util.HashSet;

public class Domain {
    private HashSet<Integer> values;

    /**
     * Main constructor, we init a domain from 1 to 9
     */
    public Domain () {
        this.values = new HashSet<>();
        initDomain();
    }

    /**
     * Copy constructor
     * @param domain	The domain we want to copy
     */
    public Domain (Domain domain) {
        this.values = new HashSet<>(domain.getValues());
    }

    private void initDomain () {
        for (int i = 0; i < 9; i++) {
            values.add(i + 1);
        }
    }

    /**
     *
     * @param x	The value we want to remove from the domain
     */
    public void remove (Integer x) {
        this.values.remove(x);
    }

    /**
     * Method to clear the whole domain
     */
    public void clearAll () {
        this.values.clear();
    }

    /**
     *
     * @param x	The value we want to check
     * @return	True if the value is in the domain, false otherwise
     */
    public boolean contains (Integer x) {
        return this.values.contains(x);
    }

    /**
     *
     * @return	The size of the domain
     */
    public int size () {
        return this.values.size();
    }

    /**
     *
     * @return	The output of the domain values
     */
    @Override
    public String toString () {
        return values.toString();
    }

    /**
     *
     * @return	The values of the domain as an HashSet<Integer>
     */
    public HashSet<Integer> getValues () {
        return values;
    }
}

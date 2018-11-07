/**
 * an abstract class represent a data structure of hash set, which implements simple set.
 * able to perform add, contain, delete. 3 types of constructors. adjustable load factor limits.
 *
 * @author lioraryepaz
 */

public abstract class SimpleHashSet implements SimpleSet {

    protected static int INITIAL_CAPACITY = 16;

    protected int capacityMinusOne;

    /**
     * the max limit, according to which we allow our set to fill.
     */
    private float upperFactor;

    /**
     * the min limit, according to which we allow our set to empty.
     */
    private float lowerFactor;

    private static final float DEFAULT_UPPER = 0.75f;

    private static final float DEFAULT_LOWER = 0.25f;

    /**
     * Constructor - allows to define load factors
     *
     * @param upperLoadFactor the max limit, according to which we allow our set to fill.
     * @param lowerLoadFactor the min limit, according to which we allow our set to empty.
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        upperFactor = upperLoadFactor;
        lowerFactor = lowerLoadFactor;
        createSet(INITIAL_CAPACITY);
    }

    /**
     * Constructor
     */
    protected SimpleHashSet() {
        upperFactor = DEFAULT_UPPER;
        lowerFactor = DEFAULT_LOWER;
        createSet(INITIAL_CAPACITY);
    }

    /**
     * Constructor - allow us to initiate along with insertion of input Array
     *
     * @param data array of strings
     */
    protected SimpleHashSet(String[] data) {
        upperFactor = DEFAULT_UPPER;
        lowerFactor = DEFAULT_LOWER;
        createSet(INITIAL_CAPACITY);
        for (String input : data) {
            add(input);
        }
    }

    /**
     * @return number of cells in the set
     */
    public abstract int capacity();

    /**
     * initiates new array which contains our set
     *
     * @param setSize desired size of new array
     */
    protected abstract void createSet(int setSize);

    /**
     * fit our index to sizes of current table size (mod)
     *
     * @param index current index of cell
     * @return adjusted index
     */
    protected int clamp(int index) {
        return index & (capacity() - 1);
    }

    /**
     * @return lowerLoadFactor
     */
    protected float getLowerLoadFactor() {
        return lowerFactor;
    }

    /**
     * @return upperLoadFactor
     */
    protected float getUpperLoadFactor() {
        return upperFactor;
    }

    /**
     * check the current load factor against its upper limit
     *
     * @return true if there is a need for rehashing
     */
    protected boolean upperRehashCheck() {
        float loadFactor = (((float) size() + 1) / capacity());
        return (upperFactor < loadFactor);
    }

    /**
     * check the current load factor against its lower limit
     *
     * @return true if there is a need for rehashing
     */
    protected boolean lowerRehashCheck() {
        float loadFactor = (((float) size()) / capacity());
        return (lowerFactor > loadFactor);
    }

    /**
     * set the new size of the table fter rehash
     *
     * @param isIncrease true if increase size of table, false if decrease
     */
    protected void rehashSwitch(boolean isIncrease) {
        int oldSize = capacity();
        if (isIncrease) {
            oldSize <<= 1;
        } else {
            oldSize >>= 1;
            if (oldSize < 1) {
                oldSize = 1;
            }
        }
        createSet(oldSize);
    }

}

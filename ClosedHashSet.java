/**
 * represent a type of simpleHashSet - every cell contain one element, and the jumping in between cells in order
 * to allocate place for additional same-hash elements will use quadratic technique.
 *
 * @author lioraryepaz
 */

public class ClosedHashSet extends SimpleHashSet {

    /**
     * array of objects, most will be the desired data strings; some will act as deletion  markers
     */
    private Object[] mySet;

    /**
     * number of elements in the set
     */
    private int size;

    /**
     * an instance of Object, which will be used to mark deleted elements
     */
    private Object deleteMarker = new Object();

    /**
     * Constructor - allows to define load factors
     *
     * @param upperLoadFactor the max limit, according to which we allow our set to fill.
     * @param lowerLoadFactor the min limit, according to which we allow our set to empty.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Constructor
     */
    public ClosedHashSet() {
        super();
    }

    /**
     * Constructor - allow us to initiate along with insertion of input Array
     *
     * @param data array of strings
     */
    public ClosedHashSet(String[] data) {
        super(data);
    }

    /**
     * @return number of cells in the set
     */
    @Override
    public int capacity() {
        return mySet.length;
    }

    /**
     * @return number of elements in the set
     */
    public int size() {
        return size;
    }

    /**
     * given a string and quadratic index, will give the correct index in hash table
     *
     * @param value input string
     * @param i     desired quadratic index
     * @return finalized index in table
     */
    private int indexFinder(Object value, int i) {
        return clamp((value.hashCode()) + (i + i * i) / 2);
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        int result = containsHelper(searchVal);
        return result != -1;
    }

    /**
     * actual work of contain - so we can use the index for contain, & delete
     *
     * @param searchVal Value to search for
     * @return -1 if not exist, non-negative integer otherwise
     */
    private int containsHelper(String searchVal) {
        for (int i = 0; i < capacity(); i++) {
            int index = indexFinder(searchVal, i);
            Object test = mySet[index];
            if (test == null) {
                return -1;
            }
            if (test.equals(searchVal)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (contains(newValue)) {
            return false;
        } else {
            if (upperRehashCheck()) {
                rehash(true);
            }
            for (int i = 0; i < capacity(); i++) {
                int index = indexFinder(newValue, i);
                Object test = mySet[index];
                if ((test == null) || (test == deleteMarker)) {
                    mySet[index] = newValue;
                    size++;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * perform re-hash in order to change table size
     *
     * @param isIncrease true if increase size of table, false if decrease
     */
    private void rehash(boolean isIncrease) {
        Object[] tempArray = mySet;
        rehashSwitch(isIncrease);
        rehashHelper(tempArray);
    }

    /**
     * perform the re-assignment of the elements in the new array
     * @param tempArray the old table we used, Object array
     */
    private void rehashHelper(Object[] tempArray) {
        for (Object obj : tempArray) {
            if (obj != null && obj != deleteMarker) {
                for (int i = 0; i < capacity(); i++) {
                    int index = indexFinder(obj, i);
                    Object test = mySet[index];
                    if (test == null) {
                        mySet[index] = obj;
                        break;
                    }
                }
            }
        }
    }

    /**
     * initiates new array which contains our set
     *
     * @param setSize desired size of new array
     */
    @Override
    protected void createSet(int setSize) {
        mySet = new Object[setSize];
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        int index = containsHelper(toDelete);
        if (index == -1) {
            return false;
        } else {
            mySet[index] = deleteMarker;
            size--;
            if (lowerRehashCheck()) {
                rehash(false);
            }
            return true;
        }
    }

}

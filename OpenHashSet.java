/**
 * represent a type of simpleHashSet - every cell contain array of LinkedListWrapper, and every time an element need
 * to allocate in a cell, its being added to the end of the linked list of that cell.
 *
 * @author lioraryepaz
 */

public class OpenHashSet extends SimpleHashSet {

    /**
     * array of LinkedListWrappers, each has a linked list data member. represent our set.
     */
    private LinkedListWrapper[] mySet;

    /**
     * number of elements in the set
     */
    private int size;

    /**
     * initiates new array which contains our set
     *
     * @param setSize desired size of new array
     */
    @Override
    protected void createSet(int setSize) {
        mySet = new LinkedListWrapper[setSize];
        for (int i = 0; i < capacity(); i++) {
            mySet[i] = new LinkedListWrapper();
        }
    }

    /**
     * Constructor - allows to define load factors
     *
     * @param upperLoadFactor the max limit, according to which we allow our set to fill.
     * @param lowerLoadFactor the min limit, according to which we allow our set to empty.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Constructor
     */
    public OpenHashSet() {
        super();
    }

    /**
     * Constructor - allow us to initiate along with insertion of input Array
     *
     * @param data array of strings
     */
    public OpenHashSet(String[] data) {
        super(data);
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal) {
        return mySet[indexFinder(searchVal)].contains(searchVal);
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (upperRehashCheck()) {
            rehash(true);
        }
        if (mySet[indexFinder(newValue)].add(newValue)) {
            size++;
            return true;
        } else return false;
    }

    /**
     * perform re-hash in order to change table size.
     *
     * @param isIncrease true if increase size of table, false if decrease
     */
    private void rehash(boolean isIncrease) {
        LinkedListWrapper[] tempArray = mySet;
        rehashSwitch(isIncrease);
        rehashHelper(tempArray);
    }

    /**
     * perform the re-assignment of the elements in the new array
     *
     * @param tempArray the old table we used, Object array
     */
    private void rehashHelper(LinkedListWrapper[] tempArray) {
        for (LinkedListWrapper wrapper : tempArray) {
            for (String string : wrapper.getMyBucket()) {
                mySet[indexFinder(string)].add(string);
            }
        }
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if (mySet[indexFinder(toDelete)].delete(toDelete)) {
            size--;
            if (lowerRehashCheck()) {
                rehash(false);
            }
            return true;
        } else return false;
    }

    /**
     * given a string and quadratic index, will give the correct index in hash table
     *
     * @param value input string
     * @return finalized index in table
     */
    private int indexFinder(String value) {
        return clamp(value.hashCode());
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

}

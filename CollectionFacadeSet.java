import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * facade class - wrap collection as simple set data structure, while implementing SimpleSet
 *
 * @author lioraryepaz
 */

public class CollectionFacadeSet implements SimpleSet {

    /**
     * the collection in hand
     */
    protected Collection<String> collection;

    /**
     * Constructor
     *
     * @param inputCollection collection to adjust as set
     */
    public CollectionFacadeSet(Collection<String> inputCollection) {
        Set<String> temp = new TreeSet<String>(inputCollection);
        inputCollection.clear();
        inputCollection.addAll(temp);
        collection = inputCollection;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        return collection.contains(searchVal);
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (contains(newValue)) {
            return false;
        } else {
            collection.add(newValue);
            return true;
        }
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return collection.size();
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        return collection.remove(toDelete);
    }


}

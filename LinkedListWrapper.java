import java.util.LinkedList;

/**
 * a custom made class created to go around java limitations. wrap the linked list in order to create an array of them.
 *
 * @author lioraryepaz
 */

class LinkedListWrapper {

    /**
     * has a LinkedList data membar - wrapped.
     */
    private LinkedList<String> myBucket = new LinkedList<String>();

    /**
     * add a string to the linked list if not there
     *
     * @param input string input
     * @return true if added
     */
    boolean add(String input) {
        if (contains(input)) {
            return false;
        }
        myBucket.add(input);
        return true;
    }

    /**
     * delete an element from  the linked list
     *
     * @param input string to delete
     * @return true if deleted
     */
    boolean delete(String input) {
        int i = 0;
        for (String s : myBucket) {
            if (s.equals(input)) {
                myBucket.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * check if a string is in the linked list
     *
     * @param input string to check
     * @return true if found
     */
    boolean contains(String input) {
        for (String stringTemp : myBucket) {
            if (stringTemp.equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return LinkedList
     */
    LinkedList<String> getMyBucket() {
        return myBucket;
    }
}

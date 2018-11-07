import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * this class is static and only use to run analysis on our data, and compare between different data structures.
 *
 * @author lioraryepaz
 */

public class SimpleSetPerformanceAnalyzer {

    private static SimpleSet[] dastCreator() {
        SimpleSet[] dastArray = new SimpleSet[5];
        dastArray[0] = new OpenHashSet();
        dastArray[1] = new ClosedHashSet();
        dastArray[2] = new CollectionFacadeSet(new TreeSet<String>());
        dastArray[3] = new CollectionFacadeSet(new LinkedList<String>());
        dastArray[4] = new CollectionFacadeSet(new HashSet<String>());
        return dastArray;
    }

    private static void containCheck(SimpleSet[] array, String[] list, String[] names) {
        for (int i = 0; i < array.length; i++) {
            for (String word : list) {
                for (int l = 0; l < 150; l++) {
                    array[i].contains(word);
                }
                float before = System.nanoTime();
                for (int l = 0; l < 1000; l++) {
                    array[i].contains(word);
                }
                float result = (System.nanoTime() - before) / 1000;
                System.out.println("contain " + word + " in " + names[i] + " took " + result + "ns");
            }
        }
    }

    public static void main(String[] args) {

        String[] names = {"OpenHashSet", "ClosedHashSet", "TreeSet", "LinkedList", "HashSet"};

        String[] data1 = Ex3Utils.file2array(args[0]);
        String[] data2 = Ex3Utils.file2array(args[1]);
        String[][] data = {data1, data2};

        for (int j = 0; j < data.length; j++) {
            SimpleSet[] array = dastCreator();
            for (int i = 0; i < array.length; i++) {
                float before = System.nanoTime();
                for (String input : data[j]) {
                    array[i].add(input);
                }
                float result = (System.nanoTime() - before) / 1000000;
                System.out.println("add data" + (j + 1) + " for " + names[i] + " took " + result + "ms");
            }
            switch (j) {
                case 0:
                    String[] data1Words = {"hi", "-13170890158"};
                    containCheck(array, data1Words, names);
                    break;
                case 1:
                    String[] data2Words = {"23", "hi"};
                    containCheck(array, data2Words, names);
                    break;
            }
        }
    }
}

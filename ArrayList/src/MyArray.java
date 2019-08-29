/**
 * Array implementations.
 * @author Yu Du
 */
public class MyArray {

    /**
     * Array of String to store words.
     */
    private String[] array;

    /**
     * Number of words in the array (none nulls).
     */
    private int nElems;

    /**
     * My array constructor.
     * Worst-case running time complexity : O(1).
     */
    public MyArray() {
        nElems = 0;
        array = new String[0];
    }

    /**
     * Method to initialize a array with capacity.
     * Worst-case running time complexity : O(n).
     * @param initalCapacity the initial capacity of the array
     */
    public MyArray(int initalCapacity) {
        array = new String[initalCapacity];
    }

    /**
     * add method to add word in the array to the end.
     * Worst-case running time complexity : O(1).
     * @param text the word wish to add
     */
    public void add(String text) {
        if (text != null && !text.isEmpty() && ensureLetter(text)) {
            ensureCapacity(nElems + 1);
            array[nElems] = text;
            nElems++;
        }
    }

    /**
     * Method to ensure words in correct format [a-zA-Z] only.
     * @param text the word to check
     * @return words that satisfy the condition
     */
    private static boolean ensureLetter(String text) {
        return text.matches("[a-zA-Z]+");
    }

    /**
     * Method to ensure the capacity of the array.
     * @param minCapacity the minimum capacity
     */
    private void ensureCapacity(int minCapacity) {
        int oldCapacity = array.length;
        int newCapacity;
        if (minCapacity > oldCapacity) {
            if (oldCapacity == 0) {
                newCapacity = oldCapacity * 2 + 1;
            } else {
                newCapacity = oldCapacity * 2;
            }
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
        String[] newArray = new String[newCapacity];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
        }
    }

    /**
     * Method to search for particular word.
     * Worst-case running time complexity : O(n).
     * @param key the word want to search
     * @return true if found false otherwise
     */
    public boolean search(String key) {
        for (String word : array) {
            try {
                if (word.equals(key)) {
                    return true;
                    }
                } catch (NullPointerException e) {
                    return false;
                }
        }
        return false;
    }

    /**
     * method to return the number of words.
     * Worst-case running time complexity : O(1).
     * @return number of words
     */
    public int size() {
        return nElems;
    }

    /**
     * Method to return the capacity of the array.
     * Worst-case running time complexity : O(1).
     * @return the capacity of the array
     */
    public int getCapacity() {
        return array.length;
    }

    /**
     * Display the words in array in one line.
     * Worst-case running time complexity : O(n).
     */
    public void display() {
        StringBuilder output = new StringBuilder();
        for (String word : array) {
            if (word != null) {
                output.append(word)
                      .append(" ");
            }
        }
        System.out.println(output);
    }

    /**
     * Method to remove the duplicates in the array.
     * Worst-case running time complexity : O(n).
     */
    public void removeDups() {
        //find duplicates and replace with null, compare the element with the rest.
        for (int i = 0; i < nElems; i++) {
            for (int j = i + 1; j < nElems; j++) {
                if ((array[i] != null) && (array[j] != null) && (array[i].equals(array[j]))) {
                    array[j] = null;
                }
            }
        }

         //look for nulls, and get number of duplicates.
        int countNull = 0;

        for (int k = 0; k < nElems; k++) {
            if (array[k] == null) {
               countNull++;
            }
        }

        // Shift the nulls to the end to ensure no holes.
        for (int k = 0; k < nElems; k++) {
            if (array[k] == null) {
                for (int q = k + 1; q < nElems; q++) {
                    if (array[q] != null) {
                        array[k] = array[q];
                        array[q] = null;
                        break;
                    }
                }
            }
        }
      nElems = nElems - countNull;
    }
}

/**
 * Linear Probing of Hash Map
 * @author Yu Du
 */
public class MyHashTable implements HTInterface {

    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;

    /**
     * The size in the table.
     */
    private int size = 0;

    /**
     * Number of collisions happened in the table.
     */
    private int numOfCollisions = 0;

    /**
     * The constant load factor for rehash.
     */
    private static final double LOAD_FACTOR = 0.5;

    /**
     * Default capacity for the initial hash array.
     */
    private static final int DEFAULT_CAP = 10;

    /**
     * The value used as base in hash function to convert string to integer.
     */
    private static final int HASH_BASE = 27;

    /**
     * Flag to indicate the deleted item.
     */
    private static final DataItem DELETED = new DataItem("");

    /**
     * no-arg constructor to create hash array with default capacity.
     */
    public MyHashTable() {
        hashArray = new DataItem[DEFAULT_CAP];
    }

    /**
     * constructor to create hash array with given capacity.
     * @param initialCapacity integer value for capacity of the hash array
     */
    public MyHashTable(int initialCapacity) {
        hashArray = new DataItem[initialCapacity];
    }

    /**
     * Helper method to validate inserting words.
     * @param text the string need to be check
     * @return true the text is a valid word, else false
     */
    private static boolean ensureLetter(String text) {
        return text.matches("[a-zA-Z]+");
    }

    @Override
    public void insert(String value) {
        if (value != null && !value.isEmpty() && ensureLetter(value)) {
            int hashVal = hashValue(value);
            DataItem item = new DataItem(value);
            boolean hasCollision = false;

            while ((hashArray[hashVal] != null) && (hashArray[hashVal] != DELETED)) {
                if (hashArray[hashVal].value.equals(value)) {
                    hashArray[hashVal].frequency++;
                    return;
                }

                if (hashValue(hashArray[hashVal].value) == hashValue(value)) {
                    hasCollision = true;
                }

                hashVal = hashVal + 1;
                hashVal = hashVal % hashArray.length;
            }

            hashArray[hashVal] = item;
            size++;

            if (hasCollision) {
                numOfCollisions++;
            }

            if (checkRehash()) {
                rehash();
            }
        }
        return;
    }

    // ** o1 running time complexity
    @Override
    public int size() {
        return size;
    }

    @Override
    public void display() {
        StringBuilder table = new StringBuilder();
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] == null) {
                table.append("** ");
            } else if (hashArray[i] == DELETED) {
                table.append("#DEL# ");
            } else {
                table.append("[").append(hashArray[i].value).append(", ").append(hashArray[i].frequency).append("] ");
            }
        }
        System.out.println(table);
    }

    @Override
    public boolean contains(String key) {
        int hashVal = hashValue(key);
        int count = 0;
        while ((hashArray[hashVal] != null) && (count < hashArray.length)) {
            if (hashArray[hashVal].value.equals(key)) {
                return true;
            }
            hashVal = hashVal + 1;
            count = count + 1;
            hashVal = hashVal % hashArray.length;
        }
        return false;
    }

    // ** o1 running time complexity
    @Override
    public int numOfCollisions() {
        return numOfCollisions;
    }

    @Override
    public int hashValue(String value) {
        return hashFunc(value);
    }

    @Override
    public int showFrequency(String key) {
        int hashVal = hashFunc(key);
        int count = 0;
        while ((hashArray[hashVal] != null) && (count < hashArray.length)) {
            if (hashArray[hashVal].value.equals(key)) {
                return hashArray[hashVal].frequency;
            }
            hashVal = hashVal + 1;
            count = count + 1;
            hashVal = hashVal % hashArray.length;
        }

        return 0;
    }

    @Override
    public String remove(String key) {
        int hashVal = hashFunc(key);
        int count = 0;
        while ((hashArray[hashVal] != null) && (count < hashArray.length)) {
            if (hashArray[hashVal].value.equals(key)) {
                String item = hashArray[hashVal].value;
                hashArray[hashVal] = DELETED;
                size--;
                return item;
            }
            hashVal = hashVal + 1;
            count = count + 1;
            hashVal = hashVal % hashArray.length;
        }

        return null;
    }

    /**
     * Horner's method used as hashing method
     *
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        // step 1: convert to integer
        int hashVal = 0;
        int n = input.length();
        // Horner's method implementation, minus 96 because of ASCII code
        for (int i = 0; i < n; i++) {
            if (input.charAt(i) <= 96 || input.charAt(i) >= 123) {
                hashVal = 0;
            } else {
                if (i == n - 1) {
                    hashVal += input.charAt(i) - 96;
                } else {
                    hashVal = (hashVal + input.charAt(i) - 96) * HASH_BASE;
                }
            }
            hashVal = hashVal % hashArray.length;
        }

        hashVal = hashVal & 0x7fffffff;
        return hashVal;
    }

    /**
     * doubles array length and rehash items whenever the load factor is reached.
     * Note: do not include the number of deleted spaces to check the load factor.
     * Remember that deleted spaces are available for insertion.
     */
    private void rehash() {
        int nextPrime = findNextPrime(hashArray.length);
        StringBuilder out = new StringBuilder();
        out.append("Rehashing ").append(size).append(" items, new length is ").append(nextPrime);
        System.out.println(out);
        size = 0;
        numOfCollisions = 0;
        DataItem[] temp = hashArray;
        DataItem[] newArray = new DataItem[nextPrime];
        hashArray = newArray;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null && temp[i] != DELETED) {
                int freq = 0;
                while (freq < temp[i].frequency) {
                    insert(temp[i].value);
                    freq++;
                }
            }
        }
    }

    /**
     * Helper method to check if need to hash (load > 0.5).
     * @return true if need rehash, false if not
     */
    private boolean checkRehash() {
        int n = hashArray.length;
        double load = size / (double) n;
        if (load > LOAD_FACTOR) {
            return true;
        }
        return false;
    }

    /**
     * Private static helper method to calculate the proper rehash size.
     * @param currLen the current length of the hash array
     * @return the next prime number of the double of the current array length
     */
    private static int findNextPrime(int currLen) {
        int newLen = 2 * currLen;
        int flag = 0; //0:initial, 1:found, -1:not found
        while (flag != 1) {
            for (int i = 2; i <= newLen / 2; i++) {
                if (newLen % i == 0) {
                    flag = -1;
                    break;
                }
            }
            if (flag == -1) {
                newLen++;
                flag = 0;
            } else {
                flag = 1;
            }
        }
        return newLen;
    }

    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        /**
         * Constructor to create the data item.
         * @param data String value for data item
         */
        DataItem(String data) {
            this.value = data;
            this.frequency = 1;
        }
    }

}

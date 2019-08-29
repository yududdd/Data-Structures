/**
 * Implement Singly LinkedList using recursion.
 * @author Yu Du
 */

public class SortedLinkedList implements ListInterface {

    /**
     * The reference to the head reference.
     */
    private Node head;

    /**
     * No arg constructor to construct an empty linked list.
     */
    public SortedLinkedList() {
        head = null;
    }

    /**
     * The other constructor return a sorted, nones duplicated, linked list.
     * @param unsorted unsorted array of strings
     */
    public SortedLinkedList(String[] unsorted) {
        this();
        this.add(unsorted[0]);
        this.addHelper(unsorted, 1);
    }


    /**
     * Constructor add helper method helping to construct the sorted array.
     * @param array the array of strings that needed to be sorted
     * @param n the nth element of the array
     */
    private void addHelper(String[] array, int n) {
        if (n == array.length) {
            return;
        } else {
            add(array[n]);
            addHelper(array, n + 1);
        }
        return;
    }

    /**
     * An add helper method to add a string to proper position
     * in a sorted linked list, also prevent duplicates, all using recursions.
     * @param curr reference to current node
     * @param value value to be inserted
     * @param prev previous node of the current node
     */
    private void addNode(Node curr, String value, Node prev) {
        if (head.next == null) {
            if (value.compareToIgnoreCase(head.data) > 0) {
                head.next = new Node(value, null);
            } else if (value.compareToIgnoreCase(curr.data) < 0) {
                head = new Node(value, head);
            } else {
                return;
            }
            return;
        }

        if (curr.next == null) {
            if (value.compareToIgnoreCase(curr.data) > 0) {
                curr.next = new Node(value, null);
            } else if (value.compareToIgnoreCase(curr.data) < 0) {
                prev.next = new Node(value, curr);
            } else {
                return;
            }
            return;
        } else {
            if (value.compareToIgnoreCase(curr.data) > 0) {
                addNode(curr.next, value, curr);
            } else if (value.compareToIgnoreCase(curr.data) < 0) {
                if (prev == null) {
                    head = new Node(value, head);
                } else {
                    prev.next = new Node(value, curr);
                }
            } else {
                return;
            }
            return;
        }
    }

    /**
     * Helper method to validate inserting words.
     * @param text the string need to be check
     * @return true the text is a valid word, else false
     */
    private static boolean ensureLetter(String text) {
        return text.matches("[a-zA-Z]+");
    }

    /*
     * (non-Javadoc)
     * @see MyListInterface#add(java.lang.String)
     * Recursion Explain: The warper method. If head is null, the add head to the linked list
     * else return the initial condition where pass the reference head node for compare
     * with previous node is null.
     */
    @Override
    public void add(String value) {
        if (value != null && !value.isEmpty() && ensureLetter(value)) {
            if (head == null) {
                head = new Node(value, null);
                return;
            } else {
                addNode(head, value, null);
            }
        }
        return;
    }

    /*
     * Recursion Explain: base case is the last node so add 0 and return
     * recursive case is if it is not 0, keep adding 1 as going through the list.
     */
    /**
     * Helper method to count number of nodes.
     * @param curr Reference to the current node
     * @return the size of the linked list
     */
    private static int countHelper(Node curr) {
        if (curr == null) {
            return 0;
        } else {
            return 1 + countHelper(curr.next);
        }
    }
    /*
     * (non-Javadoc)
     * @see MyListInterface#size()
     * Wrapper class for initialize the count with head node.
     */

    @Override
    public int size() {
        return countHelper(head);
    }

    /*
     * Recursion Explain: base case is last node, so return the last data
     * recursive case: return the current data and combine with next node's data.
     */

    /**
     * Helper method to display the result.
     * @param curr the reference to the current node
     * @return the builded string with comma plus space after
     */
    private static String displayHelper(Node curr) {
        if (curr.next == null) {
            return curr.data;
        } else {
            return curr.data + ", " + displayHelper(curr.next);
        }
    }
    /*
     * (non-Javadoc)
     * @see MyListInterface#display()
     * Wrapper class for initialize the first node to be head
     */

    @Override
    public void display() {
        if (head == null) {
            return;
        }
        StringBuilder output = new StringBuilder();
        output.append("[")
              .append(displayHelper(head))
              .append("]");
        System.out.println(output);
    }

    /*
     * Recursion Explain : If found, return true, else check the next node
     * base case if last element still does not have the key, return false
     * recursive case: check the current' next node.
     */

    /**
     * Helper method to check if linked list contains.
     * @param curr reference to the current node
     * @param key the string to check
     * @return true if found, else false
     */
    private static boolean containsNode(Node curr, String key) {
        if (curr.data.equals(key)) {
            return true;
        } else {
            if (curr.next == null) {
                return false;
            }
            return containsNode(curr.next, key);
        }
    }


    @Override
    public boolean contains(String key) {
        if (key != null && !key.isEmpty() && ensureLetter(key)) {
            if (head == null) {
                System.out.println("in this block");
                return false;
            } else {
                return containsNode(head, key);
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    @Override
    public String removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            String temp = head.data;
            head = head.next;
            return temp;
        }
    }


    /**
     * Helper method to remove a particular node.
     * @param prev reference to the previous node
     * @param curr reference to the current node
     * @param index the index of node to remove
     * @param count tracker for the current position
     * @return String object that is removed
     */
    private static String removeNode(Node prev, Node curr, int index, int count) {
         if (curr == null) {
             return null;
         }
         if (count == index) {
             Node temp = curr;
             prev.next = curr.next;
             return temp.data;
         } else {
             prev = curr;
             curr = curr.next;
             return removeNode(prev, curr, index, count + 1);
         }
    }

    @Override
    public String removeAt(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index < 0 || index >= size()) {
            throw new RuntimeException();
        }
        return removeNode(null, head, index, 0);
    }


    /**
     * static nested class for Node class.
     */
    private static class Node {
        /**
         * String data of a node.
         */
        private String data;
        /**
         * the next node of the current node.
         */
        private Node next;

        /**
         * Constructor with a new node with data and next node reference.
         * @param newData the data, string, of the node
         * @param newNext refer to the next node
         */
        Node(String newData, Node newNext) {
            data = newData;
            next = newNext;
        }
    }

}

/** Binary Search Trees
 *
 * @author Yu Du
 */
public class BST implements BSTInterface {
    /**
     * reference to root node.
     */
    private Node root;

    /**
     * Constructs an empty BST.
     */
    public BST() {
        root = null;
    }

    /**
     * Searches for the specified key in the tree.
     * @param key key of the element to search
     * @return boolean value indication of success or failure
     */
    @Override
    public boolean find(int key) {
        // tree is empty
        if (root == null) {
            return false;
        }

        Node curr = root;
        // while not found
        while (curr.key != key) {
            if (curr.key < key) {
                // go right
                curr = curr.right;
            } else {
                // go left
                curr = curr.left;
            }

            // not found
            if (curr == null) {
                return false;
            }
        }
        return true; // found
    }

    /**
     * Inserts a new element into the tree.
     * @param key key of the element
     * @param value value of the element
     */
    @Override
    public void insert(int key, int value) {
        Node newNode = new Node(key, value);
        // empty tree
        if (root == null) {
            root = newNode;
            return;
        }

        Node parent = root; // keep track of parent
        Node curr = root;
        while (true) {
            // no duplicate keys allowed
            // simply keep the existing one here
            if (curr.key == key) {
                return;
            }

            parent = curr; // update parent
            if (curr.key < key) {
                // go right
                curr = curr.right;
                if (curr == null) {
                    // found a spot
                    parent.right = newNode;
                    return;
                }
            } else {
                // go left
                curr = curr.left;
                if (curr == null) {
                    // found a spot
                    parent.left = newNode;
                    return;
                }
            } // end of if-else to go right or left
        } // end of while
    } // end of insert method

    /**
     * Deletes an element from the tree using the specified key.
     * @param key key of the element to delete
     */
    @Override
    public void delete(int key) {
        // empty tree
        if (root == null) {
            return;
        }

        Node parent = root;
        Node curr = root;
        /*
         * flag to check left child
         * use this flag during actual deletion process
         * which happens after the while loop
         */
        boolean isLeftChild = true;

        while (curr.key != key) {
            parent = curr; // update parent first
            if (curr.key < key) { // go right
                isLeftChild = false;
                curr = curr.right;
            } else { // go left
                isLeftChild = true;
                curr = curr.left;
            }

            // case 1: not found
            if (curr == null) {
                return;
            }
        }

        if (curr.left == null && curr.right == null) {
            // case 2: leaf
            if (curr == root) {
                root = null;
                System.out.println(root);
            } else if (isLeftChild) {
                parent.left = null;
                System.out.println(parent.left);
            } else {
                parent.right = null;
                System.out.println(parent.right);
            }
        } else if (curr.right == null) {
            // case 3: one child (no right child)
            if (curr == root) {
                root = curr.left;
            } else if (isLeftChild) {
                parent.left = curr.left;
            } else {
                parent.right = curr.left;
            }
        } else if (curr.left == null) {
            // case 3: one child (no left child)
            if (curr == root) {
                root = curr.right;
            } else if (isLeftChild) {
                parent.left = curr.right;
            } else {
                parent.right = curr.right;
            }
        } else {
            // case 4: two children
            // here we use successor but using predecessor is also an option
            Node successor = getSuccessor(curr);

            if (curr == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            //connect the left subtree
            successor.left = curr.left;
        }
    }

    /**
     * Helper method to find the successor of the toDelete node.
     * This tries to find the smallest value of the right subtree
     * of the toDelete node by going down to the left most node in the subtree
     * @param toDelete node to delete
     * @return the successor of the toDelete node
     */
    private Node getSuccessor(Node toDelete) {
        Node successorParent = toDelete;
        Node successor = toDelete;
        // start the search from the root of the right subtree
        Node curr = toDelete.right;

        // move down to left as far as possible in the right subtree
        // successor's left child must be null
        while (curr != null) {
            successorParent = successor;
            successor = curr;
            curr = curr.left;
        }

        /*
         * If successor is NOT the right child of the node to delete, then
         * need to take care of two connections in the right subtree
         */
        if (successor != toDelete.right) {
            successorParent.left = successor.right;
            successor.right = toDelete.right;
        }

        return successor;
    }

    /**
     * Traverses and prints values of the tree in ascending order based on key.
     */
    @Override
    public void traverse() {
        StringBuilder sb = new StringBuilder();
        inOrderHelper(root, sb);
        System.out.println(sb);
    }
    
    /**
     * Recursive helper method to traverse the tree.
     * @param toVisit node to visit
     * @param sb stringbuilder instance to append values of the node
     */
    private void inOrderHelper(Node toVisit, StringBuilder sb) {
        if (toVisit != null) {
            inOrderHelper(toVisit.left, sb);
            sb.append("[").append(toVisit.key).append(",").append(toVisit.value).append("]");
            inOrderHelper(toVisit.right, sb);
        }
    }

    /**
     * static nested Node class for Node.
     */
    private static class Node {
        /**
         * Key integer.
         */
        private int key;
        /**
         * double value mapped to the key.
         */
         private int value;
        /**
         * references to left and right children nodes.
         */
        private Node left, right;

        /**
         * Constructs a new node with key and value.
         * @param k integer key
         * @param v double value
         */
        @SuppressWarnings("unused")
		Node(int k, int v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }


}
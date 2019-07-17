import java.util.Random;

/**
 * Optimized version of the Merge Sort
 * @author Yu Du
 *
 */
public class MergeSort {
	
	/**
	 * Constant for the array size to sort.
	 */
	private static final int SIZE = 10000;

	/**
	 * Random numbers.
	 */
	private static Random rand = new Random();

	/**
	 * Merge Sort method
	 * @param from input array to sort
	 */
	public static void mergeSort(int[] from) {
		//create a new array
		int[] to = new int[from.length];
		mergeSort(from, to, 0, from.length - 1);
	}

	/**
	 * helper method to merge sort from array using an auxiliary array.
	 * @param from input array to sort
	 * @param to auxiliary array to use
	 * @param left left bound
	 * @param right right bound
	 */
	public static void mergeSort(int[] from, int[] to, int left, int right) {
		//base case
		if (left >= right) {
			return;
		}
		
		//recursive case
		
		int mid = left + (right - left) / 2;
		mergeSort(from, to, left, mid);
		mergeSort(from, to, mid + 1, right);
		merge(from, to, left, mid+ 1, right);
	}

	/**
	 * Not creating multiple arrays, only 2 arrays
	 * @param from input array
	 * @param to auxiliary array to use
	 * @param leftPos starting point of left half
	 * @param rightPos starting point of right half
	 * @param rightBound upper bound of the right half
	 */
	public static void merge(int[] from, int[] to, int leftPos, int rightPos, int rightBound) {
		
		int leftBound = rightPos - 1;
		
		//Do not put toIndex = 0, because each time this need to be a new index.
		int toIndex = leftPos;
		
		int numOfElems = rightBound - leftPos + 1;
		
		while(leftPos <= leftBound && rightPos <= rightBound) {
            if(from[leftPos] <= from[rightPos]) {
                to[toIndex++] = from[leftPos++]; 
            } else {
                to[toIndex++] = from[rightPos++];
            }
        }

        while (leftPos <= leftBound) {
            to[toIndex++] = from[leftPos++];
        }

        while(rightPos <= rightBound) {
            to[toIndex++] = from[rightPos++];
        }

        for(int i = 0; i < numOfElems; i++, rightBound--) {
            from[rightBound] = to[rightBound];
        }
	}

	/**
     * A simple debugging program to check if array is sorted.
     * @param array array to check
     * @return true if sorted and false if not
     */
    private static boolean isSorted(int[] array) {
        return isSorted(array, 0, array.length - 1);
    }

    /**
     * Helper method to check if array is sorted.
     * @param array array to check
     * @param lo low boundary
     * @param hi high boundary
     * @return true if sorted and false if not
     */
    private static boolean isSorted(int[] array, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Test program to check merge sort and its running time.
     * @param args arguments
     */
    public static void main(String[] args) {
        int[] array = new int[SIZE];

        for (int i = 0; i < SIZE; i++) array[i] = rand.nextInt();

        //for (int i = 0; i < SIZE; i++) array[i] = SIZE - i;

        Stopwatch timer = new Stopwatch();
        mergeSort(array);
        System.out.println(
                "Time taken to sort " + SIZE + " elements (Merge Sort): " + timer.elapsedTime() + " milliseconds");

        // to make sure sorting works
        // add "-ea" vm argument
        assert isSorted(array);
    }

}

import java.util.Arrays;

/**
 * Simple Sorting Algorithms Implementations
 * Bubble Sort, Selection Sort, and Insertion Sort
 * @author yudu
 *
 */
public class SimpleSorting {

	/**
	 * Bubble Sort runs in O(n^2) in the worst-case
	 * Slow
	 * @param data an array of integer to sort.
	 */
	public static void bubbleSort(int[] data) {
		//move backward from the last index.
		for (int out = data.length - 1; out >= 1; out--) {
			//move forward from the 0th position
			for (int in = 0; in < out; in++) {
				if (data[in] > data[in + 1]) {
					swap(data, in, in + 1);
				}
			}
		}
	}

	/**
	 * Selection Sort also runs in O(n^2) in the worst-case
	 * Focus on smallest value
	 * Faster than bubble sort because of less swaps.
	 * @param data
	 */
	public static void selectionSort(int[] data) {
		int min;
		for (int out = 0; out < data.length; out++) {
			//find the smallest value and put into the left index
			min = out;
			for (int in = out + 1; in < data.length; in++) {
				if (data[in] < data[min]) {
					min = in;
				}
			}
			//swap happens outside the inner loop
			if (out != min) {
				swap(data, out, min);
			}
		}
	}

    /**
     * Insertion sort runs in O(n^2) in the worst case.
     * But its best case running time complexity is O(n).
     * It is the fastest among the three.
     * Sensitive to the input values.
     *
     * Less number of comparisons on average.
     * Uses shifting (copying) instead of swapping (one swap equals to three copies).
     * @param data an array of int to sort
     */
    public static void insertionSort(int[] data) {
        // start from the 1st index till the last index
        for (int out = 1; out < data.length; out++) {
            int tmp = data[out]; // store the value temporarily
            int in = out; // initially set to be the same as out

            /* loop backward to check the sorted section
             * but not necessarily all the way to the 0th
             * On average, look halfway through the sorted section
             */
            while (in > 0 && data[in - 1] >= tmp) {
                data[in] = data[in - 1]; // shift to right
                in--;
            }
            // finally, INSERT tmp value into the right position in the sorted section
            if (out != in) {
                data[in] = tmp;
            }
        }
    }
	
	/**
	 * private static helper method to swap two elements
	 * @param data an array to update
	 * @param fir index of the first element 
	 * @param sec index of the second element
	 */
	private static void swap(int[] data, int fir, int sec) {
		int temp = data[fir];
		data[fir] = data[sec];
		data[sec] = temp;
	}
	

	/**
	 * Simple test programs with int arrays
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		int[] a = { 4, 8, 2, 5, 3 };
		int[] b = a.clone();
		int[] c = a.clone();

		System.out.println("Before sort (bubble sort): " + Arrays.toString(a));
		bubbleSort(a);
		System.out.println("After sort (bubble sort): " + Arrays.toString(a));

		System.out.println("Before sort (selection sort): " + Arrays.toString(b));
		selectionSort(b);
		System.out.println("After sort (selection sort): " + Arrays.toString(b));

		System.out.println("Before sort (insertion sort): " + Arrays.toString(c));
		insertionSort(c);
		System.out.println("After sort (insertion sort): " + Arrays.toString(c));

	}

}

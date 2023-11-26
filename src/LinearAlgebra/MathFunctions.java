package LinearAlgebra;

import java.util.Arrays;
import java.util.Comparator;

public final class MathFunctions{
	public static boolean includes(int[] arr,int data) {
		for(int t:arr) {
			if(data==t) {
				return true;
			}
		}
		return false;
	}
	
	//code from GFG
	public static void reverse(int[] array)
    {
        // Length of the array
        int n = array.length;
 
        // Swapping the first half elements with last half
        // elements
        for (int i = 0; i < n / 2; i++) {
 
            // Storing the first half elements temporarily
            int temp = array[i];
 
            // Assigning the first half to the last half
            array[i] = array[n - i - 1];
 
            // Assigning the last half to the first half
            array[n - i - 1] = temp;
        }
    }
	
	//ChatGPT code
	public static int findNthBiggest(int[] array, int n) {
        // Check if n is within the valid range
        if (n <= 0 || n > array.length) {
            throw new IllegalArgumentException("Invalid value of n");
        }

        // Create a copy of the array
        int[] sortedArray = Arrays.copyOf(array, array.length);

        // Sort the copy
        Arrays.sort(sortedArray);

        // Find the nth largest value
        int nthLargestValue = sortedArray[array.length - n];

        // Search for the nth largest value in the original array
        for (int i = 0; i < array.length; i++) {
            if (array[i] == nthLargestValue) {
                return i;
            }
        }

        // This should not happen if the array contains at least n elements
        throw new IllegalStateException("Unable to find the nth largest element");
    }
	
	//ChatGPT code
	public static int[] getSortedIndexes(int[] array) {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            indexes[i] = i;
        }

        Arrays.sort(indexes, Comparator.comparingInt(index -> array[(int) index]).reversed());

        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = indexes[i];
        }

        return result;
    }
}
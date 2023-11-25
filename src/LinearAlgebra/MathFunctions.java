package LinearAlgebra;

import java.util.*;

public final class MathFunctions{
	public static boolean includes(int[] arr,int data) {
		for(int t:arr) {
			if(data==t) {
				return true;
			}
		}
		return false;
	}
	
	

	//this method is made by ChatGPT.
	public static <T> T[] RemovePairs(T[] objects, int[] indexes) {
	        Map<T, Integer> objectIndexMap = new HashMap<>();
	        List<T> modifiedList = new ArrayList<>();

	        for (int i = 0; i < indexes.length; i += 2) {
	            int index1 = indexes[i];
	            int index2 = indexes[i + 1];

	            // Check if the objects at the specified indexes are equivalent
	            if (objects[index1].equals(objects[index2])) {
	                T object = objects[index1];
	                
	                // Add the object to the result if not already present
	                if (!objectIndexMap.containsKey(object)) {
	                    modifiedList.add(object);
	                    objectIndexMap.put(object, index1);
	                }
	            } else {
	                // If objects are not equivalent, add both to the result
	                modifiedList.add(objects[index1]);
	                modifiedList.add(objects[index2]);
	            }
	        }

	        // Convert the list to an array
	        T[] modifiedArray = (T[]) modifiedList.toArray();
	        //noinspection unchecked
	        return modifiedArray;
	}
}
package LinearAlgebra;

public final class MathFunctions{
	public static boolean includes(int[] arr,int data) {
		for(int t:arr) {
			if(data==t) {
				return true;
			}
		}
		return false;
	}
}
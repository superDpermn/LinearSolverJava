package LinearAlgebra;

public final class MathFunctions{
	public static int maxIndex(int[] arr) {
		int max = 0;
		int maxVal = 0;
		for(int i = 0; i < arr.length; i++) {
			int currentVal = Math.abs(arr[i]);
			if(currentVal > maxVal) {
				maxVal = currentVal;
				max = i;
			}
		}
		return max;
	}
	
	//{1,3,5,2}
	public static int maxIndex(int[] arr,int alreadyFound) {
		int max = 0;
		int maxVal = 0;
		for(int i = 0; i < arr.length; i++) {
			int currentVal = Math.abs(arr[i]);
			if(currentVal > maxVal && currentVal < arr[alreadyFound]) {
				maxVal = currentVal;
				max = i;
			}
			else {
				System.out.println(arr[alreadyFound]);
			}
		}
		return max;
	}
	public static int maxIndex(Fraction[] arr) {
		Fraction maxVal = new Fraction(0,1);
		int max = 0;
		for(int i = 0; i < arr.length; i++) {
			if(maxVal.compareTo(arr[i]) < 0) {
				max = i;
				maxVal = arr[i];
			}
		}
		return max;
	}
	public static int maxIndex(Fraction[] arr,Fraction upperBound) {
		Fraction maxVal = new Fraction(0,1);
		int max = 0;
		for(int i = 0; i < arr.length; i++) {
			if(maxVal.compareTo(arr[i]) < 0 && maxVal.compareTo(upperBound) > 0) {
				max = i;
				maxVal = arr[i];
			}
		}
		return max;
	}
	
	public static boolean includes(int[] arr,int data) {
		for(int t:arr) {
			if(data==t) {
				return true;
			}
		}
		return false;
	}
	
	
}
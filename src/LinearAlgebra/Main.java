package LinearAlgebra;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        
    	Scanner scnr = new Scanner(System.in);
    	
        LinearMatrix m1 = getLinearInput(scnr);
        
        scnr.close();
        
		try {
			Fraction[] solution = m1.Solve(); //this could result in an exception, not necessarily a computing error, just a mathematical contradiction like 1==0.
			
			System.out.println();
			printResults(solution);
			
		} catch (AllZeroException e) {
			e.printStackTrace();
		}
		catch (UnsolvableMatrixException e) {
			e.printStackTrace();
		}

    }

    public static void printResults(Fraction[] solution) {
    	int varIndex = 1;
        for(int t = 0; t < solution.length; t++){
        	System.out.print("x" + String.valueOf(varIndex++) + ": ");
        	System.out.println(solution[t].toString());
        }
    }

    public static Matrix getInput(Scanner scnr){
        System.out.println("is your matrix all integers?");
        boolean allIntegers = scnr.nextBoolean();
        System.out.println("how many rows does your matrix have?");
        int rowCount = scnr.nextInt();
        System.out.println("how many columns does your matrix have?");
        int colCount = scnr.nextInt();
        Fraction[][] retArr = new Fraction[rowCount][colCount];
        if(allIntegers){
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < colCount; j++){
                    System.out.print("enter data: ");
                    int temp = scnr.nextInt();
                    retArr[i][j] = new Fraction(temp);
                }
                System.out.println();
            }
        }
        else{
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < colCount; j++){
                    System.out.print("enter nominator: ");
                    int tempNom = scnr.nextInt();
                    System.out.print("enter denominator: ");
                    int tempDenom = scnr.nextInt();
                    retArr[i][j] = new Fraction(tempNom,tempDenom);
                }
                System.out.println();
            }
        }
        return new Matrix(retArr);
    }
    
    public static LinearMatrix getLinearInput(Scanner scnr) {
    	System.out.println("is your matrix all integers? (enter a boolean value)");
        boolean allIntegers = scnr.nextBoolean();
        System.out.println("how many rows does your matrix have? (enter an int value)");
        int rowCount = scnr.nextInt();
        System.out.println("how many columns does your matrix have? (enter an int value)");
        int colCount = scnr.nextInt();
        Fraction[][] retArr = new Fraction[rowCount][colCount];
        if(allIntegers){
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < colCount; j++){
                    System.out.print("enter data: ");
                    int temp = scnr.nextInt();
                    retArr[i][j] = new Fraction(temp);
                }
                System.out.println();
            }
        }
        else{
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < colCount; j++){
                    System.out.print("enter nominator: ");
                    int tempNom = scnr.nextInt();
                    System.out.print("enter denominator: ");
                    int tempDenom = scnr.nextInt();
                    retArr[i][j] = new Fraction(tempNom,tempDenom);
                }
                System.out.println();
            }
        }
        Fraction[] augmentArr = new Fraction[rowCount];
        for(int i = 0; i < rowCount; i++) {
        	System.out.println("enter augment for row "+String.valueOf(i));
        	int temp = scnr.nextInt();
        	augmentArr[i] = new Fraction(temp);
        }
    	
    	return new LinearMatrix(retArr,augmentArr);
    }

    
    public static int GCD(int a,int b){
    	if(a==0 || b==0) {return 1;}
    	if(a < 0 || b < 0) {
    		a = Math.abs(a);
    		b = Math.abs(b);
    	}
        int temp1 = Math.min(a, b);
        int temp2 = Math.max(a, b);
        while(temp1 != temp2){
            if(temp1>temp2){
                temp1 -= temp2;
            }
            else if(temp2>temp1){
                temp2 -= temp1;
            }
        }
        return temp1;
    }
    
    public static Fraction rowCoeff(Fraction A,Fraction B) { //returns row coefficient for Fraction A to cancel Fraction B. method: {A*(result) == -B} => {A*(result)+B == 0}
    	//multiply the entire row of A with the result and add the whole row to row B to get rid of the first nonzero Fraction of row B (leaving row A unchanged in the end).
    	return new Fraction(-B.getNum(),B.getDenom());
    }
    
}

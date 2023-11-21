package LinearAlgebra;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //Scanner scnr = new Scanner(System.in);
        
        LinearMatrix m1 = new LinearMatrix(
        		new Fraction[][] {
		        	{new Fraction(1),new Fraction(0),new Fraction(2)},
		        	{new Fraction(0),new Fraction(1),new Fraction(5)},
		        	{new Fraction(4),new Fraction(3),new Fraction(-2)}
		        },
		        new Fraction[] {
		        		new Fraction(4),
		        		new Fraction(8),
		        		new Fraction(-3)
		        }
		);
        
        //scnr.close();
        Fraction[] solution = m1.Solve();
        
        int varIndex = 1;
        for(Fraction f:solution) {
        	System.out.print("x" + String.valueOf(varIndex++) + ": ");
        	System.out.println(f.toString());
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
        Fraction[] augmentArr = new Fraction[rowCount];
        for(int i = 0; i < rowCount; i++) {
        	System.out.println("enter augment for row "+String.valueOf(i));
        	int temp = scnr.nextInt();
        	augmentArr[i] = new Fraction(temp);
        }
    	
    	return new LinearMatrix(retArr,augmentArr);
    }

    
    public static int GCD(int a,int b){
    	if(a==0||b==0) {return 1;}
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
    	return new Fraction(-1*A.getDenom()*B.getNum(),A.getNum()*B.getDenom());
    }
    
}

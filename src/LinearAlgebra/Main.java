package LinearAlgebra;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){    	
    	
    	Scanner scnr = new Scanner(System.in);
    	
    	System.out.println("Does your matrix consist only of integers? (true,false) : ");
    	boolean intInput = scnr.nextBoolean();
    	
    	System.out.println("Do you want to input the entire row for each row? (true,false) (quicker, but experimental) : ");
    	boolean rowInput = scnr.nextBoolean();
    	
        LinearMatrix m1 = getLinearInput(scnr,intInput,rowInput);
        
        scnr.close();
        
        Fraction[] solution;
		try {
			solution = m1.Solve(); //this could result in an exception.
			printResults(solution);
			
		} catch (AllZeroException e) {
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
    	return getLinearInput(scnr,true,false);
    }
    
    public static LinearMatrix getLinearInput(Scanner scnr,InputMode mode) {
    	boolean IntMode = false;
    	boolean RowMode = false;
    	switch(mode) {
    	case IntRow:
    		IntMode = true;
    		RowMode = true;
    		break;
    	case Int:
    		IntMode = true;
    		break;
    	case Row:
    		RowMode = true;
    		break;
    	default:
    		IntMode = false;
    		RowMode = false;
    	}

    	return getLinearInput(scnr,IntMode,RowMode);
    }
    
    public static LinearMatrix getLinearInput(Scanner scnr,boolean allIntegers,boolean rowInput) {
        System.out.println("How many rows does your matrix have?");
        int rowCount = scnr.nextInt();
        System.out.println("How many columns does your matrix have?");
        int colCount = scnr.nextInt();
        
        Fraction[][] retArr = new Fraction[rowCount][colCount];
        Fraction[] augmentArr = new Fraction[colCount];
        
        if(rowInput) {
        	System.out.println("For each row, enter the row from the regular matrix: \n");
        	String[] inputArr = new String[colCount];
        	scnr.nextLine();
    		for(int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
    			System.out.println("Enter row #"+(rowIndex+1));
    			inputArr[rowIndex] = scnr.nextLine();
    		}
    		if(allIntegers) {
    			String[][] elementArr = new String[rowCount][colCount];
    			for(int i = 0; i < rowCount; i++) {
    				elementArr[i] = inputArr[i].split(" ");
    			}
    			for(int parseRow = 0; parseRow < rowCount; parseRow++) {
    				Fraction[] temp = new Fraction[colCount];
    				for(int parseCol = 0; parseCol < colCount; parseCol++) {
    					temp[parseCol] = new Fraction(Integer.valueOf(elementArr[parseRow][parseCol]));
    				}
    				retArr[parseRow] = temp;
    			}
    			
    			System.out.println();
    			
    			for(int row = 0; row < rowCount; row++) {
    				System.out.println("Enter constant (Right Hand Side) for row #"+(row+1));
    				augmentArr[row] = new Fraction(scnr.nextInt());
    			}
    			
    		}else {
    			String[][][] elementArr = new String[rowCount][colCount][2];
    			for(int i = 0; i < rowCount; i++) {
    				String[] currentRow = inputArr[i].split(" ");
    				String[][] temp = new String[colCount][2];
    				for(int j = 0; j < colCount; j++) {
    					temp[j] = currentRow[j].split("/");
    				}
    				elementArr[i] = temp;
    			}
    			
    			for(int parseRow = 0; parseRow < rowCount; parseRow++) {
    				Fraction[] FRrow = new Fraction[colCount];
    				for(int parseCol = 0; parseCol < colCount; parseCol++) {
    					FRrow[parseCol] = new Fraction(Integer.valueOf(elementArr[parseRow][parseCol][0]),Integer.valueOf(elementArr[parseRow][parseCol][1]));
    				}
    				retArr[parseRow] = FRrow;
    			}
    			
    			System.out.println();
    			
    			for(int row = 0; row < rowCount; row++) {
    				System.out.println("Enter row constant for row #"+(row+1));
    				String[] s = scnr.nextLine().split("/");
    				int tempNum = Integer.valueOf(s[0]);
    				int tempDenom = Integer.valueOf(s[1]);
    				augmentArr[row] = new Fraction(tempNum,tempDenom);
    			}
    			
    			
    		}
        	
        }
        else {
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
            augmentArr = new Fraction[rowCount];
            for(int i = 0; i < rowCount; i++) {
            	System.out.println("enter augment for row "+String.valueOf(i));
            	if(allIntegers) {
            		int temp = scnr.nextInt();
            		augmentArr[i] = new Fraction(temp);
            	}else {
            		int temp = scnr.nextInt();
            		int temp2 = scnr.nextInt();
            		augmentArr[i] = new Fraction(temp,temp2);
            	}
            	
            }
        }
        System.out.println();
        
    	
    	return new LinearMatrix(retArr,augmentArr);
    }
    
    enum InputMode{
    	IntRow,
    	Int,
    	Row,
    	None
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

package LinearAlgebra;

import java.util.Arrays;

public class LinearMatrix extends Matrix {
	Fraction[] constants;
	public LinearMatrix(Fraction[][] myMatrix,Fraction[] augment) {
		super(myMatrix);
		constants = augment;
	}
	
	public Fraction[] Solve() throws AllZeroException, UnsolvableMatrixException {
		int rowCount = dataArr.length;
		int colCount = dataArr[0].length;
		
		
		
		//initialize rowArray
		MatrixRow[] rowArr = new MatrixRow[rowCount];
		for(int i = 0; i < rowCount; i++) {
			rowArr[i] = new MatrixRow(dataArr[i],constants[i]);
		}
		
		if(rowCount>colCount) { // there is a need to check if the matrix is solvable in this case.
			//try if a row is the multiple of another row:
			int[] pairs = new int[rowCount*2];
			int pairIndex = 0;
			int newRowCount = rowCount;
			boolean isSolvable = false;
			for(int i = 0; i < rowCount;i++) {
				for(int j = i+1; j < rowCount; j++) {
					
					System.out.println(rowArr[i].toString());
					System.out.println(rowArr[j].multiplied(Main.rowCoeff(rowArr[j].getEntry(0), rowArr[i].getEntry(0)).multiplied(rowArr[j].getEntry(0).inverse().negative())));
					if(rowArr[i].equals(rowArr[j].multiplied(Main.rowCoeff(rowArr[j].getEntry(0), rowArr[i].getEntry(0)).multiplied(rowArr[j].getEntry(0).inverse().negative())))){
						newRowCount--;
						pairs[pairIndex++] = i;
						pairs[pairIndex++] = j;
					}
				}
			}
			System.out.println("printing equivalent rows: ");
			System.out.println(Arrays.toString(pairs));
			
			if(newRowCount<=colCount) {
				isSolvable = true;
			}
			if(!isSolvable) {
				throw new UnsolvableMatrixException("Matrix is unsolvable, more rows than columns and not enough row pairs.");
			}else {
				rowArr = MathFunctions.RemovePairs(rowArr,pairs);
			}
		}
		
		MatrixRow[] sortedRowArr = new MatrixRow[rowCount];
		int[] pivots = new int[rowCount]; //we will use this data later to reorganize the answer.
		for(int index = 0; index < rowCount; index++) {
			pivots[index] = -1;
		}
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < colCount; j++) {
				if(MathFunctions.includes(pivots, j)||rowArr[i].getEntry(j).isZero()) {
					continue;
				}
				else {
					pivots[i]=j; //data for sorting
					break;
				}
			}
		}
		for(int x = 0; x < rowCount; x++) {
			sortedRowArr[x] = rowArr[pivots[x]];
		}//placing elements in sorted array
		
		System.out.println("This is the sorted matrix:"); //after the matrix is sorted, there isn't going to be any more "row swap" operations.
		for(int i = 0; i < sortedRowArr.length; i++) {
			System.out.println(sortedRowArr[i].toString());
		}
		//so far, it should be working...
		
		//step 3
		for(int currentColumn = 0; currentColumn < rowCount; currentColumn++) {
			//scale pivot row if necessary
			Fraction currentPivot = sortedRowArr[currentColumn].getEntry(currentColumn);
			if(!currentPivot.isOne()) {
				Fraction pivotScaler = new Fraction(currentPivot.getDenom(),currentPivot.getNum());
				sortedRowArr[currentColumn].multiply(pivotScaler);
				
				System.out.println("\nMaking the pivot element one by scaling the current row");
				for(int t = 0; t < rowCount;t++) {
					System.out.println(sortedRowArr[t].toString());
				}
				
			}
			
			//scale other rows so that (thatRow.getEntry(currentColumn).getNum()==0)
			for(int otherRow = 0; otherRow < rowCount; otherRow++) {
				if(otherRow == currentColumn) { //currentColumn is also same as current pivot row. going diagonally
					continue;
				}else if(!sortedRowArr[otherRow].getEntry(currentColumn).isZero()){
					Fraction rowCancel = Main.rowCoeff(currentPivot, sortedRowArr[otherRow].getEntry(currentColumn));
					MatrixRow cancelPivot = MatrixRow.deepCopy(sortedRowArr[currentColumn]);
					cancelPivot.multiply(rowCancel);
					sortedRowArr[otherRow].add(cancelPivot);
					
					System.out.println("\nMaking row "+otherRow+" column "+currentColumn+" zero by adding a multiple of the pivot row: ");
					for(int t = 0; t < rowCount;t++) {
						System.out.println(sortedRowArr[t].toString());
					}
				}
			}
			
			//this is a pseudo-toString() method
			System.out.println("\nCurrent matrix state:");
			for(int t = 0; t < rowCount;t++) {
				System.out.println(sortedRowArr[t].toString());
			}
		}
		
		for(int i = 0; i < sortedRowArr.length; i++) {
			boolean currentRowAllZero = true;
			for(int j = 0; j < sortedRowArr[i].getEntries().length; j++) {
				if(!sortedRowArr[i].getEntry(j).isZero()) {
					currentRowAllZero = false;
					break;
				}
			}
			if(currentRowAllZero) {
				throw new AllZeroException("Found an all zero row, row index: "+i);
			}
		}
		//step 4: read off of results
		Fraction[] answer = new Fraction[sortedRowArr.length];
		for(int i = 0; i < sortedRowArr.length; i++) {
			sortedRowArr[i].simplifyRHS();
			answer[i] = sortedRowArr[i].getRHS();
		}
		
		Fraction[] answerWithoutSort = new Fraction[answer.length];
		for(int i = 0; i < answer.length; i++) {
			answerWithoutSort[pivots[i]] = sortedRowArr[i].getRHS();
		}
		
		System.out.println("printing answer without row swap operation:");
		for(int i = 0; i < answer.length; i++) {
			System.out.println("x"+(i+1)+": "+answerWithoutSort[pivots[i]].toString());
		}
		System.out.println("returning sorted answer, use this if sorted array is used in place of the original.");
		
		return answer;
	}
	
	//this class doesn't have a toString() override method as it already shows all the steps for the solution. Also, all of its components have a toString() method, so there is no need for it.
}

class MatrixRow implements Comparable<MatrixRow>{ //The difference between class MatrixRow and a Fraction[] is that the augment is not accessed by array index, rather it stores the row constant separately. 
	//The MatrixRow class also has access and toString() methods to make the program simpler.
	private Fraction[] entries;
	private Fraction RHS; //RHS == Right Hand Side (for short)
	public MatrixRow(Fraction[] input,Fraction augment) {
		entries = input; //entries is the Fraction[] of non-augment coefficients.
		RHS = augment; //When you get the augmented matrix, this is what you add to the end of the row. Example: [1, 4, 5 | <RHS>] means x+4y+5z==RHS
	}
	public MatrixRow multiplied(Fraction rowCoeff) {		
		MatrixRow temp = MatrixRow.deepCopy(this);
		temp.multiply(rowCoeff);
		return temp;
	}
	public static MatrixRow deepCopy(MatrixRow matrixRow) {
		Fraction[] in = matrixRow.entries;
		Fraction aug = matrixRow.RHS;
		
		Fraction[] out = new Fraction[in.length];
		Fraction augOut = new Fraction(aug.getNum(),aug.getDenom());
		for(int i = 0; i < in.length; i++) {
			out[i] = new Fraction(in[i].getNum(),in[i].getDenom());
		}
		return new MatrixRow(out,augOut);
	}
	public void simplifyRHS() {
		RHS.simplify();
	}
	public boolean simplify() {
		boolean ret = false;
		for(int i = 0; i < entries.length; i++) {
			if(entries[i].simplify())
				ret = true;
		}
		if(RHS.simplify())
			ret = true;
		return ret;
	}
	public Fraction getRHS() {
		return RHS;
	}
	public Fraction[] getEntries() {
		return entries;
	}
	public Fraction getEntry(int colIndex) {
		return entries[colIndex];
	}
	public void multiply(Fraction F) {
		for(int temp = 0; temp < entries.length; temp++) {
			entries[temp].mult(F);
		}
		RHS.mult(F);
	}
	public void add(MatrixRow m) {
		Fraction[] otherRow = m.getEntries();
		Fraction otherRHS = m.getRHS();
		for(int i = 0; i < entries.length;i++) {
			entries[i].add(otherRow[i]);
		}
		RHS.add(otherRHS);
	}
	
	@Override
	public String toString() { //returns a string representation of the MatrixRow object. best used in a loop.
		String s = "[";
		for(Fraction f:entries) {
			int temp = f.toString().length();
			int temp2 = temp / 2;
			s += " ".repeat(Math.max(4 - temp2,0))+f.toString()+" ".repeat(Math.max(4 - (temp2+(temp%2)),0));
		}
		s += " | ";
		int temp1 = RHS.toString().length();
		int temp3 = temp1 / 2;
		s += " ".repeat(Math.max(0, 4 - temp3));
		s += RHS.toString();
		s += " ".repeat(Math.max(4 - (temp3+(temp1%2)),0));
		s += "]";
		
		return s;
	}
	@Override
	public int compareTo(MatrixRow o) {
		int ret = 0;
		for(int i = 0; i < entries.length;i++) {
			ret+=entries[i].added(o.getEntry(i)).intValue();
		}
		return ret;
	}
	
	public boolean equals(MatrixRow o) {
		for(int i = 0; i < entries.length; i++) {
			if(entries[i].compareTo(o.getEntry(i))!=0) {
				return false;
			}
		}
		return RHS.compareTo(o.getRHS())==0;
	}
}
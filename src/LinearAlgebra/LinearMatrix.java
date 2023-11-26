package LinearAlgebra;

public class LinearMatrix extends Matrix {
	Fraction[] constants;
	public LinearMatrix(Fraction[][] myMatrix,Fraction[] augment) {
		super(myMatrix);
		constants = augment;
	}
	
	public Fraction[] Solve() throws AllZeroException {
		int rowCount = dataArr.length;
		int colCount = dataArr[0].length;
		
		//initialize rowArray
		MatrixRow[] rowArr = new MatrixRow[rowCount];
		for(int i = 0; i < rowCount; i++) {
			rowArr[i] = new MatrixRow(dataArr[i],constants[i]);
		}
		
		//give priority to rows with most zeros as they are the least flexible for placement.
		
		//Initializing sorting setup
		MatrixRow[] sortedRowArr = new MatrixRow[rowCount];
		int[] pivots = new int[rowCount];
		for(int index = 0; index < rowCount; index++) {
			pivots[index] = -1;
		}
		
		//determining the priority of each row for sorting, to avoid getting stuck and throwing an exception.
		int[] priorityData = new int[rowCount]; //this array holds the priority score for each unsorted row
		for(int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			Fraction[] currentRowEntries = rowArr[rowIndex].getEntries();
			int sum = 0;
			for(int colIndex = 0; colIndex < colCount; colIndex++) {
				sum += currentRowEntries[colIndex].isZero()?Math.pow(2,colIndex):0; //incrementing sum by a (rather complex) formula, determining the current row's "priority score" PERFECTLY
			}
			priorityData[rowIndex] = sum;
		}
		
		int[] INDEXES = MathFunctions.getSortedIndexes(priorityData);
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < colCount; j++) {
				int current = INDEXES[j];
				if(!MathFunctions.includes(pivots, current) && !rowArr[current].getEntry(i).isZero()) {
					pivots[i]=current; //data for sorting
					break;
				}
			}
		}
		
		for(int x = 0; x < rowCount; x++) {
			sortedRowArr[x] = rowArr[pivots[x]];
		}//placing elements in sorted array
		
		System.out.println("This is the sorted matrix:");
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
				}
			}
			
			System.out.println("\nCurrent matrix state: ");
			for(int t = 0; t < sortedRowArr.length;t++) {
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
		
		Fraction[] unsortedAnswer = new Fraction[sortedRowArr.length];
		for(int i = 0; i < sortedRowArr.length; i++) {
			unsortedAnswer[pivots[i]] = answer[i];
		}
		
		System.out.println("\nPrinting answers in the initial order: ");
		Main.printResults(unsortedAnswer);
		
		System.out.println("\nReturning sorted answers as shown in first matrix: ");
		return answer;
	}
}

class MatrixRow{
	private Fraction[] entries;
	private Fraction RHS; //Right hand side for short
	public MatrixRow(Fraction[] input,Fraction augment) {
		entries = input;
		RHS = augment;
	}
	public int getZeros() {
		int zeroCount = 0;
		for(int t = 0; t < entries.length; t++) {
			if(entries[t].isZero()) {
				zeroCount++;
			}
		}
		return zeroCount;
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
	public String toString() {
		String s = "[";
		for(Fraction f:entries) {
			int temp = f.toString().length();
			int temp2 = temp / 2;
			s += " ".repeat(Math.max(4 - temp2,0))+f.toString()+" ".repeat(Math.max(4 - (temp2+(temp%2)),0));
		}
		s += " | ";
		int temp1 = RHS.toString().length();
		int temp3 = temp1 / 2;
		s += " ".repeat(Math.max(0, 6 - temp3));
		s += RHS.toString();
		s += " ".repeat(Math.max(6 - (temp3+(temp1%2)),0));
		s += "]";
		
		return s;
	}
}



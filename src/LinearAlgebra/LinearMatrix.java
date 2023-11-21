package LinearAlgebra;

public class LinearMatrix extends Matrix {
	Fraction[] constants;
	public LinearMatrix(Fraction[][] myMatrix,Fraction[] augment) {
		super(myMatrix);
		constants = augment;
	}
	
	public Fraction[] Solve() {
		int rowCount = dataArr.length;
		int colCount = dataArr[0].length;
		
		//initialize rowArray
		MatrixRow[] rowArr = new MatrixRow[rowCount];
		for(int i = 0; i < rowCount; i++) {
			rowArr[i] = new MatrixRow(dataArr[i],constants[i]);
		}
		MatrixRow[] sortedRowArr = new MatrixRow[rowCount];
		int[] pivots = new int[rowCount];
		for(int index = 0; index < rowCount; index++) {
			pivots[index] = -1;
		}
		
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < colCount; j++) {
				if(MathFunctions.includes(pivots, j)||rowArr[i].getEntry(j).getNum()==0) {
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
		
		//step 3
		for(int currentColumn = 0; currentColumn < rowCount; currentColumn++) {
			//scale pivot row if necessary
			Fraction currentPivot = sortedRowArr[currentColumn].getEntry(currentColumn);
			if(currentPivot.getNum()!=currentPivot.getDenom()) {
				Fraction pivotScaler = new Fraction(currentPivot.getDenom(),currentPivot.getNum());
				sortedRowArr[currentColumn].multiply(pivotScaler);
			}
			
			//scale other rows so that (thatRow.getEntry(currentColumn).getNum()==0)
			for(int otherRow = 0; otherRow < rowCount; otherRow++) {
				if(otherRow == currentColumn) { //currentColumn is also same as current pivot row. going diagonally
					continue;
				}else if(sortedRowArr[otherRow].getEntry(currentColumn).getNum()!=0){
					Fraction rowCancel = Main.rowCoeff(currentPivot, sortedRowArr[otherRow].getEntry(currentColumn));
					MatrixRow cancelPivot = new MatrixRow(sortedRowArr[currentColumn].getEntries(),sortedRowArr[currentColumn].getRHS());
					cancelPivot.multiply(rowCancel);
					sortedRowArr[otherRow].add(cancelPivot);
				}
			}
		}
		
		
		//step 4: read off of results
		Fraction[] answer = new Fraction[sortedRowArr.length];
		for(int i = 0; i < sortedRowArr.length; i++) {
			if(sortedRowArr[i].simplify()) {
				System.out.println("Simplified on last step!");
			}
			answer[i] = sortedRowArr[i].getRHS();
		}
		
		return answer;
	}
}

class MatrixRow{
	private Fraction[] entries;
	private Fraction RHS; //Right Hand Side, RHS for short
	public MatrixRow(Fraction[] input,Fraction augment) {
		entries = input;
		RHS = augment;
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
		RHS.mult(otherRHS);
	}
	
	@Override
	public String toString() {
		String s = "[";
		for(Fraction f:entries) {
			s += " "+f.toString();
		}
		s += " | ";
		s += RHS.toString();
		s += " ]";
		
		return s;
	}
}



package LinearAlgebra;

class Matrix{
    protected Fraction[][] dataArr;
    public Matrix(Fraction[][] frArr){
        dataArr = frArr;
    }

    public Matrix(MatrixRow[] sortedRowArr) {
    	Fraction[][] temp = new Fraction[sortedRowArr.length][sortedRowArr[0].getEntries().length];
    	for(int i = 0; i < temp.length; i++) {
    		temp[i] = sortedRowArr[i].getEntries();
    	}
    	dataArr = temp;
	}

	public void T(){
        dataArr = Transposed();
    }
    public Fraction[][] Transposed(){
        Fraction[][] temp = new Fraction[dataArr[0].length][dataArr.length];
        for(int row = 0; row < dataArr.length; row++){
            for(int col = 0; col < dataArr[row].length; col++){
                temp[col][row] = dataArr[row][col];
            }
        }
        return temp;
    }

    public Fraction[][] getDataArr(){
        return dataArr;
    }

    public boolean simplify(){
        boolean simplified = false;
        for(int i = 0; i < dataArr.length; i++){
            for(int j = 0; j < dataArr[i].length; j++){
                if(dataArr[i][j].simplify()){
                    simplified = true;
                }
            }
        }
        return simplified;
    }

    public Matrix dot(Matrix other){
        Fraction[][] otherArr = other.getDataArr();
        int numRowsA = dataArr.length;
        int numColsA = dataArr[0].length;
        int numRowsB = otherArr.length;
        int numColsB = otherArr[0].length;

        if(numColsA != numRowsB){
            throw new IllegalArgumentException("Matrix dimensions are not suitable for dot product.");
        }
        
        Fraction[][] tempArr = new Fraction[numRowsA][numColsB];

        for(int i = 0; i < numRowsA; i++){
            
            for(int j = 0; j < numColsB; j++){
                
                Fraction sum = new Fraction(0,1);
                for(int k = 0; k < numColsA; k++){
                    sum.add(dataArr[i][k].multiplied(otherArr[k][j]));
                }
                //sum.simplify();
                tempArr[i][j] = sum;
            }
        }
        return new Matrix(tempArr);
    }

    public void printMatrix(){
        for(int i = 0; i < dataArr.length; i++){
            System.out.print("[");
            for(int j = 0; j < dataArr[i].length; j++){
                String tempStr = dataArr[i][j].toString();
                int tempLen = tempStr.length();
                String s = " "+tempStr+" ".repeat(Math.max(10-tempLen,0));
                System.out.print(s);
            }
            System.out.println("]");
        }
        System.out.println("\n");
    }
    
}

class AllZeroException extends Exception{
	private static final long serialVersionUID = 512392052179544341L;

	public AllZeroException(String msg) {
		super(msg);
	}
}
package LinearAlgebra;

public class Fraction implements Comparable<Fraction>{ //the Fraction class uses integer fractions to represent rational numbers. Can be negative as well!
    public static final Fraction ZERO = new Fraction(0,1);

    private int num,denom;

    public Fraction(int val1,int val2){ //val1 is numerator, and val2 is denominator for this data type.
        num = val1;
        denom = val2;
    }

    public Fraction(){ //defaults to zero
        num = 0;
        denom = 1;
    }

    public Fraction(int val){
        num = val;
        denom = 1;
    }

    public int getDenom(){
        return denom;
    }

    public int getNum(){
        return num;
    }

    public void expandBy(int numb){
        num*=numb;
        denom*=numb;
    }

    public boolean simplify(){
    	if(num==0) {
    		denom=1;
    		return true;
    	}
    	if(denom<0 && num<0) {
			denom = -denom;
			num = -num;
    	}
        int gcd = Main.GCD(num, denom);
        if(gcd>1){
            num/=gcd;
            denom/=gcd;
            return true;
        }
        else{
            return false;
        }
        
    }

    public Fraction multiplied(Fraction other){
        return new Fraction(num*other.getNum(), denom*other.getDenom());
    }

    public void add(Fraction other){
        num = num*other.getDenom() + other.getNum()*denom;
        denom *= other.getDenom();
        this.simplify();
    }

    public String toString(){
        if(denom==1){
            return String.valueOf(num);
        }
        else{
            return String.valueOf(num) + "/" + String.valueOf(denom);
        }
    }

	@Override
	public int compareTo(Fraction o) {
		int temp = num*o.getDenom() - denom*o.getNum();
		if(temp!=0) {
			return temp>0 ? 1 : -1;
		}
		return 0;
	}

	public int intValue() {
		return num/denom;
	}
	
	public Fraction added(Fraction other) {
		int retNum = num*other.getDenom() + other.getNum()*denom;
		int retDenom = denom * other.getDenom();
		return new Fraction(retNum,retDenom);
	}

	public void mult(Fraction other) {
		num = num*other.getNum();
		denom = denom*other.getDenom();
		this.simplify();
	}

	public boolean isZero() {
		return num==0;
	}
	
	public boolean isOne() {
		return num==denom;
	}

	public Fraction inverse() {
		return new Fraction(denom,num);
	}

	public Fraction negative() {
		return new Fraction(-num,denom);
	}
}

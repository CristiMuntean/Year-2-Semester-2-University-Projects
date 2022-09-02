package model;

public class Monomial implements Comparable<Monomial>{
    private double coefficient;
    private final int degree;

    public Monomial(double coefficient, int degree){
        this.coefficient = coefficient;
        this.degree = degree;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient){
        this.coefficient = coefficient;
    }

    public int getDegree() {
        return degree;
    }


    @Override
    public int compareTo(Monomial o) {
        return o.getDegree() - this.getDegree();
    }

    @Override
    public String toString(){
        String monomialString;
        if(this.degree > 1 || this.degree <0){
            if(this.coefficient == 1)
                monomialString ="x^" + this.degree;
            else if(this.coefficient == -1)
                monomialString ="-x^" + this.degree;
            else if(this.coefficient == 0){
                monomialString = "";
            }
            else {
                if (this.coefficient == (int) this.coefficient)
                    monomialString = (int) this.coefficient + "x^" + this.degree;
                else
                    monomialString = this.coefficient + "x^" + this.degree;
            }
        }
        else if(this.degree == 1){
            if(this.coefficient == 1)
                monomialString = "x";
            else if(this.coefficient == -1) monomialString = "-x";
            else if(this.coefficient == 0)monomialString = "";
            else{
                if(this.coefficient == (int)this.coefficient)
                    monomialString = (int)this.coefficient + "x";
                else monomialString = this.coefficient + "x";
            }
        }
        else {
            if(this.coefficient == 0)monomialString ="";
            else if(this.coefficient == (int)this.coefficient)
                monomialString = (int)this.coefficient + "";
            else monomialString = this.coefficient + "";
        }
        return monomialString;
    }
}

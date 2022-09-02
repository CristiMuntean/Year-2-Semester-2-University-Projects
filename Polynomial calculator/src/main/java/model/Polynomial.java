package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial{
    private final ArrayList<Monomial> monomials;

    public Polynomial(String polynomial){
        this.monomials = new ArrayList<>();
        findMonomials(polynomial);
    }

    public Polynomial(){
        this.monomials = new ArrayList<>();
    }

    public Polynomial(@NotNull Polynomial polynomial){
        this.monomials = new ArrayList<>();
        this.monomials.addAll(polynomial.getMonomials());
    }


    public void findMonomials(String polynomial){
        Pattern p = Pattern.compile(
                "((-?\\d+)[xX]\\^(-?\\d+))|(-?\\d+$)|((-?)[xX]\\^(-?\\d+))|((-?\\d+)[xX])|((-?)[xX])");
        Matcher m = p.matcher(polynomial);
        while(m.find()){
//            System.out.println("Monomial: " + m.group(1));
//            System.out.println("Coef: " + m.group(2));
//            System.out.println("Deg: " + m.group(3));
//            System.out.println("Single coef(x^0): " + m.group(4));
//            System.out.println("Monomial with no coef(coef=0,deg>1): " + m.group(5));
//            System.out.println("Sign of monomial with no coef: " + m.group(6));
//            System.out.println("Deg of monomial with no coef: " + m.group(7));
//            System.out.println("Monomial with coef(deg=1): " + m.group(8));
//            System.out.println("Coef of monomial with deg=1: " + m.group(9));
//            System.out.println("Monomial with no coef and deg=1: " + m.group(10));
//            System.out.println("Sign of monomial with no coef and deg=1: " + m.group(11));
//            System.out.println();

            if(m.group(1)!=null){       ///if we have a monomial with the form 3x^2 (we have coefficient and degree stated)
                this.monomials.add(new Monomial(Integer.parseInt(m.group(2)),Integer.parseInt(m.group(3))));
            }
            else if(m.group(4)!=null){      ///if we have a monomial with the form -2/2(we have a coefficient, but we don't have x explicitly stated(i.e. the grade of the monomial is 0)
                this.monomials.add(new Monomial(Integer.parseInt(m.group(4)),0));
            }
            else if(m.group(5)!=null){      ///if we have a monomial with the form x^2(we don't have a coefficient and the degree is >1)
                int coeff = m.group(6).equals("-") ? -1:1;
                this.monomials.add(new Monomial(coeff,Integer.parseInt(m.group(7))));
            }
            else if(m.group(8)!=null){      ///if we have a monomial with the form 3x(we have a coefficient but the degree is not stated in the form x^1)
                this.monomials.add(new Monomial(Integer.parseInt(m.group(9)),1));
            }
            else if(m.group(10)!=null){      ///if we have a monomial with the form +x/-x(the coefficient is +1/-1 and the degree is 1)
                int coeff = m.group(11).equals("-") ? -1:1;
                this.monomials.add(new Monomial(coeff,1));
            }
        }
    }


    public ArrayList<Monomial> getMonomials() {
        return monomials;
    }

    public int calculateMaxDegree() {
        int maxDegree =0;
        for(Monomial monomial:this.monomials){
            if(monomial.getDegree() > maxDegree)maxDegree = monomial.getDegree();
        }
        return maxDegree;
    }

    public void addMonomial(Monomial monomial) {
        for (Monomial monomial1: this.monomials){
            if(monomial.getDegree() == monomial1.getDegree()){
                monomial1.setCoefficient(monomial1.getCoefficient() + monomial.getCoefficient());
                return;
            }
        }
        this.monomials.add(monomial);
    }

    public void sortMonomials(){
        Collections.sort(this.monomials);
    }


    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        if(this.monomials.size()==1 && this.monomials.get(0).getCoefficient() == 0 && this.monomials.get(0).getDegree() == 0)
            result.append("Empty polynomial");
        else{
            for(Monomial monomial:this.monomials){
                if(result.length()>0 && monomial.getCoefficient()>0){
                    result.append("+").append(monomial);
                }
                else result.append(monomial);
            }
        }
        if(result.isEmpty())return "";
        return result.toString();
    }

}

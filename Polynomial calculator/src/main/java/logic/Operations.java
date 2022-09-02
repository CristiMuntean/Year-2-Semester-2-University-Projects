package logic;


import model.Monomial;
import model.Polynomial;


public class Operations {
    public static Polynomial add(Polynomial polynomial1, Polynomial polynomial2){
        Polynomial result = new Polynomial();
        for(Monomial monomial1:polynomial1.getMonomials()){
            boolean found = false;
            for(Monomial monomial2: polynomial2.getMonomials()){
                if(monomial1.getDegree() == monomial2.getDegree()) {
                    result.addMonomial(new Monomial(monomial1.getCoefficient() + monomial2.getCoefficient(),
                            monomial1.getDegree()));
                    found = true;
                }
            }
            if(!found){
                result.addMonomial(new Monomial(monomial1.getCoefficient(),monomial1.getDegree()));
            }
        }
        for(Monomial monomial2: polynomial2.getMonomials()){
            boolean found = false;
            for(Monomial monomial1: polynomial1.getMonomials()){
                if (monomial2.getDegree() == monomial1.getDegree()) {
                    found = true;
                    break;
                }
            }
            if(!found){
                result.addMonomial(new Monomial(monomial2.getCoefficient(), monomial2.getDegree()));
            }
        }
        result.sortMonomials();
        return result;
    }

    public static Polynomial subtract(Polynomial polynomial1, Polynomial polynomial2){
        Polynomial result = new Polynomial();
        for(Monomial monomial1:polynomial1.getMonomials()){
            boolean found = false;
            for(Monomial monomial2: polynomial2.getMonomials()){
                if(monomial1.getDegree() == monomial2.getDegree()){
                    found = true;
                    if(monomial1.getCoefficient() - monomial2.getCoefficient() != 0)
                        result.addMonomial(new Monomial(monomial1.getCoefficient() - monomial2.getCoefficient(),
                            monomial1.getDegree()));
                }
            }
            if(!found){
                result.addMonomial(new Monomial(monomial1.getCoefficient(),monomial1.getDegree()));
            }
        }
        for(Monomial monomial2: polynomial2.getMonomials()){
            boolean found = false;
            for(Monomial monomial1:polynomial1.getMonomials()){
                if (monomial2.getDegree() == monomial1.getDegree()) {
                    found = true;
                    break;
                }
            }
            if(!found){
                result.addMonomial(new Monomial(-monomial2.getCoefficient(), monomial2.getDegree()));
            }
        }
        result.sortMonomials();
        return result;
    }

    public static Polynomial divide(Polynomial polynomial1, Polynomial polynomial2){
        if(polynomial1.getMonomials().isEmpty() || polynomial2.getMonomials().isEmpty() ||
                polynomial2.toString().equals("0")){
            throw new ArithmeticException("Can not divide by 0");
        }
        if(polynomial2.getMonomials().get(0).getDegree() == 0)
        {
            Polynomial result = new Polynomial();
            for(Monomial monomial:polynomial1.getMonomials()){
                result.addMonomial(new Monomial(monomial.getCoefficient()/polynomial2.getMonomials().get(0).getCoefficient(),
                        monomial.getDegree()));
            }
            return result;
        }

        Polynomial quotient = new Polynomial();
        Polynomial remainder = new Polynomial(polynomial1);
        polynomial2.sortMonomials();
        Monomial dividerMaxMonomial = polynomial2.getMonomials().get(0);
        while(remainder.calculateMaxDegree() >= polynomial2.calculateMaxDegree()){
            remainder.sortMonomials();
            Monomial remainderMaxMonomial = remainder.getMonomials().get(0);
            Monomial divisionResultMonomial =
                    new Monomial(remainderMaxMonomial.getCoefficient()/ dividerMaxMonomial.getCoefficient(),
                            remainderMaxMonomial.getDegree()- dividerMaxMonomial.getDegree());

            Polynomial subtractPolynomial = new Polynomial();
            for(Monomial monomial: polynomial2.getMonomials()){
                subtractPolynomial.addMonomial(
                        new Monomial(divisionResultMonomial.getCoefficient() * monomial.getCoefficient(),
                        divisionResultMonomial.getDegree() + monomial.getDegree()));
            }
            remainder = Operations.subtract(remainder,subtractPolynomial);
            quotient.addMonomial(divisionResultMonomial);
        }
        return quotient;
    }

    public static Polynomial modulo(Polynomial polynomial1, Polynomial polynomial2){
        if(polynomial1.getMonomials().isEmpty() || polynomial2.getMonomials().isEmpty() ||
                polynomial2.toString().equals("0")){
            throw new ArithmeticException("Can not divide by 0");
        }
        if(polynomial2.getMonomials().get(0).getDegree() == 0)
        {
            Polynomial result = new Polynomial();
            return result;
        }
        Polynomial quotient = new Polynomial();
        Polynomial remainder = new Polynomial(polynomial1);
        polynomial2.sortMonomials();
        Monomial dividerMaxMonomial = polynomial2.getMonomials().get(0);
        while(remainder.calculateMaxDegree() >= polynomial2.calculateMaxDegree()){
            remainder.sortMonomials();
            Monomial remainderMaxMonomial = remainder.getMonomials().get(0);
            Monomial divisionResultMonomial =
                    new Monomial(remainderMaxMonomial.getCoefficient()/ dividerMaxMonomial.getCoefficient(),
                            remainderMaxMonomial.getDegree()- dividerMaxMonomial.getDegree());

            Polynomial subtractPolynomial = new Polynomial();
            for(Monomial monomial: polynomial2.getMonomials()){
                subtractPolynomial.addMonomial(
                        new Monomial(divisionResultMonomial.getCoefficient() * monomial.getCoefficient(),
                                divisionResultMonomial.getDegree() + monomial.getDegree()));
            }
            remainder = Operations.subtract(remainder,subtractPolynomial);
            quotient.addMonomial(divisionResultMonomial);
        }
        return remainder;
    }

    public static Polynomial multiply(Polynomial polynomial1, Polynomial polynomial2){
        Polynomial result = new Polynomial();
        for(Monomial monomial1:polynomial1.getMonomials()){
            for (Monomial monomial2:polynomial2.getMonomials()){
                result.addMonomial(new Monomial(monomial1.getCoefficient()* monomial2.getCoefficient(),
                        monomial1.getDegree()+monomial2.getDegree()));
            }
        }
        result.sortMonomials();
        return result;
    }

    public static Polynomial derive(Polynomial polynomial){
        Polynomial result = new Polynomial();
        polynomial.sortMonomials();
        for(Monomial monomial:polynomial.getMonomials()){
            if(monomial.getDegree()>0)
                result.addMonomial(new Monomial(monomial.getCoefficient() * monomial.getDegree(),
                    monomial.getDegree()-1));
        }
        return result;
    }

    public static Polynomial integrate(Polynomial polynomial){
        Polynomial result = new Polynomial();
        polynomial.sortMonomials();
        for(Monomial monomial:polynomial.getMonomials()){
            result.addMonomial(new Monomial(monomial.getCoefficient()/ (monomial.getDegree()+1),
                    monomial.getDegree()+1));
        }
        return result;
    }

}

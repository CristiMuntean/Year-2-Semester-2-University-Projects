package gui;

import logic.Operations;
import model.Monomial;
import model.Polynomial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.MonitorInfo;

public class Controller implements ActionListener {
    private final View view;

    public Controller(View view){
        this.view = view;
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        String pol1 = this.view.getFirstPolynomialTextField().getText();
        String pol2 = this.view.getSecondPolynomialTextField().getText();
        Polynomial polynomial1 = new Polynomial(pol1);
        Polynomial polynomial2 = new Polynomial(pol2);


        switch (command) {
            case "ADD" -> {
                System.out.println("ADD");
                Polynomial result = Operations.add(polynomial1, polynomial2);
                if (result.getMonomials().isEmpty()) this.view.getResultTextField().setText("Invalid input");
                else this.view.getResultTextField().setText(result.toString());
            }
            case "SUBTRACT" -> {
                System.out.println("SUBTRACT");
                Polynomial result = Operations.subtract(polynomial1, polynomial2);
                if (result.getMonomials().isEmpty()) this.view.getResultTextField().setText("Invalid input");
                else this.view.getResultTextField().setText(result.toString());
            }
            case "DIVIDE" -> {
                System.out.println("DIVIDE");
                try{
                    if (pol2.equals("0") || pol2.equals("0x^0")){
                        this.view.getResultTextField().setText("Invalid Input");
                    }
                    else if(pol1.equals("0") || pol1.equals("0x^0"))
                        this.view.getResultTextField().setText("0");
                    else{
                        Polynomial quotient = Operations.divide(polynomial1, polynomial2);
                        Polynomial remainder = Operations.modulo(polynomial1, polynomial2);
                        this.view.getResultTextField().setText("Q:" + (quotient.toString().isEmpty() ? "No quotient" : quotient.toString()) +
                                "   R:" + (remainder.toString().isEmpty() ? "No remainder" : remainder.toString()));
                    }
                }catch (ArithmeticException exception){
                    this.view.getResultTextField().setText("Invalid input");
                }
            }
            case "MODULO" -> {
                System.out.println("MODULO");
                try{
                    if(pol2.equals("0") || pol2.equals("0x^0")){
                        this.view.getResultTextField().setText("Invalid input");
                    }
                    else{
                        Polynomial result = Operations.modulo(polynomial1, polynomial2);
                        this.view.getResultTextField().setText(
                                result.toString().isEmpty() ? "No remainder" : result.toString());
                    }
                }catch (ArithmeticException exception){
                    this.view.getResultTextField().setText("Invalid input");
                }
            }
            case "MULTIPLY" -> {
                System.out.println("MULTIPLY");
                if(polynomial1.getMonomials().isEmpty() || polynomial2.getMonomials().isEmpty())
                    this.view.getResultTextField().setText("Invalid input");
                else
                if((polynomial2.getMonomials().size() == 1 && polynomial2.getMonomials().get(0).getCoefficient() == 0 &&
                polynomial2.getMonomials().get(0).getDegree() == 0) ||
                        (polynomial1.getMonomials().size() == 1 &&
                        polynomial1.getMonomials().get(0).getCoefficient() == 0 &&
                        polynomial1.getMonomials().get(0).getDegree() == 0)){
                    this.view.getResultTextField().setText("0");
                }
                else{
                    Polynomial result = Operations.multiply(polynomial1, polynomial2);
                    this.view.getResultTextField().setText(result.toString());
                }
            }
            case "DERIVE" -> {
                System.out.println("DERIVE");
                Polynomial result = Operations.derive(polynomial1);
                this.view.getSecondPolynomialTextField().setText("----");
                if (result.getMonomials().isEmpty()) this.view.getResultTextField().setText("Invalid input");
                else this.view.getResultTextField().setText(result.toString());
            }
            case "INTEGRATE" -> {
                System.out.println("INTEGRATE");
                Polynomial result = Operations.integrate(polynomial1);
                this.view.getSecondPolynomialTextField().setText("----");
                if (result.getMonomials().isEmpty()) this.view.getResultTextField().setText("Invalid input");
                else this.view.getResultTextField().setText(result.toString());
            }
            case "EXIT" -> System.exit(0);
        }
    }
}

package logic;

import logic.Operations;
import model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddOperationTest {
    @Test
    public void addTest(){
        Polynomial polynomial1 = new Polynomial("x^3+3x^2+2x+1");
        Polynomial polynomial2 = new Polynomial("4x^4+2x^3+x^2-2x");
        assertTrue(Operations.add(polynomial1,polynomial2).toString().equals("4x^4+3x^3+4x^2+1"),
        "The result of adding the two polynomials is correct");
    }
}

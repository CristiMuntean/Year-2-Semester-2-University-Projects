package logic;

import logic.Operations;
import model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubtractOperationTest {
    @Test
    public void subtractTest(){
        Polynomial polynomial1 = new Polynomial("3x^5+2x^3-2x-4");
        Polynomial polynomial2 = new Polynomial("3x^5-x^4+x^3-5x^2+2x+3");
        assertTrue(Operations.subtract(polynomial1,polynomial2).toString().equals("x^4+x^3+5x^2-4x-7"),
                Operations.subtract(polynomial1,polynomial2).toString());
    }
}

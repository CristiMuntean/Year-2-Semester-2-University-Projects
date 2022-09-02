package logic;

import model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiplicationTest {
    @Test
    public void multiplyTest(){
        Polynomial polynomial1 = new Polynomial("2x^3-x^2-3x+1");
        Polynomial polynomial2 = new Polynomial("x^2-4x-12");
        assertTrue(Operations.multiply(polynomial1,polynomial2).toString().equals("2x^5-9x^4-23x^3+25x^2+32x-12"),
                Operations.multiply(polynomial1,polynomial2).toString());
    }
}

package logic;

import model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {
    @Test
    public void integrationTest(){
        Polynomial polynomial = new Polynomial("3x^2+2x+1");
        assertTrue(Operations.integrate(polynomial).toString().equals("x^3+x^2+x"),
                Operations.integrate(polynomial).toString());
    }
}

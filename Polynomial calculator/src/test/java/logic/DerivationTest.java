package logic;

import model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DerivationTest {
    @Test
    public void derivationTest(){
        Polynomial polynomial = new Polynomial("2x^3+3x^2+x+1");
        assertTrue(Operations.derive(polynomial).toString().equals("6x^2+6x+1"),Operations.derive(polynomial).toString());
    }
}

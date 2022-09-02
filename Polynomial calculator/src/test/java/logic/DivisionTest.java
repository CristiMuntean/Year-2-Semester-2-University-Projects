package logic;

import model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DivisionTest {
    @Test
    public void divisionTest(){
        Polynomial polynomial1 = new Polynomial("x^2+2x+1");
        Polynomial polynomial2 = new Polynomial("x+1");
        if(!polynomial2.toString().equals("Empty polynomial")){
            Polynomial quotient = Operations.divide(polynomial1,polynomial2);
            Polynomial remainder = Operations.modulo(polynomial1, polynomial2);
            StringBuilder result = new StringBuilder();
            result.append("Q:").append(quotient).append(" R:").append(remainder);
            assertTrue(result.toString().equals("Q:x+1 R:"),result.toString());
        }

    }
}

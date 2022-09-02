package logic;

import model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuloTest {
    @Test
    public void moduloTest(){
        Polynomial polynomial1 = new Polynomial("x^2+2x+1");
        Polynomial polynomial2 = new Polynomial("x+1");
        if(!polynomial2.toString().equals("Empty polynomial")){
            assertTrue(Operations.modulo(polynomial1,polynomial2).toString().equals(""),
                    Operations.modulo(polynomial1,polynomial2).toString());
        }
    }
}

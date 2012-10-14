package quoridor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SquareTest {

	Square a, b;
	
	@Before
	public void setUp() throws Exception {
	}

    @Test
    public void cardinality () {
    	a = new Square("e2");
    	b = new Square("e3");
    	assertTrue (a+" and "+b+" are cardinal: ", a.isCardinalTo(b));
    	b = new Square("f2");
    	assertTrue (a+" and "+b+" are cardinal: ", a.isCardinalTo(b));
    	b = new Square("f3");
    	assertFalse (a+" and "+b+" are not cardinal: ", a.isCardinalTo(b));
    }
    
    @Test
    public void opposite () {
    	a = new Square("e2");
    	b = new Square("e3");
    	Square opposite = new Square("e4");
    	assertEquals(a+"'s opposite to"+b+"is: "+opposite, opposite, a.opposite(b));
    	a = new Square("b2");
    	b = new Square("c2");
    	opposite = new Square("d2");
    	assertEquals(a+"'s opposite to"+b+"is: "+opposite, opposite, a.opposite(b));
    }

}

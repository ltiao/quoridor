package quoridor;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	Display board = new Board();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		//board.display("e8 e2 e7 e3 e6 e4 d5 e6 d4 e7 c4 f8 g2 i8");
		//board.display("e8 e2 e7 d1h e6 e3 e5 e2h e3h d6v d2v c2h");
		//board.display("d6v d2v c2h");
		board.display("e8 e2 e7 d1h e6 e3 e5 f1h e5h b1h h1h d8v d5 d2v c5 e4 d4v c5h b5 a4h c5 b2h c4 a3v d4 c3h c4 e5 b4 f5 b3 g5 g5h h5 h4v h4 g3h g4 f3v g5 c3 f5 d3 f4 d2 f3 c2 f2 b2 g2 a2 g3 a1");
	}

}

package old;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import quoridor.Square;
import quoridor.Validator;

public class ValidatorTest {

	Validator val;
	
	@Before
	public void setUp() throws Exception {
		val = new Validator ();
	}

	@Test
	public void testIsOverTrue() {
		assertTrue ( "Game is over and Player 2 has won" ,val.isOver("e8 e2 e7 e3 e6 e4 e5 e6 e4 e7 e3 e8 e2 e9"));
	}
	
	@Test
	public void testIsOverFalse() {
		assertFalse ( "No one has won yet" ,val.isOver("e8 e2 e7 e3 e6 e4 e5 e6 e4 e7 e3 e8 e2 f8"));
	}
	
	@Test
	public void testGraphInit() {
	//	val.displayAdjacencyList();
	}
	
	@Test
	public void testIsAdjacent() {
		assertTrue (val.isValidTraversal(new Square("e6"), new Square("e7")));
		assertTrue (val.isValidTraversal(new Square("f7"), new Square("e7")));
	}
	
	@Test
	public void testIsNotAdjacent() {
		assertFalse (val.isValidTraversal(new Square("e2"), new Square("e7")));
		assertFalse (val.isValidTraversal(new Square("g7"), new Square("e7")));
	}
	
	@Test
	public void testSimpleTraversalWithJump() {
		assertTrue ("Simple Valid Traversal With Jump",val.check("e8 e2 e7 e3 e6 e4 e5 e6 e4 e7 e3 e8 e2 e9"));
	}
	
	@Test
	public void testLongComplexTraversalWithLegalWallPlacements() {
		assertTrue( "Long Complex Traversal With Legal Wall Placements", val.check("e8 e2 e7 d1h e6 e3 e5 f1h e5h b1h h1h d8v d5 d2v c5 e4 d4v c5h b5 a4h c5 b2h c4 a3v d4 c3h c4 e5 b4 f5 b3 g5 g5h h5 h4v h4 g3h g4 f3v g5 c3 f5 d3 f4 d2 f3 c2 f2 b2 g2 a2 g3 a1"));
	}
	
	@Test
	public void testWallOverlap () {
		assertFalse ("Wall Overlap",val.check("c2h c2v"));
	}
	
	@Test
	public void testWallNeighborhoodNonOverlap () {
		assertTrue ("Valid wall placement in wall neighborhood",val.check("c2h b2v"));
	}
	
	@Test
	public void testEdgeRemoval () {
		assertTrue ( "c3 is adjacent to c2, b3", val.adjacencyList.get(new Square("c3")).contains(new Square("c2")) && val.adjacencyList.get(new Square("c3")).contains(new Square("b3")));
		assertTrue ("Valid wall placement",val.check("c2h b2v"));
		assertFalse ( "c3 is blocked off by walls and no longer adjacent to c2, b3", val.adjacencyList.get(new Square("c3")).contains(new Square("c2")) && val.adjacencyList.get(new Square("c3")).contains(new Square("b3")));
	}
	
	@Test
	public void testJumpOverWalls () {
		assertFalse ("A foolhardy and invalid attempt to jump over a wall",val.check("d1h e2"));
	}
	
	@Test
	public void testDontJumpOverWalls () {
		assertTrue ("Smarted this time, let's go around",val.check("d1h d1"));
	}
	
	@Test
	public void testDontJumpOverWallsAgain () {
		assertTrue ("Smarted this time, let's go around the other way",val.check("d1h f1"));
	}
	
	@Test
	public void testJumpetyJumpJump() {
		assertTrue ( "let's jump to the right (player B's right that is...)" ,val.check("e8 e2 e7 e3 e6 e4 e5 d5"));
	}
	
	@Test
	public void testStayInTheSameSpot() {
		assertFalse ( "let's stay in the same spot" ,val.check("e8 e2 e7 e3 e6 e3"));
	}
	
	@Test
	public void testStayInTheSameSpotWithAdjacentPawn() {
		assertFalse ( "let's stay in the same spot while facing another pawn" ,val.check("e8 e2 e7 e3 e6 e4 e5 e4"));
	}
	
	@Test
	public void faceOff() {
		assertFalse ( "Can't walk straight into another pawn" ,val.check("e8 e2 e7 e3 e6 e4 e5 e5"));
	}
	
	
}

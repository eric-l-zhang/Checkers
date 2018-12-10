import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class GameboardTest {
	
	private static final int yellow = 1;
	private static final int red = 2;
	private static final int yellowKing = 3;
	private static final int redKing = 4;


	// TESTING LOCATION COMPARABLE CLASS FOR SETS AND MAPS
	@Test
	public void testLocationEquals() {
		Location l = new Location(2,2);
		Location m = new Location(2,2);
		assertEquals(l, m);
	}
	
	@Test
	public void testLocationContainsSet() {
		Location l = new Location(2,2);
		Location m = new Location(2,2);
		Set<Location> set = new TreeSet<>();
		set.add(l);
		assertTrue(set.contains(m));
	}
	
	@Test
	public void testLocationGetMap() {
		Location l = new Location(2,2);
		Location m = new Location(2,2);
		Map<Location, Location> map = new TreeMap<>();
		map.put(l, m);
		assertEquals(map.get(new Location(2,2)), m);
	}
	
	
	// TESTING KING MOVEMENT
	@Test
	public void testMovesKingNormal() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), redKing);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertTrue(s.contains(new Location(3, 3)));
		assertTrue(s.contains(new Location(5, 3)));
		assertTrue(s.contains(new Location(3, 5)));
		assertTrue(s.contains(new Location(5, 5)));
		assertEquals(s.size(), 4);
		
	}
	
	@Test
	public void testMovesKingTopEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(3, 0), redKing);
		Set<Location> s = g.getPossibleMoves(3, 0);
		assertTrue(s.contains(new Location(4, 1)));
		assertTrue(s.contains(new Location(2, 1)));
		assertEquals(s.size(), 2);
		
	}
	
	@Test
	public void testMovesKingLeftEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(0, 5), redKing);
		Set<Location> s = g.getPossibleMoves(0, 5);
		assertTrue(s.contains(new Location(1, 6)));
		assertTrue(s.contains(new Location(1, 4)));
		assertEquals(s.size(), 2);
		
	}
	
	@Test
	public void testMovesKingRightEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(7, 5), redKing);
		Set<Location> s = g.getPossibleMoves(7, 5);
		assertTrue(s.contains(new Location(6, 6)));
		assertTrue(s.contains(new Location(6, 4)));
		assertEquals(s.size(), 2);
		
	}
	
	@Test
	public void testMovesKingBottomEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(5, 7), redKing);
		Set<Location> s = g.getPossibleMoves(5, 7);
		assertTrue(s.contains(new Location(4, 6)));
		assertTrue(s.contains(new Location(6, 6)));
		assertEquals(s.size(), 2);
	}
	
	@Test
	public void testMovesKingCorner() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(0, 0), redKing);
		Set<Location> s = g.getPossibleMoves(0, 0);
		assertTrue(s.contains(new Location(1, 1)));
		assertEquals(s.size(), 1);
	}
	
	@Test
	public void testMovesKingOtherPiecesInWay() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), redKing);
		g.putPiece(new Location(3, 3), red);
		g.putPiece(new Location(5, 3), yellow);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertTrue(s.contains(new Location(3, 5)));
		assertTrue(s.contains(new Location(5, 5)));
		assertEquals(s.size(), 2);
	}
	
	//TESTING RED NORMAL PIECE MOVEMENT
	@Test
	public void testMovesRedPieceNormal() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), red);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertTrue(s.contains(new Location(5, 3)));
		assertTrue(s.contains(new Location(3, 3)));
		assertEquals(s.size(), 2);
	}
	
	@Test
	public void testMovesRedPieceLeftEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(0, 4), red);
		Set<Location> s = g.getPossibleMoves(0, 4);
		assertTrue(s.contains(new Location(1, 3)));
		assertEquals(s.size(), 1);
	}
	
	@Test
	public void testMovesRedPieceRightEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(7, 4), red);
		Set<Location> s = g.getPossibleMoves(7, 4);
		assertTrue(s.contains(new Location(6, 3)));
		assertEquals(s.size(), 1);
	}
	
	@Test
	public void testMovesRedPieceOnePieceInWay() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), red);
		g.putPiece(new Location(5, 3), red);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertTrue(s.contains(new Location(3, 3)));
		assertEquals(s.size(), 1);
	}
	
	@Test
	public void testMovesRedPieceTwoPiecesInWay() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), red);
		g.putPiece(new Location(5, 3), red);
		g.putPiece(new Location(3, 3), red);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertEquals(s.size(), 0);
	}
	
	// TESTING NORMAL YELLOW PIECE MOVEMENT
	@Test
	public void testMovesYellowPieceNormal() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellow);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertTrue(s.contains(new Location(5, 5)));
		assertTrue(s.contains(new Location(3, 5)));
		assertEquals(s.size(), 2);
	}
	
	@Test
	public void testMovesYellowPieceLeftEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(0, 4), yellow);
		Set<Location> s = g.getPossibleMoves(0, 4);
		assertTrue(s.contains(new Location(1, 5)));
		assertEquals(s.size(), 1);
	}
	
	@Test
	public void testMovesYellowPieceRightEdge() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(7, 4), yellow);
		Set<Location> s = g.getPossibleMoves(7, 4);
		assertTrue(s.contains(new Location(6, 5)));
		assertEquals(s.size(), 1);
	}
	
	@Test
	public void testMovesYellowPieceOnePieceInWay() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellow);
		g.putPiece(new Location(5, 5), yellow);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertTrue(s.contains(new Location(3, 5)));
		assertEquals(s.size(), 1);
	}
	
	@Test
	public void testMovesYellowPieceTwoPiecesInWay() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellow);
		g.putPiece(new Location(5, 5), red);
		g.putPiece(new Location(3, 5), red);
		Set<Location> s = g.getPossibleMoves(4, 4);
		assertEquals(s.size(), 0);
	}
	
	// TESTING AVAILABLE JUMPING FOR NORMAL PIECE
	@Test
	public void testJumpsNormalPieceNoJumps1() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellow);
		g.putPiece(new Location(5, 5), yellow);
		g.putPiece(new Location(3, 5), yellow);
		Map<Location, Location> m = g.getpossibleJumps(4, 4);
		assertEquals(m.keySet().size(), 0);
	}
	
	@Test
	public void testJumpsNormalPieceNoJumps2() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellow);
		Map<Location, Location> m = g.getpossibleJumps(4, 4);
		assertEquals(m.keySet().size(), 0);
	}
	
	@Test
	public void testJumpsNormalPieceJumps() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellow);
		g.putPiece(new Location(5, 5), red);
		g.putPiece(new Location(3, 5), redKing);
		Map<Location, Location> m = g.getpossibleJumps(4, 4);
		assertTrue(m.containsKey(new Location(2, 6)));
		assertEquals(m.get(new Location(2, 6)), new Location(3, 5));
		assertTrue(m.containsKey(new Location(6, 6)));
		assertEquals(m.get(new Location(6, 6)), new Location(5, 5));
		assertEquals(m.keySet().size(), 2);
	}
	
	//TESTING AVAILABLE JUMPING FOR KING PIECE
	@Test
	public void testJumpsKingNoJumps1() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellowKing);
		g.putPiece(new Location(5, 5), yellow);
		g.putPiece(new Location(3, 5), yellow);
		g.putPiece(new Location(5, 3), yellow);
		g.putPiece(new Location(3, 3), yellow);
		Map<Location, Location> m = g.getpossibleJumps(4, 4);
		assertEquals(m.keySet().size(), 0);
	}
	
	@Test
	public void testJumpsKingNoJumps2() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellowKing);
		Map<Location, Location> m = g.getpossibleJumps(4, 4);
		assertEquals(m.keySet().size(), 0);
	}
	
	@Test
	public void testJumpsKingJumpsAvailable() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellowKing);
		g.putPiece(new Location(5, 5), red);
		g.putPiece(new Location(3, 5), redKing);
		g.putPiece(new Location(5, 3), red);
		g.putPiece(new Location(3, 3), redKing);
		Map<Location, Location> m = g.getpossibleJumps(4, 4);
		assertTrue(m.containsKey(new Location(2, 6)));
		assertEquals(m.get(new Location(2, 6)), new Location(3, 5));
		assertTrue(m.containsKey(new Location(6, 6)));
		assertEquals(m.get(new Location(6, 6)), new Location(5, 5));
		assertTrue(m.containsKey(new Location(2, 2)));
		assertEquals(m.get(new Location(2, 2)), new Location(3, 3));
		assertTrue(m.containsKey(new Location(6, 2)));
		assertEquals(m.get(new Location(6, 2)), new Location(5, 3));
		assertEquals(m.keySet().size(), 4);
	}
	
	//TESTING NORMAL PIECES TURNING INTO KINGS
	@Test
	public void testMovesYellowNormalIntoKing() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(6, 6), yellow);
		g.move(6, 6, 7, 7);
		assertEquals(g.getColor(new Location(7,7)), yellowKing);
	}
	
	@Test
	public void testMovesRedNormalIntoKing() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(1, 1), red);
		g.move(1, 1, 0, 0);
		assertEquals(g.getColor(new Location(0,0)), redKing);
	}
	
	//TESTING JUMP TAKES AWAY PIECE
	@Test
	public void testRemovePieceAfterJumpMove() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(4, 4), yellow);
		g.putPiece(new Location(3, 3), red);
		g.move(3, 3, 5, 5);
		assertEquals(g.getColor(new Location(4,4)), 0);
	}
	@Test
	public void testRemovePieceAfterJumpIntoKing() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(1, 1), yellow);
		g.putPiece(new Location(2, 2), red);
		g.move(2, 2, 0, 0);
		assertEquals(g.getColor(new Location(1,1)), 0);
		assertEquals(g.getColor(new Location(0,0)), redKing);
	}
	
	//TESTING GAME ENDS WHEN NO PIECES
	@Test
	public void testGameOverWhenNoMorePieces() {
		Gameboard g = new Gameboard();
		g.emptyGameBoard();
		g.putPiece(new Location(1, 1), yellow);
		g.putPiece(new Location(2, 2), red);
		g.move(2, 2, 0, 0);
		assertTrue(g.isGameOver());
	}
	
}

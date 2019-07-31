import org.junit.Test;

import cs3500.marblesolitaire.model.TriangleSolitaireModelImpl;

import static junit.framework.TestCase.assertEquals;

/**
 * Represents all the tests to test the Triangular Solitaire Model.
 */
public class TriangleSolitaireModelImplTest {
  private TriangleSolitaireModelImpl game1 = new TriangleSolitaireModelImpl();
  private TriangleSolitaireModelImpl game2 = new TriangleSolitaireModelImpl(6);
  private TriangleSolitaireModelImpl game3 = new TriangleSolitaireModelImpl(3, 3);
  private TriangleSolitaireModelImpl game4 = new TriangleSolitaireModelImpl(4, 3, 1);

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBigSCol1() {
    new TriangleSolitaireModelImpl(3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBigSCol2() {
    new TriangleSolitaireModelImpl(1, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidEmpty1() {
    new TriangleSolitaireModelImpl(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidEmpty2() {
    new TriangleSolitaireModelImpl(5, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegRow() {
    new TriangleSolitaireModelImpl(-3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegCol() {
    new TriangleSolitaireModelImpl(3, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegBoth() {
    new TriangleSolitaireModelImpl(-1, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidLength() {
    new TriangleSolitaireModelImpl(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegLength() {
    new TriangleSolitaireModelImpl(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidDim() {
    new TriangleSolitaireModelImpl(1, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidEmpty() {
    new TriangleSolitaireModelImpl(5, 3, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidEmptyPosn() {
    new TriangleSolitaireModelImpl(7, -2, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalid() {
    new TriangleSolitaireModelImpl(5, -2, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalid2() {
    new TriangleSolitaireModelImpl(-3, 6, -1);
  }

  @Test
  public void testHelpers1() {
    assertEquals("    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O", game3.getGameState());
    game3.emptyBoard(0);
    assertEquals("    _\n"
            + "   _ _\n"
            + "  _ _ _\n"
            + " _ _ _ _\n"
            + "_ _ _ _ _", game3.getGameState());
    game3.emptyBoard(1);
    assertEquals("    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O", game3.getGameState());
  }

  @Test
  public void testHelper2() {
    game4.setMarblesOrEmptyCell(2, 2, 0);
    assertEquals("   O\n"
            + "  O O\n"
            + " O O _\n"
            + "O _ O O", game4.getGameState());
    game4.setMarblesOrEmptyCell(3, 3, 0);
    assertEquals("   O\n"
            + "  O O\n"
            + " O O _\n"
            + "O _ O _", game4.getGameState());
    game4.setMarblesOrEmptyCell(3, 3, 1);
    assertEquals("   O\n"
            + "  O O\n"
            + " O O _\n"
            + "O _ O O", game4.getGameState());
    game4.setMarblesOrEmptyCell(2, 2, -1);
    assertEquals("   O\n"
            + "  O O\n"
            + " O O  \n"
            + "O _ O O", game4.getGameState());
  }

  @Test
  public void test4ConstructorGameState() {
    assertEquals("    _\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O", game1.getGameState());
    assertEquals("     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O", game2.getGameState());
    assertEquals("    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O", game3.getGameState());
    assertEquals("   O\n"
            + "  O O\n"
            + " O O O\n"
            + "O _ O O", game4.getGameState());
  }

  @Test
  public void testJumpLeft() {
    assertEquals("    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O", game3.getGameState());
    game3.move(3, 1, 3, 3);
    assertEquals("    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O _ _ O\n"
            + "O O O O O", game3.getGameState());
  }

  @Test
  public void testJumpRight() {
    assertEquals("   O\n"
            + "  O O\n"
            + " O O O\n"
            + "O _ O O", game4.getGameState());
    game4.move(3, 3, 3, 1);
    assertEquals("   O\n"
            + "  O O\n"
            + " O O O\n"
            + "O O _ _", game4.getGameState());
  }

  @Test
  public void testJumpTopRight() {
    assertEquals("    _\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O", game1.getGameState());
    assertEquals(14, game1.getScore());
    game1.move(2, 0, 0, 0);
    assertEquals("    O\n"
            + "   _ O\n"
            + "  _ O O\n"
            + " O O O O\n"
            + "O O O O O", game1.getGameState());
    assertEquals(13, game1.getScore());
  }

  @Test
  public void testJumpTopLeft() {
    assertEquals("    _\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O", game1.getGameState());
    game1.move(2, 2, 0, 0);
    assertEquals("    O\n"
            + "   O _\n"
            + "  O O _\n"
            + " O O O O\n"
            + "O O O O O", game1.getGameState());
  }

  @Test
  public void testJumpBotRight() {
    assertEquals("    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O", game3.getGameState());
    game3.move(1, 1, 3, 3);
    assertEquals("    O\n"
            + "   O _\n"
            + "  O O _\n"
            + " O O O O\n"
            + "O O O O O", game3.getGameState());
  }

  @Test
  public void testJumpBotLeft() {
    assertEquals("   O\n"
            + "  O O\n"
            + " O O O\n"
            + "O _ O O", game4.getGameState());
    game4.move(1, 1, 3, 1);
    assertEquals("   O\n"
            + "  O _\n"
            + " O _ O\n"
            + "O O O O", game4.getGameState());
    assertEquals(8, game4.getScore());
  }

  @Test
  public void testGameOver() {
    game1.move(2, 0, 0, 0);
    assertEquals(false, game1.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMarbleToMarble() {
    game1.emptyBoard(1);
    game1.move(4, 2, 2, 2);
    game1.move(4, 2, 4, 4);
    game1.move(2, 2, 4, 2);
    game1.move(2, 2, 4, 4);
    game1.move(2, 2, 0, 0);
    game1.move(2, 2, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyToEmpty() {
    game1.emptyBoard(0);
    game1.move(4, 2, 2, 2);
    game1.move(4, 2, 4, 4);
    game1.move(2, 2, 4, 2);
    game1.move(2, 2, 4, 4);
    game1.move(2, 2, 0, 0);
    game1.move(2, 2, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMarbleToMarbleMidEmpty() {
    game3.emptyBoard(1);
    game3.setMarblesOrEmptyCell(4, 1, 0);
    game3.move(4, 0, 4, 2);
    game3.move(4, 2, 4, 0);
    game3.setMarblesOrEmptyCell(2, 1, 0);
    game3.move(3, 1, 1, 1);
    game3.move(1, 1, 3, 1);
    game3.move(1, 0, 3, 2);
    game3.move(3, 2, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyToEmptyMidMarble() {
    game3.emptyBoard(0);
    game3.setMarblesOrEmptyCell(4, 1, 1);
    game3.move(4, 0, 4, 2);
    game3.move(4, 2, 4, 0);
    game3.setMarblesOrEmptyCell(2, 1, 1);
    game3.move(3, 1, 1, 1);
    game3.move(1, 1, 3, 1);
    game3.move(1, 0, 3, 2);
    game3.move(3, 2, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyToMarbleOrMarbleToEmpty() {
    game2.emptyBoard(0);
    game2.setMarblesOrEmptyCell(3, 2, 1);
    game2.move(1, 0, 3, 2);
    game2.move(3, 2, 1, 0);
    game2.move(3, 2, 5, 4);
    game2.move(5, 4, 3, 2);
    game2.move(3, 2, 3, 0);
    game2.move(3, 0, 3, 2);
    game2.setMarblesOrEmptyCell(3, 1, 1);
    game2.move(3, 1, 1, 1);
    game2.move(1, 1, 3, 1);
    game2.move(3, 1, 5, 1);
    game2.move(5, 1, 3, 1);
    game2.setMarblesOrEmptyCell(4, 1, 1);
    game2.move(4, 1, 4, 3);
    game2.move(4, 3, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOutOfBound() {
    game2.move(-2, 0, 0, 0);
    game3.move(4, 4, 6, 6);
    game3.move(4, 4, 6, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveStraightUpOrDown() {
    game2.move(2, 1, 0, 0);
    game2.setMarblesOrEmptyCell(0, 0, 1);
    game2.setMarblesOrEmptyCell(2, 1, 0);
    game2.move(0, 0, 2, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNotSameRowNorSameCol() {
    game2.move(2, 1, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToItself() {
    game2.move(2, 2, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTwoAway() {
    game2.move(2, 2, 4, 0);
  }


  @Test
  public void testIsGameOver1() {
    assertEquals(false, game1.isGameOver());
    game2.emptyBoard(0);
    assertEquals(true, game2.isGameOver());
    game3.emptyBoard(0);
    assertEquals(true, game3.isGameOver());
  }

  @Test
  public void testIsGameOver2() {
    game3.emptyBoard(0);
    game3.setMarblesOrEmptyCell(3, 3, 1);
    game3.setMarblesOrEmptyCell(2, 2, 1);
    game3.setMarblesOrEmptyCell(1, 1, 1);
    assertEquals(false, game3.isGameOver());
    game3.move(2, 2, 0, 0);
    assertEquals(true, game3.isGameOver());
  }

  @Test
  public void testIsGameOver3() {
    game1.emptyBoard(0);
    game1.setMarblesOrEmptyCell(4, 0, 1);
    game1.setMarblesOrEmptyCell(4, 1, 1);
    game1.setMarblesOrEmptyCell(4, 2, 1);
    game1.setMarblesOrEmptyCell(4, 3, 1);
    game1.setMarblesOrEmptyCell(4, 4, 1);
    assertEquals(true, game1.isGameOver());
  }

  @Test
  public void testIsGameOver4() {
    game2.emptyBoard(0);
    game2.setMarblesOrEmptyCell(0, 0, 1);
    game2.setMarblesOrEmptyCell(1, 1, 1);
    game2.setMarblesOrEmptyCell(2, 2, 1);
    game2.setMarblesOrEmptyCell(3, 3, 1);
    game2.setMarblesOrEmptyCell(4, 4, 1);
    game2.setMarblesOrEmptyCell(5, 5, 1);
    assertEquals(true, game2.isGameOver());
  }

  @Test
  public void testIsGameOver5() {
    game1.emptyBoard(0);
    game1.setMarblesOrEmptyCell(3, 3, 1);
    assertEquals(true, game1.isGameOver());
  }

  @Test
  public void testIsGameOver6() {
    game1.emptyBoard(0);
    game1.setMarblesOrEmptyCell(3, 3, 1);
    game1.setMarblesOrEmptyCell(3, 2, 1);
    assertEquals(false, game1.isGameOver());
  }

  @Test
  public void testIsGameOverWithLength2() {
    TriangleSolitaireModelImpl game = new TriangleSolitaireModelImpl(2);
    assertEquals(true, game.isGameOver());
  }
}

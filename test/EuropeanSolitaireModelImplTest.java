import org.junit.Test;

import cs3500.marblesolitaire.model.EuropeanSolitaireModelImpl;

import static junit.framework.TestCase.assertEquals;


/**
 * Represents all the tests to test the European Solitaire Model.
 */
public class EuropeanSolitaireModelImplTest {
  private EuropeanSolitaireModelImpl game1 = new EuropeanSolitaireModelImpl();
  private EuropeanSolitaireModelImpl game2 = new EuropeanSolitaireModelImpl(5);
  private EuropeanSolitaireModelImpl game3 = new EuropeanSolitaireModelImpl(2, 6);
  private EuropeanSolitaireModelImpl game4 =
          new EuropeanSolitaireModelImpl(3, 4, 2);

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    new EuropeanSolitaireModelImpl(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    new EuropeanSolitaireModelImpl(1, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    new EuropeanSolitaireModelImpl(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    new EuropeanSolitaireModelImpl(5, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor5() {
    new EuropeanSolitaireModelImpl(-3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor6() {
    new EuropeanSolitaireModelImpl(3, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor7() {
    new EuropeanSolitaireModelImpl(-1, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor8() {
    new EuropeanSolitaireModelImpl(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor9() {
    new EuropeanSolitaireModelImpl(-6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor10() {
    new EuropeanSolitaireModelImpl(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor11() {
    new EuropeanSolitaireModelImpl(4, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor12() {
    new EuropeanSolitaireModelImpl(5, 3, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor13() {
    new EuropeanSolitaireModelImpl(7, -2, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor14() {
    new EuropeanSolitaireModelImpl(5, -2, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor15() {
    new EuropeanSolitaireModelImpl(5, 6, -1);
  }


  @Test
  public void test4ConstructorsGameStates() {
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
    assertEquals("        O O O O O\n"
            + "      O O O O O O O\n"
            + "    O O O O O O O O O\n"
            + "  O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "  O O O O O O O O O O O\n"
            + "    O O O O O O O O O\n"
            + "      O O O O O O O\n"
            + "        O O O O O", game2.getGameState());
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game3.getGameState());
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O _ O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game4.getGameState());
  }

  @Test
  public void testCustomizedBoard() {
    game1.emptyBoard(0);
    game1.setMarblesOrEmptyCell(1, 1, 1);
    game1.setMarblesOrEmptyCell(1, 5, 1);
    game1.setMarblesOrEmptyCell(5, 1, 1);
    game1.setMarblesOrEmptyCell(5, 5, 1);
    game1.setMarblesOrEmptyCell(3, 5, 1);
    assertEquals("    _ _ _\n"
            + "  O _ _ _ O\n"
            + "_ _ _ _ _ _ _\n"
            + "_ _ _ _ _ O _\n"
            + "_ _ _ _ _ _ _\n"
            + "  O _ _ _ O\n"
            + "    _ _ _", game1.getGameState());
  }

  @Test
  public void testHelpers1() {
    assertEquals("    O O O\n" +
            "  O O O O O\n" +
            "O O O O O O _\n" +
            "O O O O O O O\n" +
            "O O O O O O O\n" +
            "  O O O O O\n" +
            "    O O O", game3.getGameState());
    game3.emptyBoard(0);
    assertEquals("    _ _ _\n"
            + "  _ _ _ _ _\n"
            + "_ _ _ _ _ _ _\n"
            + "_ _ _ _ _ _ _\n"
            + "_ _ _ _ _ _ _\n"
            + "  _ _ _ _ _\n"
            + "    _ _ _", game3.getGameState());
    game3.emptyBoard(1);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game3.getGameState());
  }

  @Test
  public void testHelper2() {
    game4.setMarblesOrEmptyCell(4, 4, 0);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O _ O _ O O\n"
            + "  O O O O O\n"
            + "    O O O", game4.getGameState());
    game4.setMarblesOrEmptyCell(3, 3, 0);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O _ O _ O O\n"
            + "  O O O O O\n"
            + "    O O O", game4.getGameState());
    game4.setMarblesOrEmptyCell(3, 3, 1);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O _ O _ O O\n"
            + "  O O O O O\n"
            + "    O O O", game4.getGameState());
    game4.setMarblesOrEmptyCell(2, 2, -1);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O   O O O O\n"
            + "O O O O O O O\n"
            + "O O _ O _ O O\n"
            + "  O O O O O\n"
            + "    O O O", game4.getGameState());
  }

  @Test
  public void testGetScore() {
    assertEquals(36, game1.getScore());
    assertEquals(128, game2.getScore());
    game2.emptyBoard(0);
    assertEquals(0, game2.getScore());
    game1.setMarblesOrEmptyCell(3, 4, -1);
    game1.setMarblesOrEmptyCell(3, 5, -1);
    game1.setMarblesOrEmptyCell(3, 6, -1);
    assertEquals(33, game1.getScore());
    game3.emptyBoard(-1);
    assertEquals(0, game3.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove1() {
    game1.move(-1, 1, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove2() {
    game1.move(0, -1, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove3() {
    game1.move(2, 2, -1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove4() {
    game1.move(4, 4, -2, -4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove5() {
    game1.move(-5, -5, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove6() {
    game1.move(-1, -1, -2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove7() {
    game3.move(3, 0, 3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove8() {
    game3.setMarblesOrEmptyCell(2, 1, 0);
    game3.move(2, 0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove9() {
    game3.move(2, 1, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove10() {
    game3.move(2, 2, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove11() {
    game3.setMarblesOrEmptyCell(2, 5, -1);
    game3.move(2, 4, 2, 6);
  }


  @Test(expected = IllegalArgumentException.class)
  public void invalidMove12() {
    game3.setMarblesOrEmptyCell(2, 3, 0);
    game3.move(2, 2, 2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove13() {
    game3.setMarblesOrEmptyCell(3, 2, 0);
    game3.setMarblesOrEmptyCell(4, 2, 0);
    game3.move(2, 2, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove14() {
    game4.move(3, 3, 7, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove15() {
    game4.move(2, 3, 2, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove16() {
    game4.move(2, 3, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove17() {
    game4.move(3, 3, 6, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove18() {
    game1.setMarblesOrEmptyCell(3, 3, 1);
    game1.setMarblesOrEmptyCell(4, 4, 0);
    game1.move(2, 2, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove19() {
    game1.move(4, 4, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMoveEmptyFrom() {
    game1.move(3, 3, 3, 5);
  }

  @Test
  public void testMove1() {
    game1.move(3, 1, 3, 3);
    game1.move(1, 1, 3, 1);
    game1.move(5, 2, 3, 2);
    game1.move(4, 4, 4, 2);
    game1.move(2, 4, 4, 4);
    game1.move(5, 4, 3, 4);
    game1.move(4, 1, 4, 3);
    game1.move(2, 6, 2, 4);
    game1.move(4, 5, 2, 5);
    game1.move(1, 3, 1, 1);
    assertEquals("    O O O\n"
            + "  O _ _ O O\n"
            + "O _ O O O O _\n"
            + "O O O O O _ O\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O", game1.getGameState());
    assertEquals(26, game1.getScore());
    assertEquals(false, game1.isGameOver());
  }


  @Test
  public void testMoveLeft() {
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
    game1.move(3, 1, 3, 3);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
    assertEquals(false, game1.isGameOver());
  }

  @Test
  public void testMoveRight() {
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
    game1.move(3, 5, 3, 3);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ _ O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
  }

  @Test
  public void testMoveDown() {
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
    game1.move(1, 3, 3, 3);
    assertEquals("    O O O\n"
            + "  O O _ O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
  }

  @Test
  public void testMoveUp() {
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O", game1.getGameState());
    game1.move(5, 3, 3, 3);
    assertEquals("    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "  O O _ O O\n"
            + "    O O O", game1.getGameState());
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
    game3.setMarblesOrEmptyCell(3, 5, 1);
    game3.setMarblesOrEmptyCell(3, 2, 1);
    game3.setMarblesOrEmptyCell(3, 1, 1);
    assertEquals(false, game3.isGameOver());

    game3.move(3, 1, 3, 3);
    assertEquals(true, game3.isGameOver());
  }

  @Test
  public void testIsGameOver3() {
    game1.emptyBoard(0);
    game1.setMarblesOrEmptyCell(0, 2, 1);
    game1.setMarblesOrEmptyCell(0, 3, 1);
    game1.setMarblesOrEmptyCell(0, 4, 1);
    assertEquals(true, game1.isGameOver());
  }

  @Test
  public void testIsGameOver4() {
    game1.emptyBoard(0);
    game1.setMarblesOrEmptyCell(0, 3, 1);
    game1.setMarblesOrEmptyCell(1, 3, 1);
    game1.setMarblesOrEmptyCell(2, 3, 1);
    game1.setMarblesOrEmptyCell(3, 3, 1);
    game1.setMarblesOrEmptyCell(4, 3, 1);
    game1.setMarblesOrEmptyCell(5, 3, 1);
    game1.setMarblesOrEmptyCell(6, 3, 1);
    assertEquals(true, game1.isGameOver());
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
}

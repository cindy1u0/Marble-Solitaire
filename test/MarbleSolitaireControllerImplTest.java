import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;

import cs3500.marblesolitaire.model.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.MarbleSolitaireModelImpl;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.AbstractSolitaireModel;
import cs3500.marblesolitaire.model.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.TriangleSolitaireModelImpl;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for MarbleSolitaireControllerImpl.
 */
public class MarbleSolitaireControllerImplTest {

  /**
   * An interaction with the user consists of some input to send the program and some output to
   * expect.  We represent it as an object that takes in two StringBuilders and produces the
   * intended effects on them.
   */
  interface Interaction {
    void apply(StringBuilder in, StringBuilder out);
  }

  /**
   * Represents the printing of a sequence of lines to input.
   */
  private static Interaction inputs(String in) {
    return (input, output) -> input.append(in);
  }

  /**
   * Represents a user providing the program with  an output.
   */
  private static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append("\n");
      }
    };
  }

  private AbstractSolitaireModel game1 = new MarbleSolitaireModelImpl();
  private AbstractSolitaireModel game2 = new MarbleSolitaireModelImpl(5);
  private AbstractSolitaireModel game3 = new MarbleSolitaireModelImpl(0, 3);
  private AbstractSolitaireModel game4 = new MarbleSolitaireModelImpl(3, 3, 6);
  private AbstractSolitaireModel game5 = new EuropeanSolitaireModelImpl();
  private AbstractSolitaireModel game6 = new EuropeanSolitaireModelImpl(5);
  private AbstractSolitaireModel game7 = new EuropeanSolitaireModelImpl(2, 6);
  private AbstractSolitaireModel game8 = new EuropeanSolitaireModelImpl(3, 4, 2);
  private AbstractSolitaireModel game9 = new TriangleSolitaireModelImpl();
  private AbstractSolitaireModel game10 = new TriangleSolitaireModelImpl(6);
  private AbstractSolitaireModel game11 = new TriangleSolitaireModelImpl(3, 3);
  private AbstractSolitaireModel game12 =
          new TriangleSolitaireModelImpl(4, 3, 1);

  /**
   * Represents a mock for appendable.
   */
  private class AppendableMock implements Appendable {

    /**
     * Appends message to an appendable.
     *
     * @param csq given charSequence
     * @return throwing exception
     * @throws IOException if the given input is a charsequence
     */
    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("Failed output");
    }

    /**
     * Appends message to an appendable.
     *
     * @param csq given charSequence
     * @return throwing exception
     * @throws IOException if the given input is a charsequence
     */
    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("Failed output");
    }

    /**
     * Appends message to an appendable.
     *
     * @param c given char
     * @return throwing exception
     * @throws IOException if the given input is a char
     */
    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("Failed output");
    }
  }

  /**
   * Represents a ReadableMock.
   */
  private class ReadableMock implements Readable {

    /**
     * Reads a message from a Readable.
     *
     * @param r given CharBuffer
     * @return throwing exception
     * @throws IOException if the given input is a CharBuffer
     */
    @Override
    public int read(CharBuffer r) throws IOException {
      throw new IOException("Failed input");
    }
  }

  /**
   * Cleaner way to test methods.
   *
   * @param model        given MarbleSolitaireModel
   * @param interactions lists of operations that the user is giving
   * @return actual output
   */
  private String testRun(MarbleSolitaireModel model, Interaction... interactions) {
    StringBuilder actualOutput = new StringBuilder();
    actualOutput.setLength(0);
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();
    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }
    StringReader input = new StringReader(fakeUserInput.toString());
    MarbleSolitaireControllerImpl controller =
            new MarbleSolitaireControllerImpl(input, actualOutput);
    try {
      controller.playGame(model);
    } catch (IllegalStateException e) {
      // When the IllegalStateException is caught, do not execute anything.
    }
    return actualOutput.toString();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() throws IllegalArgumentException {
    StringBuilder out = new StringBuilder();
    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(null, out);
    controller.playGame(game1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() throws IllegalArgumentException {
    Reader in = new StringReader("");
    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(in, null);
    controller.playGame(game11);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadableAndAppendable() throws IllegalArgumentException {
    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(null, null);
    controller.playGame(game3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() throws IllegalArgumentException {
    Reader in = new StringReader("");
    StringBuilder out = new StringBuilder();
    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(in, out);
    controller.playGame(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelAndReadableAndAppendable() throws IllegalArgumentException {
    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(null, null);
    controller.playGame(null);
  }

  @Test
  public void testNullMsg() throws IllegalArgumentException {
    Reader in = new StringReader("");
    StringBuilder out = new StringBuilder();
    String msg = "The controller is unable to receive input or transmit output";
    String msgModel = "Provided model cannot be read";

    try {
      MarbleSolitaireControllerImpl controller =
              new MarbleSolitaireControllerImpl(null, null);
      controller.playGame(game10);
    } catch (IllegalArgumentException e) {
      assertEquals(msg, e.getMessage());
      assertNotEquals("huh", e.getMessage());
    }

    try {
      MarbleSolitaireControllerImpl controller =
              new MarbleSolitaireControllerImpl(in, null);
      controller.playGame(game9);
    } catch (IllegalArgumentException e) {
      assertEquals(msg, e.getMessage());
      assertNotEquals("huh", e.getMessage());
    }

    try {
      MarbleSolitaireControllerImpl controller =
              new MarbleSolitaireControllerImpl(null, out);
      controller.playGame(game6);
    } catch (IllegalArgumentException e) {
      assertEquals(msg, e.getMessage());
      assertNotEquals("huh", e.getMessage());
    }

    try {
      MarbleSolitaireControllerImpl controller =
              new MarbleSolitaireControllerImpl(in, out);
      controller.playGame(null);
    } catch (IllegalArgumentException e) {
      assertEquals(msgModel, e.getMessage());
      assertNotEquals("huh", e.getMessage());
    }
  }

  @Test
  public void testNoNextScannerMsg() throws IllegalStateException {
    Reader in = new StringReader(" 3");
    StringBuilder out = new StringBuilder();
    String msg = "No next input";

    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(in, out);
    try {
      controller.playGame(game7);
    } catch (IllegalStateException e) {
      assertEquals(msg, e.getMessage());
    }
  }

  @Test
  public void testNoPassedInInput() throws IllegalArgumentException {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs(""),
            prints(expected)));
  }

  @Test
  public void testQuitWithoutInput() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs(" q"),
            prints(expected)));
  }

  @Test
  public void testQuitBeforeInput() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game4, inputs("q 3"), prints(expected)));
  }

  @Test
  public void testQuit2ndPos() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game4, inputs("3  q"),
            prints(expected)));
  }

  @Test
  public void testQuit2ndPosBeforeInput() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game4, inputs("4 q 9 2"),
            prints(expected)));
  }

  @Test
  public void testQuit3rdPos() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs("4 5 Q"),
            prints(expected)));
  }

  @Test
  public void testQuit3rdPosBeforeInput() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs("4 5 q  qqq"),
            prints(expected)));
  }

  @Test
  public void testQuit4th() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Input QQQ is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs("4 5 3   QQQ q"),
            prints(expected)));
  }

  @Test
  public void testQuit4thBeforeInput() {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game3, inputs("4 3 4 Q 9 2"),
            prints(expected)));
  }

  @Test
  public void testQuit5thValidMove() {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game3, inputs("3 4 1 5  q"),
            prints(expected)));
  }

  @Test
  public void testQQQ() {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n"
            + "Input QQQ is bad! Enter new one!\n";
    assertEquals(expected, testRun(game3, inputs("3 4\n 1 4 QQQ"),
            prints(expected)));
  }

  @Test
  public void testQuit5thInValidInput() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Input -2 is bad! Enter new one!\n"
            + "Input a is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs("3 -2 a 2 Q"),
            prints(expected)));
  }

  @Test
  public void testQuit5thInValidInputExtra() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Input -2 is bad! Enter new one!\n"
            + "Input a is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs("3 -2 a 2 q 3 2"),
            prints(expected)));
  }

  @Test
  public void testLess4Input() throws IllegalArgumentException {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n";
    assertEquals(expected, testRun(game1, inputs("3 3   1"),
            prints(expected)));
  }

  @Test
  public void test4CorrectInput() throws IllegalStateException {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n";
    assertEquals(expected, testRun(game3, inputs("3 4 1 4"),
            prints(expected)));
  }

  @Test
  public void test4IncorrectInput() throws IllegalStateException {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game3, inputs("3 4 1 5"),
            prints(expected)));
  }

  @Test
  public void test4InputsWithLetterAnd0() throws IllegalStateException {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Input a is bad! Enter new one!\n"
            + "Input 0 is bad! Enter new one!\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game3, inputs(" 3 4 a 4 0 6 "),
            prints(expected)));
  }

  @Test
  public void test5InputsWithSymbolsAndNegativeNum() throws IllegalStateException {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Input * is bad! Enter new one!\n"
            + "Input -1-2 is bad! Enter new one!\n"
            + "Input !7 is bad! Enter new one!\n"
            + "Input -4 is bad! Enter new one!\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n";
    assertEquals(expected, testRun(game3, inputs("3 4 * 1 -1-2 !7  -4 4"),
            prints(expected)));
  }

  @Test(expected = IllegalStateException.class)
  public void testNoNextScanner() throws IllegalStateException {
    Reader in = new StringReader("3    1 3 2");
    StringBuilder out = new StringBuilder();

    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(in, out);
    controller.playGame(game2);
  }


  @Test
  public void testMoreThan4Inputs() throws IllegalStateException {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 30\n";
    assertEquals(expected, testRun(game3, inputs("3 4      1\n   4   \n  5 4 \n"),
            inputs("  3  4  "),
            prints(expected)));
  }

  @Test
  public void testInvalidJump() throws IllegalStateException {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game4, inputs("4 5 4 6 "), prints(expected)));
  }

  @Test
  public void testInvalidCellNonExistent() throws IllegalStateException {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Invalid move. Play again. Invalid cell position\n";
    assertEquals(expected, testRun(game4, inputs("1 2 3 1"),
            prints(expected)));
  }

  @Test
  public void testInvalidCellOutOfBound() throws IllegalStateException {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Invalid move. Play again. Invalid cell position\n";
    assertEquals(expected, testRun(game4, inputs("4 7 4 9"),
            prints(expected)));
  }

  @Test
  public void testJumpDown() throws IllegalStateException {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n";
    assertEquals(expected, testRun(game1, inputs("2 4    \n4 4\n"),
            prints(expected)));
  }


  @Test
  public void testGameOver() {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 30\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 29\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 28\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ _ O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 27\n"
            + "Game over!\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ O _ _\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 26\n";
    assertEquals(expected, testRun(game3,
            inputs("3 4 1 4 5 4 3    4 7 4 5 4 4 2 4 4 4 5 4 3 \n"),
            inputs("4 7    4 5  "), prints(expected)));
  }

  @Test
  public void testGameOverAndQuit() {
    String expected = "    O _ O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 30\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 29\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 28\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ _ O O\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 27\n"
            + "Game over!\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ O _ _\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 26\n";
    assertEquals(expected, testRun(game3,
            inputs("3 4 1 4 5 4 3    4 7 4 5 4 4 2 4 4 4 5 4 3 \n"),
            inputs("4 7    4 5 q "), prints(expected)));
  }

  @Test
  public void testInvalidJumpSeries() {
    String expected = "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O _ O _ _ O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 30\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Input ! is bad! Enter new one!\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O _ O _ O _ _\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 29\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game1, inputs(" 4 \n2  \n4\n 4 \n"),
            inputs("3   2  5  2\n"),
            inputs("1 3 3 3 \n"),
            inputs("4 1\n 4 3 \n"),
            inputs("4  5 4 3\n"),
            inputs("4  2  4  4\n"),
            inputs("4  5 4 7\n"),
            inputs("4  4 4 6\n"),
            inputs("4 ! 7 4 5\n"),
            inputs("2 5 4    7\n"),
            inputs("1\n"),
            inputs("3\n"),
            inputs("4\n"),
            inputs("4\n"),
            prints("")));
  }

  @Test(expected = IllegalStateException.class)
  public void testAppendableException() {
    String wrongMsg = "what";
    String message = "Failed output";
    try {
      new AppendableMock().append("ye");
    } catch (IOException e) {
      assertEquals(message, e.getMessage());
      assertNotEquals(wrongMsg, e.getMessage());
      throw new IllegalStateException(e);
    }

    try {
      new AppendableMock().append('e');
    } catch (IOException e) {
      assertEquals(message, e.getMessage());
      assertNotEquals(wrongMsg, e.getMessage());
      throw new IllegalStateException(e);
    }

    try {
      new AppendableMock().append("tests are so annoying", 2, 8);
    } catch (IOException e) {
      assertEquals(message, e.getMessage());
      assertNotEquals(wrongMsg, e.getMessage());
      throw new IllegalStateException(e);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testReadableException() {
    String wrongMsg = "Ah";
    String msg = "Failed input";
    try {
      new ReadableMock().read(CharBuffer.allocate(4));
    } catch (IOException e) {
      assertEquals(msg, e.getMessage());
      assertNotEquals(wrongMsg, e.getMessage());
      throw new IllegalStateException(e);
    }
  }

  @Test
  public void testEuroNoPassedInInput() throws IllegalArgumentException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs(""),
            prints(expected)));
  }

  @Test
  public void testEuroQuitWithoutInput() {
    String expected = "        O O O O O\n"
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
            + "        O O O O O\n"
            + "Score: 128\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "        O O O O O\n"
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
            + "        O O O O O\n"
            + "Score: 128\n";
    assertEquals(expected, testRun(game6, inputs(" q"),
            prints(expected)));
  }

  @Test
  public void testEuroQuitBeforeInput() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game7, inputs("q 3"), prints(expected)));
  }

  @Test
  public void testEuroQuit2ndPos() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs("3  q"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit2ndPosBeforeInput() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O _ O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "O O _ O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game8, inputs("4 q 9 2"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit3rdPos() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs("4 5 Q"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit3rdPosBeforeInput() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs("4 5 q  qqq"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit4th() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Input QQ is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs("4 5 3 QQ  q"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit4thBeforeInput() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs("4 3 4 Q 9 2"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit5thValidMove() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 35\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 35\n";
    assertEquals(expected, testRun(game5, inputs("4 2    4 4  q"),
            prints(expected)));
  }

  @Test
  public void testEuroQQQ() {
    String expected = "    O O O\n" +
            "  O O O O O\n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "O O O O O O O\n" +
            "  O O O O O\n" +
            "    O O O\n" +
            "Score: 36\n" +
            "    O O O\n" +
            "  O O O O O\n" +
            "O O O O O O O\n" +
            "O O O O _ _ O\n" +
            "O O O O O O O\n" +
            "  O O O O O\n" +
            "    O O O\n" +
            "Score: 35\n" +
            "Input QQQ is bad! Enter new one!\n";
    assertEquals(expected, testRun(game5, inputs("4 6\n 4 4 QQQ"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit5thInvalidInput() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Input -2 is bad! Enter new one!\n"
            + "Input a is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs("3 -2 a 2 Q"),
            prints(expected)));
  }

  @Test
  public void testEuroQuit5thInValidInputExtra() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Input -2 is bad! Enter new one!\n"
            + "Input a is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game7, inputs("3 -2 a 2 q 3 2"),
            prints(expected)));
  }

  @Test
  public void testEuroLess4Input() throws IllegalArgumentException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n";
    assertEquals(expected, testRun(game5, inputs("3 3   2"),
            prints(expected)));
  }

  @Test
  public void testEuro4CorrectInput() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O _\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O _ _ O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 35\n";
    assertEquals(expected, testRun(game7, inputs("3 5   3 7"),
            prints(expected)));
  }

  @Test
  public void testEuro4IncorrectInput() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game5, inputs("2  3  5 3"),
            prints(expected)));
  }


  @Test
  public void testEuro4InputsWithLetterAnd0() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Input a is bad! Enter new one!\n"
            + "Input 0 is bad! Enter new one!\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game5, inputs(" 3 4 a 4 0 6 "),
            prints(expected)));
  }

  @Test
  public void testEuro5InputsWithSymbolsAndNegativeNum() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Input * is bad! Enter new one!\n"
            + "Input -1-2 is bad! Enter new one!\n"
            + "Input !7 is bad! Enter new one!\n"
            + "Input -4 is bad! Enter new one!\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ _ O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 35\n";
    assertEquals(expected, testRun(game5, inputs("4 6 * 4 -1-2 !7  -4 4"),
            prints(expected)));
  }


  @Test
  public void testEuroMoreThan4Inputs() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ _ O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 35\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O _ _ O _ O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 34\n";
    assertEquals(expected, testRun(game5, inputs("4  6      4\n   4   \n  4 3 \n"),
            inputs("  4  5  "),
            prints(expected)));
  }

  @Test
  public void testEuroInvalidJump() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game5, inputs("4 5 4 6 "), prints(expected)));
  }

  @Test
  public void testEuroInvalidCellNonExistent() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Invalid move. Play again. Invalid cell position\n";
    assertEquals(expected, testRun(game5, inputs("1 2 3 1"),
            prints(expected)));
  }

  @Test
  public void testEuroInvalidCellOutOfBound() throws IllegalStateException {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Invalid move. Play again. Invalid cell position\n";
    assertEquals(expected, testRun(game5, inputs("4 7 4 9"),
            prints(expected)));
  }

  @Test
  public void testEuroGameOver() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ _ O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 35\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ O O\n"
            + "O O O O O _ O\n"
            + "  O O O O _\n"
            + "    O O O\n"
            + "Score: 34\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ O O\n"
            + "O O O O O _ O\n"
            + "  O O _ _ O\n"
            + "    O O O\n"
            + "Score: 33\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ O O\n"
            + "O O O O O _ O\n"
            + "  _ _ O _ O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O _ O O _ O O\n"
            + "O _ O O O _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 31\n"
            + "    O O O\n"
            + "  _ O O O O\n"
            + "O _ O O O O O\n"
            + "O O O O _ O O\n"
            + "O _ O O O _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 30\n"
            + "    O O O\n"
            + "  _ O O O O\n"
            + "O O _ _ O O O\n"
            + "O O O O _ O O\n"
            + "O _ O O O _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 29\n"
            + "    O O O\n"
            + "  _ O O O O\n"
            + "O O _ O _ _ O\n"
            + "O O O O _ O O\n"
            + "O _ O O O _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 28\n"
            + "    O O O\n"
            + "  _ O O O O\n"
            + "O _ _ O _ _ O\n"
            + "O _ O O _ O O\n"
            + "O O O O O _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 27\n"
            + "    O O O\n"
            + "  _ O O O O\n"
            + "O _ O O _ _ O\n"
            + "O _ _ O _ O O\n"
            + "O O _ O O _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 26\n"
            + "    O O O\n"
            + "  _ O O O O\n"
            + "O _ O O _ _ O\n"
            + "O _ _ O _ O O\n"
            + "O O O _ _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 25\n"
            + "    O O O\n"
            + "  _ O O O O\n"
            + "O _ O O _ _ O\n"
            + "O _ _ O _ O O\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 24\n"
            + "    O O O\n"
            + "  O _ _ O O\n"
            + "O _ O O _ _ O\n"
            + "O _ _ O _ O O\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 23\n"
            + "    O O O\n"
            + "  O _ O _ _\n"
            + "O _ O O _ _ O\n"
            + "O _ _ O _ O O\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 22\n"
            + "    O O O\n"
            + "  O _ O _ _\n"
            + "O _ _ _ O _ O\n"
            + "O _ _ O _ O O\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 21\n"
            + "    O _ O\n"
            + "  O _ _ _ _\n"
            + "O _ _ O O _ O\n"
            + "O _ _ O _ O O\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 20\n"
            + "    O _ O\n"
            + "  O _ _ _ _\n"
            + "O _ O _ _ _ O\n"
            + "O _ _ O _ O O\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 19\n"
            + "    O _ O\n"
            + "  O _ _ _ _\n"
            + "O _ O _ _ _ O\n"
            + "O _ _ O O _ _\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 18\n"
            + "    O _ O\n"
            + "  O _ _ _ _\n"
            + "O _ O _ _ _ O\n"
            + "O _ O _ _ _ _\n"
            + "O _ _ O _ _ O\n"
            + "  O _ O _ O\n"
            + "    O O O\n"
            + "Score: 17\n"
            + "    O _ O\n"
            + "  O _ _ _ _\n"
            + "O _ O _ _ _ O\n"
            + "O _ O O _ _ _\n"
            + "O _ _ _ _ _ O\n"
            + "  O _ _ _ O\n"
            + "    O O O\n"
            + "Score: 16\n"
            + "Game over!\n"
            + "    O _ O\n"
            + "  O _ _ _ _\n"
            + "O _ O _ _ _ O\n"
            + "O _ _ _ O _ _\n"
            + "O _ _ _ _ _ O\n"
            + "  O _ _ _ O\n"
            + "    O O O\n"
            + "Score: 15\n";
    assertEquals(expected, testRun(game5,
            inputs("4 6 4 4 6 6 4 \n"),
            inputs("6 6 4 6 6 6 2 6 4\n"),
            inputs(" 4 2 6 2 2 2 4 2 3 4 3 \n"),
            inputs("2 3 6 3 4 3 2 5 2 5 3 3 \n"
                    + "3 5 5 5 3 5 2 5 4 2 "),
            inputs("4 2 2 2 6 2 4 3 3 3 \n"
                    + "5 1 4 3 4 3 5 3 3 4 7 4 \n"
                    + "5 4 5 4 3 6 4 4 4 4 3 4 "),
            inputs("5\n"),
            prints(expected)));
  }

  @Test
  public void testEuroInvalidJumpSeries() {
    String expected = "    O O O\n"
            + "  O O O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "  O O O O O\n"
            + "    O O O\n"
            + "Score: 36\n"
            + "Invalid move. Play again. Not a valid jump\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game5,
            inputs("4 2 2 2\n"),
            inputs("2 2 4 2\n"),
            prints(expected)));
  }


  @Test
  public void testTriNoPassedInInput() throws IllegalArgumentException {
    String expected = "    _\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O O\n"
            + "O O O O O\n"
            + "Score: 14\n";
    assertEquals(expected, testRun(game9, inputs(""),
            prints(expected)));
  }

  @Test
  public void testTriQuitWithoutInput() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs(" q"),
            prints(expected)));
  }

  @Test
  public void testTriQuitBeforeInput() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("q 3"), prints(expected)));
  }

  @Test
  public void testTriQuit2ndPos() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("3  q"),
            prints(expected)));
  }

  @Test
  public void testTriQuit2ndPosBeforeInput() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("4 q 9 2"),
            prints(expected)));
  }

  @Test
  public void testTriQuit3rdPos() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("4 5 Q"),
            prints(expected)));
  }

  @Test
  public void testTriQuit3rdPosBeforeInput() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("4 5 q  qqq"),
            prints(expected)));
  }

  @Test
  public void testTriQuit4th() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Input QQ is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("4 5 3 QQ  q"),
            prints(expected)));
  }

  @Test
  public void testTriQuit4thBeforeInput() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("4 3 4 Q 9 2"),
            prints(expected)));
  }

  @Test
  public void testTriQuit5thValidMove() {
    String expected = "     _\n" +
            "    O O\n" +
            "   O O O\n" +
            "  O O O O\n" +
            " O O O O O\n" +
            "O O O O O O\n" +
            "Score: 20\n" +
            "     O\n" +
            "    O _\n" +
            "   O O _\n" +
            "  O O O O\n" +
            " O O O O O\n" +
            "O O O O O O\n" +
            "Score: 19\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "     O\n" +
            "    O _\n" +
            "   O O _\n" +
            "  O O O O\n" +
            " O O O O O\n" +
            "O O O O O O\n" +
            "Score: 19\n";
    assertEquals(expected, testRun(game10, inputs("3 3 1 1  q"),
            prints(expected)));
  }

  @Test
  public void testTriQQQ() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "     O\n"
            + "    O _\n"
            + "   O O _\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 19\n"
            + "Input QQQ is bad! Enter new one!\n";
    assertEquals(expected, testRun(game10, inputs("3  3\n 1  1 QQQ"),
            prints(expected)));
  }

  @Test
  public void testTriQuit5thInvalidInput() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Input -2 is bad! Enter new one!\n"
            + "Input a is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("3 -2 a 2 Q"),
            prints(expected)));
  }

  @Test
  public void testTriQuit5thInValidInputExtra() {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Input -2 is bad! Enter new one!\n"
            + "Input a is bad! Enter new one!\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n";
    assertEquals(expected, testRun(game10, inputs("3 -2 a 2 q 3 2"),
            prints(expected)));
  }

  @Test
  public void testTriLess4Input() throws IllegalArgumentException {
    String expected = "    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O\n"
            + "Score: 14\n";
    assertEquals(expected, testRun(game11, inputs("3 3   2"),
            prints(expected)));
  }

  @Test
  public void testTri4CorrectInput() throws IllegalStateException {
    String expected = "    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O\n"
            + "Score: 14\n"
            + "    O\n"
            + "   O _\n"
            + "  O O _\n"
            + " O O O O\n"
            + "O O O O O\n"
            + "Score: 13\n";
    assertEquals(expected, testRun(game11, inputs("2   2 4 4"),
            prints(expected)));
  }

  @Test
  public void testTri4IncorrectInput() throws IllegalStateException {
    String expected = "    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O\n"
            + "Score: 14\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game11, inputs("3 2 4 3"),
            prints(expected)));
  }


  @Test
  public void testTri4InputsWithLetterAnd0() throws IllegalStateException {
    String expected = "    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O\n"
            + "Score: 14\n"
            + "Input a is bad! Enter new one!\n"
            + "Input 0 is bad! Enter new one!\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game11, inputs(" 3 2 a 4 0 4"),
            prints(expected)));
  }

  @Test
  public void testTri5InputsWithSymbolsAndNegativeNum() throws IllegalStateException {
    String expected = "     _\n"
            + "    O O\n"
            + "   O O O\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 20\n"
            + "Input * is bad! Enter new one!\n"
            + "Input -1-2 is bad! Enter new one!\n"
            + "Input !7 is bad! Enter new one!\n"
            + "Input -4 is bad! Enter new one!\n"
            + "     O\n"
            + "    O _\n"
            + "   O O _\n"
            + "  O O O O\n"
            + " O O O O O\n"
            + "O O O O O O\n"
            + "Score: 19\n";
    assertEquals(expected, testRun(game10, inputs("3 3  * 1 -1-2 !7  -4 1"),
            prints(expected)));
  }


  @Test
  public void testTriMoreThan4Inputs() throws IllegalStateException {
    String expected = "    O\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O _\n" +
            "O O O O O\n" +
            "Score: 14\n" +
            "    O\n" +
            "   O _\n" +
            "  O O _\n" +
            " O O O O\n" +
            "O O O O O\n" +
            "Score: 13\n" +
            "    O\n" +
            "   O _\n" +
            "  _ _ O\n" +
            " O O O O\n" +
            "O O O O O\n" +
            "Score: 12\n";
    assertEquals(expected, testRun(game11, inputs("2 2      4\n   4   \n  3 1 \n"),
            inputs("  3  3  "),
            prints(expected)));
  }

  @Test
  public void testTriInvalidJump() throws IllegalStateException {
    String expected = "    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O\n"
            + "Score: 14\n"
            + "Invalid move. Play again. Not a valid jump\n";
    assertEquals(expected, testRun(game11, inputs("4 3 4 2 "), prints(expected)));
  }

  @Test
  public void testTriInvalidCellNonExistent() throws IllegalStateException {
    String expected = "    O\n"
            + "   O O\n"
            + "  O O O\n"
            + " O O O _\n"
            + "O O O O O\n"
            + "Score: 14\n"
            + "Invalid move. Play again. Invalid cell position\n";
    assertEquals(expected, testRun(game11, inputs("1 2 3 1"),
            prints(expected)));
  }

  @Test
  public void testTriGameOver() {
    String expected = "   O\n" +
            "  O O\n" +
            " O O O\n" +
            "O _ O O\n" +
            "Score: 9\n" +
            "   O\n" +
            "  O O\n" +
            " O O O\n" +
            "O O _ _\n" +
            "Score: 8\n" +
            "   O\n" +
            "  O O\n" +
            " O O O\n" +
            "_ _ O _\n" +
            "Score: 7\n" +
            "   O\n" +
            "  O _\n" +
            " O _ O\n" +
            "_ O O _\n" +
            "Score: 6\n" +
            "   O\n" +
            "  O _\n" +
            " O _ O\n" +
            "_ _ _ O\n" +
            "Score: 5\n" +
            "   O\n" +
            "  O O\n" +
            " O _ _\n" +
            "_ _ _ _\n" +
            "Score: 4\n" +
            "   O\n" +
            "  _ O\n" +
            " _ _ _\n" +
            "O _ _ _\n" +
            "Score: 3\n" +
            "Game over!\n" +
            "   _\n" +
            "  _ _\n" +
            " _ _ O\n" +
            "O _ _ _\n" +
            "Score: 2\n";
    assertEquals(expected, testRun(game12,
            inputs("4 4 4 2 4 1 4 3 2 2 4 2 4 2 4 4 4 4 2 2 2 1 4 1 1 1 3 3\n"),
            prints(expected)));
  }

  @Test
  public void testTriInvalidJumpSeries() {
    String expected = "   O\n" +
            "  O O\n" +
            " O O O\n" +
            "O _ O O\n" +
            "Score: 9\n" +
            "Invalid move. Play again. Not a valid jump\n" +
            "   O\n" +
            "  O _\n" +
            " O _ O\n" +
            "O O O O\n" +
            "Score: 8\n";
    assertEquals(expected, testRun(game12,
            inputs("4 2 2 2\n"),
            inputs("2 2 4 2\n"),
            prints(expected)));
  }
}

package cs3500.marblesolitaire.controller;

import java.io.IOException;

import java.util.NoSuchElementException;

import java.util.Objects;
import java.util.Scanner;

import cs3500.marblesolitaire.model.MarbleSolitaireModel;


/**
 * The class MarbleSolitaireControllerImpl represents the controller that takes in user's words and
 * transfer into readable so that the program will execute actions accordingly.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructor that accepts and stores these objects for doing input and output.
   *
   * @param rd given input
   * @param ap transmit output
   */
  public MarbleSolitaireControllerImpl(Readable rd, Appendable ap) {
    Objects.requireNonNull(rd);
    Objects.requireNonNull(ap);
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Plays a new game of Marble Solitaire using the provided model. The program will request 4 valid
   * inputs from the users to use them fromRow, fromCol, toRow, and toCol in order to play the
   * game.
   *
   * @param model the Marble Solitaire Model
   * @throws IllegalStateException if the program catches other exceptions such as
   *                               NoSuchElementException, NumberFormatException, IOException, etc.
   */
  @Override
  public void playGame(MarbleSolitaireModel model) throws IllegalStateException {
    String quitMsg = "Game quit!\nState of game when quit:\n";
    Objects.requireNonNull(model);
    appendMsg(addNewLine(model) + "Score: " + model.getScore() + "\n");
    Scanner scan = new Scanner(this.rd);
    while (!model.isGameOver()) {
      String fromRow = getValue(scan);
      // quit the program if fromRow is q or Q
      if (quitKeyWords(fromRow)) {
        break;
      }
      String fromCol = getValue(scan);
      // quit the program if fromCol is q or Q
      if (quitKeyWords(fromCol)) {
        break;
      }
      String toRow = getValue(scan);
      // quit the program if toRow is q or Q
      if (quitKeyWords(toRow)) {
        break;
      }
      String toCol = getValue(scan);
      // quit the program if toCol is q or Q
      if (quitKeyWords(toCol)) {
        break;
      }
      tryMove(model, Integer.valueOf(fromRow), Integer.valueOf(fromCol), Integer.valueOf(toRow),
              Integer.valueOf(toCol));
    }
    if (model.isGameOver()) {
      appendMsg("Game over!\n");
    } else {
      appendMsg(quitMsg);
    }
    appendMsg(addNewLine(model) + "Score: " + model.getScore() + "\n");
  }

  /**
   * Moves the marble if the move is valid.catch Exception if 1) from position or to position is not
   * valid. 2) if from position to to position is more than two positions away. 3) if from position
   * is not a marble and to position is not empty. 4) if there is no marble between from position
   * and to position
   *
   * @param model   provided MarbleSolitaireModel model
   * @param fromRow the number of row the marble to moving from
   * @param fromCol the number of column the marble to moving from
   * @param toRow   the number of row the marble to moving to
   * @param toCol   the number of column the marble to moving to
   */
  private void tryMove(MarbleSolitaireModel model, int fromRow, int fromCol,
                       int toRow, int toCol) {
    try {
      model.move(fromRow - 1, fromCol - 1, toRow - 1, toCol - 1);
      if (!model.isGameOver()) {
        appendMsg(addNewLine(model) + "Score: " + model.getScore() + "\n");
      }
    } catch (IllegalArgumentException e) {
      appendMsg("Invalid move. Play again. " + e.getMessage() + "\n");
    }
  }

  /**
   * Adds a new line to the getGameState to ensure that the controller puts everything on a new
   * line.
   *
   * @param model given marble solitaire model
   * @return String of the getGameState and a new line
   */
  private static String addNewLine(MarbleSolitaireModel model) {
    Objects.requireNonNull(model);
    return model.getGameState() + "\n";
  }

  /**
   * Checks if the given String if either "q" or "Q."
   *
   * @param object the given string
   * @return true if the string is either "q" or "Q"
   */
  private static boolean quitKeyWords(String object) {
    Objects.requireNonNull(object);
    return object.equals("q") || object.equals("Q");
  }

  /**
   * Appends a message to the appendable.
   *
   * @param msg message that should be added to the appendable
   * @throws IllegalArgumentException if the I/O is not recognizable
   */
  private void appendMsg(String msg) throws IllegalStateException {
    try {
      this.ap.append(msg);
    } catch (IOException e) {
      // throws an error if an IOException is caught
      throw new IllegalStateException("Failed or interrupted I/O operations");
    }
  }

  /**
   * Gets a value that is not either letter or a negative number.
   *
   * @param sc the scanner
   * @return a string that is a positive number
   */
  private String getValue(Scanner sc) {
    // checks if there is a next value in the scanner
    String str = checkNext(sc);
    if (!validValues(str)) {
      appendMsg("Input " + str + " is bad! Enter new one!" + "\n");
      return getValue(sc);
    } else {
      return str;
    }
  }

  /**
   * Determines if the given String is a valid value.
   *
   * @param str the given string that should be checked
   * @return boolean if the string is a positive number
   */
  private static boolean validValues(String str) {
    try {
      Integer.parseInt(str);
      return Integer.valueOf(str) > 0;
    } catch (NumberFormatException e) {
      // checks if the letter is q or Q
      return quitKeyWords(str);
    }
  }

  /**
   * Checks if there is a next value in the scanner.
   *
   * @param sc the giveen scanner
   * @return the next value in the scanner if there is one
   * @throws IllegalArgumentException if there is no next value in the scanner
   */
  private static String checkNext(Scanner sc) throws IllegalStateException {
    try {
      return sc.next();
    } catch (NoSuchElementException e) {
      // throws exception when NoSuchElementException
      throw new IllegalStateException("No next input");
    }
  }
}
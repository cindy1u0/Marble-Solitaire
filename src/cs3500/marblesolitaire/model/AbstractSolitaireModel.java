package cs3500.marblesolitaire.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class that implements the interface. Provided all the methods that will be used in
 * the subclasses such as classic, European, and triangle boards. The abstract class has an enum
 * class that determines which model is being called
 */
public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {
  private int length;
  private Fields[][] board;
  private List<Fields> marbles = new ArrayList<>();
  private SolitaireTypes type;
  private int sRow;
  private int sCol;

  /**
   * A super constructor that initializes the board, ensures the dimension, and the empty position
   * to be valid. It also ensures what type the current model is so that the method changes its
   * calculation accordingly.
   */
  AbstractSolitaireModel(int length, int sRow, int sCol, SolitaireTypes type) {
    this.length = length;
    this.type = type;
    ensureDimension(length);
    initBoard();
    checkEmpty(sRow, sCol);
    initMarbles();
  }

  /**
   * Checks if that current position is valid for an empty marble to be placed. If so, initializes
   * that position on the board as well.
   */
  private void checkEmpty(int sRow, int sCol) {
    if (checkInvalid(sRow, sCol)) {
      throw new IllegalArgumentException("Invalid empty cell position ("
              + sRow + ", " + sCol + ")");
    }
    this.sRow = sRow;
    this.sCol = sCol;
    this.board[sRow][sCol] = Fields.X;
  }

  /**
   * Makes sure arm thickness, sRow, and sCol are valid. EFFECT: Initialize the parameters if they
   * have met the constraints.
   *
   * @param arm the arm thickness of the game board
   */
  private void ensureDimension(int arm) throws IllegalArgumentException {
    boolean invalid = arm <= 1 || arm % 2 == 0;
    if (this.type == SolitaireTypes.TRIANGLE) {
      invalid = arm < 2;
    }
    if (invalid) {
      throw new IllegalArgumentException("Invalid arm thickness");
    }
    this.length = arm;
  }

  /**
   * Determines if the given marble position is valid or not.
   *
   * @param row the row number of the empty cell
   * @param col the column number of the empty cell
   */
  private boolean checkInvalid(int row, int col) {
    try {
      return board[row][col].equals(Fields.INVALID);
    } catch (Exception e) {
      return true;
    }
  }

  /**
   * Initializes the parameters EFFECT: Fill in the board with Fields.O if there is a marble,
   * Fields.X if there is no marble, and Fields.INVALID if the piece is not part of the board.
   * Depends on which board the user is selecting, there are different ways to initialize the
   * board.
   */
  public void initBoard() {
    int size = 3 * this.length - 2;
    int midStart = this.length - 1;
    int midEnd = 2 * this.length - 2;
    Fields[][] board;
    if (this.type == SolitaireTypes.TRIANGLE) {
      board = new Fields[length][];
    } else {
      board = new Fields[size][];
    }
    for (int i = 0; i < board.length; i++) {
      if (this.type == SolitaireTypes.TRIANGLE) {
        board[i] = new Fields[i + 1];
      } else {
        board[i] = new Fields[size];
      }
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = Fields.O;
        if (this.type == SolitaireTypes.CLASSIC) {
          if ((i < midStart || i > midEnd) && (j < midStart || j > midEnd)) {
            board[i][j] = Fields.INVALID;
          }
        }
        if (this.type == SolitaireTypes.EUROPEAN) {
          if (i < midStart) {
            if (j <= midStart - 1 - i || j > midEnd + i) {
              board[i][j] = Fields.INVALID;
            }
          }
          if (i > midEnd) {
            if (j <= i - midEnd - 1 || j > midEnd + size - i - 1) {
              board[i][j] = Fields.INVALID;
            }
          }
        }
      }
    }
    this.board = board;
  }

  /**
   * Initializes the marbles from the board so that it creates a 1D array list of fields.
   */
  public void initMarbles() {
    for (Fields[] fields : this.board) {
      marbles.addAll(Arrays.asList(fields));
    }
  }

  /**
   * Moves a marble to another position if fromPosition and toPosition are valid. EFFECT: Modifies
   * marbles' representation after moving to according integer. Depends on which board the user is
   * selecting, there are different ways to calculate if the move can be made or not.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
   * @throws IllegalArgumentException 1) from position or to position is not valid. 2) if from
   *                                  position to to position is more than two positions away. 3) if
   *                                  from position is not a marble and to position is not empty. 4)
   *                                  if there is no marble between from position and to position
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    int checkRow = fromRow - toRow;
    int checkCol = fromCol - toCol;
    if (checkInvalid(fromRow, fromCol) || checkInvalid(toRow, toCol)) {
      throw new IllegalArgumentException("Invalid cell position");
    }
    Fields current = this.board[fromRow][fromCol];
    Fields isEmpty = this.board[toRow][toCol];
    Fields midCell = this.board[(fromRow + toRow) / 2][(fromCol + toCol) / 2];
    boolean sameRow = Math.abs(checkCol) == 2 && checkRow == 0;
    boolean sameCol = Math.abs(checkRow) == 2 && checkCol == 0;
    boolean twos = ((fromRow - toRow == 2) && (fromCol - toCol == 2))
            || ((toRow - fromRow == 2) && (toCol - fromCol == 2));
    boolean checkValidMove = sameRow || sameCol;
    if (this.type == SolitaireTypes.TRIANGLE) {
      checkValidMove = checkValidMove || twos;
    }
    if (checkValidMove && current == Fields.O && isEmpty == Fields.X && midCell == Fields.O) {
      this.board[fromRow][fromCol] = Fields.X;
      changeField(fromRow, fromCol, Fields.X);
      this.board[toRow][toCol] = Fields.O;
      changeField(toRow, toCol, Fields.O);
      this.board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = Fields.X;
      changeField((fromRow + toRow) / 2, (fromCol + toCol) / 2, Fields.X);
    } else {
      throw new IllegalArgumentException("Not a valid jump");
    }
  }

  private void changeField(int row, int col, Fields f) {
    Objects.requireNonNull(f);
    if (this.type == SolitaireTypes.TRIANGLE) {
      this.marbles.set(col + (row * (row + 1) / 2), f);
    } else {
      this.marbles.set(row * 7 + col, f);
    }
  }

  /**
   * Determine and return if the game is over or not. A game is over if no more moves can be made.
   * Depends on which board the user is selecting, there are different ways to calculate if the game
   * is over or not.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    boolean gameOver = false;
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        if (this.board[i][j] == Fields.X) {
          if (!gameOver && i - 2 >= 0) {
            if (this.type == SolitaireTypes.TRIANGLE) {
              if (j - 2 >= 0) {
                gameOver = this.board[i - 1][j - 1] == Fields.O
                        && this.board[i - 2][j - 2] == Fields.O;
              }
              if (j + 2 < this.board[i - 2].length) {
                gameOver = this.board[i - 1][j + 1] == Fields.O
                        && this.board[i - 2][j + 2] == Fields.O;
              }
            } else {
              gameOver = this.board[i - 1][j] == Fields.O
                      && this.board[i - 2][j] == Fields.O;
            }
          }
          if (!gameOver && i + 2 < this.board.length) {
            if (this.type == SolitaireTypes.TRIANGLE) {
              if (j - 2 >= 0) {
                gameOver = this.board[i + 1][j - 1] == Fields.O
                        && this.board[i + 2][j - 2] == Fields.O;
              }
              if (j + 2 < this.board[i + 2].length) {
                gameOver = this.board[i + 1][j + 1] == Fields.O
                        && this.board[i + 2][j + 2] == Fields.O;
              }
            } else {
              gameOver = this.board[i + 1][j] == Fields.O && this.board[i + 2][j] == Fields.O;
            }
          }
          if (!gameOver && j - 2 >= 0) {
            gameOver = this.board[i][j - 1] == Fields.O && this.board[i][j - 2] == Fields.O;
          }
          if (!gameOver && j + 2 < this.board[i].length) {
            gameOver = this.board[i][j + 1] == Fields.O && this.board[i][j + 2] == Fields.O;
          }
        }
      }
    }
    return !gameOver;
  }

  /**
   * Return a string that represents the current state of the board. The string should have one line
   * per row of the game board. Each slot on the game board is a single character (O, X or space for
   * a marble, empty and invalid position respectively). Slots in a row should be separated by a
   * space. Each row has no space before the first slot and after the last slot. Depends on which
   * board the user is selecting, the board can be represented differently
   *
   * @return the game state as a string
   */
  public String getGameState() {
    StringBuilder gameState = new StringBuilder();
    int size = this.length * 3 - 2;
    int midStart = this.length - 2;
    int midEnd = 2 * this.length - 2;
    boolean check;
    for (int i = 0; i < this.board.length; i++) {
      if (i > 0) {
        gameState.append("\n");
      }
      if (this.type == SolitaireTypes.TRIANGLE) {
        for (int j = length - i; j > 1; j--) {
          gameState.append(" ");
        }
      }
      for (int j = 0; j < this.board[i].length; j++) {
        if (this.board[i][j] == Fields.INVALID && j < midEnd) {
          gameState.append(" ");
        }
        if (this.board[i][j] == Fields.X) {
          gameState.append("_");
        }
        if (this.board[i][j] == Fields.O) {
          gameState.append("O");
        }
        boolean checkMiddle = (i < midEnd + 1 && i > midStart && j < size - 1);
        switch (this.type) {
          case CLASSIC:
            check = j < midEnd || checkMiddle;
            break;
          case EUROPEAN:
            check = (j < midEnd + i && i < midStart + 1)
                    || checkMiddle
                    || (j < midEnd + size - 1 - i && i > midEnd);
            break;
          case TRIANGLE:
            check = j < board[i].length - 1;
            break;
          default:
            throw new IllegalStateException("Not a valid board");
        }
        if (check) {
          gameState.append(" ");
        }
      }
    }
    return gameState.toString();
  }


  /**
   * Return the number of marbles currently on the board. If the current position is marked as "O,"
   * it means there is a marble at that position.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    int count = 0;
    for (Fields[] f : this.board) {
      for (Fields fields : f) {
        if (fields == Fields.O) {
          count++;
        }
      }
    }
    return count;
  }


  /**
   * Returns the board with no marbles at any position (for testing purpose).
   *
   * @param num set the board to all marbles "1" or empty "0" or invalid "-1"
   */
  public void emptyBoard(int num) {
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        if (board[i][j] != Fields.INVALID) {
          this.board[i][j] = convertFields(num);
        }
      }
    }
  }

  /**
   * Modifies the specific position to the given number (for testing purpose).
   *
   * @param row the row number of the given position
   * @param col the column number of the given position
   * @param num set it to marble "1" or empty "0" or invalid "-1"
   */
  public void setMarblesOrEmptyCell(int row, int col, int num) {
    this.board[row][col] = convertFields(num);
  }

  /**
   * Converts number to enum fields. 0 as Fields.X, 1 as Fields.O, and -1 as Fields.INVALID.
   *
   * @param num the number that the user wants to convert to
   * @return the corresponding enum field
   */
  private Fields convertFields(int num) {
    if (num == 0) {
      return Fields.X;
    } else if (num == 1) {
      return Fields.O;
    } else {
      return Fields.INVALID;
    }
  }

  /**
   * Getter that gets the board of the solitaire model.
   *
   * @return the board of the model
   */
  @Override
  public Fields[][] getFields() {
    return this.board;
  }

  /**
   * Getter that gets the board type.
   *
   * @return the the type of the board
   */
  @Override
  public SolitaireTypes getType() {
    return this.type;
  }

  /**
   * Getter that gets the board in 1D array.
   *
   * @return the  board of the model
   */
  @Override
  public List<Fields> getMarbles() {
    return this.marbles;
  }

  /**
   * Getter.
   *
   * @return the row number of the empty cell
   */
  @Override
  public int getRow() {
    return this.sRow;
  }


  /**
   * Getter.
   *
   * @return the column number of the empty cell
   */
  @Override
  public int getCol() {
    return this.sCol;
  }
}

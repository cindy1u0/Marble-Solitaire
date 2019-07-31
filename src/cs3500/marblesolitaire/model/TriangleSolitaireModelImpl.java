package cs3500.marblesolitaire.model;

/**
 * Represent the information of a marble solitaire game, in the version of an European board, and 4
 * constructors that can be used.
 */
public class TriangleSolitaireModelImpl extends AbstractSolitaireModel {

  /**
   * Constructor that initializes the game board with 5 rows with the empty slot at the center.
   */
  public TriangleSolitaireModelImpl() {
    super(5, 0, 0, SolitaireTypes.TRIANGLE);
  }

  /**
   * Constructor that initializes the game board so that it has 5 rows and the empty slot is at the
   * position (sRow, sCol).
   *
   * @param sRow the row number of the empty cell
   * @param sCol the column number of the empty cell
   */
  public TriangleSolitaireModelImpl(int sRow, int sCol) {
    super(5, sRow, sCol, SolitaireTypes.TRIANGLE);
  }

  /**
   * Constructor that initializes a game board with the empty slot at the position (0, 0), and the
   * customized length.
   *
   * @param dimensions the number of rows of the game board
   */
  public TriangleSolitaireModelImpl(int dimensions) {
    super(dimensions, 0, 0, SolitaireTypes.TRIANGLE);
  }

  /**
   * Constructor that initializes a game with the given row number, and places the empty slot at the
   * position (sRow, sCol).
   *
   * @param dimensions the number of rows of the game board
   * @param sRow       the row number of the empty cell
   * @param sCol       the column number of the empty cell
   */
  public TriangleSolitaireModelImpl(int dimensions, int sRow, int sCol) {
    super(dimensions, sRow, sCol, SolitaireTypes.TRIANGLE);
  }
}

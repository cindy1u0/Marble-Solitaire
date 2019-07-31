package cs3500.marblesolitaire.model;

/**
 * Represent the information of a marble solitaire game, in the version of an European board, and 4
 * constructors that can be used.
 */
public class EuropeanSolitaireModelImpl extends AbstractSolitaireModel {

  /**
   * Constructor that initializes the game board with arm thickness 3 with the empty slot at the
   * center.
   */
  public EuropeanSolitaireModelImpl() {
    super(3, 3, 3, SolitaireTypes.EUROPEAN);
  }

  /**
   * Constructor that initializes the game board so that the arm thickness is 3 and the empty slot
   * is at the position (sRow, sCol).
   *
   * @param sRow the row number of the empty cell
   * @param sCol the column number of the empty cell
   */
  public EuropeanSolitaireModelImpl(int sRow, int sCol) {
    super(3, sRow, sCol, SolitaireTypes.EUROPEAN);
  }

  /**
   * Constructor that initializes a game board with the empty slot at the center, and the customized
   * length.
   *
   * @param length the arm thickness of the game board
   */
  public EuropeanSolitaireModelImpl(int length) {
    super(length, (3 * length - 2) / 2, (3 * length - 2) / 2, SolitaireTypes.EUROPEAN);
  }

  /**
   * Constructor that initializes a game with the given arm thickness, and places the empty slot at
   * the position (sRow, sCol).
   *
   * @param length the arm thickness of the game board
   * @param sRow   the row number of the empty cell
   * @param sCol   the column number of the empty cell
   */
  public EuropeanSolitaireModelImpl(int length, int sRow, int sCol) {
    super(length, sRow, sCol, SolitaireTypes.EUROPEAN);
  }
}

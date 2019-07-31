package cs3500.marblesolitaire.model;

/**
 * Represent the information of a marble solitaire game, and 4 constructors that can be used.
 */
public class MarbleSolitaireModelImpl extends AbstractSolitaireModel {

  /**
   * Constructor that initializes the game board with arm thickness 3 with the empty slot at the
   * center.
   */
  public MarbleSolitaireModelImpl() {
    super(3, 3, 3, SolitaireTypes.CLASSIC);
  }

  /**
   * Constructor that initializes the game board so that the arm thickness is 3 and the empty slot
   * is at the position (sRow, sCol).
   *
   * @param sRow the row number of the empty cell
   * @param sCol the column number of the empty cell
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) {
    super(3, sRow, sCol, SolitaireTypes.CLASSIC);
  }

  /**
   * Constructor that initializes a game board with the empty slot at the center.
   *
   * @param arm the arm thickness of the game board
   */
  public MarbleSolitaireModelImpl(int arm) {
    super(arm, (3 * arm - 2) / 2, (3 * arm - 2) / 2, SolitaireTypes.CLASSIC);
  }

  /**
   * Constructor that initializes a game with the given arm thickness, and places the empty slot at
   * the position (sRow, sCol).
   *
   * @param arm  the arm thickness of the game board
   * @param sRow the row number of the empty cell
   * @param sCol the column number of the empty cell
   */
  public MarbleSolitaireModelImpl(int arm, int sRow, int sCol) {
    super(arm, sRow, sCol, SolitaireTypes.CLASSIC);
  }

}
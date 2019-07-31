package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.MarbleSolitaireModel;

/**
 * View features that are used in the controller.
 */
public interface IViewFeatures {

  /**
   * Resets the game back to the original.
   */
  void reset();

  /**
   * Switches to the selected board.
   *
   * @param model given board
   */
  void change(MarbleSolitaireModel model);
}

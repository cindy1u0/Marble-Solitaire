package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.MarbleSolitaireModel;

/**
 * Provides a visual representation of the game that allows the user to play.
 */
public interface IView {
  /**
   * Renders the given output depends on with view the user is selecting.
   *
   * @param model given IReadOnlyShapes that are from the model
   */
  void render(MarbleSolitaireModel model);

  /**
   * Adds all the IViewFeatures to the listeners to execute.
   *
   * @param vf given IViewFeatures
   * @throws UnsupportedOperationException if the given view doesn't support the listeners
   */
  void addListener(IViewFeatures vf) throws UnsupportedOperationException;
}

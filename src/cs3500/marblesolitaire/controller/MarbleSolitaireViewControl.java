package cs3500.marblesolitaire.controller;

import java.util.Objects;

import cs3500.marblesolitaire.model.Fields;
import cs3500.marblesolitaire.model.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.IView;
import cs3500.marblesolitaire.view.IViewFeatures;

/**
 * A controller that provides the visual demonstration of the game.
 */
public class MarbleSolitaireViewControl implements MarbleSolitaireController, IViewFeatures {

  private MarbleSolitaireModel model;
  private final IView view;

  /**
   * A constructor of the controller.
   *
   * @param model the provided model
   * @param view  view that is provided
   */
  MarbleSolitaireViewControl(MarbleSolitaireModel model, IView view) {
    this.model = model;
    this.view = view;
    view.addListener(this);
  }

  /**
   * Plays the marble solitaire game.
   *
   * @param model the Marble Solitaire Model
   * @throws IllegalStateException if nothing is being read
   */
  @Override
  public void playGame(MarbleSolitaireModel model) throws IllegalStateException {
    Objects.requireNonNull(model);
    view.render(model);
  }

  /**
   * Resets the game back to original.
   */
  @Override
  public void reset() {
    model.initBoard();
    model.getFields()[model.getRow()][model.getCol()] = Fields.X;
    model.getMarbles().clear();
    model.initMarbles();
    view.render(model);
  }

  /**
   * Switches to the selected board.
   *
   * @param mod given board
   */
  @Override
  public void change(MarbleSolitaireModel mod) {
    Objects.requireNonNull(mod);
    this.model = mod;
    view.render(model);
  }
}

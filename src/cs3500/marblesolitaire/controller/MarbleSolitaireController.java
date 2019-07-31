package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.MarbleSolitaireModel;

/**
 * This interface represents the controls operated by the user.
 */
public interface MarbleSolitaireController {

  /**
   * Plays a new game of Marble Solitaire using the provided model.
   *
   * @param model the Marble Solitaire Model
   * @throws IllegalArgumentException if the provided model is null
   * @throws IllegalStateException    if the program catches other exceptions such as
   *                                  NoSuchElementException, NumberFormatException, IOException,
   *                                  etc.
   */
  void playGame(MarbleSolitaireModel model) throws IllegalStateException;
}


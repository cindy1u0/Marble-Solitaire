package cs3500.marblesolitaire.controller;

import java.io.InputStreamReader;

import cs3500.marblesolitaire.model.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.ViewImpl;

/**
 * The factory controller allows the user to change which view they want, including visual or
 * command codes.
 */
public class FactoryController {
  /**
   * a factory method that renders the corresponding view according to the view type.
   */
  public static MarbleSolitaireController makeController(String type, MarbleSolitaireModel model)
          throws IllegalArgumentException {
    switch (type) {
      case "view":
        return new MarbleSolitaireViewControl(model, new ViewImpl(model));
      case "command":
        return new MarbleSolitaireControllerImpl(new InputStreamReader(System.in),
                System.out);
      default:
        throw new IllegalArgumentException("Not a valid view");
    }
  }
}

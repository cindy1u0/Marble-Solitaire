package cs3500.marblesolitaire.view;

import java.awt.*;
import java.util.List;

import cs3500.marblesolitaire.model.MarbleSolitaireModel;

/**
 * Drawing panel.
 */
public interface IDrawing {
  /**
   * draws the shapes on the canvas with the corresponding animations.
   */
  void draw(MarbleSolitaireModel model);

  /**
   * Getter.
   *
   * @return the list of shapes
   */
  List<Shape> getShapeList();

}

package cs3500.marblesolitaire.view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.marblesolitaire.model.Fields;
import cs3500.marblesolitaire.model.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.SolitaireTypes;

/**
 * A drawing class that will draw all the shapes with corresponding in their positions, colors, and
 * sizes on a canvas with the given size using swing and corresponding methods within the library.
 */
public class Drawing extends JPanel implements IDrawing, MouseListener {
  private MarbleSolitaireModel mod = null;
  private List<Shape> shapes;
  private ArrayList<Integer> work = new ArrayList<>();
  private Shape selectedShape = null;
  private int count = 0;


  /**
   * A constructor that is called in the controller to enable this action.
   */
  Drawing() {
    super();
    shapes = new ArrayList<>();
    addMouseListener(this);
  }

  private void addShapes(MarbleSolitaireModel model) {
    for (int i = 0; i < model.getFields().length; i++) {
      for (int j = 0; j < model.getFields()[i].length; j++) {
        if (model.getType().equals(SolitaireTypes.TRIANGLE)) {
          shapes.add(new Ellipse2D.Float(50 * j + 25 * (model.getFields().length - i) + 100,
                  50 * i + 100, 40, 40));
        } else {
          shapes.add(new Ellipse2D.Float(50 * i + 100, 50 * j + 100, 40, 40));
        }
      }
    }
  }

  /**
   * Draws a shape with corresponding position, color, and size.
   *
   * @param g given graphics
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (mod != null) {
      Graphics2D g2 = (Graphics2D) g;
      for (int i = 0; i < shapes.size(); i++) {
        Shape shape = shapes.get(i);
        if (mod.getMarbles().get(i).equals(Fields.O)) {
          g2.setColor(Color.GREEN);
          g2.fill(shape);
        }
        if (mod.getMarbles().get(i).equals(Fields.X)) {
          g2.setColor(Color.BLACK);
          g2.draw(shape);
        }
      }
    }
    if (selectedShape != null) {
      Graphics2D newG2 = (Graphics2D) g.create();
      newG2.setColor(Color.RED);
      newG2.setStroke(new BasicStroke(4f));
      newG2.draw(selectedShape);
      newG2.dispose();
    }
  }


  /**
   * draws the shapes on the canvas with the corresponding animations.
   *
   * @param model the given model
   */
  @Override
  public void draw(MarbleSolitaireModel model) {
    this.mod = model;
    addShapes(mod);
    repaint();
  }

  /**
   * Getter.
   *
   * @return the list of shapes
   */
  @Override
  public List<Shape> getShapeList() {
    return this.shapes;
  }

  /**
   * Allows the user to make a move in the game.
   *
   * @param e detected MouseEvent
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).contains(e.getPoint())) {
        int x = (int) Math.floor((Math.sqrt(1 + 8 * i) - 1) / 2);
        int flow = (x * (x + 1) / 2);
        int y = i - flow;
        if (mod.getType().equals(SolitaireTypes.TRIANGLE)) {
          work.add(x);
          work.add(y);
          selectedShape = shapes.get(work.get(1) + (work.get(0) * (work.get(0) + 1) / 2));
        } else {
          work.add(i / 7);
          work.add(i % 7);
          int index = work.get(0) * 7 + work.get(1);
          if (!mod.getMarbles().get(index).equals(Fields.INVALID)) {
            selectedShape = shapes.get(index);
          }
        }
        repaint();
        if (work.size() == 4) {
          try {
            mod.move(work.get(0), work.get(1), work.get(2), work.get(3));
            count++;
            selectedShape = null;
            repaint();
            work.clear();
          } catch (IllegalArgumentException a) {
            work.clear();
          }
        }
      }
    }
    if (mod.isGameOver()) {
      tryAgain("YOU FINISHED! Moves Count: " + count + ". Marbles Left: " + mod.getScore());
    }
  }

  private void tryAgain(String s) {
    JFrame tryAgainWindow = new JFrame();
    JLabel text = new JLabel(s);
    text.setFont(new Font("Impact", Font.BOLD, 16));
    UIManager.put("OptionPane.background", Color.WHITE);
    UIManager.put("Panel.background", Color.WHITE);

    JOptionPane.showMessageDialog(tryAgainWindow, text,
            "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
  }


  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}

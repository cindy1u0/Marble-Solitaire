package cs3500.marblesolitaire.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.marblesolitaire.model.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.model.TriangleSolitaireModelImpl;

/**
 * Provides a visual representation of the game that allows the user to play.
 */
public class ViewImpl extends JFrame implements IView, ActionListener {
  private MarbleSolitaireModel model;
  private final Drawing panel;
  private List<IViewFeatures> listeners = new ArrayList<>();

  /**
   * A constructor.
   *
   * @param model provided model
   */
  public ViewImpl(MarbleSolitaireModel model) {
    super();
    this.model = model;
    panel = new Drawing();
    panel.setBackground(Color.WHITE);

    setSize(600, 600);
    panel.setPreferredSize(new Dimension(1000, 1000));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel bottomPanel = new JPanel();

    JButton reset = new JButton("Reset");
    reset.setActionCommand("reset");
    bottomPanel.add(reset);
    reset.addActionListener(this);

    JMenuBar menuBar = new JMenuBar();
    JMenu change = new JMenu("ChangeBoard");
    JMenuItem classic = new JMenuItem("Classic");
    classic.setActionCommand("classic");
    classic.addActionListener(this);
    JMenuItem european = new JMenuItem("European");
    european.setActionCommand("european");
    european.addActionListener(this);
    JMenuItem triangle = new JMenuItem("Triangle");
    triangle.setActionCommand("triangle");
    triangle.addActionListener(this);

    change.add(classic);
    change.add(european);
    change.add(triangle);
    menuBar.add(change);

    JScrollPane scrollPane = new JScrollPane(panel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(scrollPane);

    setResizable(true);
    add(bottomPanel, BorderLayout.SOUTH);
    setJMenuBar(menuBar);
    setVisible(true);

  }

  /**
   * Render the visual view.
   *
   * @param mod the provided model
   */
  @Override
  public void render(MarbleSolitaireModel mod) {
    panel.draw(model);
  }

  /**
   * Adds all the IViewFeatures to the listeners to execute.
   *
   * @param vf given IViewFeatures
   * @throws UnsupportedOperationException if the given view doesn't support the listeners
   */
  @Override
  public void addListener(IViewFeatures vf) throws UnsupportedOperationException {
    this.listeners.add(vf);
  }

  /**
   * Allows the user to pick whatever board they want and reset the game.
   *
   * @param e detected action event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    panel.getShapeList().clear();
    for (IViewFeatures feat : listeners) {
      switch (e.getActionCommand()) {
        case "reset":
          feat.reset();
          break;
        case "classic":
          model = new MarbleSolitaireModelImpl();
          feat.change(model);
          break;
        case "european":
          model = new EuropeanSolitaireModelImpl();
          feat.change(model);
          break;
        case "triangle":
          model = new TriangleSolitaireModelImpl();
          feat.change(model);
          break;
      }
    }
  }
}

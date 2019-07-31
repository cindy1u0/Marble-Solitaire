package cs3500;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.model.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.model.TriangleSolitaireModelImpl;

import static cs3500.marblesolitaire.controller.FactoryController.makeController;

/**
 * The main configuration of the game Marble Solitaire.
 */
public class MarbleSolitaire {
  /**
   * Transforms what the user types to the controller in order to play the game. If the user types
   * -size, then the program will take the next integer as the new length for the model. If there is
   * no size given, the program will output an error message. Similarly, if the user types -hole,
   * then the program will take the next two integers as the new empty cell positions. If there is
   * not enough integers, the program will output an error message. If it is not constructable by
   * the constructors, then the program will output an error message as well.
   *
   * @param args what user wants to transfer to the program. The way the user wants to play the
   *             game
   */
  public static void main(String[] args) {
    MarbleSolitaireModel model = null;
    String type = "view";
    int defLength = 3;
    int defX = (defLength * 3 - 2) / 2 + 1;
    int defY = (defLength * 3 - 2) / 2 + 1;
    int defTriLength = 3;
    int defTriX = 1;
    int defTriY = 1;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-size")) {
        try {
          defLength = Integer.parseInt(args[i + 1]);
          defTriLength = Integer.parseInt(args[i + 1]);
          defX = (defLength * 3 - 2) / 2 + 1;
          defY = (defLength * 3 - 2) / 2 + 1;
        } catch (Exception e) {
          // catch the out of bound exception
          System.out.println("Size cannot be found :(\nRe-edit the configuration");
          return;
        }
      }
      if (args[i].equals("-hole")) {
        try {
          defX = Integer.parseInt(args[i + 1]);
          defY = Integer.parseInt(args[i + 2]);
          defTriX = Integer.parseInt(args[i + 1]);
          defTriY = Integer.parseInt(args[i + 2]);
        } catch (Exception e) {
          // catch the out of bound exception
          System.out.println("Empty cell position cannot be found :(\nRe-edit the configuration");
          return;
        }
      }

      if (args[i].equals("-type")) {
        try {
          switch (args[i + 1]) {
            case "english":
              model = new MarbleSolitaireModelImpl(defLength, defX - 1, defY - 1);
              break;
            case "european":
              model = new EuropeanSolitaireModelImpl(defLength, defX - 1, defY - 1);
              break;
            case "triangular":
              model = new TriangleSolitaireModelImpl(defTriLength,
                      defTriX - 1, defTriY - 1);
              break;
            default:
              // error message when type is not one of the above three.
              System.out.println("Invalid model\nRe-edit the configuration");
              break;
          }
        } catch (IllegalArgumentException e) {
          // if given size or empty cell position is not constructable
          System.out.println(e.getMessage());
          return;
        }
      }

      if (args[i].equals("-control")) {
        try {
          type = args[i + 1];
        } catch (Exception e) {
          // catch the out of bound exception
          System.out.println("No view selected");
          return;
        }
      }
    }

    try {
      MarbleSolitaireController controller = makeController(type, model);
      controller.playGame(model);
    } catch (NullPointerException e) {
      System.out.println("Oops nothing shows up...");
    }
  }
}

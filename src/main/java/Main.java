import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * [Brief one-sentence description of what this class does.]
 *
 * @author Drew "Dr.C" Clinkenbeard
 * @version 0.1.0
 * @since 3/9/26
 */
public class Main extends Application {

  // Window dimensions in pixels
  private static final int SCENE_WIDTH = 400;
  private static final int SCENE_HEIGHT = 300;

  // Text used for both the window title bar and the on-screen label
  private static final String TITLE = "Hello There: ";

  /**
   * Application entry point. JavaFX requires calling launch(), which
   * internally creates the JavaFX runtime and calls start().
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Called by the JavaFX runtime after the application is initialized.
   * Build your scene graph here and show the primary Stage (window).
   *
   * @param stage the primary window provided by the JavaFX runtime
   */
  @Override
  public void start(Stage stage) {
    // Root layout — StackPane centers its children by default
    StackPane root = new StackPane(new Label(TITLE + "Label"));

    // Scene holds the layout and defines the window size
    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

    //TODO: add something fun!

    stage.setTitle(TITLE + "title"); // text shown in the OS title bar
    stage.setScene(scene);
    stage.show();                    // make the window visible
  }
}
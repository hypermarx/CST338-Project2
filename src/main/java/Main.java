import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * [Brief one-sentence description of what this class does.]
 *
 * @author Drew "Dr.C" Clinkenbeard
 * @version 0.1.0
 * @since 3/9/26
 */
public class Main extends Application {


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
    SceneManager.init(stage);
    SceneManager sceneManager = SceneManager.getInstance();
    Scene scene = SceneFactory.create(SceneType.LOGIN, stage);
    stage.setScene(scene);
    stage.setTitle("Quizweaver");
    stage.show();
  }

}
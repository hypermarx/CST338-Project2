import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
  private static final String S1TEXT = "Fahrenheit -> Celsius";
  private static final String F_TO_C_PROMPT = "Enter 'F'";
  private static final String BUTTON_TEXT = "Convert";
  private static final String INVALID_MESSAGE = "Invalid Response";
  // Text used for both the window title bar and the on-screen label
  //private static final String TITLE = "Hello There: ";

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
    //StackPane root = new StackPane(new Label(TITLE + "Label"));
    Label s1Label = new Label(S1TEXT);
    TextField s1Input = new TextField();
    s1Input.setPromptText(F_TO_C_PROMPT);
    //TODO: set somekind of padding for elements

    Button s1Button = new Button(BUTTON_TEXT);
    Label s1Result = new Label();

    int spacing = 12;

    VBox s1Root = new VBox(spacing, s1Label, s1Input, s1Button, s1Result);
    s1Input.setPrefWidth(200);
    s1Root.setAlignment(Pos.CENTER);
    s1Root.setPadding(new Insets(30));

    //Scene scene = new Scene(s1Root, SCENE_WIDTH, SCENE_HEIGHT);






    // Scene holds the layout and defines the window size
    Scene scene = new Scene(s1Root, SCENE_WIDTH, SCENE_HEIGHT);

    //TODO: add something fun!
    s1Button.setOnAction(e ->{
      String input = s1Input.getText();
      try {
        double fahrenheit = Double.parseDouble(input);
        s1Result.setText(String.format("%.2f'c",TemperatureCoverters.FarenheitToCelsius(fahrenheit)));
      }catch(NumberFormatException ex){
        s1Result.setText(String.format("%s%s ", INVALID_MESSAGE, input));

      }
      //s1Result.setText("BLAH LAL");
    });
    stage.setTitle(S1TEXT + "title"); // text shown in the OS title bar
    stage.setScene(scene);
    stage.show();                    // make the window visible
  }


}
// ./gradlew run
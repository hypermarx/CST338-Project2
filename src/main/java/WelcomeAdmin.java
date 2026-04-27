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

public class WelcomeAdmin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label welcome = new Label("Welcome Admin");
        //Have each quiz be an object ????
        Button editQ1 = new Button("Edit Quiz 1");
        Button editQ2 = new Button("Edit Quiz 2");
        Button editQ3 = new Button("Edit Quiz 3");
        Button newQuiz = new Button("Create New Quiz");
        Label result = new Label();
        VBox root = new VBox(12);
        root.getChildren().addAll(welcome, editQ1, editQ2, editQ3, newQuiz, result);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        //hello world

        Scene scene = new Scene(root, 400, 300);
        editQ1.setOnAction(e ->{
            result.setText("Goes to the edit mode on that quiz");
        });
        editQ2.setOnAction(e ->{
            result.setText("Goes to the edit mode on that quiz");
        });
        editQ3.setOnAction(e ->{
            result.setText("Goes to the edit mode on that quiz");
        });

        newQuiz.setOnAction(e ->{
            result.setText("Creates new quiz in edit mode");
        });
        /**
         *
         * FIGURE OUT HOW TO LINK TO OTHER SCENES
         * stage.setScene(SignUpScene.getScene(stage)); THIS IS TO LINK TO NEW SCENES, edit as u please
         */
        stage.setScene(scene);
        stage.setTitle("Welcome Admin");
        stage.show();

    }
}

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpScene {
    public static Scene getScene(Stage stage) {
        Label title = new Label("Sign Up");
        TextField userName = new TextField();
        userName.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        Button loginBtn = new Button("Back to Log In");
        Button signUpBtn = new Button("Sign Up");

        loginBtn.setOnAction(event -> {
            stage.setScene(LoginScene.getScene(stage));
        });
        VBox s1Root = new VBox(10, title, userName, password, loginBtn, signUpBtn);
        s1Root.setAlignment(Pos.CENTER);
        s1Root.setPadding(new Insets(30));

        return new Scene(s1Root, 400, 300);
    }
}

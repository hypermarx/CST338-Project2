/**
 * Description: The SignUp Scene for users to signup for a new account on our website.
 * April 26, 2026
 * @author Anjelina Jasso
 */
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
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        Label errorLabel = new Label("");
        Button loginBtn = new Button("Back to Log In");
        Button signUpBtn = new Button("Sign Up");

        loginBtn.setOnAction(event -> {
            stage.setScene(LoginScene.getScene(stage));
        });
        signUpBtn.setOnAction(event -> {
            if (!password.getText().equals(confirmPassword.getText())) {
                errorLabel.setVisible(true);
                errorLabel.setText("Passwords do not match!");
            } else if (password.getText().isEmpty() || confirmPassword.getText().isEmpty() || userName.getText().isEmpty()) {
                errorLabel.setVisible(true);
                errorLabel.setText("Please complete all fields.");
            } else if (userName.getText().length() < 5) {
                errorLabel.setVisible(true);
                errorLabel.setText("Username must be at least 5 characters!");
            } else if (password.getText().length() < 8) {
                errorLabel.setVisible(true);
                errorLabel.setText("Password must be at least 8 characters!");
            } else {
                errorLabel.setVisible(false);
                // add username & pass to database
            }
        });
        VBox s1Root = new VBox(10, title, userName, password, confirmPassword, errorLabel, loginBtn, signUpBtn);
        s1Root.setAlignment(Pos.CENTER);
        s1Root.setPadding(new Insets(30));

        return new Scene(s1Root, 400, 300);
    }
}

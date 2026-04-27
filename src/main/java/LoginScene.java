/**
 * Description: The Log in Scene for users to sign in to their pre-existing account on our website.
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


public class LoginScene {
    public static Scene getScene(Stage stage) {
        Label title = new Label("Login");
        Label errorLabel = new Label("");
        TextField userName = new TextField();
        userName.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        Button loginBtn = new Button("Log In");
        Button signUpBtn = new Button ("Sign Up");
        Button createYourOwnQuiz = new Button ("Create your own quiz");

        // create the if statement to check if the login credentials can be found in the db
        // take them to the signup scene if their login info is not found in the db
        signUpBtn.setOnAction(event -> {
            stage.setScene(SignUpScene.getScene(stage));
        });
        loginBtn.setOnAction(event -> {
            if (userName.getText().isEmpty() || password.getText().isEmpty()) {
                errorLabel.setVisible(true);
                errorLabel.setText("Please complete all fields.");
            } else {
                errorLabel.setVisible(false);
                //check if the username & pass is in the database
            }
        });

        //for testing purposes
        createYourOwnQuiz.setOnAction(event -> {
            stage.setScene(createYourOwenQuiz.getScene(stage));
        });
        //get rid of create YourOwnQuiz when home is made
        VBox s1Root = new VBox(10, title, userName, password, errorLabel, loginBtn, signUpBtn, createYourOwnQuiz);
        s1Root.setAlignment(Pos.CENTER);
        s1Root.setPadding(new Insets(30));

        return new Scene(s1Root, 400, 300);
    }
}

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private DatabaseManager db;

    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label wrongDetails;

    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        wrongDetails.setManaged(false);
    }

    @FXML
    private void login(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        passwordField.clear();

        if(db.login(username, password) != -1) {
            SceneManager.getInstance().navigateFresh(SceneType.SELECT_QUIZ);
        }
        else{
            wrongDetails.setManaged(true);
        }
    }

    @FXML
    private void toRegister(){
        SceneManager.getInstance().navigateFresh(SceneType.REGISTRATION);
    }
}

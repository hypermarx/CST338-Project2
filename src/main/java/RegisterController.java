import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.UnaryOperator;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button createUser;
    @FXML
    private Label lengthError;
    @FXML
    private Label confirmError;

    private DatabaseManager db;

    //Unary operator filer that disallows spaces in the input
    UnaryOperator<TextFormatter.Change> noSpace = change -> {
        if(change.getText().contains(" ")){
            return null;
        }
        return change;
    };

    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        lengthError.setManaged(false);
        confirmError.setManaged(false);

        usernameField.setTextFormatter(new TextFormatter<>(noSpace));
        passwordField.setTextFormatter(new TextFormatter<>(noSpace));
        confirmPassword.setTextFormatter(new TextFormatter<>(noSpace));
    }

    @FXML
    private void createUser(ActionEvent actionEvent) {
        String username = usernameField.getText();
        boolean valid = true;
        if(passwordField.getText().equals(confirmPassword.getText())){
            confirmError.setManaged(false);
            confirmError.setVisible(false);
        }
        else{
            confirmError.setManaged(true);
            confirmError.setVisible(true);
            valid = false;
        }

        if(passwordField.getText().length() < 8){
            lengthError.setManaged(true);
            lengthError.setVisible(true);
            valid = false;
        }
        else{
            lengthError.setManaged(false);
            lengthError.setVisible(false);
        }

        if(!valid){
            return;
        }
        String password = passwordField.getText();

        int uid = db.createUser(username, password, false);
        if(uid != -1){
            SessionManager.getInstance().setUserID(uid);
            SceneManager.getInstance().navigateFresh(SceneType.SELECT_QUIZ);
        }
    }

    public void back(ActionEvent actionEvent) {
        SceneManager.getInstance().navigateFresh(SceneType.LOGIN);
    }
}

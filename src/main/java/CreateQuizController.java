import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateQuizController {
    @FXML
    private Button backBtn;
    @FXML
    private VBox questionBox;
    @FXML
    private Button newQuestion;
    @FXML
    private TextField nameEntry;

    private SessionManager manager;
    private DatabaseManager db;

    private int quizID;

    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        manager = SessionManager.getInstance();
        quizID = manager.getQuizID();
        //Add Vboxes for questions that also contain Hboxes for
        //details/adding answers based on questions in the quiz.
    }

    @FXML
    public void saveName(ActionEvent actionEvent) {
        String name = nameEntry.getText();
        db.updateQuiz(name, quizID);
        nameEntry.clear();
    }

    @FXML
    public void addQuestion(ActionEvent actionEvent) {
        //To add functionality
        //Would rebuild the question using details from the database, while ui components
        //are based on the item it returns; This is why updateQuiz returns a quiz, similar
        //functionality would be used here.
    }

    public void back(ActionEvent actionEvent) {
        SceneManager.getInstance().navigateFresh(SceneType.SELECT_QUIZ);
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class SelectQuizController {

    private final SessionManager manager = SessionManager.getInstance();

    @FXML
    private VBox buttonContainer;

    @FXML
    private Button createQuizBtn;

    int uid = manager.getUserID();

    @FXML
    public void initialize(){
        DatabaseManager db = DatabaseManager.getInstance();

        //Get ids of quizzes belong to the user
        //Populate VBox
        ArrayList<Integer> quizIDs = db.getQuizzesByUID(uid);
        for(Integer id : quizIDs){
            Quiz quiz = db.getQuizbyID(id);
            Button btn = new Button();

            //Set visual attributes of button
            //TODO: Move most of these to a CSS file
            btn.setText(quiz.getSubject());
            btn.setFont(Font.font("Trajan Pro", 29));
            btn.setPrefHeight(66);
            btn.setPrefWidth(447);

            //Set behavior of button
            btn.setOnAction(e -> {
                manager.setQuizID(id);
                SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ);
            });

            //Add button to VBox
            buttonContainer.getChildren().add(0, btn);
        }
    }

    public void createQuiz(ActionEvent actionEvent) {
        DatabaseManager db = DatabaseManager.getInstance();
        Quiz quiz = new Quiz(uid);
        quiz.setSubject("Untitled quiz");
        int quizID = db.addQuiz(quiz);
        if(quizID != -1) {
            manager.setQuizID(quizID);
            SceneManager.getInstance().navigateTo(SceneType.CREATE_QUIZ);
        }
    }
}

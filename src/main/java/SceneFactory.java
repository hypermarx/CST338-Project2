import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage){
        return switch(type){
            case LOGIN -> loadScene("/resources/Login.fxml");
            case REGISTRATION -> loadScene("/resources/register.fxml");
            case TAKE_QUIZ -> loadScene("/resources/takeQuiz.fxml");
            case SELECT_QUIZ -> loadScene("/resources/selectQuiz.fxml");
        };
    }

    private static Scene loadScene(String fxmlPath){
        URL url = SceneFactory.class.getResource(fxmlPath);
        if(url == null){
            throw new IllegalArgumentException("FXML not found " + fxmlPath);
        }
        try{
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            return new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load" + e);
        }
    }
}



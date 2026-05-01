import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.EnumMap;
import java.util.Map;

public class SceneManager {

    private static SceneManager instance;

    private final Stage stage;

    private final Map<SceneFactory.SceneType, Scene> cache = new EnumMap<>(SceneFactory.SceneType.class);

    private SceneManager(Stage stage){
        this.stage = stage;
    }

    public static void init(Stage stage){
        if(instance == null){
            instance = new SceneManager(stage);
        }
    }

    public static SceneManager getInstance(){
        if(instance == null){
            throw new IllegalStateException("SceneManager not initialized");
        }
        return instance;
    }

    public void navigateTo(SceneFactory.SceneType type){
        if(!type.isStateless()) {
            Scene scene = cache.computeIfAbsent(type,
                    t -> SceneFactory.create(type, stage));
            stage.setScene(scene);
        }
        else{
            stage.setScene(SceneFactory.create(type, stage));
        }
    }
}

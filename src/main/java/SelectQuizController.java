import javafx.fxml.FXML;
import javafx.scene.Scene;

public class SelectQuizController {
    int uid = SessionManager.getInstance().getUserID();
    //Add items like @FXML private Button AAAAA; (matches fx:id = "AAAAA")

    @FXML
    public void initialize(){
        //can put things here that happen after initialization
    }

    /* ## Event Handler Methods
    Any `onAction="#methodName"` in FXML must have a corresponding method in the
    controller.
    **FXML:**
    ```xml
    <Button text="Save" onAction="#handleSave"/>
    <Button text="Cancel" onAction="#handleCancel"/>

    Java:
    @FXML
    private void handleSave(){
        //do stuff
    }
     */
}

package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class progressInfoFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text infoText;

    @FXML
    private Button returnBack;

    @FXML
    void clickToReturn(ActionEvent event) throws IOException {
        Stage stage = (Stage) returnBack.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 561, 695);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
        assert infoText != null : "fx:id=\"infoText\" was not injected: check your FXML file 'progressInfoForm.fxml'.";
        assert returnBack != null : "fx:id=\"returnBack\" was not injected: check your FXML file 'progressInfoForm.fxml'.";

    }

}

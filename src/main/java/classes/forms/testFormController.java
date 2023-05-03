package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.comm.GeneralComm;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class testFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label question;

    @FXML
    void initialize() {
        assert question != null : "fx:id=\"question\" was not injected: check your FXML file 'testForm.fxml'.";
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "RandomGeneration";
            communication.writeLine(request);
            String response = communication.readLine();
            System.out.println(response);
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }

    }

}

package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.comm.GeneralComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteAcc;

    @FXML
    private Button generalTests;

    @FXML
    private Button leaveButton;

    @FXML
    private Text login;
    @FXML
    private Button myProgress;

    @FXML
    private Button randomTest;


    @FXML
    public void getData(String login)
    {
        this.login.setText(login);
    }

    @FXML
    void clickToLeave(ActionEvent event) throws IOException {
        Stage stage = (Stage) leaveButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("logForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 543);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
        assert deleteAcc != null : "fx:id=\"deleteAcc\" was not injected: check your FXML file 'mainMenuForm.fxml'.";
        assert generalTests != null : "fx:id=\"generalTests\" was not injected: check your FXML file 'mainMenuForm.fxml'.";
        assert leaveButton != null : "fx:id=\"leaveButton\" was not injected: check your FXML file 'mainMenuForm.fxml'.";
        assert myProgress != null : "fx:id=\"myProgress\" was not injected: check your FXML file 'mainMenuForm.fxml'.";
        assert randomTest != null : "fx:id=\"randomTest\" was not injected: check your FXML file 'mainMenuForm.fxml'.";

    }

}

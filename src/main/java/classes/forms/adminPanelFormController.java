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

public class adminPanelFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text login;
    @FXML
    private Button addWord;
    @FXML
    private String ID;

    @FXML
    private Button quit;

    @FXML
    private String getLogin()
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "GetLogin," + ID;
            communication.writeLine(request);

            return communication.readLine();
        }
        catch (IOException e) {
            return "Нет соединения с сервером!";
        }
    }
    @FXML
    public void setData(String ID) {
        this.ID = ID;
        login.setText("Hi, " + getLogin());
    }
    @FXML
    void addWordToDic(ActionEvent event) {

    }

    @FXML
    void clickToLeave(ActionEvent event) throws IOException {
        Stage stage = (Stage) quit.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("logForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 543);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
        assert addWord != null : "fx:id=\"addWord\" was not injected: check your FXML file 'adminPanelForm.fxml'.";
        assert quit != null : "fx:id=\"quit\" was not injected: check your FXML file 'adminPanelForm.fxml'.";
    }
}

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
    private int MAX_WORDS = 1025;

    @FXML
    private String ID = "";
    @FXML
    public void setID(String ID) {this.ID = ID;}
    @FXML
    void clickToReturn(ActionEvent event) throws IOException {
        Stage stage = (Stage) returnBack.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 561, 695);
        MainMenuController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setData(this.ID);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private String progressInfo()
    {
        String response = "";
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "getProgress," + ID;
            communication.writeLine(request);
            response = communication.readLine();
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
        return response;
    }
    @FXML
    public void setData(String operation, int correctAnswers, int maxAnswers)
    {
        switch (operation){
            case "Test" -> infoText.setText("Всего правильных ответов - " + correctAnswers + "/" + maxAnswers);
            case "myProgress" -> infoText.setText("Всего правильных ответов - " + progressInfo() + "/" + MAX_WORDS);
        }
    }

    @FXML
    void initialize() {
        assert infoText != null : "fx:id=\"infoText\" was not injected: check your FXML file 'progressInfoForm.fxml'.";
        assert returnBack != null : "fx:id=\"returnBack\" was not injected: check your FXML file 'progressInfoForm.fxml'.";

    }

}

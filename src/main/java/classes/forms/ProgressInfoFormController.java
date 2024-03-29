package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.comm.GeneralComm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
public class ProgressInfoFormController {

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

    private String progressInfo(String request)
    {
        String response = "";
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            communication.writeLine(request);
            response = communication.readLine();
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
        return response;
    }

    /**
     * Отображение результата, взависимости от запроса
     * @param operation
     * @param correctAnswers
     * @param maxAnswers
     */
    @FXML
    public void setData(String operation, int correctAnswers, int maxAnswers)
    {
        switch (operation){
            case "Test" -> infoText.setText("Всего правильных ответов - " + correctAnswers + "/" + maxAnswers);
            case "myProgress" -> infoText.setText("Всего правильных ответов - " + progressInfo("getProgress," + ID) + "/" + MAX_WORDS);
            case "AverageProgress" -> infoText.setText("Средний результат всех участников - " + progressInfo("checkAverageProgress") + "/" + progressInfo("getQuantityWords,engruswords"));
        }
    }

    @FXML
    void initialize() {
        assert infoText != null : "fx:id=\"infoText\" was not injected: check your FXML file 'progressInfoForm.fxml'.";
        assert returnBack != null : "fx:id=\"returnBack\" was not injected: check your FXML file 'progressInfoForm.fxml'.";
    }

}

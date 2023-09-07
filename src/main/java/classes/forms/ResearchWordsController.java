package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import classes.comm.GeneralComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResearchWordsController {

    @FXML
    private Button continueWords;

    @FXML
    private Text eng;

    @FXML
    private Button leave;

    @FXML
    private Label counter;
    @FXML
    private Text rus;

    @FXML
    private Text transcription;

    @FXML
    private ArrayList<String> words = new ArrayList<>();

    private String ID = "";
    public void setData(String ID) {this.ID = ID;}
    private int count = 0, counterStep = 1;
    final int ENG_TRANSCRIPTION_RUS = 3;
    @FXML
    void clickToContinue(ActionEvent event) {
        if (counterStep == (words.size() / ENG_TRANSCRIPTION_RUS)+1)
            continueWords.setDisable(true);
        else
            arrangeAns();
    }

    /**
     * Вернуться в главное меню
     * @param event
     * @throws IOException
     */
    @FXML
    void clickToLeave(ActionEvent event) throws IOException {
        Stage stage = (Stage) leave.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 561, 695);
        MainMenuController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setData(this.ID);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    private String request(String request)
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            communication.writeLine(request);
            return communication.readLine();
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
        return "-1";
    }

    /**
     * Заполнение информации об слове (английское - транскрипция - русское)
     */
    private void arrangeAns()
    {
        counter.setText(counterStep + "/" + (words.size() / ENG_TRANSCRIPTION_RUS));
        eng.setText(words.get(count++));
        transcription.setText(words.get(count++));
        rus.setText(words.get(count++));
        counterStep++;
    }

    private void processingStr(String str)
    {
        Collections.addAll(words, str.split("!"));
        arrangeAns();
    }
    @FXML
    void initialize() {
        assert continueWords != null : "fx:id=\"continueWords\" was not injected: check your FXML file 'researchForm.fxml'.";
        assert eng != null : "fx:id=\"eng\" was not injected: check your FXML file 'researchForm.fxml'.";
        assert leave != null : "fx:id=\"leave\" was not injected: check your FXML file 'researchForm.fxml'.";
        assert rus != null : "fx:id=\"rus\" was not injected: check your FXML file 'researchForm.fxml'.";
        assert transcription != null : "fx:id=\"transcription\" was not injected: check your FXML file 'researchForm.fxml'.";
        processingStr(request("GetWordsTable"));
    }

}

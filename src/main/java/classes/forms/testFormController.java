package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import classes.comm.GeneralComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class testFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button firstAns;

    @FXML
    private Button fourAns;

    @FXML
    private Button quit;

    @FXML
    private Label questionNumLabel;
    @FXML
    private Text question;

    @FXML
    private Button secAns;

    @FXML
    private final int MAX_QUESTIONS = 20;
    @FXML
    private int questionWord = 0;
    @FXML
    private int randomWord = MAX_QUESTIONS-1;
    @FXML
    private ArrayList<String> engWords = new ArrayList<>();
    @FXML
    private ArrayList<String> rusWords = new ArrayList<>();
    @FXML
    private int currentQuestionNum = 0;
    @FXML
    private Button thirdAns;

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) quit.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 561, 695);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void arrangeEngWords()
    {
        int rand = (int)(Math.random() * 4) + 1;

        question.setText("How does word '" + engWords.get(questionWord) + "' translate into Russian?");

        firstAns.setText(rusWords.get(randomWord++));
        secAns.setText(rusWords.get(randomWord++));
        thirdAns.setText(rusWords.get(randomWord++));
        fourAns.setText(rusWords.get(randomWord++));

        randomWord--;
        switch (rand)
        {
            case 1:
                firstAns.setText(rusWords.get(questionWord++));
                break;
            case 2:
                secAns.setText(rusWords.get(questionWord++));
                break;
            case 3:
                thirdAns.setText(rusWords.get(questionWord++));
                break;
            case 4:
                fourAns.setText(rusWords.get(questionWord++));
                break;
        }
    }
    @FXML
    private void arrangeRusWords()
    {
        int rand = (int)(Math.random() * 4) + 1;

        question.setText("Как переводится слово '" + rusWords.get(questionWord) + "' на английский язык?");

        firstAns.setText(engWords.get(randomWord++));
        secAns.setText(engWords.get(randomWord++));
        thirdAns.setText(engWords.get(randomWord++));
        fourAns.setText(engWords.get(randomWord++));

        randomWord--;
        switch (rand)
        {
            case 1:
                firstAns.setText(engWords.get(questionWord++));
                break;
            case 2:
                secAns.setText(engWords.get(questionWord++));
                break;
            case 3:
                thirdAns.setText(engWords.get(questionWord++));
                break;
            case 4:
                fourAns.setText(engWords.get(questionWord++));
                break;
        }
    }
    @FXML
    private void arrangeAns()
    {
        questionNumLabel.setText(String.valueOf(currentQuestionNum+1) + "/" + String.valueOf(MAX_QUESTIONS));
        int rand = (int)(Math.random() * 2) + 1;
        if (rand == 1)
            arrangeEngWords();
        else
            arrangeRusWords();
        currentQuestionNum++;
    }
    @FXML
    void checkAnswers(MouseEvent event) throws IOException {
        if (currentQuestionNum == MAX_QUESTIONS)
        {
            //(Stage) quit.getScene().getWindow();
            Stage stage = new Stage();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("progressInfoForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 249, 320);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        else
            arrangeAns();
    }

    @FXML
    private void processResponse(String response)
    {
        String strings[] = response.split("!");
        for (int i = 0; i < strings.length;i++) {
            if ((i % 2 == 0)) {
                engWords.add(strings[i]);
            } else {
                rusWords.add(strings[i]);
            }
        }
        arrangeAns();
    }
    @FXML
    void initialize() {
        assert firstAns != null : "fx:id=\"firstAns\" was not injected: check your FXML file 'testForm.fxml'.";
        assert fourAns != null : "fx:id=\"fourAns\" was not injected: check your FXML file 'testForm.fxml'.";
        assert quit != null : "fx:id=\"qu\" was not injected: check your FXML file 'testForm.fxml'.";
        assert question != null : "fx:id=\"question\" was not injected: check your FXML file 'testForm.fxml'.";
        assert secAns != null : "fx:id=\"secAns\" was not injected: check your FXML file 'testForm.fxml'.";
        assert thirdAns != null : "fx:id=\"thirdAns\" was not injected: check your FXML file 'testForm.fxml'.";
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "RandomGeneration";
            communication.writeLine(request);
            String response = communication.readLine();
            processResponse(response);
            System.out.println(response);
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
    }

}

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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestFormController {

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
    private int MAX_QUESTIONS = 20;
    @FXML
    private int questionWord = 0;
    @FXML
    private int randomWord = 0;
    @FXML
    private final ArrayList<String> words = new ArrayList<>();
    private final ArrayList<String> engWords = new ArrayList<>();
    @FXML
    private final ArrayList<String> rusWords = new ArrayList<>();
    @FXML
    private int currentQuestionNum = 0;
    @FXML
    private int correctAnswers = 0;
    @FXML
    private String correctAnswersWords = "";
    @FXML
    private String incorrectAnswersWords = "";
    @FXML
    private String response = "";
    @FXML
    private Button thirdAns;
    @FXML
    private String ID = "";
    private String method = "";
    @FXML
    public void setID(String ID) {this.ID = ID;}
    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) quit.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 561, 695);
        MainMenuController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setData(this.ID);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private int[] getRandomArray(int questionWord)
    {
        final int QUANTITY_ANSWERS = 4;
        String[] randSrt = {"", "", "", ""};
        int[] randWords = new int[QUANTITY_ANSWERS];
        int i = 0;

        while (i < QUANTITY_ANSWERS) {
            int rand = (int) (Math.random() * (engWords.size()-1)) + 1;
            if (!randSrt[i].contains(String.valueOf(rand)) && rand != questionWord)
            {
                randWords[i] = rand;
                randSrt[i] = String.valueOf(rand);
                i++;
            }
        }
        return randWords;
    }
    @FXML
    private void arrangeEngWords() {
        int rand = (int) (Math.random() * 4) + 1;
        int[] randWords = getRandomArray(questionWord);

        question.setText("How does word '" + engWords.get(questionWord) + "' translate into Russian?");

        firstAns.setText(rusWords.get(randWords[0]));
        secAns.setText(rusWords.get(randWords[1]));
        thirdAns.setText(rusWords.get(randWords[2]));
        fourAns.setText(rusWords.get(randWords[3]));

        randomWord--;
        switch (rand) {
            case 1 -> firstAns.setText(rusWords.get(questionWord++));
            case 2 -> secAns.setText(rusWords.get(questionWord++));
            case 3 -> thirdAns.setText(rusWords.get(questionWord++));
            case 4 -> fourAns.setText(rusWords.get(questionWord++));
        }
    }

    @FXML
    private void arrangeRusWords() {
        int rand = (int) (Math.random() * 4) + 1;
        int[] randWords = getRandomArray(questionWord);

        question.setText("Как переводится слово '" + rusWords.get(questionWord) + "' на английский язык?");

        firstAns.setText(engWords.get(randWords[0]));
        secAns.setText(engWords.get(randWords[1]));
        thirdAns.setText(engWords.get(randWords[2]));
        fourAns.setText(engWords.get(randWords[3]));

        randomWord--;
        switch (rand) {
            case 1 -> firstAns.setText(engWords.get(questionWord++));
            case 2 -> secAns.setText(engWords.get(questionWord++));
            case 3 -> thirdAns.setText(engWords.get(questionWord++));
            case 4 -> fourAns.setText(engWords.get(questionWord++));
        }
    }

    private int j = 0;
    private void arrangeAdminTestAns()
    {
        if (words.get(j + 5).equals("Английский"))
            question.setText("How does word '" + words.get(j++) + "' translate into Russian?");
        else
            question.setText("Как переводится слово '" + words.get(j++) + "' на английский язык?");
        firstAns.setText(words.get(j++));
        secAns.setText(words.get(j++));
        thirdAns.setText(words.get(j++));
        fourAns.setText(words.get(j++));
        j++;
    }
    @FXML
    private void arrangeAns() {
        questionNumLabel.setText(currentQuestionNum + 1 + "/" + MAX_QUESTIONS);
        if (method.equals("AdminTest"))
            arrangeAdminTestAns();
        else
        {
            int rand = (int) (Math.random() * 2) + 1;
            if (rand == 1)
                arrangeEngWords();
            else
                arrangeRusWords();
        }
        currentQuestionNum++;
    }

    @FXML
    private void checkCorrectAnswers(MouseEvent event)
    {
        String chosenButtWord = ((Button)event.getSource()).getText();

        if ((engWords.indexOf(chosenButtWord) == currentQuestionNum-1) || (rusWords.indexOf(chosenButtWord) == currentQuestionNum-1)) {
            correctAnswers++;
            correctAnswersWords += engWords.get(currentQuestionNum-1) + "!";
        }
        else
            incorrectAnswersWords += engWords.get(currentQuestionNum-1) + "!";
    }

    @FXML
    void checkAnswers(MouseEvent event) throws IOException {
        if (currentQuestionNum == MAX_QUESTIONS)
        {
            checkCorrectAnswers(event);
            System.out.println(correctAnswers);
            System.out.println(correctAnswersWords);

            requestToServer("AddWordsProgress," + this.ID + "," + this.correctAnswersWords + "," + this.incorrectAnswersWords);

            firstAns.setDisable(true);
            secAns.setDisable(true);
            thirdAns.setDisable(true);
            fourAns.setDisable(true);

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("progressInfoForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 249, 320);
            ProgressInfoFormController controllerEditBook = fxmlLoader.getController();
            controllerEditBook.setData("Test",correctAnswers,MAX_QUESTIONS);
            controllerEditBook.setID(this.ID);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            checkCorrectAnswers(event);
            arrangeAns();
        }

    }

    @FXML
    private void processResponse(String response)
    {
        String[] strings = response.split("!");
        if (!method.equals("AdminTest"))
        {
            for (int i = 0; i < strings.length;i++) {
                if ((i % 2 == 0)) {
                    engWords.add(strings[i]);
                } else {
                    rusWords.add(strings[i]);
                }
            }
            this.response = "";
        }
        else
        {
            Collections.addAll(words,strings);
            words.removeAll(Collections.singleton(""));
        }

        arrangeAns();
    }

    private void requestToServer(String request)
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            communication.writeLine(request);
            response = communication.readLine();
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
    }

    public void generationMethods(String method, final int MAX_QUESTIONS)
    {
        this.MAX_QUESTIONS = MAX_QUESTIONS;
        requestToServer(method);
        this.method = method.substring(0, 9);
        processResponse(response);
    }
    @FXML
    void initialize() {
        assert firstAns != null : "fx:id=\"firstAns\" was not injected: check your FXML file 'testForm.fxml'.";
        assert fourAns != null : "fx:id=\"fourAns\" was not injected: check your FXML file 'testForm.fxml'.";
        assert quit != null : "fx:id=\"qu\" was not injected: check your FXML file 'testForm.fxml'.";
        assert question != null : "fx:id=\"question\" was not injected: check your FXML file 'testForm.fxml'.";
        assert secAns != null : "fx:id=\"secAns\" was not injected: check your FXML file 'testForm.fxml'.";
        assert thirdAns != null : "fx:id=\"thirdAns\" was not injected: check your FXML file 'testForm.fxml'.";
    }
}

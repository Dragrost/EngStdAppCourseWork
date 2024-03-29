package classes.forms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import classes.comm.GeneralComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestFormController {

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
    private Button continueTest;

    @FXML
    private AnchorPane errorPane;

    @FXML
    private AnchorPane errorPane1;

    @FXML
    private Text errorWordTest;
    @FXML
    private int MAX_QUESTIONS = 20;
    @FXML
    private int questionWord = 0;
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

    /**
     * Возврат в главное меню
     * @param event
     * @throws IOException
     */
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

    /**
     * Получение ID рандомных слов
     * @param questionWord
     * @return
     */
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

    /**
     * Расстановка вариантов ответа и слово-вопроса
     */
    @FXML
    private void arrangeEngWords() {
        int rand = (int) (Math.random() * 4) + 1;
        int[] randWords = getRandomArray(questionWord);

        question.setText("How does word '" + engWords.get(questionWord) + "' translate into Russian?");

        firstAns.setText(rusWords.get(randWords[0]));
        secAns.setText(rusWords.get(randWords[1]));
        thirdAns.setText(rusWords.get(randWords[2]));
        fourAns.setText(rusWords.get(randWords[3]));

        switch (rand) {
            case 1 -> firstAns.setText(rusWords.get(questionWord++));
            case 2 -> secAns.setText(rusWords.get(questionWord++));
            case 3 -> thirdAns.setText(rusWords.get(questionWord++));
            case 4 -> fourAns.setText(rusWords.get(questionWord++));
        }
    }

    /**
     * Расстановка вариантов ответа и слово-вопроса
     */
    @FXML
    private void arrangeRusWords() {
        int rand = (int) (Math.random() * 4) + 1;
        int[] randWords = getRandomArray(questionWord);

        question.setText("Как переводится слово '" + rusWords.get(questionWord) + "' на английский язык?");

        firstAns.setText(engWords.get(randWords[0]));
        secAns.setText(engWords.get(randWords[1]));
        thirdAns.setText(engWords.get(randWords[2]));
        fourAns.setText(engWords.get(randWords[3]));

        switch (rand) {
            case 1 -> firstAns.setText(engWords.get(questionWord++));
            case 2 -> secAns.setText(engWords.get(questionWord++));
            case 3 -> thirdAns.setText(engWords.get(questionWord++));
            case 4 -> fourAns.setText(engWords.get(questionWord++));
        }
    }

    /**
     * Расстановка вариантов ответа и слово-вопроса
     */
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

    /**
     * Проверка выбора тестов с расстановкой вопросов
     */
    @FXML
    private void arrangeAns() {
        if (currentQuestionNum == MAX_QUESTIONS)
            currentQuestionNum--;
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
    private void setMode(boolean visible)
    {
        errorPane.setVisible(visible);
        errorPane1.setVisible(visible);
        errorWordTest.setVisible(visible);
        continueTest.setVisible(visible);

        firstAns.setDisable(visible);
        secAns.setDisable(visible);
        thirdAns.setDisable(visible);
        fourAns.setDisable(visible);
    }

    /**
     * Проверка правильности ответа
     * @param event
     */
    @FXML
    private void checkCorrectAnswersAdminTest(MouseEvent event)
    {
        String chosenButtWord = ((Button)event.getSource()).getText();
        requestToServer("checkWordsID," + words.get(j-6) + "," + chosenButtWord);

        if (response.equals("allGood")){
            correctAnswers++;
            correctAnswersWords += words.get(j-6) + "!";
            arrangeAns();
        }
        else
        {
            requestToServer("getWordFromWord," + words.get(j-6));
            String[] enRuWord = response.split("!");
            incorrectAnswersWords += words.get(j-6) + "!";
            setMode(true);
            errorWordTest.setText("Неправильно: " + enRuWord[0] + " - " + enRuWord[1] + '!');
        }

    }

    /**
     * Проверка правильности ответа
     * @param event
     */
    @FXML
    private void checkCorrectAnswersRandom(MouseEvent event)
    {
        String chosenButtWord = ((Button)event.getSource()).getText();

        if ((engWords.indexOf(chosenButtWord) == currentQuestionNum-1) || (rusWords.indexOf(chosenButtWord) == currentQuestionNum-1)) {
            correctAnswers++;
            correctAnswersWords += engWords.get(currentQuestionNum-1) + "!";
            arrangeAns();
        }
        else
        {
            incorrectAnswersWords += engWords.get(currentQuestionNum-1) + "!";
            setMode(true);
            errorWordTest.setText("Неправильно: " + engWords.get(currentQuestionNum-1) + " - " + rusWords.get(currentQuestionNum-1) + '!');
        }

    }

    private void checkTest(MouseEvent event)
    {
        if (method.equals("AdminTest"))
            checkCorrectAnswersAdminTest(event);
        else
            checkCorrectAnswersRandom(event);
    }

    private void openNewForm() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("progressInfoForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 249, 219);
        ProgressInfoFormController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setData("Test",correctAnswers,MAX_QUESTIONS);
        controllerEditBook.setID(this.ID);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Проверка ответов
     * @param event
     * @throws IOException
     */
    @FXML
    void checkAnswers(MouseEvent event) throws IOException {
        setMode(false);
        if (currentQuestionNum == MAX_QUESTIONS)
        {
            checkTest(event);
            System.out.println(correctAnswers);
            System.out.println(correctAnswersWords);

            requestToServer("AddWordsProgress," + this.ID + "," + this.correctAnswersWords + "," + this.incorrectAnswersWords);

            firstAns.setDisable(true);
            secAns.setDisable(true);
            thirdAns.setDisable(true);
            fourAns.setDisable(true);

            openNewForm();
        }
        else if (((Button)event.getSource()).getId().equals("continueTest")){
            arrangeAns();
        }
        else
            checkTest(event);
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
        }
        else
        {
            Collections.addAll(words,strings);
            words.removeAll(Collections.singleton(""));
        }
        this.response = "";
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

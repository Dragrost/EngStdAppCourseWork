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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button allWords;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField testID;

    @FXML
    private Label testIDLabel;
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
    private String ID = "";
    @FXML
    private Button randomTest;

    private void openTestForm(String test) {
        int MAX_QUESTIONS;

        switch (test){
            case "AllQuestions" -> MAX_QUESTIONS = Integer.parseInt(sendRequest("getQuantityWords,engruswords"));
            case "RandomGeneration" -> MAX_QUESTIONS = 20;
            default -> MAX_QUESTIONS = Integer.parseInt(sendRequest("getQuantityWordsTest," + testID.getText()));
        }

        try{
            Stage stage = (Stage) randomTest.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("testForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 771, 538);
            TestFormController controllerEditBook = fxmlLoader.getController();
            controllerEditBook.setID(this.ID);
            controllerEditBook.generationMethods(test,MAX_QUESTIONS);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    private String sendRequest(String request)
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            communication.writeLine(request);
            return communication.readLine();
        }
        catch (IOException e) {
            return "error";
        }
    }
    @FXML
    public void setData(String ID)
    {
        this.ID = ID;
        this.login.setText(sendRequest("GetLogin," + this.ID));
    }

    @FXML
    void adminTests(ActionEvent event) {
        if (testID.getText().equals(""))
        {
            testIDLabel.setText("Введите номер теста [1 - " + Integer.parseInt(sendRequest("getQuantityWords,mytests")) + "]");
            testID.setVisible(true);
            testIDLabel.setVisible(true);
        }
        else
        {
            if (Integer.parseInt(testID.getText()) >= 1 && Integer.parseInt(testID.getText()) <= Integer.parseInt(sendRequest("getQuantityWords,mytests")))
            {
                String request = "AdminTest," + testID.getText();
                openTestForm(request);
            }
        }
    }
    @FXML
    void deleteAcc(ActionEvent event) throws IOException {
        sendRequest("Delete," + this.ID);

        Stage stage = (Stage) leaveButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("logForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 543);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void checkResult(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("progressInfoForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 249, 320);
        ProgressInfoFormController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setID(this.ID);
        controllerEditBook.setData("myProgress",0,0);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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
    void getRandTest(ActionEvent event){
        openTestForm("RandomGeneration");
    }

    @FXML
    void getAllQuestions(ActionEvent event){
        openTestForm("AllQuestions");
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

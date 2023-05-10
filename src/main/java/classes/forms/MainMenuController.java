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
    private Button allWords;
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
    private String ID = "";
    @FXML
    private Button randomTest;

    @FXML
    private void getLogin()
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "GetLogin," + this.ID;
            communication.writeLine(request);

            this.login.setText(communication.readLine());
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
    }
    @FXML
    public void setData(String ID)
    {
        this.ID = ID;
        getLogin();
    }

    @FXML
    void deleteAcc(ActionEvent event) throws IOException {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "Delete," + this.ID;
            communication.writeLine(request);

            String response = communication.readLine();
            Stage stage = (Stage) leaveButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("logForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 516, 543);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
    }
    @FXML
    void checkResult(ActionEvent event) throws IOException {
        Stage stage = (Stage) myProgress.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("progressInfoForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 249, 320);
        progressInfoFormController controllerEditBook = fxmlLoader.getController();
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
    void genRandTest(ActionEvent event) throws IOException {
        final int MAX_QUESTIONS = 20;
        Stage stage = (Stage) randomTest.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("testForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 771, 538);
        testFormController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setID(this.ID);
        controllerEditBook.generationMethods("RandomGeneration",MAX_QUESTIONS);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void getAllQuestions(ActionEvent event) throws IOException {
        final int MAX_QUESTIONS = 1025;
        Stage stage = (Stage) randomTest.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("testForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 771, 538);
        testFormController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setID(this.ID);
        controllerEditBook.generationMethods("AllQuestions",MAX_QUESTIONS);
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

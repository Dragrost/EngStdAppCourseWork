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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Text errorInput;

    @FXML
    private URL location;

    @FXML
    private Button ChangeFormButton;

    @FXML
    private TextField login;

    @FXML
    private ImageView passSee;

    @FXML
    private TextField passSeeTextField;
    @FXML
    private PasswordField password;
    @FXML
    private Button regButton;

    @FXML
    private boolean isAdmin() throws IOException
    {
        GeneralComm communication = new GeneralComm("127.0.0.1", 8000);
        String request = "GetStatus," + getID();
        communication.writeLine(request);
        return (communication.readLine().equals("ADMIN")) ? true : false;
    }
    @FXML
    private String getID()
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "GetID," + login.getText();
            communication.writeLine(request);

            return communication.readLine();
        }
        catch (IOException e) {
            return "Нет соединения с сервером!";
        }

    }
    @FXML
    void ClickToChangeForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ChangeFormButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("registerForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 543);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changePasswordSee()
    {
        if (passSeeTextField.getText().equals(""))
        {
            passSeeTextField.setText(password.getText());
            passSeeTextField.toFront();
            passSee.toFront();
            password.setPromptText("");
            password.setText("");
        }
        else
        {
            password.setText(passSeeTextField.getText()); // url("file:/C:/Davydov_Univer/Java/Other/EngStdAppCourseWork/src/main/resources/Images/buttondesign.png")
            password.setPromptText("Пароль");
            passSeeTextField.toBack();
            passSeeTextField.setText("");
        }

    }

    @FXML
    void clickReg(ActionEvent event) throws IOException {
        if (login.getText().equals(""))
            errorInput.setText("Заполните поле 'Логин!'");
        else if (password.getText().equals(""))
            errorInput.setText("Заполните поле 'Пароль!'");
        else {
            try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
            {
                String request = "Login," + login.getText() +  "," + password.getText();
                communication.writeLine(request);

                String response = communication.readLine();
                if (response.equals("errorKey") || response.equals("wrongPassword"))
                    errorInput.setText("Неверный логин / пароль!");
                else {
                    Stage stage = (Stage) ChangeFormButton.getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader;
                    Scene scene;

                    if (isAdmin())
                    {
                        fxmlLoader = new FXMLLoader(StarterForm.class.getResource("adminPanelForm.fxml"));
                        scene = new Scene(fxmlLoader.load(), 567, 565);
                        adminPanelFormController controllerEditBook = fxmlLoader.getController();
                        controllerEditBook.setData(getID());

                    }
                    else {
                        fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
                        scene = new Scene(fxmlLoader.load(), 561, 695);
                        MainMenuController controllerEditBook = fxmlLoader.getController();
                        controllerEditBook.setData(getID());
                    }
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            catch (IOException e) {
                errorInput.setText("Нет соединения с сервером!");
            }
        }
    }

    @FXML
    void initialize() {
        assert ChangeFormButton != null : "fx:id=\"ChangeFormButton\" was not injected: check your FXML file 'logForm.fxml'.";
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'logForm.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'logForm.fxml'.";
        assert regButton != null : "fx:id=\"regButton\" was not injected: check your FXML file 'logForm.fxml'.";
    }
}

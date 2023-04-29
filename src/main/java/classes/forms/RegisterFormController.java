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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ChangeFormButton;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Text errorInput;
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button regButton;

    @FXML
    void ClickToChangeForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ChangeFormButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("logForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 543);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickReg(ActionEvent event) throws IOException {
        if (login.getText().equals(""))
            errorInput.setText("Заполните поле 'Логин!'");
        else if (password.getText().equals(""))
            errorInput.setText("Заполните поле 'Пароль!'");
        else if (confirmPassword.getText().equals(""))
            errorInput.setText("Заполните поле 'Повторите пароль!'");
        else if (!password.getText().equals(confirmPassword.getText()))
            errorInput.setText("Пароли не совпадают!");
        else
        {
            try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
            {
                String request = "Registration," + login.getText() +  "," + password.getText();
                //System.out.println("Request " + request);
                communication.writeLine(request);

                String response = communication.readLine();
                if (response.equals("errorKey"))
                    errorInput.setText("Данный пользователь уже существует!");
                else {
                    Stage stage = (Stage) regButton.getScene().getWindow();
                    stage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 561, 695);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            catch (IOException e) {
                errorInput.setText("Нет соединения с сервером!");
            }
            catch (RuntimeException exx){
                errorInput.setText("Такой пользователь уже существует!");
            }
        }
    }

    @FXML
    void initialize() {
        assert ChangeFormButton != null : "fx:id=\"ChangeFormButton\" was not injected: check your FXML file 'registerForm.fxml'.";
        assert confirmPassword != null : "fx:id=\"confirmPassword\" was not injected: check your FXML file 'registerForm.fxml'.";
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'registerForm.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'registerForm.fxml'.";
        assert regButton != null : "fx:id=\"regButton\" was not injected: check your FXML file 'registerForm.fxml'.";
    }

}

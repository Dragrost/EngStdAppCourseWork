package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

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

    /**
     * Перейти к форме входа
     * @param event
     * @throws IOException
     */
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

    /**
     * Регистрация пользователя
     * @param event
     * @throws IOException
     */
    @FXML
    void clickReg(ActionEvent event) throws IOException {
        if (login.getText().equals("") || password.getText().equals("") || confirmPassword.getText().equals("")) {
            errorInput.setText("Заполните все поля!");
            return;
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            errorInput.setText("Пароли не совпадают!");
            return;
        }
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            UUID id = UUID.randomUUID();
            String request = "Registration," + id + "," + login.getText() +  "," + password.getText();
            communication.writeLine(request);

            String response = communication.readLine();
            if (response.equals("errorKey"))
                errorInput.setText("Данный пользователь уже существует!");
            else {
                Stage stage = (Stage) regButton.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 561, 695);
                MainMenuController controllerEditBook = fxmlLoader.getController();
                controllerEditBook.setData(String.valueOf(id));
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (IOException e) {
            errorInput.setText("Нет соединения с сервером!");
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

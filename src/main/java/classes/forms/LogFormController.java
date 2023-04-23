package classes.forms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
            password.setText(passSeeTextField.getText());
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
            Stage stage = (Stage) ChangeFormButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("mainMenuForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 561, 695);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
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

package classes.forms;

import java.io.IOException;

import classes.comm.GeneralComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InputDataController {
    @FXML
    private Label countElemsLabel;

    @FXML
    private Label currentStep;

    @FXML
    private RadioButton eng;

    @FXML
    private Text errorInfo;

    @FXML
    private TextField info1;

    @FXML
    private TextField info2;

    @FXML
    private TextField info3;

    @FXML
    private TextField info4;

    @FXML
    private Label infoLabel;

    @FXML
    private Button inputData;

    @FXML
    private TextField quantityElems;

    @FXML
    private TextField questionWord;

    @FXML
    private Label questionWordLabel;

    @FXML
    private Button quit;

    @FXML
    private RadioButton rus;

    @FXML
    private ToggleGroup group = new ToggleGroup();
    @FXML
    private Label typeQuestLabel;
    @FXML
    private int currentMotion = 0;
    @FXML
    private String operInfo = "";
    @FXML
    private String data = "";
    @FXML
    private String ID;

    private String getData()
    {
        return questionWord.getText() + " "
                + info1.getText() + " "
                + info2.getText() + " "
                + info3.getText() + " "
                + info4.getText() + " "
                + (rus.isSelected() ? rus.getText() : eng.getText());
    }
    private void setResetTexts()
    {
        questionWord.setText("");
        info1.setText("");
        info2.setText("");
        info3.setText("");
        info4.setText("");
    }
    private void setVisibleFalse(boolean visible)
    {
        typeQuestLabel.setVisible(visible);
        eng.setVisible(visible);
        rus.setVisible(visible);
        questionWordLabel.setVisible(visible);
        questionWord.setVisible(visible);
        infoLabel.setVisible(visible);
        currentStep.setVisible(visible);
        info1.setVisible(visible);
        info2.setVisible(visible);
        info3.setVisible(visible);
        info4.setVisible(visible);
    }
    private void setDisable(boolean disable)
    {
        countElemsLabel.setDisable(disable);
        quantityElems.setDisable(disable);
        currentStep.setVisible(disable);
    }
    private void settings(String info)
    {
        switch (info){
            case "AddWords":
            {
                infoLabel.setVisible(true);
                infoLabel.setText("Введите: английское слово -> транскрипция -> перевод");
                info1.setVisible(true);
                info2.setVisible(true);
                info3.setVisible(true);
                break;
            }
            case "DeleteTest", "DeleteWords":
            {
                questionWordLabel.setVisible(true);
                questionWord.setVisible(true);
                questionWordLabel.setText("Введите удаляемый номер");
                questionWord.setPromptText("Номер");
                break;
            }
            default:
                setVisibleFalse(true);
                info1.setPromptText("1й ответ");
                info2.setPromptText("2й ответ");
                info3.setPromptText("3й ответ");
                info4.setPromptText("4й ответ");
                break;
        }
    }
    public void setData(String ID) {this.ID = ID;}
    private String sendData()
    {
        data += "," + getData();
        String response = "";
        String request = this.operInfo;
        request += "," + data;
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            communication.writeLine(request);
            response = communication.readLine();
        }
        catch (IOException e) {
            response = "errorKey";
        }
        return response;
    }
   /* private String handlerExceptions(String response)
    {
        switch (response) {
            case "errorNumKey" -> errorInfo.setText("Данного номера не существует");
            case "" -> errorInfo.setText("Заполните поле 'Количество элементов'");
            default -> {return "allGood";}
        }
        currentMotion--;
        return response;
    }*/
    @FXML
    void clickToLeave(ActionEvent event) throws IOException {
        Stage stage = (Stage) quit.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("adminPanelForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 567, 565);
        adminPanelFormController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setData(ID);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void inputData(ActionEvent event) {
        if (currentMotion == 0)
        {
            if (!quantityElems.getText().equals("")) {
                settings(operInfo);
                setDisable(true);
                errorInfo.setText("");
            }
            else
            {
                errorInfo.setText("Заполните поле 'Количество элементов'");
                currentMotion = -1;
            }
        }

        else if (String.valueOf(currentMotion).equals(quantityElems.getText()))
        {
            sendData();
            currentMotion = -1;
            setVisibleFalse(false);
            setDisable(false);
            quantityElems.setText("");
            errorInfo.setText("");
            data = "";
        }
        else
            data += getData();

        currentMotion++;
        currentStep.setText(currentMotion + "/" + quantityElems.getText());
        setResetTexts();
    }

    public void input(String info) {this.operInfo = info;}
    @FXML
    void initialize() {
        assert eng != null : "fx:id=\"eng\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert info1 != null : "fx:id=\"info1\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert info2 != null : "fx:id=\"info2\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert info3 != null : "fx:id=\"info3\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert info4 != null : "fx:id=\"info4\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert infoLabel != null : "fx:id=\"infoLabel\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert inputData != null : "fx:id=\"inputData\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert quantityElems != null : "fx:id=\"quantityElems\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert questionWord != null : "fx:id=\"questionWord\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert questionWordLabel != null : "fx:id=\"questionWordLabel\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert quit != null : "fx:id=\"quit\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert rus != null : "fx:id=\"rus\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        assert typeQuestLabel != null : "fx:id=\"typeQuestLabel\" was not injected: check your FXML file 'inputDataForm.fxml'.";
        setVisibleFalse(false);
    }

}

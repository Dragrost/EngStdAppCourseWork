package classes.forms;

import java.io.IOException;
import classes.comm.GeneralComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminPanelFormController {
    @FXML
    private Button addWord;

    @FXML
    private Text login;

    @FXML
    private String ID;

    @FXML
    private Button quit;

    @FXML
    private String getLogin()
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            String request = "GetLogin," + ID;
            communication.writeLine(request);

            return communication.readLine();
        }
        catch (IOException e) {
            return "Нет соединения с сервером!";
        }
    }
    @FXML
    public void setData(String ID) {
        this.ID = ID;
        login.setText("Hi, " + getLogin());
    }

    private void openTable() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("tableInfoForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 561, 695);
        TableInfoController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setTableData("GetEngTable");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Открытие формы с передачей информации о запросе
     * @param event
     * @throws IOException
     */
    @FXML
    public void methodOperation(MouseEvent event) throws IOException {

        Stage stage = (Stage) quit.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("inputDataForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 356);
        InputDataController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setData(this.ID);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        String chosenButtWord = ((Button) event.getSource()).getId();
        openTable();
        switch (chosenButtWord) {
            case "delWord" ->
                controllerEditBook.input("DeleteWords");
            case "delTest" ->
                controllerEditBook.input("DeleteTest");
            case "addWord" -> controllerEditBook.input("AddWords");
            default -> controllerEditBook.input("AddTest");
        }
    }
    @FXML
    void clickToLeave(ActionEvent event) throws IOException {
        Stage stage = (Stage) quit.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("logForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 543);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickToCheckAVGProgress(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("tableInfoForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 561, 695);
        TableInfoController controllerEditBook = fxmlLoader.getController();
        controllerEditBook.setTableData("PersonProgress");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void initialize() {
        assert addWord != null : "fx:id=\"addWord\" was not injected: check your FXML file 'adminPanelForm.fxml'.";
        assert quit != null : "fx:id=\"quit\" was not injected: check your FXML file 'adminPanelForm.fxml'.";
    }
}

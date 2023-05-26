package classes.forms;

import classes.comm.GeneralComm;
import info.Info;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableInfoController {

    @FXML
    private ObservableList<Info> info = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Info, String> tab1;

    @FXML
    private TableColumn<Info, String> tab2;

    @FXML
    private TableColumn<Info, String> tab3;
    @FXML
    private TableView<Info> table;

    private String response = "";
    @FXML
    private String requestToServer(String request)
    {
        try (GeneralComm communication = new GeneralComm("127.0.0.1", 8000))
        {
            communication.writeLine(request);
            return communication.readLine();
        }
        catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
        }
        return "-1";
    }

    private void settings(String request)
    {
        if (request.equals("PersonProgress"))
        {
            tab2.setText("login");
            tab3.setText("progress");
        }
        else
        {
            tab2.setText("english word");
            tab3.setText("russian word");
        }
    }
    public void setData(String request)
    {
        settings(request);

        ArrayList<String> words = new ArrayList<>();
        Collections.addAll(words,requestToServer(request).split("!"));
        words.removeAll(Collections.singleton(""));

        for (int i = 0; i < words.size();i++)
            info.add(new Info(words.get(i++), words.get(i++), words.get(i)));

        table.setItems(info);
    }

    @FXML
    void initialize() {
        assert tab1 != null : "fx:id=\"tab1\" was not injected: check your FXML file 'tableInfoForm.fxml'.";
        assert tab2 != null : "fx:id=\"tab2\" was not injected: check your FXML file 'tableInfoForm.fxml'.";
        assert tab3 != null : "fx:id=\"tab3\" was not injected: check your FXML file 'tableInfoForm.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'tableInfoForm.fxml'.";
        tab1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tab2.setCellValueFactory(new PropertyValueFactory<>("data1"));
        tab3.setCellValueFactory(new PropertyValueFactory<>("data2"));

       // info = FXCollections.observableArrayList(new Info("1","aboba","bobaboba"),new Info ("IDD", "sdsd", "fdgg"));

        //Info info = new Info("1","aboba","bobaboba");

        //table.getItems().add(info);
        //table.setItems(info);
    }

}

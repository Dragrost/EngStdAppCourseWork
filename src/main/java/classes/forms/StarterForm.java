package classes.forms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class StarterForm extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterForm.class.getResource("registerForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 543); // url(" file:/C:/Davydov_Univer/Java/Other/EngStdApp_CourseWork/Images/BackGround.png")
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}

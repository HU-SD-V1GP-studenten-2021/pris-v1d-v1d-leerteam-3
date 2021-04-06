import domeinLaag.Klas;
import domeinLaag.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Magister200app extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlPagina = "userInterfaceLaag/LoginScherm.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPagina));
        Parent root = loader.load();
        stage.setTitle("Login scherm");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("HU.png"));
        stage.show();

//        Klas k1 = new Klas("V1D");
//        Student s1 = new Student("Jens", 11, "jens@student.hu.nl" , false, 90 ,0 ,"jens", k1 );
    }


}


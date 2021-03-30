import domeinLaag.Docent;
import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.stream.events.StartDocument;
import java.time.LocalDate;
import java.time.LocalTime;

public class Magister200app extends Application {
    public static void main(String[] args) {
        launch(args);
    Klas k1 = new Klas("V1D");
    Student st1 = new Student("test", 11, "test@student.hu.nl", false, 100.00, 0, "123", k1);
    Docent d1 = new Docent("Docentman", 22, "D@hu.nl", 0, 0, "123123", k1);

    LocalDate begindatum = LocalDate.parse("2021-03-26");
    LocalTime begintijd = LocalTime.parse("12:00:00");
    LocalTime eindtijd = LocalTime.parse("15:00:00");

    Les l1 = new Les(begindatum, begintijd, eindtijd, k1 );
    k1.voegDocentToe(d1);
    k1.voegLeerlingToe(st1);
    k1.voegLesToe(l1);


    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlPagina = "userInterfaceLaag/LoginScherm.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPagina));
        Parent root = loader.load();
        stage.setTitle("Login scherm");
        stage.setScene(new Scene(root));
        stage.show();
    }


}


import domeinLaag.Docent;
import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class Magister200app extends Application {
    public static void main(String[] args) {
        Klas onzeKlas = new Klas("1VD");
        Docent josvanEgmond = new Docent("Jos van Egmond", 123123, "Josvanegmond@hu.nl", 0, 0, "docent", onzeKlas);
        Student lucaFranken = new Student("Luca", 444,"luca.franken",0,100.00,0,"1234", onzeKlas);
        Les programmerenLes13 = new Les(LocalDate.of(2021, 03, 26), LocalTime.of(14,30,00), LocalTime.of(17,30,00), onzeKlas);
//        onzeKlas.voegDocentToe(josvanEgmond);
//        onzeKlas.voegLeerlingToe(lucaFranken);
//        onzeKlas.voegLesToe(programmerenLes13);
        onzeKlas.toString();

        launch(args);
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


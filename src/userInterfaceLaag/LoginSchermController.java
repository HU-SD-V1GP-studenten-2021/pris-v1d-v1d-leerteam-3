package userInterfaceLaag;

import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class LoginSchermController {
    @FXML private Button loginKnop;
    @FXML private PasswordField wachtwoordVeld;
    @FXML private TextField naamVeld;
    @FXML private Label Waarschuwing;

    private Student account = Student.getAccount();

    public void loginAccount(ActionEvent actionEvent) throws Exception  {
        String naam = naamVeld.getText();
        String wachtwoord = wachtwoordVeld.getText();
        Stage loginscherm = (Stage) loginKnop.getScene().getWindow();

        if(!naam.contains("@student.hu.nl") &&!naam.contains("@docent.hu.nl")){
            Waarschuwing.setText("E-mailadres is onjuist.\nVolg het format: gebruiker@domein.hu.nl");
        }
        else if(wachtwoord.equals("")){
            Waarschuwing.setText("Wachtwoordveld is verplicht");
        }

        else {
            if (naam.contains("@student.hu.nl")){


                String url = "jdbc:postgresql://localhost/SDGP";
                Properties props = new Properties();
                props.setProperty("user","postgres");
                props.setProperty("password","united");
                Connection conn = DriverManager.getConnection(url, props);
                Statement stmt = conn.createStatement();
                String SQL = "SELECT email, wachtwoord FROM student";
                ResultSet rs = stmt.executeQuery(SQL);

                while(rs.next()){
                    if(rs.getString("email").equals(naam)){
                        if(rs.getString("wachtwoord").equals(wachtwoord)){
                            System.out.println("ingelogged");
                        }
                    }

                }

                wachtwoord = wachtwoordVeld.getText();
                System.out.println("ingelogd als student. met naam: " + naam + " en wachtwoord: " + wachtwoord);

                // dsfsdfdsf@student.hu.nl
                //* if(naam.equals() && wachtwoord.equals()){}
                // als het gecheckt is en het komt overeen met hetgeen in de database,
                // dan select * from student where email = 'naam' and wachtwoord = 'wachtwoord';
                // dan heb je de gegevens van deze persoon en kan je een persoon object maken die je vervolgens
                // BV: Student user = new Student(De juiste gegevens uit de database om een tijdelijke student aan te maken voor zn account);
                // met account.setAccount(Persoon persoon); maakt.
                // om dan de persoon aan de juiste dingen te koppelen haal je alle gegevens op
                // en aan de hand daarvan maak je nieuwe klas, les(sen) en docent(en)(arraylisten toevoegen met een for loop).
                // BV: Klas klas = new Klas("de naam van de klas van de leerling uit de database);
                // BV: (dit moet in een for loop omdat er meerdere lessen per klas zijn en de leerling zit in die klas)
                // Les les = new Les(Alle gegevens van 1 les);
                // BV: (dit moet in een for loop omdat het kan zijn dat de lessen verschillende docenten hebben)
                // Docent docent = new Docent(gegevens van de Docent opgehaald via de les, via de klas van de leerling);
                // Alle gegevens zoals een docent, les en leerling moeten aan de klas gekoppeld worden in de for loop.
                // Hierin maak en koppel je dus eigenlijk de objecten voor het volgende scherm.
                try{

                    loginscherm.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LeerlingHoofdscherm.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Leerlingen scherm");
                    stage.setScene(new Scene(root));
                    stage.show();


                }
                catch (Exception ignored){
                    System.out.println(ignored);
                }

            }
            else{
                naam += "docent@hu.nl";
                wachtwoord = wachtwoordVeld.getText();
                //* if(naam.equals() && wachtwoord.equals()){}
                System.out.println("ingelogd als docent. met naam: " + naam + " en wachtwoord: " + wachtwoord);
                try{
                    loginscherm.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DocentenScherm.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Docenten scherm");
                    stage.setScene(new Scene(root));
                    stage.show();

                }
                catch (Exception ignored){
                    System.out.println(ignored);
                }
            }
        }
    }

    public void setStatusDocent(ActionEvent actionEvent) {
    }
}
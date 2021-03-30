package userInterfaceLaag;

import domeinLaag.Klas;
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
    private Object Klas;

    public void loginAccount(ActionEvent actionEvent) throws Exception  {
        String naam = naamVeld.getText();
        String wachtwoord = wachtwoordVeld.getText();
        Stage loginscherm = (Stage) loginKnop.getScene().getWindow();
        String url = "jdbc:postgresql://localhost/SDGP";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","united");
        Connection conn = DriverManager.getConnection(url, props);

        if(!naam.contains("@student.hu.nl") &&!naam.contains("@hu.nl")){
            Waarschuwing.setText("E-mailadres is onjuist.\nVolg het format: gebruiker@student.hu.nl");
        }
        else if(wachtwoord.equals("")){
            Waarschuwing.setText("Wachtwoordveld is verplicht");
        }

        else {


            if (naam.contains("@student.hu.nl")){
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT email, wachtwoord, pogingen, status, studentnummer, naam, pogingen, percentage, klasnaam FROM student");
                while(rs.next()){
                    int studentnummer = rs.getInt("studentnummer");
                    if(rs.getString("email").equals(naam) && rs.getString("wachtwoord").equals(wachtwoord) && !rs.getBoolean("status")){

                        String usernaam = rs.getString("naam");
                        String email = rs.getString("email");
                        int userstudentnummer = rs.getInt("studentnummer");
                        boolean status = rs.getBoolean("status");
                        int pogingen = rs.getInt("pogingen");
                        double percentage =  rs.getDouble("percentage");
                        String userwachtwoord =  rs.getString("wachtwoord");
                        Klas userklas = new Klas(rs.getString("klasnaam"));
                        stmt.executeUpdate("UPDATE student SET pogingen = 0 WHERE studentnummer = " + studentnummer);

                        Student user = new Student(usernaam , userstudentnummer, email, status, percentage, pogingen, userwachtwoord, userklas);
                        System.out.println("gebuikers email: " + user.getEmail());
                        System.out.println("gebruikers klas: " + user.getKlas());
                        System.out.println("gebruikers naam: " + user.getNaam());
                        System.out.println("gebruikers studentnummer: " + user.getStudentennummer());


                        try{
                            loginscherm.close();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leerlingHoofdscherm.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setTitle("Leerling scherm");
                            stage.setScene(new Scene(root));
                            stage.show();
                        }
                        catch (Exception ignored){
                            System.out.println(ignored);
                        }
                        break;

                    }
                    else if (rs.getString("email").equals(naam)){
                        int i = rs.getInt("pogingen");
                        System.out.println(i);
                        i ++;
                        System.out.println(i);
                        stmt.executeUpdate("UPDATE student SET pogingen =" + i + " WHERE studentnummer = " + studentnummer);
                        if (i == 3 || i == 4){
                            Waarschuwing.setText("Let op, je zit op " + i + " pogingen!\n" +
                                    "bij 5 wordt je geblokkerd!");
                        }
                        else if (i >= 5){
                            stmt.executeUpdate("UPDATE student SET status = true WHERE studentnummer = " + studentnummer);
                            Waarschuwing.setText("Je bent geblokkeerd!\nStuur een mailtje naar de administrator!");
                        }else{
                            Waarschuwing.setText("Email of wachtwoord onjuist!");
                        }
                        break;
                    }
                    else {
                        Waarschuwing.setText("Email of wachtwoord onjuist!");

                    }
                }
            }
            else if(naam.contains("@hu.nl")){
                wachtwoord = wachtwoordVeld.getText();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT email, wachtwoord, pogingen, status, docentnummer FROM docent");
                while(rs.next()){
                    int docentnummer = rs.getInt("docentnummer");
                    if(rs.getString("email").equals(naam) && rs.getString(2).equals(wachtwoord) && !rs.getBoolean("status")){
                        stmt.executeUpdate("UPDATE docent SET pogingen = 0 WHERE docentnummer = " + docentnummer);
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
                        break;

                    }
                    else if (rs.getString("email").equals(naam)){
                        int i = rs.getInt("pogingen");
                        System.out.println(i);
                        i ++;
                        System.out.println(i);
                        stmt.executeUpdate("UPDATE docent SET pogingen =" + i + " WHERE docentnummer = " + docentnummer);
                        if (i == 3 || i == 4){
                            Waarschuwing.setText("Let op, je zit op " + i + " pogingen!\n" +
                                    "bij 5 wordt je geblokkerd!");
                        }
                        else if (i >= 5){
                            stmt.executeUpdate("UPDATE docent SET status = true WHERE docentnummer = " + docentnummer);
                            Waarschuwing.setText("Je bent geblokkeerd!\nStuur een mailtje naar de administrator!");
                        }else{
                            Waarschuwing.setText("Email of wachtwoord onjuist!");
                        }
                        break;
                    }
                    else {
                        Waarschuwing.setText("Email of wachtwoord onjuist!");

                    }




                }
            }
        }
    }

    public void setStatusDocent(ActionEvent actionEvent) {
    }
}
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
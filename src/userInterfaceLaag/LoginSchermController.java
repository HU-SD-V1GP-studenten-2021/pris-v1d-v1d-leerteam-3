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

        if(!naam.contains("@student.hu.nl") &&!naam.contains("@hu.nl")){
            Waarschuwing.setText("E-mailadres is onjuist.\nVolg het format: gebruiker@student.hu.nl");
        }
        else if(wachtwoord.equals("")){
            Waarschuwing.setText("Wachtwoordveld is verplicht");
        }

        else {
            String url = "jdbc:postgresql://localhost/SDGP";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","united");
            Connection conn = DriverManager.getConnection(url, props);
            if (naam.contains("@student.hu.nl")){
                Statement stmt = conn.createStatement();
                String SQL = "SELECT email, wachtwoord FROM student";
                ResultSet rs = stmt.executeQuery(SQL);
                while(rs.next()){
                    if(rs.getString("email").equals(naam) && rs.getString(2).equals(wachtwoord)){
                            try{
                                loginscherm.close();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LeerlingHoofdscherm.fxml"));
                                Parent root = (Parent) fxmlLoader.load();
                                Stage stage = new Stage();
                                stage.setTitle("Leerlingen scherm");
                                stage.setScene(new Scene(root));
                                stage.show();

                                wachtwoord = wachtwoordVeld.getText();
                                System.out.println("ingelogd als student. met naam: " + naam + " en wachtwoord: " + wachtwoord);
                            }
                            catch (Exception ignored){
                                System.out.println(ignored);
                            }
                    }
                    else {
                        Waarschuwing.setText("Email of wachtwoord onjuist!");
                    }
                }
            }
            else if(naam.contains("@hu.nl")){
                wachtwoord = wachtwoordVeld.getText();
                Statement stmt = conn.createStatement();
                String SQL = "SELECT email, wachtwoord, pogingen, status, docentnummer FROM docent";
                ResultSet rs = stmt.executeQuery(SQL);
                while(rs.next()){
                    if(rs.getString("email").equals(naam) && rs.getString(2).equals(wachtwoord)){
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
                    else {
                    Waarschuwing.setText("Email of wachtwoord onjuist!");
                    Statement stmt1 = conn.createStatement();
                    ResultSet rs1 = stmt.executeQuery(SQL);
                    int i = rs.getInt("pogingen");
                    int docentnummer = rs.getInt(5);
                    i ++;
                    String SQL1 = "UPDATE docent SET pogingen " + i + " WHERE docentnummer = " + docentnummer;
                    System.out.println(rs.getInt("pogingen"));
                    }




                }
                    }
        }
    }

    public void setStatusDocent(ActionEvent actionEvent) {
    }
}
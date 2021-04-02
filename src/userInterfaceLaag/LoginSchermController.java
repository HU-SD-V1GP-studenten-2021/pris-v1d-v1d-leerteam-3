package userInterfaceLaag;

import domeinLaag.Docent;
import domeinLaag.Klas;
import domeinLaag.Les;
import domeinLaag.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
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
        props.setProperty("password","ruben");
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
                ResultSet rsHuidigeStudent = stmt.executeQuery("SELECT email, wachtwoord, pogingen, status, studentnummer, naam, pogingen, rollcall FROM student");
                while(rsHuidigeStudent.next()){
                    int studentnummer = rsHuidigeStudent.getInt("studentnummer");
                    if(rsHuidigeStudent.getString("email").equals(naam) && rsHuidigeStudent.getString("wachtwoord").equals(wachtwoord) && !rsHuidigeStudent.getBoolean("status")){
                        int userstudentnummer = rsHuidigeStudent.getInt("studentnummer");

                        stmt.executeUpdate("UPDATE student SET pogingen = 0 WHERE studentnummer = " + studentnummer);

                        ResultSet userGegevens = stmt.executeQuery("SELECT studentnummer, naam, email, status, pogingen, rollcall, wachtwoord, k.klasnummer, k.klasnaam FROM student " +
                                "join klas k on k.klasnummer = student.klasnummer " +
                                "WHERE studentnummer = " + userstudentnummer);

                        userGegevens.next();

                        String usernaam = userGegevens.getString("naam");
                        String email = userGegevens.getString("email");
                        boolean status = userGegevens.getBoolean("status");
                        int pogingen = userGegevens.getInt("pogingen");
                        double rollcall = userGegevens.getDouble("rollcall");
                        String userwachtwoord = userGegevens.getString("wachtwoord");
                        int klasnummer = userGegevens.getInt("klasnummer");
                        String klasnaam = userGegevens.getString("klasnaam");


                        Klas klas = new Klas(klasnummer, klasnaam);
                        Student user = new Student(usernaam, userstudentnummer, email, status, pogingen, rollcall, userwachtwoord);
                        user.setKlas(klas);
                        klas.voegStudentToe(user);


                        Student.setAccount(user);

                        ResultSet lessen = stmt.executeQuery("SELECT l.lesnummer, l.datum, l.begintijd, l.eindtijd, l.docentnummer, l.lesnaam " +
                                "FROM les l JOIN klas k on k.klasnummer = l.klasnummer " +
                                "WHERE k.klasnummer = '" + klasnummer + "'");


                        int docentnummer = 0;
                        ArrayList<Les> alleLessen = new ArrayList<>();
                        while (lessen.next()){
                            int lesnummer = lessen.getInt(1); //lesnummer
                            String lesnaam = lessen.getString(6); //lesnaam
                            LocalDate datum = lessen.getDate(2).toLocalDate(); //datum
                            LocalTime begintijd = lessen.getTime(3).toLocalTime(); //begintijd
                            LocalTime eindtijd = lessen.getTime(4).toLocalTime(); //eindtijd
                            docentnummer = lessen.getInt(5); //docent nummer

                            Les les = new Les(lesnummer, lesnaam, datum, begintijd, eindtijd);
                            les.setKlas(klas);
                            alleLessen.add(les);

                        }


                        ResultSet docent = stmt.executeQuery("SELECT docent.docentnummer, naam, email, status, pogingen, wachtwoord from docent " +
                                "join les l on docent.docentnummer = l.docentnummer " +
                                "WHERE l.klasnummer = '" + klasnummer + "'");


                        int i = 0;
                        while (docent.next()) {
                            Les les = alleLessen.get(i);
                            String docentNaam = docent.getString(2);//docentnaam
                            String docentEmail = docent.getString(3);// docentemail
                            boolean docentStatus = docent.getBoolean(4); //docent status
                            int docentPogingen = docent.getInt(5); //docent pogingen
                            String docentWW = docent.getString(6); //docent wachtwoord

                            Docent docentobject = new Docent(docentNaam, docentnummer, docentEmail, docentStatus, docentPogingen, docentWW);

                            les.setDocent(docentobject);
                            klas.voegLesToe(les);
                            i ++;
                        }

                        ResultSet alleStudenten = stmt.executeQuery("select studentnummer, naam, email, status, pogingen, rollcall, wachtwoord from student " +
                                "join klas k on k.klasnummer = student.klasnummer " +
                                "where k.klasnummer = '" + klasnummer + "'");

                        while (alleStudenten.next()){
                            int studentnummerNu = alleStudenten.getInt("studentnummer");
                            if (studentnummerNu != studentnummer){
                                String naamNu = alleStudenten.getString("naam");
                                String emailNu = alleStudenten.getString("email");
                                boolean statusNu = alleStudenten.getBoolean(4);//status
                                int pogingenNu = alleStudenten.getInt("pogingen");
                                double rollcallNu = alleStudenten.getDouble("rollcall");
                                String wachtwoordNu = alleStudenten.getString("wachtwoord");
                                Student s1 = new Student(naamNu, studentnummerNu, emailNu, statusNu, pogingenNu, rollcallNu,wachtwoordNu);
                                s1.setKlas(klas);
                                klas.voegStudentToe(s1);

                            }
                        }
 //                        ResultSet rollcallMaken = stmt.executeQuery("SELECT count(*) FROM afwezigheid " +
//                                "WHERE studentnummer = " + studentnummer);

                        ResultSet rollcallMaken = stmt.executeQuery("SELECT count(*) FROM afwezigheid " +
                                "WHERE studentnummer = " + studentnummer);

                        int aantal = 0;
                        while(rollcallMaken.next()){
                            aantal ++;
                        }
                        System.out.println(aantal);
                        System.out.println(user.getKlas().getTotaalAantalLessen());
                        double bereken = 100 - (100 / user.getKlas().getTotaalAantalLessen() * aantal);
                        System.out.println("roll call: " + bereken);
                        user.setRollCall(bereken);


                        try{
                            loginscherm.close();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leerlingHoofdscherm.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setTitle("Lessen");
                            stage.getIcons().add(new Image("src/LogoManufactra500pxBeeldmerk.png"));
                            stage.setScene(new Scene(root));
                            stage.show();
                        }
                        catch (Exception ignored){
                            System.out.println(ignored);
                        }
                        break;

                    }
                    else if (rsHuidigeStudent.getString("email").equals(naam)){
                        int i = rsHuidigeStudent.getInt("pogingen");
                        i ++;
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


                        ResultSet klassengegevens = stmt.executeQuery("SELECT k.klasnummer, k.klasnaam FROM les " +
                                "    join docent d on d.docentnummer = les.docentnummer " +
                                "    join klas k on k.klasnummer = les.klasnummer " +
                                "where d.docentnummer = '" + docentnummer + "'");

                        ArrayList<Klas> alleKlassen = new ArrayList<>();
                        while (klassengegevens.next()){
                            String klasnaam = klassengegevens.getString("klasnaam");
                            int klasnummer = klassengegevens.getInt("klasnummer");
                            Klas k10 = new Klas(klasnummer, klasnaam);
                            alleKlassen.add(k10);
                        }

                        ResultSet userGegevens = stmt.executeQuery("select docent.docentnummer, naam, email, status, pogingen, wachtwoord, k.klasnummer, k.klasnaam from docent " +
                                "    join les l on docent.docentnummer = l.docentnummer " +
                                "    join klas k on k.klasnummer = l.klasnummer " +
                                "where docent.docentnummer = " + docentnummer);


                        Klas klas = null;
                        Docent docent = null;
                        while (userGegevens.next()) {
                            String usernaam = userGegevens.getString("naam");
                            String email = userGegevens.getString("email");
                            boolean status = userGegevens.getBoolean("status");
                            int pogingen = userGegevens.getInt("pogingen");
                            String userwachtwoord = userGegevens.getString("wachtwoord");
                            int klasnummer = userGegevens.getInt("klasnummer");
                            String klasnaam = userGegevens.getString("klasnaam");
                            docent = new Docent(usernaam, docentnummer, email, status, pogingen,userwachtwoord);
                            klas = new Klas(klasnummer, klasnaam);
                        }

                        Docent.setAccount(docent);



                        ResultSet lessen = stmt.executeQuery("SELECT l.lesnummer, l.datum, l.begintijd, l.eindtijd, l.lesnaam FROM les l " +
                                "    JOIN klas k on k.klasnummer = l.klasnummer " +
                                "    join docent d on d.docentnummer = l.docentnummer " +
                                "WHERE l.docentnummer = '" + docentnummer + "'");


                        ArrayList<Les> alleLessen = new ArrayList<>();
                        while (lessen.next()){
                            int lesnummer = lessen.getInt(1); //lesnummer
                            String lesnaam = lessen.getString(5); //lesnaam
                            LocalDate datum = lessen.getDate(2).toLocalDate(); //datum
                            LocalTime begintijd = lessen.getTime(3).toLocalTime(); //begintijd
                            LocalTime eindtijd = lessen.getTime(4).toLocalTime(); //eindtijd

                            Les les = new Les(lesnummer, lesnaam, datum, begintijd, eindtijd);
                            les.setKlas(klas);
                            alleLessen.add(les);
                        }



//                        }


                        ResultSet docentles = stmt.executeQuery("select docent.docentnummer, naam, email, status, pogingen, wachtwoord from docent" +
                                "    join les l on docent.docentnummer = l.docentnummer " +
                                "    join klas k on k.klasnummer = l.klasnummer " +
                                "where docent.docentnummer = '" + docentnummer + "'");


                        int i = 0;
                        while (docentles.next()) {
                            Les les = alleLessen.get(i);
                            Klas klas1 = alleKlassen.get(i);

                            les.setDocent(docent);
                            les.setKlas(klas1);
                            klas1.voegLesToe(les);
                            docent.addLes(les);
                            i++;
                        }


                        for (Klas k : alleKlassen) {
                            ResultSet alleStudenten = stmt.executeQuery("select studentnummer, naam, email, status, pogingen, rollcall, wachtwoord from student " +
                                    "    join klas k on k.klasnummer = student.klasnummer " +
                                    "where k.klasnummer = '" + k.getKlasnummer() + "'");

                            while (alleStudenten.next()) {
//                                System.out.println(alleStudenten.getString("naam"));
                                int studentnummer = alleStudenten.getInt("studentnummer");
                                String naamStudent = alleStudenten.getString("naam");
                                String email = alleStudenten.getString("email");
                                boolean status = alleStudenten.getBoolean(4);//status
                                int pogingen = alleStudenten.getInt("pogingen");
                                double rollcall = alleStudenten.getDouble("rollcall");
                                String wachtwoordStudent = alleStudenten.getString("wachtwoord");

                                Student student = new Student(naamStudent, studentnummer, email, status, pogingen, rollcall, wachtwoordStudent);

                                student.setKlas(k);
                                k.voegStudentToe(student);

                            }
                        }



                        try{

                            loginscherm.close();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DocentenScherm.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setTitle("Les presentie");
                            stage.setScene(new Scene(root));
                            stage.getIcons().add(new Image("src/LogoManufactra500pxBeeldmerk.png"));
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
package preExam;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import static preExam.loginController.*;


public class PreloaderController implements Initializable {

    //Declaring some custom static variables for Name, Subjects and Questions and Timer Backups
    public static String name = null, qNobackup = null, psgIdbackup = null, 
           sub2 = null, sub3 = null, sub4 = null;
    public static Integer mins = 0;
     
    //Declaring static List that saves file name (from database) of images for questions and options
    public static ObservableList <Image> qImage = FXCollections.observableArrayList(),
                                         aImage = FXCollections.observableArrayList(),
                                         bImage = FXCollections.observableArrayList(),
                                         cImage = FXCollections.observableArrayList(),
                                         dImage = FXCollections.observableArrayList();
   
    //Declaring static List that saves IDs of Questions
    public static ObservableList <Integer> Qid = FXCollections.observableArrayList(),
                                          realPsgId = FXCollections.observableArrayList();
   
    //Declaring static List that saves retrieved questions, options, instructions and pasages from database
    public static ObservableList <String> Qtext = FXCollections.observableArrayList(),
                                         Atext = FXCollections.observableArrayList(),
                                         Btext = FXCollections.observableArrayList(),
                                         Ctext = FXCollections.observableArrayList(),
                                         Dtext = FXCollections.observableArrayList(),
                                         Instruction = FXCollections.observableArrayList(),
                                         passages = FXCollections.observableArrayList();
   
    //Declaring static List that saves retrieved answers from database
    public static ObservableList <Character> realAns = FXCollections.observableArrayList();
    
    @FXML
    private VBox preloader;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {          
            //Retrieving full name and registered subjects  for this candidate
            PreparedStatement ps4 = con.prepareStatement("select name, sub2,  sub3, sub4 from login where un = ? and pw = ?");
            ps4.setString(1, un);
            ps4.setString(2, pw);   

            ResultSet rs4 = ps4.executeQuery();
            while (rs4.next()) {
                name = rs4.getString("name").toUpperCase();
                sub2 = rs4.getString("sub2");
                sub3 = rs4.getString("sub3");
                sub4 = rs4.getString("sub4");
            }

            //Retrieving timer, question backup and passage id  for this candidate
            PreparedStatement ps9 = con.prepareStatement("SELECT timermin, backup, psgid FROM login WHERE un = ? and pw = ?");
            ps9.setString(1, un);
            ps9.setString(2, pw);

            ResultSet rs9 = ps9.executeQuery();
            while (rs9.next()) {
                mins = rs9.getInt("timermin");
                qNobackup = rs9.getString("backup");
                psgIdbackup = rs9.getString("psgid");
            }  

            if (!qNobackup.isEmpty()){ //If backup is NOT empty, this means this student has logged in previously at least once
                    //i.e  this student has started this exam previously and has answered at least one question
                for (int i=0; i<2; i++) {
                    realPsgId.add(Integer.parseInt(String.valueOf(psgIdbackup.charAt(i))));
                    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM english_passage where id = " + realPsgId.get(i));
                    ResultSet rs1 = ps1.executeQuery();

                    while (rs1.next()) {
                        passages.add(rs1.getString("passage"));
                    }
                }
                //Splitting up the retrieved question backup(Since it is a string from the database)
                Scanner scanQNoBackUp = new Scanner (qNobackup);
                scanQNoBackUp.useDelimiter("[^0-9]+"); //making use of (square brackets and commas) from saved array as delimeters

                for (int i=0; i<180; i++) {
                    // Adding these split integers into the List "Qid"
                    Qid.add(Integer.parseInt(scanQNoBackUp.next()));
                }

                //Setting Questions for the First 5 questions for the 1st English Passage
                for (int i=0; i<5; i++) {
                    setQuestionOldEnglish(i, "eng"+realPsgId.get(0));
                }
                //Setting Questions for the next 5 questions for the 2nd English Passage
                for (int i=5; i<10; i++) {
                    setQuestionOldEnglish(i, "eng"+realPsgId.get(1));
                }
                //Setting Questions for the next 40 questions for the next part of English
                for (int i=10; i<50; i++) {
                    setQuestionOldEnglish(i, "engpass");
                }
                //Setting Questions for the last 10 questions for the last part of English
                for (int i=50; i<60; i++) {                       
                    setQuestionOldEnglish(i, "life_changer");
                }
                //Setting Questions for the 40 questions of the 2nd subject
                for (int i=60; i<100; i++) {
                    setQuestionOldOthers(i, sub2);
                }
                //Setting Questions for the 40 questions of the 3rd subject
                for (int i=100; i<140; i++) {
                    setQuestionOldOthers(i, sub3); 
                }
                //Checking if 4th subject is literature
                if (sub4.equals("literature")) { //executes if yes
                    // Thus, questions are set for the different sections of the literature texts
                    for (int i=140; i<152; i++) {
                        setQuestionOldOthers(i, "literature");
                    }
                    for (int i=152; i<155; i++) {
                        setQuestionOldOthers(i, "midsummer");
                    }
                    for (int i=155; i<158; i++) {
                        setQuestionOldOthers(i, "lion_jewel");
                    }
                    for (int i=158; i<161; i++) {
                        setQuestionOldOthers(i, "die_alone");
                    }
                    for (int i=161; i<164; i++) {
                        setQuestionOldOthers(i, "fences");
                    }
                    for (int i=164; i<167; i++) {
                        setQuestionOldOthers(i, "unexpected");
                    }
                    for (int i=167; i<170; i++) {
                        setQuestionOldOthers(i, "second_class");
                    }
                    for (int i=170; i<172; i++) {
                        setQuestionOldOthers(i, "black_woman");
                    }
                    for (int i=172; i<174; i++) {
                        setQuestionOldOthers(i, "leader_led");
                    }
                    for (int i=174; i<176; i++) {
                        setQuestionOldOthers(i, "song_of_women");
                    }
                    for (int i=176; i<178; i++) {
                        setQuestionOldOthers(i, "good_morrow");
                    }
                    for (int i=178; i<180; i++) {
                        setQuestionOldOthers(i, "caged_bird");
                    }

                } else { //otherwise, Set Questions for the 40 questions of the Non-Literature 4th subject
                    for (int i=140; i<180; i++) {
                        setQuestionOldOthers(i, sub4);
                    }
                }


            } else { // If backup is empty, execute

                //Getting 2 random passages from the database
                PreparedStatement ps = con.prepareStatement("SELECT * from english_passage order by rand() limit 2");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    realPsgId.add(rs.getInt("id")); //Saves the 2 passages Ids also
                    passages.add(rs.getString("passage"));
                } 

                //Calling functions to select new random questions for English
                setQuestionNewEnglish(5, "eng"+realPsgId.get(0));
                setQuestionNewEnglish(5, "eng"+realPsgId.get(1));
                setQuestionNewEnglish(40, "engpass");
                setQuestionNewEnglish(10, "life_changer");

                //Calling functions to select new random questions for 2nd and 3rd subjects
                setQuestionNewOthers(40, sub2);
                setQuestionNewOthers(40, sub3);

                //checks if 4th subject is literature 
                if (sub4.equals("literature")) { //If true,Call function to select new random questions for literature
                    setQuestionNewOthers(12, "literature");
                    setQuestionNewOthers(3, "midsummer");
                    setQuestionNewOthers(3, "lion_jewel");
                    setQuestionNewOthers(3, "die_alone");
                    setQuestionNewOthers(3, "fences");
                    setQuestionNewOthers(3, "unexpected");
                    setQuestionNewOthers(3, "second_class");
                    setQuestionNewOthers(2, "black_woman");
                    setQuestionNewOthers(2, "leader_led");
                    setQuestionNewOthers(2, "song_of_women");
                    setQuestionNewOthers(2, "good_morrow");
                    setQuestionNewOthers(2, "caged_bird");

                } else {
                    setQuestionNewOthers(40, sub4); // Otherwise, Call function to select new random questions for 4th Non-literature subject
                }
            }            

            Thread preloader = new Thread(this::preloaderThread); //A call to a thread that switches this page to the MAIN Exam Page
            preloader.start();
                                                               
        } catch (SQLException ex) {
            Logger.getLogger(PreloaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }  
    //Function that sets questions for sub2, sub3 and sub4 if backup is NOT Empty
    private void setQuestionOldOthers (int i, String dbTable) throws SQLException {
        PreparedStatement ps8 = con.prepareStatement("SELECT * from " +dbTable+ " where id = ?");
        ps8.setInt(1, Qid.get(i));
        ResultSet rs8 = ps8.executeQuery();

        while (rs8.next()) {
            Qtext.add(rs8.getString("qtext"));
            Atext.add(rs8.getString("atext"));
            Btext.add(rs8.getString("btext"));
            Ctext.add(rs8.getString("ctext"));
            Dtext.add(rs8.getString("dtext"));
            realAns.add(rs8.getString("ans").charAt(0));
            Instruction.add(rs8.getString("instruction"));

            qImage.add(new Image ("/images/"+rs8.getString("qimage")));
            aImage.add(new Image ("/images/"+rs8.getString("aimage")));
            bImage.add(new Image ("/images/"+rs8.getString("bimage")));
            cImage.add(new Image ("/images/"+rs8.getString("cimage")));
            dImage.add(new Image ("/images/"+rs8.getString("dimage")));
        }
    }
    //Function that sets questions for English if backup is NOT Empty
    private void setQuestionOldEnglish (int i, String dbTable) throws SQLException {
        PreparedStatement ps8 = con.prepareStatement("SELECT * from " +dbTable+ " where id = ?");
        ps8.setInt(1, Qid.get(i));
        ResultSet rs8 = ps8.executeQuery();

        while (rs8.next()) {
            Qtext.add(rs8.getString("qtext"));
            Atext.add(rs8.getString("atext"));
            Btext.add(rs8.getString("btext"));
            Ctext.add(rs8.getString("ctext"));
            Dtext.add(rs8.getString("dtext"));
            realAns.add(rs8.getString("ans").charAt(0));
            Instruction.add(rs8.getString("instruction"));
        }
    }
    //Function that sets questions for sub2, sub3 and sub4 if backup is  Empty
    private void setQuestionNewOthers (int limit, String dbTable) throws SQLException {
        PreparedStatement ps6 = con.prepareStatement("SELECT * from " +dbTable+ " order by rand() limit "+limit);
        ResultSet rs6 = ps6.executeQuery();

        while (rs6.next()) {
            Qid.add(rs6.getInt("id"));
            Qtext.add(rs6.getString("qtext"));
            Atext.add(rs6.getString("atext"));
            Btext.add(rs6.getString("btext"));
            Ctext.add(rs6.getString("ctext"));
            Dtext.add(rs6.getString("dtext"));
            realAns.add(rs6.getString("ans").charAt(0));
            Instruction.add(rs6.getString("instruction"));

            qImage.add(new Image ("/images/"+rs6.getString("qimage")));
            aImage.add(new Image ("/images/"+rs6.getString("aimage")));
            bImage.add(new Image ("/images/"+rs6.getString("bimage")));
            cImage.add(new Image ("/images/"+rs6.getString("cimage")));
            dImage.add(new Image ("/images/"+rs6.getString("dimage")));
        }  

    }
    //Function that sets questions for English if backup is Empty
    private void setQuestionNewEnglish (int limit, String dbTable) throws SQLException {
        PreparedStatement ps11 = con.prepareStatement("SELECT * from " +dbTable+ " order by rand() limit " +limit);
        ResultSet rs11 = ps11.executeQuery();

        while (rs11.next()) {
            Qid.add(rs11.getInt("id"));
            Qtext.add(rs11.getString("qtext"));
            Atext.add(rs11.getString("atext"));
            Btext.add(rs11.getString("btext"));
            Ctext.add(rs11.getString("ctext"));
            Dtext.add(rs11.getString("dtext"));
            realAns.add(rs11.getString("ans").charAt(0));
            Instruction.add(rs11.getString("instruction"));
        } 
    }
    
    private void preloaderThread (){ //The thread that switches this page to the main page
      
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(PreloaderController.class.getName()).log(Level.SEVERE, null, ex);
        }   
            
        //Checks if all resources needed for the exam are complete
        if (Qid.size()==180 && qImage.size()==120 && aImage.size()==120 && bImage.size()==120 &&
             cImage.size()==120 && dImage.size()==120 && realAns.size()==180 && Instruction.size()==180
             && Qtext.size()==180 && Atext.size()==180 && Btext.size()==180 && Ctext.size()==180 && Dtext.size()==180) {

            Platform.runLater(()-> { 

                //The transitioning
                try {
                    FadeTransition fd = new FadeTransition();
                    fd.setDuration(Duration.millis(0));
                    fd.setNode(preloader);
                    fd.setFromValue(1);
                    fd.setToValue(0);

                    fd.setOnFinished((ActionEvent event) -> {
                      Parent main = null;
                        try {
                            main = (SplitPane)FXMLLoader.load(getClass().getResource("main.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(PreloaderController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                      Scene mainScene = new Scene(main);
                      Stage mainStage = (Stage)preloader.getScene().getWindow();
                      mainStage.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
                      mainStage.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                      mainStage.setMaximized(true);
                      mainStage.setScene(mainScene);
                      mainStage.setResizable(true);
                    });

                    fd.play();
        
                } catch (Exception ex) {
                    Logger.getLogger(PreloaderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } 
                
    }
               
}

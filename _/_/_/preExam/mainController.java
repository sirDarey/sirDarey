package preExam;

import java.awt.Toolkit;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import static preExam.PreloaderController.*;
import static preExam.loginController.*;
import static preExam.finalController.finalPage;

public class mainController implements Initializable {
    
    //Declaring List that holds various UI components and variables
    public static ObservableList <Button> qNavigator = FXCollections.observableArrayList(); //For Questions Navigator
    public static  ObservableList<Toggle> rButtonAns = FXCollections.observableArrayList(); //For selected answers as Radio Button
    private final ObservableList<english> dataEnglish = FXCollections.observableArrayList(); //For English Table Under Review
    private final ObservableList<sub2> dataSub2 = FXCollections.observableArrayList();//For Sub2 Table Under Review
    private final ObservableList<sub3> dataSub3 = FXCollections.observableArrayList();  //For Sub3 Table Under Review
    private final ObservableList<sub4> dataSub4 = FXCollections.observableArrayList();   //For Sub4 Table Under Review
    private ObservableList <Character> answers =  FXCollections.observableArrayList(); //For Selected answers as characters
    
    public static Integer secs = 0;   //Secs for Timer
    
    //Some other Static vari=ables - Use is seen later
    public static int attemptStatus = 0, id1 = 0, id2 = 0, id3 = 0, id4=0, finishAttemptStatus = 0, enterStatus = 0,
                      score1 = 0, score2 = 0,  score3 = 0, score4 = 0;
    
    public static Button finish; //Static variable to emulate the finishAttemptButton
    @FXML
    private Label labelMin, labelSec, labelSubject, qNoEnglish, instruction, englishNo, passageNo,
                   qNoSub2, qNoSub3, qNoSub4, labelPreviewSub2,  labelPreviewSub3, labelPreviewSub4;
    
    @FXML
    private VBox main;  //VBox main Page
   
    // All Image Views for passport, questions and options in sub2, sub3 and sub4
    @FXML
    private ImageView passport, qImageSub2, aImageSub2, bImageSub2, cImageSub2, dImageSub2,
                      qImageSub3, aImageSub3, bImageSub3, cImageSub3, dImageSub3,
                      qImageSub4, aImageSub4, bImageSub4, cImageSub4, dImageSub4;
    
    //Tabs as represented in UI
    @FXML
    private Tab subject2, subject3, subject4, english, preview;
    
    @FXML
    private TabPane tabPane;   //The TabPane
   
    //All text that displays questions for 4 subjects and instructions for sub2, sub3 and sub4
    @FXML
    private Text qEnglish, qSub2, qSub3, qSub4, instruction2, instruction3, instruction4;
    
    //All RadioButtons Options for 4 Subjects
    @FXML    
    private RadioButton aEnglish, bEnglish, cEnglish, dEnglish, noneEnglish,
                        aSub2, bSub2, cSub2, dSub2, noneSub2,
                        aSub3, bSub3, cSub3, dSub3, noneSub3,
                        aSub4, bSub4, cSub4, dSub4, noneSub4;
    
    @FXML
    private Text passage;  //The text that displays Passage for English
    
    //All Questions Navigators for 4 subjects
    @FXML
    private Button qne1, qne2, qne3, qne4, qne5, qne6, qne7, qne8, qne9, qne10,
                   qne11, qne12, qne13, qne14, qne15, qne16, qne17, qne18, qne19, qne20,
                   qne21, qne22, qne23, qne24, qne25, qne26, qne27, qne28, qne29, qne30,
                   qne31, qne32, qne33, qne34, qne35, qne36, qne37, qne38, qne39, qne40,
                   qne41, qne42, qne43, qne44, qne45, qne46, qne47, qne48, qne49, qne50,
                   qne51, qne52, qne53, qne54, qne55, qne56, qne57, qne58, qne59, qne60,
            
                   qnSub2_1, qnSub2_2, qnSub2_3, qnSub2_4, qnSub2_5, qnSub2_6, qnSub2_7, qnSub2_8, qnSub2_9, qnSub2_10,
                   qnSub2_11, qnSub2_12, qnSub2_13, qnSub2_14, qnSub2_15, qnSub2_16, qnSub2_17, qnSub2_18, qnSub2_19, qnSub2_20,
                   qnSub2_21, qnSub2_22, qnSub2_23, qnSub2_24, qnSub2_25, qnSub2_26, qnSub2_27, qnSub2_28, qnSub2_29, qnSub2_30,
                   qnSub2_31, qnSub2_32, qnSub2_33, qnSub2_34, qnSub2_35, qnSub2_36, qnSub2_37, qnSub2_38, qnSub2_39, qnSub2_40,
            
                   qnSub3_1, qnSub3_2, qnSub3_3, qnSub3_4, qnSub3_5, qnSub3_6, qnSub3_7, qnSub3_8, qnSub3_9, qnSub3_10,
                   qnSub3_11, qnSub3_12, qnSub3_13, qnSub3_14, qnSub3_15, qnSub3_16, qnSub3_17, qnSub3_18, qnSub3_19, qnSub3_20,
                   qnSub3_21, qnSub3_22, qnSub3_23, qnSub3_24, qnSub3_25, qnSub3_26, qnSub3_27, qnSub3_28, qnSub3_29, qnSub3_30,
                   qnSub3_31, qnSub3_32, qnSub3_33, qnSub3_34, qnSub3_35, qnSub3_36, qnSub3_37, qnSub3_38, qnSub3_39, qnSub3_40,
            
                   qnSub4_1, qnSub4_2, qnSub4_3, qnSub4_4, qnSub4_5, qnSub4_6, qnSub4_7, qnSub4_8, qnSub4_9, qnSub4_10,
                   qnSub4_11, qnSub4_12, qnSub4_13, qnSub4_14, qnSub4_15, qnSub4_16, qnSub4_17, qnSub4_18, qnSub4_19, qnSub4_20,
                   qnSub4_21, qnSub4_22, qnSub4_23, qnSub4_24, qnSub4_25, qnSub4_26, qnSub4_27, qnSub4_28, qnSub4_29, qnSub4_30,
                   qnSub4_31, qnSub4_32, qnSub4_33, qnSub4_34, qnSub4_35, qnSub4_36, qnSub4_37, qnSub4_38, qnSub4_39, qnSub4_40,
            
                   buttonFinishAttempt;
    
    @FXML
    private ToggleGroup toggleEnglish, toggleSub2, toggleSub3, toggleSub4;
  
    @FXML
    private TableView tableViewEnglish, tableViewSub2, tableViewSub3, tableViewSub4;
    
    @FXML
        private TableColumn<?, ?> columnQNoEnglish,  columnStatusEnglish, columnQNoSub2,  columnStatusSub2,
                              columnQNoSub3, columnStatusSub3, columnQNoSub4, columnStatusSub4;
      
    @FXML
    private Label welcome;
    @FXML
    private TextFlow textFlow;
    
/*********** CALCULATOR VARIABLES ***********/
    @FXML
    private TextField inputScreen;
    
    @FXML
    private Label labelWorkings;
    
    private double x = 0, y = 0, ans = 0; 
    private int status = 1, recur = 0, saveLastEntry = 1;
    private String operator = "";
   
/**** END OF CALCULATOR VARIABLES **********/
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Adding all Questions Navigators to the qNavigator list
        qNavigator.addAll(qne1, qne2, qne3, qne4, qne5, qne6, qne7, qne8, qne9, qne10,
                   qne11, qne12, qne13, qne14, qne15, qne16, qne17, qne18, qne19, qne20,
                   qne21, qne22, qne23, qne24, qne25, qne26, qne27, qne28, qne29, qne30,
                   qne31, qne32, qne33, qne34, qne35, qne36, qne37, qne38, qne39, qne40,
                   qne41, qne42, qne43, qne44, qne45, qne46, qne47, qne48, qne49, qne50,
                   qne51, qne52, qne53, qne54, qne55, qne56, qne57, qne58, qne59, qne60,
        
                   qnSub2_1, qnSub2_2, qnSub2_3, qnSub2_4, qnSub2_5, qnSub2_6, qnSub2_7, qnSub2_8, qnSub2_9, qnSub2_10,
                   qnSub2_11, qnSub2_12, qnSub2_13, qnSub2_14, qnSub2_15, qnSub2_16, qnSub2_17, qnSub2_18, qnSub2_19, qnSub2_20,
                   qnSub2_21, qnSub2_22, qnSub2_23, qnSub2_24, qnSub2_25, qnSub2_26, qnSub2_27, qnSub2_28, qnSub2_29, qnSub2_30,
                   qnSub2_31, qnSub2_32, qnSub2_33, qnSub2_34, qnSub2_35, qnSub2_36, qnSub2_37, qnSub2_38, qnSub2_39, qnSub2_40,
        
                   qnSub3_1, qnSub3_2, qnSub3_3, qnSub3_4, qnSub3_5, qnSub3_6, qnSub3_7, qnSub3_8, qnSub3_9, qnSub3_10,
                   qnSub3_11, qnSub3_12, qnSub3_13, qnSub3_14, qnSub3_15, qnSub3_16, qnSub3_17, qnSub3_18, qnSub3_19, qnSub3_20,
                   qnSub3_21, qnSub3_22, qnSub3_23, qnSub3_24, qnSub3_25, qnSub3_26, qnSub3_27, qnSub3_28, qnSub3_29, qnSub3_30,
                   qnSub3_31, qnSub3_32, qnSub3_33, qnSub3_34, qnSub3_35, qnSub3_36, qnSub3_37, qnSub3_38, qnSub3_39, qnSub3_40,
        
                   qnSub4_1, qnSub4_2, qnSub4_3, qnSub4_4, qnSub4_5, qnSub4_6, qnSub4_7, qnSub4_8, qnSub4_9, qnSub4_10,
                   qnSub4_11, qnSub4_12, qnSub4_13, qnSub4_14, qnSub4_15, qnSub4_16, qnSub4_17, qnSub4_18, qnSub4_19, qnSub4_20,
                   qnSub4_21, qnSub4_22, qnSub4_23, qnSub4_24, qnSub4_25, qnSub4_26, qnSub4_27, qnSub4_28, qnSub4_29, qnSub4_30,
                   qnSub4_31, qnSub4_32, qnSub4_33, qnSub4_34, qnSub4_35, qnSub4_36, qnSub4_37, qnSub4_38, qnSub4_39, qnSub4_40);
        
        int j = 0;
        while (j <180){
            if (j<60){
                rButtonAns.add(noneEnglish); //Initially adding Hidden NONE RadoButton (In English) to rButtonAns List
                j++;
            } else if (j>=60 && j<100){
                rButtonAns.add(noneSub2); //Initially adding Hidden NONE RadoButton (In Sub2) to rButtonAns List
                j++;
            } else if (j>=100 && j<140){
                rButtonAns.add(noneSub3);   //Initially adding Hidden NONE RadoButton (In Sub3) to rButtonAns List
                j++;
            } else {
                rButtonAns.add(noneSub4);  //Initially adding Hidden NONE RadoButton (In Sub4) to rButtonAns List
                j++;
            }
            
        }
       
        try { //Trting to get passport file name from database
                
            PreparedStatement ps = con.prepareStatement("select pp from login where un = ? and pw = ?");
            ps.setString(1, un);
            ps.setString(2, pw);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                File sourceFile = new File ("C:\\images\\"+rs.getString("pp")); //Retrieivng the pix from file system that matches this retrieved file name
                Image image = new Image(sourceFile.toURI().toString(), passport.getFitWidth(), passport.getFitHeight(), true, true);
                passport.setImage(image);  //Setting the passport image 
            }
            
            //Retrieving selected answers from database
            PreparedStatement ps2 = con.prepareStatement("SELECT ans FROM answers WHERE un = ? and pw = ?");

            ps2.setString(1, un);
            ps2.setString(2, pw);

            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) { // If found for this candidate, that means this candidates has answered at least one question
                String answers = rs2.getString("ans");
                Scanner scanAnswers = new Scanner (answers); //Using delimeters to split the retrieved string in form of array
                scanAnswers.useDelimiter("[^a-d*]+");

                for (int i=0; i<180; i++) {
                    this.answers.add(scanAnswers.next().charAt(0)); // Saving the split array to answers list               
                }
                
                //1. Setting All RadioButtons to previously selected answers for English 
                //2. Updating rButtonAns List for each selected option
                //3. Styling the QuestionsNavigators for answered questions
                for (int i=0; i<60; i++) {
                    switch (this.answers.get(i)) {
                        case 'a':
                            aEnglish.setSelected(true);
                            rButtonAns.set(i, aEnglish);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'b':
                            bEnglish.setSelected(true);
                            rButtonAns.set(i, bEnglish);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'c':
                            cEnglish.setSelected(true);
                            rButtonAns.set(i, cEnglish);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'd':
                            dEnglish.setSelected(true);
                            rButtonAns.set(i, dEnglish);  
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                        default:
                            break;
                    }
                }
                
                //Steps 1, 2, 3 above for Sub2
                for (int i=60; i<100; i++) {
                    switch (this.answers.get(i)) {
                        case 'a':
                            aSub2.setSelected(true);
                            rButtonAns.set(i, aSub2);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'b':
                            bSub2.setSelected(true);
                            rButtonAns.set(i, bSub2);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'c':
                            cSub2.setSelected(true);
                            rButtonAns.set(i, cSub2);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'd':
                            dSub2.setSelected(true);
                            rButtonAns.set(i, dSub2);  
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                        default:
                            break;
                    }
                }
                //Steps 1, 2, 3 above for Sub3
                for (int i=100; i<140; i++) {
                    switch (this.answers.get(i)) {
                        case 'a':
                            aSub3.setSelected(true);
                            rButtonAns.set(i, aSub3);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'b':
                            bSub3.setSelected(true);
                            rButtonAns.set(i, bSub3);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'c':
                            cSub3.setSelected(true);
                            rButtonAns.set(i, cSub3);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'd':
                            dSub3.setSelected(true);
                            rButtonAns.set(i, dSub3);  
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                        default:
                            break;
                    }
                }
                //Steps 1, 2, 3 above for Sub4
                for (int i=140; i<180; i++) {
                    switch (this.answers.get(i)) {
                        case 'a':
                            aSub4.setSelected(true);
                            rButtonAns.set(i, aSub4);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'b':
                            bSub4.setSelected(true);
                            rButtonAns.set(i, bSub4);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'c':
                            cSub4.setSelected(true);
                            rButtonAns.set(i, cSub4);
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                            break;
                        case 'd':
                            dSub4.setSelected(true);
                            rButtonAns.set(i, dSub4);  
                            qNavigator.get(i).getStyleClass().add("qbuttonf");
                        default:
                            break;
                    }
                }
                
                preview.setDisable(false);
                
            } else {  //Executes if NO answers Backup is found; thus, the student has not answered any question yet
                for (int i=0; i<180; i++) {
                    answers.add('*'); //Therefore, add * to answers List
                }
            }

        } catch (SQLException ex) {
                
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        welcome.setText("Welcome, " + name); //Setting Welcome Text

        subject2.setText(setSubject(sub2)); //Setting Sub2 Tab Name to the right subject
        subject3.setText(setSubject(sub3)); //Setting Sub3 Tab Name to the right subject
        subject4.setText(setSubject(sub4)); //Setting Sub4 Tab Name to the right subject

        passage.setText(passages.get(0));   //Setting the passage field to the right passage

        setQuestions1 (); //Function Call that set questions for English
        
        Thread timerThread = new Thread(() -> { //Starting Thread that handles timer
            try {
                this.handleTimerThread();
            } catch (SQLException ex) {
                Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        timerThread.start();    

        finish = buttonFinishAttempt;  // Setting finish to emulate the buttonFinishAttempt Button
        
    }  //End of Initialization
    
    //Function that saves the IDs of the random questions generated to "backup" and "psgid"
    private void saveRand (){
        try{
            PreparedStatement ps = con.prepareStatement("UPDATE login SET backup=?, psgid=? WHERE un = ? and pw = ?");

            ps.setString(1, Qid.toString());
            ps.setString(2, realPsgId.get(0).toString()+realPsgId.get(1).toString());
            ps.setString(3, un);
            ps.setString(4, pw);

            ps.executeUpdate();

        } catch (SQLException e) {
             Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //Function that saves answers anytime an option is clicked
    public void setSavedAns () {
        
        //Intial check if this is first time of clicking an option for this particular login, irrespective of if you've logged in before now

        if (attemptStatus == 0) { //Executes if true
                                //This can only execute once for each  new login
            try{ //Checks if you've answered any question now or before
                PreparedStatement ps = con.prepareStatement("SELECT ans FROM answers WHERE un = ? and pw = ?");

                ps.setString(1, un);
                ps.setString(2, pw);

                ResultSet rs = ps.executeQuery();

                if (rs.next()){ //If true, just update previously saved answers
                    attemptStatus = 1;
                    
                    PreparedStatement ps3 = con.prepareStatement("UPDATE answers SET ans = ? WHERE un = ? and pw = ?");

                    ps3.setString(1, answers.toString());
                    ps3.setString(2, un);
                    ps3.setString(3, pw);

                    ps3.executeUpdate();
                    
                } else { //otherwise, insert new answers to the database
                    
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO answers (un, pw, ans, score1, score2,  score3, score4, tscore) VALUES "
                        + "(?,?,?,?,?,?,?,?)");

                    ps2.setString(1, un);
                    ps2.setString(2, pw);
                    ps2.setString(3, answers.toString());
                    ps2.setInt(4, 0);
                    ps2.setInt(5, 0);
                    ps2.setInt(6, 0);
                    ps2.setInt(7, 0);
                    ps2.setInt(8, 0);

                    ps2.execute();
                    
                    saveRand();
                    attemptStatus = 1;
                }      
                
            } catch (SQLException e) {
                Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
            }
            
        } else { //Executes if this is not the first time of clicking any option for this login
            try{ // So, just keep on updating. This is the part that will always execute after the first one above till this candidate submits
                PreparedStatement ps3 = con.prepareStatement("UPDATE answers SET ans = ? WHERE un = ? and pw = ?");

                    ps3.setString(1, answers.toString());
                    ps3.setString(2, un);
                    ps3.setString(3, pw);

                ps3.executeUpdate();

            } catch (SQLException e) {
                 Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    //Function that sets subjects name for each tab
    private String setSubject (String subject){
        String returnSubject;
        
        switch (subject) {
            case "literature" : returnSubject = "LITERATUE IN ENGLISH"; break;
            case "crs" : returnSubject = "CHRISTIAN RELIGIOUS STUDIES"; break;
            case "accounts" : returnSubject = "FINANCIAL ACCOUNTING"; break;
            default: returnSubject = subject; break;
        }
        
        return returnSubject.toUpperCase();
    }
    
    //Function that sets questions for English
    public void setQuestions1 () {
            
            id1++;  //Increment id1, a static variable by 1 
        if (id1 <6){   //If id1 is from is within the range 1 - 5,execute
            passageNo.setText("PASSAGE I");
            englishNo.setText(" 1 - 5"); 
            passage.setText(passages.get(0));
                    
            setVariables1(id1); // Call to function that sets the needed resources
            
        } else if (6<=id1 && id1<11) { //If id1 is from is within the range 6 - 10,execute
            passageNo.setText("PASSAGE II");
            englishNo.setText(" 6 - 10");
            passage.setText(passages.get(1));
              
            setVariables1(id1); // Call to function that sets the needed resources
         
        } else if (11<=id1 && id1<50) { //If id1 is from is within the range 11 - 50,execute
            passageNo.setText("");
            englishNo.setText("");
            setVariables1(id1); // Call to function that sets the needed resources
         
        } else if (50<=id1 && id1<61) { //If id1 is from is within the range 51 - 60,execute
           passageNo.setText("");
           englishNo.setText("");
           setVariables1(id1); // Call to function that sets the needed resources
            
        } else { // Otherwise, switch this tab to sub2
            tabPane.getSelectionModel().select(subject2);
            id1--;
        }
    }
    //Set Resources for English as required
    private void setVariables1 (int qNo) {
            
        qNoEnglish.setText(String.valueOf(qNo));
        
        qEnglish.setText(Qtext.get(qNo-1));
        aEnglish.setText(Atext.get(qNo-1));
        bEnglish.setText(Btext.get(qNo-1));
        cEnglish.setText(Ctext.get(qNo-1));
        dEnglish.setText(Dtext.get(qNo-1)); 
        instruction.setText(Instruction.get(qNo-1));        
    }
    //Function that sets questions for Sub2
    public void setQuestions2 () throws IOException { 
        
        id2++; //Increment id2, a static variable by 1 
        
        if (id2 < 41){   //If id2 is from is within the range 1 - 40,execute
            
            setVariables2 (id2); // Call to function that sets the needed resources
            
        } else {// Otherwise, switch this tab to sub3
            tabPane.getSelectionModel().select(subject3);
            id2--;
        }
    }
    //Set Resources for Sub2 as required
    private void setVariables2 (int qNo) throws IOException {
    
        qNoSub2.setText(String.valueOf(qNo));

        qSub2.setText(Qtext.get(qNo+59));
        aSub2.setText(Atext.get(qNo+59));
        bSub2.setText(Btext.get(qNo+59));
        cSub2.setText(Ctext.get(qNo+59));
        dSub2.setText(Dtext.get(qNo+59));
        instruction2.setText(Instruction.get(qNo+59));
                
        qImageSub2.setImage(qImage.get(qNo-1));
        aImageSub2.setImage(aImage.get(qNo-1));
        bImageSub2.setImage(bImage.get(qNo-1));
        cImageSub2.setImage(cImage.get(qNo-1));
        dImageSub2.setImage(dImage.get(qNo-1));       
    }
    //Function that sets questions for Sub3
    public void setQuestions3 () throws IOException { 
        
        id3++; //Increment id3, a static variable by 1 
        
        if (id3 < 41){   //If id3 is from is within the range 1 - 40,execute
            
            setVariables3 (id3); // Call to function that sets the needed resources
            
        } else {// Otherwise, switch this tab to sub4
            tabPane.getSelectionModel().select(subject4);
            id3--;
        }
    }
    //Set Resources for Sub3 as required
    private void setVariables3 (int qNo) throws IOException {
    
        qNoSub3.setText(String.valueOf(qNo));

        qSub3.setText(Qtext.get(qNo+99));
        aSub3.setText(Atext.get(qNo+99));
        bSub3.setText(Btext.get(qNo+99));
        cSub3.setText(Ctext.get(qNo+99));
        dSub3.setText(Dtext.get(qNo+99));
        instruction3.setText(Instruction.get(qNo+99));
                
        qImageSub3.setImage(qImage.get(qNo+39));
        aImageSub3.setImage(aImage.get(qNo+39));
        bImageSub3.setImage(bImage.get(qNo+39));
        cImageSub3.setImage(cImage.get(qNo+39));
        dImageSub3.setImage(dImage.get(qNo+39));
    }
    //Function that sets questions for Sub4
    public void setQuestions4 () throws IOException { 
        
        id4++; //Increment id4, a static variable by 1 
        
        if (id4 < 41){   //If id4 is from is within the range 1 - 40,execute
            
            setVariables4 (id4);  // Call to function that sets the needed resources
            
        } else {// Otherwise, switch this tab to preview
            tabPane.getSelectionModel().select(preview);
            id4--;
        }
    }
    //Set Resources for Sub4 as required
    private void setVariables4 (int qNo) throws IOException {
    
        qNoSub4.setText(String.valueOf(qNo));

        qSub4.setText(Qtext.get(qNo+139));
        aSub4.setText(Atext.get(qNo+139));
        bSub4.setText(Btext.get(qNo+139));
        cSub4.setText(Ctext.get(qNo+139));
        dSub4.setText(Dtext.get(qNo+139));
        instruction4.setText(Instruction.get(qNo+139));
                
        qImageSub4.setImage(qImage.get(qNo+79));
        aImageSub4.setImage(aImage.get(qNo+79));
        bImageSub4.setImage(bImage.get(qNo+79));
        cImageSub4.setImage(cImage.get(qNo+79));
        dImageSub4.setImage(dImage.get(qNo+79));        
    }
    
    //Get database connection
    //Here, IP is that of the server system
    private void getConnection () {
        try {
            con = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/preexam?useSSL=false" 
                    + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "phsps", "5236");
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("CONNECTION LOST \n  LOGOUT AT THE TOP RIGHT CORNER OF THE SCREEN AND \n"
                    + "CONTACT THE ADMIN");
            alert.setHeaderText(null);
            alert.showAndWait();
            
            tabPane.setDisable(true);
        }  
    }
    
    private void saveTimer () throws SQLException {
        
        try{
            PreparedStatement ps = con.prepareStatement("UPDATE login SET timermin = ? WHERE un = ? AND pw = ?");

            ps.setInt(1, mins);
            ps.setString(2, un);
            ps.setString(3, pw);

            ps.executeUpdate();

        } catch (SQLException e) { 
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }
        
        
    }
    
    private void  handleTimerThread () throws SQLException{
        while (finishAttemptStatus ==0) {
            Platform.runLater(()-> {
                labelSec.setText(secs.toString());
                labelMin.setText(mins.toString());
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            }


            if (mins>0) {
                if (secs>0) {
                    secs--;
                } else {
                    saveTimer();
                    secs = 59;
                    mins--;
                }
            } else {
                //Once timer has 1 min left, it turns red
                labelMin.setStyle("-fx-text-fill: red"); 
                labelSec.setStyle("-fx-text-fill: red");
                
                if (secs>0) {
                    secs--;
                } else {
                    
                    preview.setDisable(false);                    
                    english.setDisable(true);
                    subject2.setDisable(true);
                    subject3.setDisable(true);
                    subject4.setDisable(true);
                    break;
                }
            }   
        }
    }    
    
   @FXML //When previous is clicked in English
    private void previousEnglish(ActionEvent event) throws IOException {
        
        if (id1 > 1) {
        
            rButtonAns.set(id1-1, toggleEnglish.getSelectedToggle()); //  Saves Selected option for this question before going to previous
            
            id1-=2;
            
            rButtonAns.get(id1).setSelected(true); //  Sets Selected option for this question on reaching previous
            
            setQuestions1(); 

            if (id1 <=10) {
                textFlow.setPrefHeight(Region.USE_COMPUTED_SIZE); // When id <= 10, show passage area
            }
        } else { //When id = 0, show this alert
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("YOU HAVE REACHED THE FIRST QUESTION");
            alert.setHeaderText(null);
            alert.showAndWait();
        } 
    }

    @FXML  //When next is clicked in English
    private void nextEnglish(ActionEvent event) throws IOException {
        //  Saves Selected option for this question before going next
        rButtonAns.set(id1-1, toggleEnglish.getSelectedToggle());
        
        if (id1<60) {
            rButtonAns.get(id1).setSelected(true);//  Sets Selected option for this questionon on reaching next
        } 
          
        setQuestions1();
       
        if (id1 >= 11) {
            textFlow.setPrefHeight(0); // if id >= 11, Close passage
            passage.setText(""); //Set passage title to empty
        }  
         
    }
    
    @FXML //When previous is clicked in sub2
    private void previousSub2(ActionEvent event) throws IOException {
        
        if (id2 > 1) {
        
            rButtonAns.set(id2+59, toggleSub2.getSelectedToggle());
            
            id2-=2;
            
            rButtonAns.get(id2+60).setSelected(true);
            
            setQuestions2();

        } else {
            tabPane.getSelectionModel().select(english);
        } 
    }

    @FXML //When next is clicked in sub2
    private void nextSub2 (ActionEvent event) throws IOException {
        rButtonAns.set(id2+59, toggleSub2.getSelectedToggle());
        
        if (id2<40) {
            rButtonAns.get(id2+60).setSelected(true);
        } 
          
        setQuestions2();  
    }  
    
    @FXML //When previous is clicked in sub3
    private void previousSub3(ActionEvent event) throws IOException {
        if (id3 > 1) {
        
            rButtonAns.set(id3+99, toggleSub3.getSelectedToggle());
            
            id3-=2;
            
            rButtonAns.get(id3+100).setSelected(true);
            
            setQuestions3();

        } else {
            tabPane.getSelectionModel().select(subject2);
        } 
    }

    @FXML  //When next is clicked in sub3
    private void nextSub3(ActionEvent event) throws IOException {
        rButtonAns.set(id3+99, toggleSub3.getSelectedToggle());
        
        if (id3<40) {
            rButtonAns.get(id3+100).setSelected(true);
        } 
          
        setQuestions3();  
    }
    
     @FXML //When previous is clicked in sub4
    private void previousSub4(ActionEvent event) throws IOException {
        if (id4 > 1) {
        
            rButtonAns.set(id4+139, toggleSub4.getSelectedToggle());
            
            id4-=2;
            
            rButtonAns.get(id4+140).setSelected(true);
            
            setQuestions4();

        } else {
            tabPane.getSelectionModel().select(subject3);
        } 
    }

    @FXML //When next is clicked in sub4
    private void nextSub4(ActionEvent event) throws IOException {
        rButtonAns.set(id4+139, toggleSub4.getSelectedToggle());
        
        if (id4<40) {
            rButtonAns.get(id4+140).setSelected(true);
        } 
          
        setQuestions4();  
    }
    
   @FXML //Same function as the next and previous above
    private void QNavEng(ActionEvent event) throws IOException {
        
        rButtonAns.set(id1-1, toggleEnglish.getSelectedToggle());
        
        for (int i=0; i<60; i++) {
            if (qNavigator.get(i).isFocused()){
               
                id1 = i;
                
                rButtonAns.get(id1).setSelected(true);
                
                setQuestions1();
            }
        }  
        
        if (id1 >= 11) {
            textFlow.setPrefHeight(0);
            passage.setText("");
        } else
            textFlow.setPrefHeight(Region.USE_COMPUTED_SIZE);
    }

    @FXML //Same function as the next and previous above
    private void QNavSub2(ActionEvent event) throws IOException {
        rButtonAns.set(id2+59, toggleSub2.getSelectedToggle());
        
        for (int i=0; i<40; i++) {
            if (qNavigator.get(i+60).isFocused()){
               
                id2 = i;
                
                rButtonAns.get(id2+60).setSelected(true);
                
                setQuestions2();
            }
        }  
    }
    
    @FXML //Same function as the next and previous above
    private void QNavSub3(ActionEvent event) throws IOException {
        rButtonAns.set(id3+99, toggleSub3.getSelectedToggle());
        
        for (int i=0; i<40; i++) {
            if (qNavigator.get(i+100).isFocused()){
               
                id3 = i;
                
                rButtonAns.get(id3+100).setSelected(true);
                
                setQuestions3();
            }
        }  
    }

    @FXML //Same function as the next and previous above
    private void QNavSub4(ActionEvent event) throws IOException {
        rButtonAns.set(id4+139, toggleSub4.getSelectedToggle());
        
        for (int i=0; i<40; i++) {
            if (qNavigator.get(i+140).isFocused()){
               
                id4 = i;
                
                rButtonAns.get(id4+140).setSelected(true);
                
                setQuestions4();
            }
        }  
    }

    @FXML //Executes when the English tab is clicked
    private void toEnglish(Event event) {       
        labelSubject.setText("use of english");         
    }

    @FXML //Executes when the Sub2 tab is clicked
    private void toSub2(Event event) throws IOException {        
        labelSubject.setText(setSubject(sub2));  
        if (id2==0)
            setQuestions2();
    }

    @FXML //Executes when the Sub3 tab is clicked
    private void toSub3(Event event) throws IOException {
        labelSubject.setText(setSubject(sub3));   
        if (id3==0)
            setQuestions3();   
    }

    @FXML //Executes when the Sub4 tab is clicked
    private void toSub4(Event event) throws IOException {
        labelSubject.setText(setSubject(sub4));  
        if (id4==0)
            setQuestions4();
    }
    
    @FXML //Executes when any of the options is clicked in English
    private void saveEnglishAns(ActionEvent event) {
        
        preview.setDisable(false); //Enables Preview which was disabled initially
        
        try {
            if (con.isValid(0)) {  // Revalidates connection
                
                for (int i=0; i<60;  i++) {
                    if (id1==i+1) {
                        
                        qNavigator.get(i).getStyleClass().add("qbuttonf"); //chages color of Navigator for this question
                        
                        
                        //Decision to save which option selected to answers
                        if (aEnglish.isSelected()) {
                            answers.set(id1-1, 'a'); 
                        } else if (bEnglish.isSelected()) {
                            answers.set(id1-1, 'b');
                        } else if (cEnglish.isSelected()) {
                            answers.set(id1-1, 'c');
                        } else if (dEnglish.isSelected()) {
                            answers.set(id1-1, 'd');
                        }
                        
                        setSavedAns(); //Funtion call to save answers to database
                    }
                }
            }  else
                getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        } 
                
    }

    @FXML //Executes when any of the options is clicked in sub2
    private void saveSub2Ans(ActionEvent event) {
        
        preview.setDisable(false);
        
        try {
            if (con.isValid(0)){
                
                for (int i=0; i<40;  i++) {
                    if (id2==i+1) {
                        
                        qNavigator.get(i+60).getStyleClass().add("qbuttonf");
                        
                        if (aSub2.isSelected()) {
                            answers.set(id2+59, 'a');
                        } else if (bSub2.isSelected()) {
                            answers.set(id2+59, 'b');
                        } else if (cSub2.isSelected()) {
                            answers.set(id2+59, 'c');
                        } else if (dSub2.isSelected()) {
                            answers.set(id2+59, 'd');
                        }
                        
                        setSavedAns();
                        
                    }
                }
            }   else 
                    getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            getConnection();
        }
    }

    @FXML //Executes when any of the options is clicked in sub3
    private void saveSub3Ans(ActionEvent event) {
       
        preview.setDisable(false);
        
        try {
            if (con.isValid(0)) {
                
                for (int i=0; i<40;  i++) {
                    if (id3==i+1) {
                        
                        qNavigator.get(i+100).getStyleClass().add("qbuttonf");
                        
                        if (aSub3.isSelected()) {
                            answers.set(id3+99, 'a');
                        } else if (bSub3.isSelected()) {
                            answers.set(id3+99, 'b');
                        } else if (cSub3.isSelected()) {
                            answers.set(id3+99, 'c');
                        } else if (dSub3.isSelected()) {
                            answers.set(id3+99, 'd');
                        }
                        
                        setSavedAns();
                    }
                }
            } else 
                getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            getConnection();
        }
    }
    
    @FXML //Executes when any of the options is clicked in sub4
    private void saveSub4Ans(ActionEvent event) {
       
        preview.setDisable(false);
        
        try {
            if (con.isValid(0)) {
                
                for (int i=0; i<40;  i++) {
                    if (id4==i+1) {
                        
                        qNavigator.get(i+140).getStyleClass().add("qbuttonf");
                        
                        if (aSub4.isSelected()) {
                            answers.set(id4+139, 'a');
                        } else if (bSub4.isSelected()) {
                            answers.set(id4+139, 'b');
                        } else if (cSub4.isSelected()) {
                            answers.set(id4+139, 'c');
                        } else if (dSub4.isSelected()) {
                            answers.set(id4+139, 'd');
                        }
                        
                        setSavedAns();
                    }
                }
            } else 
                getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            getConnection();
        }
    }
    
    @FXML //Executes when the preview tab is clicked
    private void toPreview(Event event) {
        setData();
        loadData();
        labelSubject.setText("Preview");        
    }

    @FXML
    private void previousPreview(ActionEvent event) {
        tabPane.getSelectionModel().select(subject4);
    }

    //For Preview Page
    private void setData () {
        columnQNoEnglish.setCellValueFactory(new PropertyValueFactory<>("QNo"));
        columnStatusEnglish.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        columnQNoSub2.setCellValueFactory(new PropertyValueFactory<>("QNo"));
        columnStatusSub2.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        columnQNoSub3.setCellValueFactory(new PropertyValueFactory<>("QNo"));
        columnStatusSub3.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        columnQNoSub4.setCellValueFactory(new PropertyValueFactory<>("QNo"));
        columnStatusSub4.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    //For Preview Page
    private void loadData () {
        
        dataEnglish.clear();
        dataSub2.clear();
        dataSub3.clear();
        dataSub4.clear();
        
        labelPreviewSub2.setText(setSubject(sub2));
        labelPreviewSub3.setText(setSubject(sub3));
        labelPreviewSub4.setText(setSubject(sub4));
        
        for (int i=0; i<60; i++) {
            if (answers.get(i).equals('*')) {
                dataEnglish.add(new english(i + 1, "No Answer Chosen"));
            } else {
                dataEnglish.add(new english(i + 1, "Answer Saved"));
            }
        }
        
        for (int i=0; i<40; i++) {
            if (answers.get(i+60).equals('*')) {
                 dataSub2.add(new sub2(i + 1, "No Answer Chosen"));
             } else {
                 dataSub2.add(new sub2(i + 1, "Answer Saved"));
             }
            
            if (answers.get(i+100).equals('*')) {
                 dataSub3.add(new sub3(i + 1, "No Answer Chosen"));
             } else {
                 dataSub3.add(new sub3(i + 1, "Answer Saved"));
             }
            
            if (answers.get(i+140).equals('*')) {
                 dataSub4.add(new sub4(i + 1, "No Answer Chosen"));
             } else {
                 dataSub4.add(new sub4(i + 1, "Answer Saved"));
             }
        }       
       
        tableViewEnglish.setItems(dataEnglish);
        tableViewSub2.setItems(dataSub2);
        tableViewSub3.setItems(dataSub3);
        tableViewSub4.setItems(dataSub4);        
    }
    
    @FXML //Executes when finishAttempt button is clicked
    private void finishAttempt(ActionEvent event) throws IOException {
        //Check if attemptStatus is true for this login
        //It's possible that a candidate is  done but yet to submit and then something happens
        //Thus, he/she can relogin and then submit
        try{ 
            PreparedStatement ps = con.prepareStatement("SELECT ans FROM answers WHERE un = ? and pw = ?");

            ps.setString(1, un);
            ps.setString(2, pw);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){ //If false, set to 1(True)
                attemptStatus = 1;
            }
        } catch (SQLException e){}
        
        if (attemptStatus == 0) { //Alert shows if attemptStatus is 0
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("NO QUESTION ATTEMPTED");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (attemptStatus == 1 && finishAttemptStatus == 0) { // navigate to preview where final submission is done
            tabPane.getSelectionModel().select(preview);
        } else { // Otherwise, this action wil be taken as logout since a single button controls logging out and finishing attempt
            Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("ARE YOU SURE YOU WANNA LOGOUT");
            alert.setHeaderText(null);
            alert.setTitle("Confirm LOGOUT");
            Optional <ButtonType> result =  alert.showAndWait();  

            if (result.get() == ButtonType.OK) {
                 
                //Clearing up stuffs before logging out
                qNavigator.clear(); rButtonAns.clear(); psgIdbackup = null; realPsgId.clear();
                dataEnglish.clear(); dataSub2.clear(); dataSub3.clear(); dataSub4.clear(); 
                qImage.clear(); aImage.clear(); bImage.clear(); answers.clear();
                cImage.clear(); dImage.clear(); Qid.clear(); Qtext.clear(); Atext.clear(); 
                Btext.clear(); Ctext.clear(); Dtext.clear(); realAns.clear(); Instruction.clear();
                secs = 0; attemptStatus = 0; id1 = 0; id2 = 0; id3 = 0; id4=0; finishAttemptStatus = 0; enterStatus = 0;
                score1 = 0; score2 = 0;  score3 = 0; score4 = 0;
                answers = null; passages.clear(); name = null; qNobackup = null;
                sub2 = null; sub3 = null; sub4 = null; mins = 0;
                
                Node n = (Node) event.getSource();
                Stage stage = (Stage) n.getScene().getWindow();
                stage.close();     
                
                FXMLLoader loader1 = new FXMLLoader();
                loader1.setLocation(getClass().getResource("login.fxml"));
                Parent mainPage = loader1.load();
                Scene mainScene = new Scene(mainPage);
                Stage mainWindow = new Stage();
                mainWindow.setMaximized(true);
                mainWindow.setResizable(false);
                mainWindow.setScene(mainScene);
                mainWindow.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
                mainWindow.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());

                mainWindow.show();
                               
            }
            
        }
    }
    
    //Calculate Scores before submitting
    private void doSubmit () {
       
        for (int i=0; i<60; i++) {
           if (answers.get(i).equals(realAns.get(i))) {
                score1+=1;
            } 
        }

        for (int i=60; i<100; i++) {
           if (answers.get(i).equals(realAns.get(i))) {
                score2+=1;
            } 
        }
        
        for (int i=100; i<140; i++) {
           if (answers.get(i).equals(realAns.get(i))) {
                score3+=1;
            } 
        }
        
        for (int i=140; i<180; i++) {
           if (answers.get(i).equals(realAns.get(i))) {
                score4+=1;
            } 
        }
        
        score1 = (int) Math.floor((double)(score1*5/3)+1);
        score2 = (int) Math.floor((double)(score2*5/2)+1);
        score3 = (int) Math.floor((double)(score3*5/2)+1);
        score4 = (int) Math.floor((double)(score4*5/2)+1);

        finishAttemptStatus = 1;

        //Switching  to the final page after submission where result is displayed
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("final.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }
        
        //Updating database records
        try{
            
            PreparedStatement ps = con.prepareStatement("UPDATE login SET submitstatus = ? WHERE un = ? AND pw = ?");

            ps.setInt(1, 1);
            ps.setString(2, un);
            ps.setString(3, pw);

            ps.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("UPDATE answers SET score1=?, score2=?, score3=?, score4=?, tscore=? WHERE un = ? AND pw = ?");

            ps2.setInt(1, score1);
            ps2.setInt(2, score2);
            ps2.setInt(3, score3);
            ps2.setInt(4, score4);
            ps2.setInt(5, score1+score2+score3+score4);
            ps2.setString(6, un);
            ps2.setString(7, pw);

            ps2.executeUpdate();
            
            PreparedStatement ps3 = con.prepareStatement("INSERT into scores (un, name, score1, score2, score3, score4,  tscore, sub2, sub3, sub4) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?)");
            
            ps3.setString(1, un);
            ps3.setString(2, name);
            ps3.setInt(3, score1);
            ps3.setInt(4, score2);
            ps3.setInt(5, score3);
            ps3.setInt(6, score4);
            ps3.setInt(7, score1+score2+score3+score4);
            ps3.setString(8, sub2);
            ps3.setString(9, sub3);
            ps3.setString(10, sub4);
            
            ps3.execute();

        } catch (SQLException e) { 
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }

        finalPage = loader.getRoot();
        FadeTransition fd = new FadeTransition();
        fd.setDuration(Duration.millis(2000));
        fd.setNode(main);
        fd.setFromValue(1);
        fd.setToValue(0);

        main.getChildren().setAll(finalPage);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.play();
    }

    @FXML //Executes once submit is clicked
    private void submit(ActionEvent event) {
        try {
            if (con.isValid(0)) {
                Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("ARE YOU SURE YOU WANNA SUBMIT");
                alert.setHeaderText(null);
                alert.setTitle("Confirm Submission");
                Optional <ButtonType> result =  alert.showAndWait();
                
                if (result.get() == ButtonType.OK) {      
                    doSubmit(); //Calls the doSubmit Function which calculates the scores 
                } 
            } else 
                getConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            getConnection();
        }
    }
    
    @FXML //Executes when the logout btn at the top right corner is clicked
    private void logout(ActionEvent event) throws IOException {
        
        Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("ARE YOU SURE YOU WANNA LOGOUT");
        alert.setHeaderText(null);
        alert.setTitle("Confirm LOGOUT");
        Optional <ButtonType> result =  alert.showAndWait();  

        if (result.get() == ButtonType.OK) {

            Node n = (Node) event.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
              
            //Cleaning up stuffs
            qNavigator.clear(); rButtonAns.clear(); psgIdbackup = null; realPsgId.clear();
            dataEnglish.clear(); dataSub2.clear(); dataSub3.clear(); dataSub4.clear(); 
            qImage.clear(); aImage.clear(); bImage.clear(); answers.clear();
            cImage.clear(); dImage.clear(); Qid.clear(); Qtext.clear(); Atext.clear(); 
            Btext.clear(); Ctext.clear(); Dtext.clear(); realAns.clear(); Instruction.clear();
            secs = 0; attemptStatus = 0; id1 = 0; id2 = 0; id3 = 0; id4=0; finishAttemptStatus = 0; enterStatus = 0;
            score1 = 0; score2 = 0;  score3 = 0; score4 = 0;
            answers = null; passages.clear(); name = null; qNobackup = null;
            sub2 = null; sub3 = null; sub4 = null; mins = 0;

            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(getClass().getResource("login.fxml"));
            Parent mainPage = loader1.load();
            Scene mainScene = new Scene(mainPage);
            Stage mainWindow = new Stage();
            mainWindow.setMaximized(true);
            mainWindow.setResizable(false);
            mainWindow.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
            mainWindow.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
            mainWindow.setScene(mainScene);

            mainWindow.show();

        }
    }
    
    
    
    /************
     * CALCULATOR FUNCTIONS
     *************************/
    
    @FXML
    private void screenKeyEvent(KeyEvent event) {
        event.consume();
    }
    
    private Double otherOperations () {
        recur = 0; status = 1;
        x = Double.parseDouble(inputScreen.getText());
        return x;
    }

    @FXML
    private void ClickedPercent(ActionEvent event) {
        inputScreen.setText(String.valueOf(otherOperations ()/100));
        labelWorkings.setText(String.valueOf(x) +"% =");
        x = Double.parseDouble(inputScreen.getText());
    }

    @FXML
    private void ClickedClear(ActionEvent event) {
        x = 0; y = 0; ans = 0; status = 1; recur = 0; saveLastEntry = 1;
        labelWorkings.setText("0");
        inputScreen.setText("0");
    }
    
   
    @FXML
    private void ClickedInverse(ActionEvent event) {     
        otherOperations();
        inputScreen.setText(String.valueOf(1/otherOperations ()));
        labelWorkings.setText("1/"+String.valueOf(x) +" =");
        x = Double.parseDouble(inputScreen.getText());
    }

    @FXML
    private void ClickedSquareRoot(ActionEvent event) {
        otherOperations();
        inputScreen.setText(String.valueOf(Math.sqrt(otherOperations ())));
        labelWorkings.setText("√"+String.valueOf(x) +" =");
        x = Double.parseDouble(inputScreen.getText());
    }

    @FXML
    private void ClickedSquare(ActionEvent event) {
        otherOperations();
        inputScreen.setText(String.valueOf(Math.pow(otherOperations (), 2)));
        labelWorkings.setText("sqr("+String.valueOf(x) +") =");
        x = Double.parseDouble(inputScreen.getText());
    }

    @FXML
    private void ClickedDivide(ActionEvent event) {
        doOperation(operator);
        operator = "/";
        labelWorkings.setText(x +" / ");
    }

    @FXML
    private void ClickedTImes(ActionEvent event) {
        doOperation(operator);            
        operator = "*";
        labelWorkings.setText(x +" * ");
        
    }

    @FXML
    private void ClickedMinus(ActionEvent event) {
        doOperation(operator);            
        operator = "-";
        labelWorkings.setText(x +" - ");
    }
    
     @FXML
    private void ClickedPlus(ActionEvent event) {
        doOperation(operator);
        operator = "+";
        labelWorkings.setText(x +" + ");
    }

    @FXML
    private void ClickedEquals(ActionEvent event) {
        recur = 1;
        doOperation(operator);
        labelWorkings.setText(x +" = ");
    } 
    
    
    private void getNumEntry (String num) {
        recur = 1; saveLastEntry = 1;
        
        if (status ==1) {
            inputScreen.setText(num);
            status = 0;
        }else {
            inputScreen.setText(inputScreen.getText()+num);
        }
    }
    
    private void doOperation (String operator) {
       
        status = 1;
        
        if (x == 0) {
            x = Double.parseDouble(inputScreen.getText());
        } else {
            if (recur ==1) {
                if (saveLastEntry ==  1) {
                    y = Double.parseDouble(inputScreen.getText());
                    saveLastEntry = 0;
                }
                
                switch (operator) {
                    case "+" : 
                        ans = x + y; 
                        inputScreen.setText(String.valueOf(ans)); break; 
                    case "-" : 
                        ans = x - y; 
                        inputScreen.setText(String.valueOf(ans)); break;
                    case "*" : 
                        ans = x * y; 
                        inputScreen.setText(String.valueOf(ans)); break;
                    case "/" :  
                        if (y == 0) {
                            inputScreen.setText("Math Error");
                            ans = 0; status = 1; y = 0; saveLastEntry = 1; break;
                        } else {
                            ans = x / y; 
                            inputScreen.setText(String.valueOf(ans));break;
                        }
                }
                
                x = ans;
                recur = 0;
            }      
        } 
    }
   
    
    @FXML
    private void Clicked1(ActionEvent event) {
        getNumEntry("1");
    }

    @FXML
    private void Clicked2(ActionEvent event) {
        getNumEntry("2");
    }

    @FXML
    private void Clicked3(ActionEvent event) {
        getNumEntry("3");
    }
    
    @FXML
    private void Clicked4(ActionEvent event) {
        getNumEntry("4");
    }

    @FXML
    private void Clicked5(ActionEvent event) {
        getNumEntry("5");
    }

    @FXML
    private void Clicked6(ActionEvent event) {
        getNumEntry("6");
    }

    @FXML
    private void Clicked7(ActionEvent event) {
        getNumEntry("7");
    }

    @FXML
    private void Clicked8(ActionEvent event) {
        getNumEntry("8");
    }

    @FXML
    private void Clicked9(ActionEvent event) {
        getNumEntry("9");
    }

    @FXML
    private void Clicked0(ActionEvent event) {
        getNumEntry("0");
    }

    @FXML
    private void ClickedDecimal(ActionEvent event) {
        getNumEntry(".");
    }

    @FXML
    private void ClickedBackSpace(ActionEvent event) {
        String value = inputScreen.getText();
        int length = inputScreen.getText().length()-1;
        
        if (length>0) 
            value = value.substring(0, length);
        else {
            value = "0";
            status = 1;
        }
       
        inputScreen.setText(value);
    }
}

package preExam;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

//Registration Controller
public class RegisterController implements Initializable {
    //Declaring a list of subjects
    private final ObservableList <String> subjects = FXCollections.observableArrayList(),
                                          subjects4 = FXCollections.observableArrayList();
    //Initially assigning null to other 3 subjects
    private String subject2=null, subject3=null, subject4=null;

    private Connection con;
    
    @FXML
    private TextField surname, othernames, code, ipAddress, error;
    @FXML
    private ComboBox <String> sub2Combo, sub3Combo, sub4Combo;
       
    @Override  //This helps to add a list of subjects to the combo boxes for selecting subjects
    public void initialize(URL url, ResourceBundle rb) {
      
        // Sunject 1 is fixed - Use of English
        //Subjects 2 & 3 make use of "subjects" list below
        subjects.addAll("accounts", "biology", "chemistry", "commerce", "crs",
                "economics", "government", "mathematics", "physics", 
                "agric", "irs", "homeecons");
        
        //Subject 4 makes use of "subjects4" below. The only difference is "literature" added (A big reason WHY)
        subjects4.addAll("accounts", "biology", "chemistry", "commerce", "crs",
                "economics", "government", "literature", "mathematics", "physics");
        
        sub2Combo.setItems(subjects);
        sub3Combo.setItems(subjects);
        sub4Combo.setItems(subjects4);
    }       

    @FXML //Executes once the REGISTER button is clicked
    private void register(ActionEvent event) throws SQLException {
        //checks if any field is empty      
        if (surname.getText().isEmpty() || othernames.getText().isEmpty() || code.getText().isEmpty()
                || subject2==null || subject3==null || subject4==null) {
            
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("ALL FIELDS REQUIRED");
            alert.setHeaderText(null); 
            alert.show();
        } else { 
            //  Setting up JDBC connection with LOCALHOST ONLY - Server System
            try{
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/preexam?useSSL=false" 
                        + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", 
                    "root", "");
                
                PreparedStatement ps = con.prepareStatement("SELECT un from login where un = ?");
                ps.setString(1, code.getText().trim());

                ResultSet rs = ps.executeQuery();

                //Checks if a student with this student code exists in the database
                if (rs.next()) {
                    Alert alert  = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("REGISTRATION FAILED \nSTUDENT ALREADY EXISTS!!!");
                    code.clear();
                    alert.setHeaderText(null);
                    alert.show();
                } else { // if not, register
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO login (name, un, pw, pp, timermin, submitstatus, backup, psgid, sub2, sub3, sub4) VALUES "
                                + "(?,?,?,?,?,?,?,?,?,?,?)");

                    ps2.setString(1, surname.getText().toUpperCase() + " " + othernames.getText().toUpperCase());
                    ps2.setString(2, code.getText().toUpperCase());
                    ps2.setString(3, surname.getText().toLowerCase());
                    ps2.setString(4, code.getText().toUpperCase()+".JPG");
                    ps2.setInt(5, 120);
                    ps2.setInt(6, 0);
                    ps2.setString(7, "");
                    ps2.setString(8, "");
                    ps2.setString(9, subject2);
                    ps2.setString(10, subject3);
                    ps2.setString(11, subject4);

                    ps2.execute();

                    Alert alert  = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("REGISTRATION SUCCESSFUL");
                    alert.setHeaderText(null);
                    alert.show();
                }

            } catch (SQLException e) { //Executes if connection fails
                Alert alert  = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("REGISTRATION FAILED \nCONNECTION ERROR");
                alert.setHeaderText(null);
                alert.show();

                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, e);
                error.setText(e.getMessage());
            }
        }
        
    }

    @FXML  //Executes once a subject is selected from  second combobox
    private void comboSub2OnAction(ActionEvent event) {
        //Checks if subject is chosen already
        if ((subject3!=null && sub2Combo.getSelectionModel().getSelectedItem().equals(subject3)) 
           ||  (subject4!=null && sub2Combo.getSelectionModel().getSelectedItem().equals(subject4))) {
           Alert alert  = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("SUBJECT ALREADY CHOSEN");
           alert.setHeaderText(null);
           alert.show();  
           
           sub2Combo.getSelectionModel().select(null);

        } else  // otherwise, assgin to subject2
            subject2 = sub2Combo.getSelectionModel().getSelectedItem();
    }

    @FXML //Executes once a subject is selected from  third combobox
    private void comboSub3OnAction(ActionEvent event) {
        //Checks if subject is chosen already
        if ((subject2!=null && sub3Combo.getSelectionModel().getSelectedItem().equals(subject2)) 
           ||  (subject4!=null && sub3Combo.getSelectionModel().getSelectedItem().equals(subject4))) {
           Alert alert  = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("SUBJECT ALREADY CHOSEN");
           alert.setHeaderText(null);
           alert.show();  
           
           sub3Combo.getSelectionModel().select(null);

        } else // otherwise, assgin to subject3
            subject3 = sub3Combo.getSelectionModel().getSelectedItem();
    }

    @FXML //Executes once a subject is selected from  fourth combobox
    private void comboSub4OnAction(ActionEvent event) {
        //Checks if subject is chosen already
        if ((subject2!=null && sub4Combo.getSelectionModel().getSelectedItem().equals(subject2)) 
           ||  (subject3!=null && sub4Combo.getSelectionModel().getSelectedItem().equals(subject3))) {
           Alert alert  = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("SUBJECT ALREADY CHOSEN");
           alert.setHeaderText(null);
           alert.show();  
           
           sub4Combo.getSelectionModel().select(null);

        } else // otherwise, assgin to subject4
            subject4 = sub4Combo.getSelectionModel().getSelectedItem();
    }   
    
}

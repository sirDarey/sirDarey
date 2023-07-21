package preExam;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Controller for checking results
public class ResultsController implements Initializable {

    @FXML
    private TableView tableViewResults;
    @FXML
    private TableColumn<?, ?> columnStudentCode;
    @FXML
    private TableColumn<?, ?> columnName;
    @FXML
    private TableColumn<?, ?> columnSub1;
    @FXML
    private TableColumn<?, ?> columnScore1;
    @FXML
    private TableColumn<?, ?> columnSub2;
    @FXML
    private TableColumn<?, ?> columnScore2;
    @FXML
    private TableColumn<?, ?> columnSub3;
    @FXML
    private TableColumn<?, ?> columnScore3;
    @FXML
    private TableColumn<?, ?> columnSub4;
    @FXML
    private TableColumn<?, ?> columnScore4;
    @FXML
    private TableColumn<?, ?> columnTotal;
    
    private Connection con;
    private final ObservableList<results> dataResults = FXCollections.observableArrayList();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
           //  Setting up JDBC connection with LOCALHOST ONLY - Server System
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/preexam?useSSL=false"
                    + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
            setData();
            loadData();
            
        } catch (SQLException ex) { //Executes if connection to localhost fails
            Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("UNAUTHORISED ACCESS DENIED");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        
    }    
    
    //Setting table colums to match tableheading variables declared in "results.java"
    private void setData () {
        columnStudentCode.setCellValueFactory(new PropertyValueFactory<>("studentCode"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnScore1.setCellValueFactory(new PropertyValueFactory<>("score1"));
        columnScore2.setCellValueFactory(new PropertyValueFactory<>("score2"));
        columnScore3.setCellValueFactory(new PropertyValueFactory<>("score3"));
        columnScore4.setCellValueFactory(new PropertyValueFactory<>("score4"));
        columnSub1.setCellValueFactory(new PropertyValueFactory<>("sub1"));
        columnSub2.setCellValueFactory(new PropertyValueFactory<>("sub2"));
        columnSub3.setCellValueFactory(new PropertyValueFactory<>("sub3"));
        columnSub4.setCellValueFactory(new PropertyValueFactory<>("sub4")); 
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("tScore"));
    }
    
    //Loads data from database and fills table
    private void loadData () throws SQLException {
        
        dataResults.clear();
 
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM scores");
        
        
        while (rs.next()) {         
            dataResults.add(new results(
                    rs.getString("un"), 
                    rs.getString("name"), 
                    "Use of  English", 
                    rs.getString("sub2"), 
                    rs.getString("sub3"), 
                    rs.getString("sub4"), 
                    rs.getInt("score1"), 
                    rs.getInt("score2"), 
                    rs.getInt("score3"), 
                    rs.getInt("score4"), 
                    rs.getInt("tscore"))
            );
        }
    
        tableViewResults.setItems(dataResults);
    }

    
}

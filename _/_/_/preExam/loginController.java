package preExam;

import animatefx.animation.Shake;
import animatefx.animation.SlideInUp;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import static preExam.loginController.con;

public class loginController {

    @FXML
    private TextField inputUn;
    @FXML
    private PasswordField inputPw;
    @FXML
    private Label labelValidateLogin;   
    
    public static Connection con=null;
    public static String un, pw, ip;
    private ResultSet rs;
    
    @FXML
    private TextField ipAddress;
    @FXML
    private ImageView imageLogo;
    @FXML
    private Label labelInstructions;

    // Checks if entered username exists in database
    private boolean checkUn () throws SQLException {
         
        Statement statement1 = con.createStatement();
        rs = statement1.executeQuery("SELECT COUNT(un) FROM login WHERE un = '" +inputUn.getText().trim() +"'");
        rs.next();
        int count = rs.getInt(1);

        return count > 0;
            
    }
    
    //Checks if entered password is correct
    private boolean checkPw () throws SQLException {
        String pw = null;
        
        Statement statement1 = con.createStatement();
        rs = statement1.executeQuery("SELECT pw FROM login where un = '" +inputUn.getText().trim()+ "'");

        while (rs.next()) {
            pw = rs.getString("pw");
        }
              
        return inputPw.getText().trim().equals(pw);
    }
    
    
    //This loads the preloader
    private void start (ActionEvent actionEvent) throws IOException {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("preloader.fxml"));
        Parent preloaderPage = loader.load();
        Scene preloaderScene = new Scene(preloaderPage);
        Stage preloaderWindow = new Stage();
        preloaderWindow.setTitle("STGEI PRE-EXAM");
        preloaderWindow.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        preloaderWindow.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        preloaderWindow.setResizable(false);
        preloaderWindow.setMaximized(true);
        preloaderWindow.setScene(preloaderScene);

        preloaderWindow.show();
    }
    
    @FXML // This is called once a student tries to login
    private void login(ActionEvent event) throws SQLException, IOException {
        //Checks if any field is empty
        if (inputPw.getText().isEmpty() || inputUn.getText().isEmpty()) {
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("PLEASE ENTER ALL LOGIN DETAILS");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            //Reconfirmation of JDBC Connectivity, If true validateLogin
            if (con==null || !con.isValid(0)) {
                getConnection();
                validateLogin(event);
            } else {
                validateLogin(event);
            }
        }
    }
    
    //Calls the boolean functions that confirms entered username and password
    private void validateLogin (ActionEvent event) throws SQLException, IOException{
        // If both are true, execute if block, otherwise,execute else
        if (checkUn() && checkPw()) {
            
            labelValidateLogin.setVisible(false);
            int submitStatus=0;

            //checks submitStatus of this student
            try{
                PreparedStatement ps = con.prepareStatement("SELECT submitstatus FROM login WHERE un = ? AND pw = ?");

                ps.setString(1, inputUn.getText().trim());
                ps.setString(2, inputPw.getText().trim());

                rs = ps.executeQuery();

                while (rs.next()) {
                    submitStatus = rs.getInt("submitstatus");
                }

            } catch (SQLException e) { 
                Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
            }

            if (submitStatus == 0) { //The student is yet to submit, thus allow
                
                //Assigns un to a static un variable which is used throughout the application when running
                un = inputUn.getText().trim();
                //Assigns pw to a static pw variable which is used throughout the application when running
                pw = inputPw.getText().trim();

                start(event);
            } else {  //Otherwise, don't allow                  
                Alert alert  = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("NO MORE ATTEMPT ALLOWED \n FOR THIS CANDIDATE!!!");
                alert.setHeaderText(null);
                alert.showAndWait();

                inputUn.clear();
                inputPw.clear();
            }
                    
        } else { //This execute if username and/or password is incorrect
            labelValidateLogin.setText("Wrong Credentials!!!");
            labelValidateLogin.setVisible(true);
            inputPw.clear();
        }
        
    }

    @FXML // Makes changeIp field visible as it is invisible by default
    private void changeIp(MouseEvent event) {
        ipAddress.setVisible(true);
    }

    @FXML //Calls Registration page for a new candidate; It only workson the server system
    private void register(MouseEvent event) throws IOException {
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("register.fxml"));
        Parent registerPage = loader1.load();
        Scene registerScene = new Scene(registerPage);
        Stage registerWindow = new Stage();
        registerWindow.setTitle("REGISTER A CANDIDATE");
        registerWindow.setWidth(600);
        registerWindow.setHeight(600);
        registerWindow.setResizable(false);
        registerWindow.setScene(registerScene);
        registerWindow.initModality(Modality.APPLICATION_MODAL);

        registerWindow.show();
    }

    @FXML //A call  to some animations
    private void rootEntered(MouseEvent event) {
        SlideInUp animate1 = new SlideInUp(imageLogo);
        Shake animate2 = new Shake (labelInstructions);
   
        animate1.play();
        animate2.setDelay(Duration.millis(2000));
        animate2.play(); 
    }

    @FXML // A call to check students results so far; It only works on the server system
    private void checkResults(MouseEvent event) throws IOException {
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("results.fxml"));
        Parent resultPage = loader1.load();
        Scene resultScene = new Scene(resultPage);
        Stage resultWindow = new Stage();
        resultWindow.setTitle("RESULTS");
        resultWindow.setMaximized(true);
        resultWindow.setResizable(false);
        resultWindow.setScene(resultScene);
        resultWindow.initModality(Modality.APPLICATION_MODAL);

        resultWindow.show();
    } 
    
    //Sets up JDBC Connection
    //Here, IP is that of the server system
    private void getConnection () {
        try {
            con = DriverManager.getConnection("jdbc:mysql://"+ipAddress.getText()+":3306/preexam?useSSL=false" 
                    + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            
            //Assigns server ip to a static ip variable which is used throughout the application when running
            ip = ipAddress.getText();
            
        } catch (SQLException ex) { // executes, If connectivity fails
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            labelValidateLogin.setText("*******Connection Error*******");
            labelValidateLogin.setVisible(true);

            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("CONNECTION LOST");
            alert.setHeaderText(null);
            alert.showAndWait();

        }  
    }
    
    
}

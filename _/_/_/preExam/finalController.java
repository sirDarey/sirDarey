package preExam;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import static preExam.PreloaderController.sub2;
import static preExam.PreloaderController.sub3;
import static preExam.PreloaderController.sub4;
import static preExam.mainController.*;

//Final Page Controller
public class finalController implements Initializable{

    @FXML
    public static VBox finalPage;
    @FXML
    private Label finalScore;
    @FXML
    private Label score1l, score2l, score3l, score4l, sub2l, sub3l, sub4l;

    @Override //Sets scores for each subject and calculates total immediately submission is done
    public void initialize(URL location, ResourceBundle resources) {
        sub2l.setText(sub2.toUpperCase()+": ");
        sub3l.setText(sub3.toUpperCase()+": ");
        sub4l.setText(sub4.toUpperCase()+": ");
        
        score1l.setText(String.valueOf(score1));
        score2l.setText(String.valueOf(score2));
        score3l.setText(String.valueOf(score3));
        score4l.setText(String.valueOf(score4));
        
        finalScore.setText(String.valueOf(score1+score2+score3+score4));
        finish.setText("LOGOUT");
    }
    
}

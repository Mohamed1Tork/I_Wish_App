package Screens;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author shorouk abd elraouf
 */
public class RechargeSceneController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private AnchorPane anchorrecharge;
    @FXML
    private Pane panerecharge;
    @FXML
    private ImageView imagrecharge;
    @FXML
    private TextField cardNum;
    @FXML
    private TextField CVV;
    @FXML
    private Text textcardrecharge;
    @FXML
    private DatePicker ExpireDate;
    @FXML
    private Text textcvvrecharge;
    @FXML
    private TextField amounfieldrecharge;
    @FXML
    private Text textamountrecharge;
    @FXML
    private Button buttconfirmrecharge;

    static Socket socket;
    static PrintStream ps ;
    static InputStream is;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public void recharge(){
                String amount = amounfieldrecharge.getText();
                if (amount == null || amount.isEmpty() || !amount.matches("\\d+(\\.\\d+)?")) {
                    JOptionPane.showMessageDialog(null,"please enter a valid amount (numbers only)");
                    return;
                }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Transaction");
                    String s = "Are you sure you want to recharge your balance with "+amount+"?";
                    alert.setContentText(s);
                    Optional<ButtonType> result = alert.showAndWait();
                    if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                        try {
                            socket=new Socket(ip, 5005);
                            ps = new PrintStream(socket.getOutputStream());
                            ps.println("Recharge");
                            ps.println(DataHolder.getInstance().getData()); //user
                            ps.println(amounfieldrecharge.getText());   // amount
                            JOptionPane.showMessageDialog(null,"Recharging has been done succssfully, refresh your profile");
                        } catch (IOException ex) {
                            Logger.getLogger(RechargeSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally{
                            try {
                                ps.close();
                                socket.close();
                            } catch (IOException ex) {
                                Logger.getLogger(RechargeSceneController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }  
                    }     
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
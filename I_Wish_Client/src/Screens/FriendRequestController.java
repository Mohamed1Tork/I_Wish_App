/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import Screens.DataHolder;
import java.util.Arrays;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import i_wish_server.UserFriendList;
import i_wish_server.UserItems;
import i_wish_server.myItems;
import i_wish_server.UserWishList;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect.Type;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author shoroukabdelraouf
 */
public class FriendRequestController implements Initializable {
    
    private final String ip = "127.0.0.1";
    
    static Socket socket1;
    static DataInputStream dis1 ;
    static PrintStream ps1 ;
    static InputStream is1;
    @FXML
    private HBox hboxfriendrequest;
    @FXML
    private Pane panefriendrequesth;
    @FXML
    private ImageView imagfriendrequesth;
    @FXML
    private Pane panefriendrequest2h;
    @FXML
    private VBox vbox1friendrequest;
    @FXML
    private Pane panefriendrequestvbox1;
    @FXML
    private Label labelfriendname;
    @FXML
    private HBox hbox2invbox1friendequest;
    @FXML
    private Text textemailfriendrquest;
    @FXML
    private Label labelemail;
    @FXML
    private Pane panehbox2vbox1friendrequest;
    @FXML
    private VBox vbox2friendrequest;
    @FXML
    private Pane panevbox2friendrequest;
    @FXML
    private HBox hboxvbox2friendrequest;
    @FXML
    private Pane pane2vbox2friendrequest;
    @FXML
    private HBox nameFieldfriendrequest;
    @FXML
    private Text textnamefriend;
    @FXML
    private Button butaccceptfriendrequest;
    @FXML
    private Button butdeclinefriendrequest;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setData(String fname, String email) {
        Platform.runLater(() -> {
                labelfriendname.setText(fname);
                 labelemail.setText(email);
        });
        

    }
    
    @FXML
    public void acceptRequest(){
    
        try {
            socket1=new Socket(ip, 5005);
            ps1 = new PrintStream(socket1.getOutputStream());
            InputStream is1 = socket1.getInputStream();
            ps1.println("acceptrequest");
            System.out.println("accept");
            ps1.println(DataHolder.getInstance().getData()); /// come to (owner)
            ps1.println(labelemail.getText()); /// come from (sender)
            VBox parentVBox = (VBox) hboxfriendrequest.getParent();
            parentVBox.getChildren().remove(hboxfriendrequest);
            
        } catch (IOException ex) {
            Logger.getLogger(ConnectPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @FXML
    public void declineRequest(){
        try {
            socket1=new Socket(ip, 5005);
            ps1 = new PrintStream(socket1.getOutputStream());
            InputStream is1 = socket1.getInputStream();
            ps1.println("declinerequest");
            System.out.println("decline");
            ps1.println(DataHolder.getInstance().getData()); /// come to (owner)
            ps1.println(labelemail.getText()); /// come from (sender)
            
            VBox parentVBox = (VBox) hboxfriendrequest.getParent();
            parentVBox.getChildren().remove(hboxfriendrequest);
        } catch (IOException ex) {
            Logger.getLogger(FriendRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
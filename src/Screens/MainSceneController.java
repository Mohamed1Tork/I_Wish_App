/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shoroukabdelraouf
 */
public class MainSceneController implements Initializable {

    @FXML
    private BorderPane main;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private Hyperlink logoutBT;
    @FXML
    private Text txtwelcomemainpane;
    @FXML
    private ImageView makeawishimgmain;
    @FXML
    private Text emailHolder;
    @FXML
    private AnchorPane anchorpanevboxmainpane;
    @FXML
    private VBox vboxmainpane;
    @FXML
    private Button buttbreak1;
    @FXML
    private HBox hboxprofilemainpane;
    @FXML
    private ImageView imagprofilemainpane;
    @FXML
    private Pane paneprofilemainpane;
    @FXML
    private Button profilebuttmainpane;
    @FXML
    private Button buttbreak2;
    @FXML
    private HBox hboxfriendsmainpane;
    @FXML
    private ImageView imagfriendsmainpane;
    @FXML
    private Pane panefriendsmainpane;
    @FXML
    private Button friendsBTN;
    @FXML
    private Button buttobreak3;
    @FXML
    private HBox hboxitemsmainpane;
    @FXML
    private ImageView imagitemsmainpane;
    @FXML
    private Pane paneitemsmainpane;
    @FXML
    private Button buttitemsmainpane;
    @FXML
    private Button buttonbreak4;
    @FXML
    private HBox hboxconnectmainpane;
    @FXML
    private ImageView imagconnectmainpane;
    @FXML
    private Pane paneconnectmainpane;
    @FXML
    private Button buttconnectmainpane;
    @FXML
    private Button buttonbreak5;
    @FXML
    private HBox hboxnotifimainpane;
    @FXML
    private ImageView imagnotifimainpane;
    @FXML
    private Pane panenotimainpane;
    @FXML
    private Button buttnotimainpane;
    @FXML
    private AnchorPane pagepane;
    
    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logoutSwitch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root= loader.load();
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }

//    @FXML
//    private void loadPageProfile(MouseEvent event) {
//    }

    @FXML
    public void loadPageProfile() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ProfilePane.fxml"));
        Parent root= fxmlLoader.load();
        main.setCenter(root);                           
    }

//    @FXML
//    private void loadFriends(MouseEvent event) {
//    }

    @FXML
    private void loadFriends(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FriendsPane.fxml"));
        Parent root= fxmlLoader.load();
        main.setCenter(root);  
    }

//    @FXML
//    private void loadItemsPage(MouseEvent event) {
//    }

    @FXML
    private void loadItemsPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Item_Pane.fxml"));
        Parent root= fxmlLoader.load();
        main.setCenter(root);   
    }

//    @FXML
//    private void loadConnectPage(MouseEvent event) {
//    }

    @FXML
    private void loadConnectPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("connectPane.fxml"));
        Parent root= fxmlLoader.load();
        main.setCenter(root);   
    }

//    @FXML
//    private void notificationPage(MouseEvent event) {
//    }

    @FXML
    private void notificationPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Notification.fxml"));
        Parent root= fxmlLoader.load();
        main.setCenter(root);   
    }
    
}

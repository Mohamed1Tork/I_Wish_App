/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import static Screens.ProfilepaneController.is;
import static Screens.ProfilepaneController.ps;
import static Screens.ProfilepaneController.socket;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import i_wish_server.UserFriendList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author shoroukabdelraouf
 */
public class FriendsWishesController implements Initializable {

    @FXML
    private AnchorPane anchorfriendswishes;
    @FXML
    private TableView<?> wishListTable;
    @FXML
    private TableColumn<?, ?> image;
    @FXML
    private TableColumn<?, ?> item_name;
    @FXML
    private TableColumn<?, ?> price;
    @FXML
    private TableColumn<?, ?> remained;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private Button menuBtn;
    @FXML
    private ImageView buttonimage;
    @FXML
    private Pane contPane;
    @FXML
    private Label ContLBL;
    @FXML
    private TextField amount;
    @FXML
    private Pane slider;
    @FXML
    private Rectangle rectanglefriends;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label balance;
    @FXML
    private Button HomeBtn;
    @FXML
    private Button friend;
    @FXML
    private Button wishListFriends;
    @FXML
    private Button add1;
    @FXML
    private Button logout;
    @FXML
    private Button addfriend;
    @FXML
    private Button chrgBalance;
    @FXML
    private ImageView imagebalancefriends;
    @FXML
    private ImageView imagaboveuserfriends;
    @FXML
    private Label priceLBL;
    @FXML
    private TableView<?> friendsView;
    @FXML
    private TableColumn<?, ?> date2;
    @FXML
    private TableColumn<?, ?> username;
    @FXML
    private TableColumn<?, ?> emailfriends;
    @FXML
    private Label choosefriendlabel;
    @FXML
    private Button buttcancelfriends;
    @FXML
    private Button buttcontributefriends;
    static Socket socket;
    static DataInputStream dis;
    static PrintStream ps;
    static InputStream is;

    /**
     * Initializes the controller class.
     */
    @Override
       public void initialize(URL url, ResourceBundle rb) {
           

       }
       

    @FXML
    private void Home(ActionEvent event) {
    }

    @FXML
    private void friend(ActionEvent event) {
    }

    @FXML
    private void wishListFriends(ActionEvent event) {
    }

    @FXML
    private void add_item(ActionEvent event) {
    }

    @FXML
    private void logOut(ActionEvent event) {
    }

    @FXML
    private void addfriend(ActionEvent event) {
    }
    
}

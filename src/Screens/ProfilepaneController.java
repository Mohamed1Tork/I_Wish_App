/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
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
import i_wish_server.myItems;
import i_wish_server.UserWishList;
import i_wish_server.UserFriendList;
import i_wish_server.UserData;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect.Type;


public class ProfilepaneController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Pane profilepane;
    @FXML
    private Label ProfilenameLabel;
    @FXML
    private Pane Imagepane;
    @FXML
    private ImageView perimg;
    @FXML
    private Circle profcir;
    @FXML
    private Text textbalanceprofilepane;
    @FXML
    private Text balanceLabel;
    @FXML
    private Text text$profilepane;
    @FXML
    private Button buttrechargeprofilepane;
    @FXML
    private Pane friendspane;
    @FXML
    private Text textfriendsprofilepane;
    @FXML
    private Pane mywishlistpane;
    @FXML
    private Text mywishtextprofilepane;
    @FXML
    private TableView<UserWishList> wishlisttable;
    @FXML
    private TableColumn<UserWishList, String> productwfield;
    @FXML
    private TableColumn<UserWishList, Integer> pricewfield;
    @FXML
    private TableColumn<UserWishList, Integer> tcontributionswfield;
    @FXML
    private TableView<UserFriendList> friendstable;
    @FXML
    private TableColumn<UserFriendList, String> firstnamefield;
    @FXML
    private TableColumn<UserFriendList, String> lastnamefield;
    @FXML
    private TableColumn<UserFriendList, String> emailfield;
    @FXML
    private Pane myitmspane;
    @FXML
    private Text myitemstextprofilepane;
    @FXML
    private Text profiletextprofilepane;
    @FXML
    private TableView<myItems> itemstable;
    @FXML
    private TableColumn<myItems, String> Productidifield;
    @FXML
    private TableColumn<myItems, String> productifield;
    @FXML
    private TableColumn<myItems, Integer> priceifield;

    private Parent root;
    private Stage stage;
    private Scene scene;
    static Socket socket;
    static DataInputStream dis;
    static PrintStream ps;
    static InputStream is;
    private Label ProfilenameLabel1;
    @FXML
    private Label mobilelabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            socket = new Socket(ip, 5005);
            ps = new PrintStream(socket.getOutputStream());
            is = socket.getInputStream();
            String user = DataHolder.getInstance().getData();
            Platform.runLater(() -> {
                try {
                    ps.println("myitems");
                    ps.println(user);

// Read JSON data from the server
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder jsonBuilder = new StringBuilder();
                    String line;
                    line = br.readLine();
                    jsonBuilder.append(line);
                    System.out.println(line);
                    System.out.println("item");

// Print the received JSON string
                    System.out.println("Received JSON: " + jsonBuilder.toString());

// Convert JSON to ArrayList using Gson
                    Gson gson = new Gson();
                    String json = jsonBuilder.toString();
                    System.out.println("JSON to Deserialize: " + json);

// Use TypeToken to handle generic type (ArrayList<myItems>)
                    java.lang.reflect.Type type = new TypeToken<ArrayList<myItems>>() {
                    }.getType();

                    try {
                        ArrayList<myItems> arr2 = gson.fromJson(json, type);
                        System.out.println("Deserialization Successful. Size: " + arr2.size());

                        // Print the deserialized objects for verification
                        for (myItems item : arr2) {
                            System.out.println("Item ID: " + item.getItem_id() + ", Item Name: " + item.getItem_name() + ", Price: " + item.getPrice());
                        }

                        // Update your UI (JavaFX) with the deserialized data
                        ObservableList<myItems> myItemsList = FXCollections.observableArrayList(arr2);
                        Productidifield.setCellValueFactory(new PropertyValueFactory<>("item_id"));
                        productifield.setCellValueFactory(new PropertyValueFactory<>("item_name"));
                        priceifield.setCellValueFactory(new PropertyValueFactory<>("price"));
                        itemstable.setItems(myItemsList);

                    } catch (JsonSyntaxException e) {
                        System.err.println("Error in deserialization: " + e.getMessage());
                    }

                    //////////////mywishlist
                    ps.println("mywish");
                    ps.println(user);

// Read JSON data from the server
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
                    StringBuilder jsonBuilder1 = new StringBuilder();
                    String line1;
                    line1 = br1.readLine();
                    jsonBuilder1.append(line1);
                    System.out.println(line1);
                    System.out.println("wish");

// Print the received JSON string
                    System.out.println("Received JSON: " + jsonBuilder1.toString());

// Convert JSON to ArrayList using Gson
                    Gson gson1 = new Gson();
                    String json1 = jsonBuilder1.toString();
                    System.out.println("JSON to Deserialize: " + json1);

// Use TypeToken to handle generic type (ArrayList<myItems>)
                    java.lang.reflect.Type type1 = new TypeToken<ArrayList<UserWishList>>() {
                    }.getType();

                    try {
                        ArrayList<UserWishList> arr3 = gson1.fromJson(json1, type1);
                        System.out.println("Deserialization Successful. Size: " + arr3.size());

                        // Print the deserialized objects for verification
                        for (UserWishList wish : arr3) {
                            System.out.println("Item ID: " + wish.getProduct() + ", Item Name: " + wish.getPrice() + ", Price: " + wish.getTotal());
                        }

                        // Update your UI (JavaFX) with the deserialized data
                        ObservableList<UserWishList> myWishLists = FXCollections.observableArrayList(arr3);
                        productwfield.setCellValueFactory(new PropertyValueFactory<>("Product"));
                        pricewfield.setCellValueFactory(new PropertyValueFactory<>("Price"));
                        tcontributionswfield.setCellValueFactory(new PropertyValueFactory<>("Total"));
                        wishlisttable.setItems(myWishLists);

                    } catch (JsonSyntaxException e) {
                        System.err.println("Error in deserialization: " + e.getMessage());
                    }
                    //////////////////
                    ps.println("allusers");
                    ps.println(user);

// Read JSON data from the server
                    BufferedReader br3 = new BufferedReader(new InputStreamReader(is));
                    StringBuilder jsonBuilder3 = new StringBuilder();
                    String line3;
                    line3 = br3.readLine();
                    jsonBuilder3.append(line3);
                    System.out.println(line3);
                    System.out.println("friend");

// Print the received JSON string
                    System.out.println("Received JSON: " + jsonBuilder3.toString());

// Convert JSON to ArrayList using Gson
                    Gson gson3 = new Gson();
                    String json3 = jsonBuilder3.toString();
                    System.out.println("JSON to Deserialize: " + json1);

// Use TypeToken to handle generic type (ArrayList<myItems>)
                    java.lang.reflect.Type type3 = new TypeToken<ArrayList<UserFriendList>>() {
                    }.getType();

                    try {
                        ArrayList<UserFriendList> arr4 = gson1.fromJson(json3, type3);
                        System.out.println("Deserialization Successful. Size: " + arr4.size());

                        // Print the deserialized objects for verification
                        for (UserFriendList friend : arr4) {
                            System.out.println("fname: " + friend.getFriend_fname() + ", lanme: " + friend.getFriend_lname() + ", email: " + friend.getFriend_email());

                        }

                        // Update your UI (JavaFX) with the deserialized data
                        ObservableList<UserFriendList> user_fr = FXCollections.observableArrayList(arr4);
                        firstnamefield.setCellValueFactory(new PropertyValueFactory<>("friend_fname"));
                        lastnamefield.setCellValueFactory(new PropertyValueFactory<>("friend_lname"));
                        emailfield.setCellValueFactory(new PropertyValueFactory<>("friend_email"));
                        friendstable.setItems(user_fr);
                        System.out.println("friend");

                    } catch (JsonSyntaxException e) {
                        System.err.println("Error in deserialization: " + e.getMessage());
                    }//////UserData
                    ps.println("UserData");
                    ps.println(user);

                } catch (IOException ex) {
                    Logger.getLogger(ProfilepaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }); /////////////////Userdata
            ps.println("userData");
            ps.println(user);

// Read JSON data from the server
            BufferedReader brUserData = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonBuilderUserData = new StringBuilder();
            String lineUserData;
            lineUserData = brUserData.readLine();
            jsonBuilderUserData.append(lineUserData);
            System.out.println(lineUserData);
            System.out.println("userData");

// Print the received JSON string
            System.out.println("Received JSON: " + jsonBuilderUserData.toString());

// Convert JSON to ArrayList using Gson
            Gson gsonUserData = new Gson();
            String jsonUserData = jsonBuilderUserData.toString();
            System.out.println("JSON to Deserialize: " + jsonUserData);

// Use TypeToken to handle generic type (ArrayList<UserData>)
            java.lang.reflect.Type typeUserData = new TypeToken<ArrayList<UserData>>() {
            }.getType();

            try {
                ArrayList<UserData> arrUserData = gsonUserData.fromJson(jsonUserData, typeUserData);
                System.out.println("Deserialization Successful. Size: " + arrUserData.size());

                // Update your UI (JavaFX) with the deserialized data
                UserData userData = arrUserData.get(0); // Get the first element directly

                balanceLabel.setText(String.valueOf(userData.getCurrent_balance()));
                DataHolder.getInstance().setBalance((int) userData.getCurrent_balance());
                ProfilenameLabel.setText(userData.getFname());
                mobilelabel.setText(userData.getPhone());
                System.out.println(userData.getPhone());
                

            } catch (JsonSyntaxException e) {
                System.err.println("Error in deserialization: " + e.getMessage());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProfilepaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML

    private void rechargePanel(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Recharge.fxml"));
        Parent root = loader.load();

        // Set up an alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Recharge Information");
        alert.setHeaderText(null); // You can set a header text if needed
        alert.getDialogPane().setContent(root);

        // Customize the OK button text
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        // Adjust the size of the alert dialog if needed
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // Show the alert
        alert.showAndWait();
    }

}

package Screens;

import static Screens.ProfilepaneController.is;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import i_wish_server.UserData;
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
import java.util.Collection;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ConnectPaneController implements Initializable {
    
    private final String ip = "127.0.0.1";

    private Text emailHolder;

    static Socket socket1;
    static DataInputStream dis1;
    static PrintStream ps1;
    static InputStream is1;
    @FXML
    private AnchorPane anchorconnectpane;
    @FXML
    private TableView<UserFriendList> tableconnectpane;
    @FXML
    private TableColumn<UserFriendList, String> colfname;
    @FXML
    private TableColumn<UserFriendList, String> collname;
    @FXML
    private TableColumn<UserFriendList, String> colemail;
    @FXML
    private Button buttaddfriend;
    @FXML
    private TextField textfsearch;
    @FXML
    private Button buttsearch;
    @FXML
    private ScrollPane scrollconnectpane;
    @FXML
    private VBox vboxconnectpane;
    @FXML
    private HBox hboxconnectpane;
    @FXML
    private Label labfriendrequ;

    /**
     * Initializes the controller class.
     */

    @FXML
    public void addFriend() {
       
        try {
            socket1 = new Socket(ip, 5005);
            ps1 = new PrintStream(socket1.getOutputStream());
            InputStream is1 = socket1.getInputStream();
            ps1.println("addfriend");
            ps1.println(DataHolder.getInstance().getData()); /// from user 
            ps1.println(tableconnectpane.getSelectionModel().getSelectedItem().getFriend_email()); /// to user
        } catch (IOException ex) {
            Logger.getLogger(ConnectPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }           
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            String userEmail = DataHolder.getInstance().getData(); // Rename the variable to userEmail
            vboxconnectpane.getChildren().clear();
            socket1 = new Socket(ip, 5005);
            String user = DataHolder.getInstance().getData();
            ps1 = new PrintStream(socket1.getOutputStream());
            InputStream is1 = socket1.getInputStream();

            //////////////////////////////////////////////////
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        ps1.println("users");
                        ps1.println(user);
                        Thread.sleep(100);
// Read JSON data from the server
                        BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                        StringBuilder jsonBuilder = new StringBuilder();
                        String line;
                        line = br.readLine();
                        jsonBuilder.append(line);
                        System.out.println(line);
                        System.out.println("users2");

/// Print the received JSON string
                        System.out.println("Received JSON: " + jsonBuilder.toString());

// Convert JSON to ArrayList using Gson
                        Gson gson = new Gson();
                        String json = jsonBuilder.toString();
                        System.out.println("JSON to Deserialize: " + json);
// Use TypeToken to handle generic type (ArrayList<myItems>)
                        java.lang.reflect.Type type = new TypeToken<ArrayList<UserFriendList>>() {
                        }.getType();

                        ArrayList<UserFriendList> arr2 = gson.fromJson(json, type);
                        System.out.println("Deserialization Successful. Size of users: " + arr2.size());

                        ObservableList<UserFriendList> user_fr = FXCollections.observableArrayList(arr2);
                        colfname.setCellValueFactory(new PropertyValueFactory<>("friend_fname"));
                        collname.setCellValueFactory(new PropertyValueFactory<>("friend_lname"));
                        colemail.setCellValueFactory(new PropertyValueFactory<>("friend_email"));
                        tableconnectpane.setItems(user_fr);


                        

//////////////////////////////////////////////////
                        ps1.println("friendRequests");
                        ps1.println(userEmail);
                        Thread.sleep(100);
// Read JSON data from the server
                        BufferedReader br2 = new BufferedReader(new InputStreamReader(is1));
                        StringBuilder jsonBuilder2 = new StringBuilder();
                        String line2;
                        line2 = br2.readLine();
                        jsonBuilder2.append(line2);
                        System.out.println(line2);
                        System.out.println("requests");

// Print the received JSON string
                        System.out.println("Received JSON: " + jsonBuilder2.toString());

// Convert JSON to ArrayList using Gson
                        Gson gson2 = new Gson();
                        String json2 = jsonBuilder2.toString();
                        System.out.println("JSON to Deserialize: " + json2);

// Use TypeToken to handle generic type (ArrayList<myItems>)
                        java.lang.reflect.Type type2 = new TypeToken<ArrayList<UserData>>() {
                        }.getType();
                        ArrayList<UserData> arr3 = gson2.fromJson(json2, type2);
                        System.out.println("Deserialization Successful. Size: " + arr3.size());
                        for (UserData item : arr3) {
                            System.out.println("Item ID: " + item.getFname() + ", Item Name: " + item.getEmail());
                        }

                        for (UserData userData : arr3) {
                            System.out.println("Item ID: " + userData.getFname() + ", Item Name: " + userData.getEmail());
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("friendRequest.fxml"));
                            HBox hBox = fxmlLoader.load();
                            FriendRequestController frc = fxmlLoader.getController();
                            frc.setData(userData.getFname(), userData.getEmail());
                            System.out.println("finish");
                            vboxconnectpane.getChildren().add(hBox);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConnectPaneController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ConnectPaneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(ConnectPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
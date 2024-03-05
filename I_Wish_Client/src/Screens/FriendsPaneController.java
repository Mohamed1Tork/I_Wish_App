/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Screens.DataHolder;
import Screens.FriendRequestController;
import Screens.MyfriendswishlistsceneController;
import Screens.ProfilepaneController;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import i_wish_server.UserData;
import i_wish_server.UserFriendList;
import i_wish_server.UserWishList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author fayez
 */
public class FriendsPaneController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private VBox MywishlistVbox;
    @FXML
    private TableView<UserFriendList> friendsTable1;
    @FXML
    private TableColumn<UserFriendList, String> fname1;
    @FXML
    private TableColumn<UserFriendList, String> lname1;
    @FXML
    private TableColumn<UserFriendList, String> email1;
    static Socket socket;
    static DataInputStream dis;
    static PrintStream ps;
    static InputStream is;
    
    

    /**
     * Initializes the controller class.
     */
    @FXML
    private void getFriendWishlist() throws InterruptedException {
        try {
            MywishlistVbox.getChildren().clear();
            DataHolder.getInstance().setFriend(friendsTable1.getSelectionModel().getSelectedItem().getFriend_email());
            socket = new Socket(ip, 5005);
            ps = new PrintStream(socket.getOutputStream());
            InputStream is = socket.getInputStream();
            ps.println("userwishlist");
            ps.println(friendsTable1.getSelectionModel().getSelectedItem().getFriend_email());
            System.out.println(friendsTable1.getSelectionModel().getSelectedItem().getFriend_email());
            Thread.sleep(100);
            BufferedReader br5 = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonBuilder3 = new StringBuilder();
            String line5;
            line5 = br5.readLine();
            jsonBuilder3.append(line5);
            System.out.println(line5);
// Print the received JSON string
            System.out.println("Received JSON: " + jsonBuilder3.toString());

// Convert JSON to ArrayList using Gson
            Gson gson5 = new Gson();
            String json5 = jsonBuilder3.toString();
            System.out.println("JSON to Deserialize: " + json5);

// Use TypeToken to handle generic type (ArrayList<myItems>)
            java.lang.reflect.Type type5 = new TypeToken<ArrayList<UserWishList>>() {
            }.getType();

            ArrayList<UserWishList> arr5 = gson5.fromJson(json5, type5);
            System.out.println("Deserialization Successful. Size: " + arr5.size());

            // Print the deserialized objects for verification
            for (UserWishList wish : arr5) {
                System.out.println("product: " + wish.getProduct() + ", price: " + wish.getPrice() + "total: " + wish.getTotal());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("myfriendswishlistscene.fxml"));
                System.out.println("test1");
                HBox hBox = fxmlLoader.load();
                MyfriendswishlistsceneController cic = fxmlLoader.getController();
                cic.setData(wish.getProduct(), wish.getPrice(), wish.getTotal());
                System.out.println("test2");
                Platform.runLater(() -> {
                    
                    MywishlistVbox.getChildren().add(hBox);
                });
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            Logger.getLogger(FriendsPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            socket = new Socket(ip, 5005);
            ps = new PrintStream(socket.getOutputStream());
            is = socket.getInputStream();
            String user = DataHolder.getInstance().getData();
            Platform.runLater(() -> {
                try {
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
                    System.out.println("JSON to Deserialize: " + json3);

// Use TypeToken to handle generic type (ArrayList<myItems>)
                    java.lang.reflect.Type type3 = new TypeToken<ArrayList<UserFriendList>>() {
                    }.getType();

                    try {
                        ArrayList<UserFriendList> arr4 = gson3.fromJson(json3, type3);
                        System.out.println("Deserialization Successful. Size: " + arr4.size());

                        // Print the deserialized objects for verification
                        for (UserFriendList friend : arr4) {
                            System.out.println("fname: " + friend.getFriend_fname() + ", lanme: " + friend.getFriend_lname() + ", email: " + friend.getFriend_email());

                        }

                        // Update your UI (JavaFX) with the deserialized data
                        ObservableList<UserFriendList> user_fr = FXCollections.observableArrayList(arr4);
                        fname1.setCellValueFactory(new PropertyValueFactory<>("friend_fname"));
                        lname1.setCellValueFactory(new PropertyValueFactory<>("friend_lname"));
                        email1.setCellValueFactory(new PropertyValueFactory<>("friend_email"));
                        friendsTable1.setItems(user_fr);
                        System.out.println("friend");

                    } catch (JsonSyntaxException e) {
                        System.err.println("Error in deserialization: " + e.getMessage());
                    }//////UserData

                } catch (IOException ex) {
                    Logger.getLogger(FriendsPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }//////UserData

            });
        } catch (IOException ex) {
            Logger.getLogger(FriendsPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void removeFriend() throws IOException{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Friend");
                    String s = "Are you sure you want to delete this friend?";
                    alert.setContentText(s);
                    Optional<ButtonType> result = alert.showAndWait();
                    if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                        
                                MywishlistVbox.getChildren().clear();
                                socket=new Socket(ip, 5005);
                                ps = new PrintStream(socket.getOutputStream());
                                InputStream is = socket.getInputStream();
                                ps.println("removefriend");
                                ps.println(DataHolder.getInstance().getData());
                                ps.println(friendsTable1.getSelectionModel().getSelectedItem().getFriend_email());
                                System.out.println(friendsTable1.getSelectionModel().getSelectedItem().getFriend_email());
                                friendsTable1.getItems().removeAll(friendsTable1.getSelectionModel().getSelectedItem());


                    } 
    }

}

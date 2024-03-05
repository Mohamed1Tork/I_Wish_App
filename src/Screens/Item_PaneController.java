/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import i_wish_server.UserData;
import i_wish_server.UserItems;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author shoroukabdelraouf
 */
public class Item_PaneController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private AnchorPane anchoritemspane;
    @FXML
    private ScrollPane scrollitemspane;
    @FXML
    private VBox vboxscrollitemspane;
    @FXML
    private Label labelselectproductitemspane;
    @FXML
    private Rectangle rectitemspane;
    @FXML
    private Label labeladdnewitemspane;
    @FXML
    private Label labelinsertitemspane;
//    @FXML
//    private TextField textfielditemspane;
    @FXML
    private Label labelcatitemapane;
    @FXML
    private TextField textfieldpropriceitemspane;
    @FXML
    private ChoiceBox<String> categorylistitemspane;
    private final String[] categories = {"Cloths", "Electronics", "Gifts", "Sports", "Games", "Beauty", "Health", "Other"};
    @FXML
    private Button savebuttonuploaditemspane;

    private Parent root;
    private Stage stage;
    private Scene scene;
    static Socket socket;
    static DataInputStream dis;
    static PrintStream ps;
    static InputStream is;
    @FXML
    private TextField textfielditemspane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            socket = new Socket(ip, 5005);
            ps = new PrintStream(socket.getOutputStream());
            is = socket.getInputStream();
            String user = DataHolder.getInstance().getData();
            Platform.runLater(() -> {

                try {
                    ps.println("MarketItems");
                    ps.println(user);

// Read JSON data from the server
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder jsonBuilder = new StringBuilder();
                    String line;
                    line = br.readLine();
                    jsonBuilder.append(line);
                    System.out.println(line);
                    System.out.println("MarketItems");

// Print the received JSON string
                    System.out.println("Received JSON: " + jsonBuilder.toString());

// Convert JSON to ArrayList using Gson
                    Gson gson = new Gson();
                    String json = jsonBuilder.toString();
                    System.out.println("JSON to Deserialize: " + json);

// Use TypeToken to handle generic type (ArrayList<myItems>)
                    java.lang.reflect.Type type = new TypeToken<ArrayList<UserItems>>() {
                    }.getType();

                    ArrayList<UserItems> arr2 = gson.fromJson(json, type);
                    System.out.println("Deserialization Successful. Size: " + arr2.size());

// Print the deserialized objects for verification
                    for (UserItems item : arr2) {
                        System.out.println(" Item Name: " + item.getItem_name() + ", Price: " + item.getPrice() + ", Category: " + item.getCategory());
                    }

                    // Update your UI (JavaFX) with the deserialized data
                    for (UserItems item : arr2) {
                        System.out.println("item id :" + item.getItem_id() + " Item Name: " + item.getItem_name() + ", Price: " + item.getPrice() + ", Category: " + item.getCategory());
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("Market_Item.fxml"));
                        HBox hBox = fxmlLoader.load();
                        Market_ItemController mrc = fxmlLoader.getController();
                        mrc.setData(item.getItem_id(), item.getItem_name(), String.valueOf(item.getPrice()), item.getCategory());
                        System.out.println("finish");
                        vboxscrollitemspane.getChildren().add(hBox);
                    }

//
                } catch (IOException ex) {
                    Logger.getLogger(Item_PaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Item_PaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

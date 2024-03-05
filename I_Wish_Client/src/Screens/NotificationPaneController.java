/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import i_wish_server.Notification;
import i_wish_server.UserData;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author shoroukabdelraouf
 */
public class NotificationPaneController implements Initializable {

    private final String ip = "127.0.0.1";

    private Text emailHolder;

    static Socket socket1;
    static DataInputStream dis1;
    static PrintStream ps1;
    static InputStream is1;
    @FXML
    private AnchorPane anchorconnectpane;
    @FXML
    private ScrollPane scrollconnectpane;
    @FXML
    private VBox vboxnotipane;
    @FXML
    private HBox hboxnotipane;

    /**
     * Initializes the controller class.
     */
    public void setData(String email) {
        emailHolder.setText(email);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            socket1 = new Socket(ip, 5005);
            ps1 = new PrintStream(socket1.getOutputStream());
            InputStream is1 = socket1.getInputStream();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        ps1.println("allNotifications");
                        ps1.println(DataHolder.getInstance().getData());
                        System.out.println("test notification");
                        BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                        StringBuilder jsonBuilder = new StringBuilder();
                        String line;
                        line = br.readLine();
                        jsonBuilder.append(line);
                        System.out.println(line);
                        System.out.println("notifs");

                        /// Print the received JSON string
                        System.out.println("Received JSON: " + jsonBuilder.toString());

// Convert JSON to ArrayList using Gson
                        Gson gson = new Gson();
                        String json = jsonBuilder.toString();
                        System.out.println("JSON to Deserialize: " + json);
// Use TypeToken to handle generic type (ArrayList<myItems>)
                        java.lang.reflect.Type type = new TypeToken<ArrayList<Notification>>() {
                        }.getType();

                        ArrayList<Notification> arr = gson.fromJson(json, type);
                        System.out.println("Deserialization Successful. Size of users: " + arr.size());

                        for (Notification notif : arr) {
                            System.out.println("Item ID: " + notif.getNotification());
                        }
                        for (Notification userData : arr) {
                            System.out.println("Item ID: " + userData.getNotification());
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("mininotification.fxml"));
                            HBox hBox = fxmlLoader.load();
                            MininotificationController frc = fxmlLoader.getController();
                            frc.setData(userData.getNotification());
                            System.out.println("finish");
                            vboxnotipane.getChildren().add(hBox);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(NotificationPaneController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        } catch (IOException ex) {
            Logger.getLogger(NotificationPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

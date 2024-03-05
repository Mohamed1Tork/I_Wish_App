/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import static Screens.FriendRequestController.ps1;
import static Screens.FriendRequestController.socket1;
import i_wish_server.UserItems;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author shoroukabdelraouf
 */
public class Market_ItemController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private VBox vboxmarkitem;
    @FXML
    private Pane paneabvpromaekitem;
    @FXML
    private HBox hboxpromarketitem;
    @FXML
    private Pane panepromarketitem;
    @FXML
    private Text textproductmarketitem;
    @FXML
    private Label ProdNameLabel;
    @FXML
    private HBox hbox2catmarkitem;
    @FXML
    private Pane panecatmarkitem;
    @FXML
    private Text textcatmarkitem;
    @FXML
    private Label categorylabel;
    @FXML
    private HBox hbox3markitem;
    @FXML
    private Pane panepricemarkitem;
    @FXML
    private Text textpricemarkitem;
    @FXML
    private Label PriceLabel;
    @FXML
    private Pane panemywishmarkitem;
    @FXML
    private Button myWishBtn;
    private HBox container;

    @FXML
    private Text itemID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void setData(int itemid, String pname, String price, String category) {
        Platform.runLater(() -> {
            ProdNameLabel.setText(pname);
            PriceLabel.setText(price);
            categorylabel.setText(category);
            itemID.setText(String.valueOf(itemid));;
        });
    }

    @FXML
    public void wishItem() {

        try {
            socket1 = new Socket(ip, 5005);
            ps1 = new PrintStream(socket1.getOutputStream());
            ps1.println("wishitem");
            System.out.println("wish");
            ps1.println(DataHolder.getInstance().getData());
            ps1.println(itemID.getText());
            System.out.println(itemID.getText());
            HBox parenthBox = (HBox) container.getParent();
            parenthBox.getChildren().remove(container);
        } catch (IOException ex) {
            Logger.getLogger(Item_PaneController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps1.close();
                socket1.close();
            } catch (IOException ex) {
                Logger.getLogger(Market_ItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

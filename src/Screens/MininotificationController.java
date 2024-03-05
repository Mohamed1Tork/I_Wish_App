/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Hassan Hosny
 */
public class MininotificationController implements Initializable {

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
    private Label notilabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void setData(String notification) {
        Platform.runLater(() -> {
            notilabel.setText(notification);
        });
    
    }
}

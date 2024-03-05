
package Screens;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.swing.JOptionPane;



public class Server_paneController implements Initializable {
    
    private final String ip = "127.0.0.0";

    @FXML
    private AnchorPane anchoritemspane;
    @FXML
    private Rectangle rectitemspane;
    @FXML
    private Label labelinsertitemspane;
    @FXML
    private TextField textfielditemspane;
    @FXML
    private Label labelcatitemapane;
    @FXML
    private TextField textfieldpropriceitemspane;
    @FXML
    private ChoiceBox<String> categorylistitemspane;
    @FXML
    private Button savebuttonuploaditemspane;
    private final String[] categories = {"Clothes","Electronics","Gifts","Sports","Games","Beauty","Health","Furniture"};
    
    static   Socket socket2;
    static   PrintStream ps2;
    @FXML
    private Hyperlink logoutBT1;
    private Parent root;
    private Stage stage;
    private Scene scene;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorylistitemspane.getItems().addAll(categories);
        // TODO
    }    

    @FXML
    private void SaveAction(ActionEvent event) throws IOException {
                    String name = textfielditemspane.getText();
                    if (name == null || name.isEmpty() || !name.matches("[a-zA-Z0-9\\s]+")) {
                        JOptionPane.showMessageDialog(null,"please enter item name");
                        return;
                    }
                    String price = textfieldpropriceitemspane.getText();
                    if (price == null || price.isEmpty() || !price.matches("\\d+(\\.\\d+)?")) {
                        JOptionPane.showMessageDialog(null,"please enter a valid price (numbers only)");
                        return;
                    }

                    String category = categorylistitemspane.getSelectionModel().getSelectedItem();
                    if (category == null || category.isEmpty()) {
                        JOptionPane.showMessageDialog(null,"please select category");
                        return;
    
                    }
                    
    
    try {
        Socket socket2 = new Socket(ip, 5005);
        PrintStream ps2 = new PrintStream(socket2.getOutputStream());
        


                    // If all inputs are valid, send the data
                    System.out.println(name);
                    System.out.println(price);
                    System.out.println(category);
                    ps2.println("submitItem");
                    ps2.println(name);
                    ps2.println(price);
                    ps2.println(category);

                    textfielditemspane.clear();
                    textfieldpropriceitemspane.clear();
                    categorylistitemspane.valueProperty().set(null);
                    System.out.println("item saved successfully.");
 
        } catch (IOException ex) {
            Logger.getLogger(Server_paneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            ps2.close();
            socket2.close();
        }

        } 

    @FXML
    private void logoutSwitch(ActionEvent event) {
        try {
            Object root = FXMLLoader.load(getClass().getResource("login.fxml"));
            stage = (Stage) logoutBT1.getScene().getWindow();
            Scene scene = new Scene((Parent) root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Server_paneController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    }
    


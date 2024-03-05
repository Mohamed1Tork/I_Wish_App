/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Screens.DataHolder;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class LoginController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private BorderPane borderlogin;
    @FXML
    private AnchorPane anchorlogin;
    @FXML
    private ImageView imgstarlogin;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField paswordPF;
    @FXML
    private Button loginbt;
    @FXML
    private Hyperlink signuplink;
    @FXML
    private Text textyoulogin;
    @FXML
    private Text text2023;
    @FXML
    private Text wronglogin;
    @FXML
    private Label emailtxtlogin;
    @FXML
    private Label txtpasslogin;
    @FXML
    private Label labcreatelogin;
    @FXML
    private Circle circlelogin;
    @FXML
    private ImageView loginbird;
    @FXML
    private Button registerBT;

    private Parent root;
    private Stage stage;
    private Scene scene;

    String resopnse;

    static Socket socket;
    static DataInputStream dis;
    static PrintStream ps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clearMsg(MouseEvent event) {
    }

    @FXML
    private void clearMsg(ActionEvent event) {
    }

    @FXML

    public void login(ActionEvent event) throws IOException {
        if ("root@gmail.com".equals(usernameTF.getText())&& "123456789".equals(paswordPF.getText())) {
            // Load the server_pane.fxml scene for the root user
            root = FXMLLoader.load(getClass().getResource("server_pane.fxml"));
            stage = (Stage) loginbt.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        // Start networking thread for login
        startNetworkingThread();
    }

    private void startNetworkingThread() {
        new Thread(() -> {
            try {
                socket = new Socket(ip, 5005);
                dis = new DataInputStream(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
                ps.println("loginReq");
                ps.println(usernameTF.getText());
                ps.println(paswordPF.getText());

                while (true) {
                    String response = dis.readLine();
                    if (response.equals("Successfull Login")) {
                        System.out.println(response);
                        Platform.runLater(() -> handleSuccessfulLogin());
                        break;
                    } else {
                        Platform.runLater(() -> handleFailedLogin());
                        break;
                    }
                }
            } catch (SocketException se) {
                Platform.runLater(() -> handleConnectionError(se));
            } catch (NullPointerException ne) {
                Platform.runLater(() -> handleNullPointerException(ne));
            } catch (IOException ex) {
                Platform.runLater(() -> handleIOException(ex));
            }
        }).start();
    }

    private void handleSuccessfulLogin() {
        System.out.println("Login successful");

        try {
            DataHolder.getInstance().setData(usernameTF.getText());
            // Load the main pane upon successful login
            root = FXMLLoader.load(getClass().getResource("mainpane.fxml"));
            stage = (Stage) loginbt.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleFailedLogin() {
        Platform.runLater(() -> wronglogin.setText("Wrong Email or Password"));
    }

    private void handleConnectionError(SocketException se) {
        Platform.runLater(this::showConnectionErrorAlert);
    }

    private void showConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection Problem");
        alert.setHeaderText("Server not responding");
        String s = "Please try again";
        alert.setContentText(s);
        alert.show();
    }

    private void handleIOException(IOException ex) {
        System.out.println("IOException: " + ex.getMessage());
    }

    private void handleNullPointerException(NullPointerException ne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @FXML

public void signupSceneSwitch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Registeration.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}



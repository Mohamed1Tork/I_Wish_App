/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author shoroukabdelraouf
 */
public class RegisteringSceneController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private BorderPane borderregisteration;
    @FXML
    private AnchorPane anchorregisteration;
    @FXML
    private TextField fnameTF;
    @FXML
    private TextField lnameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField mobileTF;
    @FXML
    private PasswordField PasswordTF;
    @FXML
    private PasswordField PasswordTF2;
    @FXML
    private Button registerBT;
    @FXML
    private Hyperlink loginlink;
    @FXML
    private ImageView imagiconregisteration;
    @FXML
    private Text textmakeregisteration;
    @FXML
    private Pane paneimgregisteration;
    @FXML
    private ImageView ProfImag;
    @FXML
    private Button uploadPP;
    @FXML
    private Text textprofileregisteration;
    
     private Parent root;
    private Stage stage;
    private Scene scene;
    
    File selectedFile;
     Image image;
     File output = null;
     
     static Socket socket;
    static DataInputStream dis ;
    static PrintStream ps ;
    
   
  
    public void registerClear(){
        fnameTF.clear();
        lnameTF.clear();
        emailTF.clear();
        mobileTF.clear();
        PasswordTF.clear();
        PasswordTF2.clear();
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    String new_name;
    @FXML
    private void register(ActionEvent event)throws IOException {
               
         try{
                socket=new Socket(ip, 5005);
                dis = new DataInputStream(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
                
                
                String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

                
                new Thread(){
                          public void run(){
                             try
                              { 
                                  if (fnameTF.getText().isEmpty() ||emailTF.getText().isEmpty()|| !emailTF.getText().matches(emailPattern)
                                    ||mobileTF.getText().isEmpty()|| PasswordTF.getText().isEmpty()||PasswordTF2.getText().isEmpty()){
                                  JOptionPane.showMessageDialog(null,"All fields are necessary to be filled,Provide a valid email");
                                         return;} 
                                  else if (PasswordTF.getText() == PasswordTF2.getText()) {
                                      JOptionPane.showMessageDialog(null, "Passwords don't match.");
                                      return;
                                  } else if (PasswordTF.getText().length() < 8) {
                                     JOptionPane.showMessageDialog(null,"Invalid Password, at least 8 characters needed");
                                  }
                                  // Send registration request to server
                                  ps.println("registerReq");
                                  ps.println(fnameTF.getText());
                                  ps.println(lnameTF.getText());
                                  ps.println(emailTF.getText());
                                  ps.println(mobileTF.getText());
                                  ps.println(PasswordTF.getText());
                                  
                                  // Wait for response from server
                                while (true){ 
                                    String response = dis.readLine();
                                    System.out.println(response);
                                    if (response.equals("registerSuccess")){//output stream server
                                        System.out.println(response);
                                            registerClear();
                                     Platform.runLater(new Runnable(){
                                    @Override
                                    public void run() {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Registration");
                                        alert.setHeaderText("Congratulations");
                                        String s = "Registrated successfully.";
                                        alert.setContentText(s);
                                        alert.show();
                                    }
                                   });
                                     break;       
                              }
                                    else if (response.equals("ExistedUser")){
                                System.out.println(response);
                                Platform.runLater(new Runnable(){
                                    @Override
                                    public void run() {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Registration");
                                        alert.setHeaderText("User Already Exists");
                                        String s = "The email you are using for registration is already associated with an existing account.";
                                        alert.setContentText(s);
                                        alert.show();
                                    }
                                });
                                break;
                            }
                         }
                      } catch (SocketException se) {
                             JOptionPane.showMessageDialog(null,"Faild to connect with the server" +"\n"+"Error Message: "+se.getMessage());
                             } 
                        catch (NullPointerException ne) {
                                 System.out.println("iiii");
                             } 
                        catch (IOException ex) {
                                 System.out.println("nuio");
                             } 
                        finally {
                                 try {
                                     ps.close();
                                     dis.close();
                                     socket.close();
                                 } catch (IOException ex) {
                                     Logger.getLogger(RegisteringSceneController.class.getName()).log(Level.SEVERE, null, ex);
                                 }
                          }
                }
        
        
        }.start();
                
    } catch (ConnectException conex) {
                 Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Connection Problem");
                        alert.setHeaderText("Server not responding");
                        String s ="Please tru again";
                        alert.setContentText(s);
                        alert.show();
                         }
                    });
        }
        catch (IOException ex) {
            Logger.getLogger(RegisteringSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root= loader.load();
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }

    @FXML
    private void signinSwitch(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root= loader.load();
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
}

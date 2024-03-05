/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Screens.DataHolder;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author fayez
 */
public class MyfriendswishlistsceneController implements Initializable {
    
    private final String ip = "127.0.0.1";

    @FXML
    private HBox container;
    @FXML
    private Pane con;
    @FXML
    private Text itemIdLabel;
    @FXML
    private Label ProdNameLabel;
    @FXML
    private Label PriceLabel;
    @FXML
    private Label CurrContribution;
    @FXML
    private TextField amountLabel;
    static Socket socket1;
    static DataInputStream dis1;
    static PrintStream ps1;

    public void setData(String Product, Float Price, Float Total) {
        ProdNameLabel.setText(Product);
        PriceLabel.setText(String.valueOf(Price));
        CurrContribution.setText(String.valueOf(Total));

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("MyfriendswishlistsceneController initialized.");
    }

    @FXML
    private void contribute(ActionEvent event) {
        int amt;

        amt = Integer.parseInt(amountLabel.getText().trim());
        String amountText = amountLabel.getText().trim();
        if (!amountText.matches("\\d+")) {
            // Show error message to user
            System.out.println("Invalid input, amount should be an integer");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input");
            alert.setHeaderText("Amount should be a number");
            alert.setContentText("Please enter a valid number for the amount");
            alert.showAndWait();
            return;
        }
        amt = Integer.parseInt(amountText);
        int bal = DataHolder.getInstance().getBalance();
        float price = Float.valueOf(PriceLabel.getText());
        float cont = Float.valueOf(CurrContribution.getText());

        if (bal >= amt) {
            // to do
            if (amt > (price - cont)) {
                System.out.println("Amount more than needed");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contribution not completed");
                alert.setHeaderText("Amount more than required");
                String s = "The amount needed to help your friend is just " + (price - cont);
                alert.setContentText(s);
                alert.show();
            } else {
                try {
                    socket1 = new Socket(ip, 5005);
                    ps1 = new PrintStream(socket1.getOutputStream());
                    InputStream is1 = socket1.getInputStream();
                    ps1.println("Contribute");
                    ps1.println(DataHolder.getInstance().getData()); // to email
                    ps1.println(DataHolder.getInstance().getFriend()); // from email
                    ps1.println(ProdNameLabel.getText()); // item_id
                    ps1.println(amountLabel.getText()); // amount
                    
                    System.out.println(DataHolder.getInstance().getData());
                    System.out.println(DataHolder.getInstance().getFriend());
                    System.out.println(ProdNameLabel.getText());
                    System.out.println(amountLabel.getText());
               
                    CurrContribution.setText(String.valueOf(amt + cont));
                    DataHolder.getInstance().setBalance(bal - amt);
                    JOptionPane.showMessageDialog(null, "Succesful contribution with " + amt + " and your balance now is  " + (bal - amt));
                } catch (IOException ex) {
                    Logger.getLogger(MyfriendswishlistsceneController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        ps1.close();
                        socket1.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MyfriendswishlistsceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            System.out.println("Amount not enough");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contribution not completed");
            alert.setHeaderText("Your Balance is not enough");
            String s = "You only have " + bal + " LE in your balance \nYou need to recharge your balance";
            System.out.println(bal);
            alert.setContentText(s);
            alert.show();
        }

    }
}

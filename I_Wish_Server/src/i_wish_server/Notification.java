/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_wish_server;

/**
 *
 * @author fayez
 */
import java.io.Serializable;

/**
 *
 * @author karam
 */
public class Notification implements Serializable {

    private String notification;

    public Notification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}

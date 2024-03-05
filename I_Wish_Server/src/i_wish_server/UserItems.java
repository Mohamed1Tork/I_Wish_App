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
 * @author Karam
 */
public class UserItems implements Serializable {

    private int item_id;
    private String item_name;
    private float price;
    private String category;

    public UserItems(int item_id, String item_name, float price, String category) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.price = price;
        this.category = category;
    }


    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}

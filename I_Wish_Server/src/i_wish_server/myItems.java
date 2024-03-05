// new class 16/1/2024
package i_wish_server;

import java.io.Serializable;

/**
 *
 * @author Hassan Hosny
 */
public class myItems implements Serializable {

    private int item_id;
    private String item_name;
    private float price;

    public myItems(int item_id, String item_name, float price) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.price = price;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}

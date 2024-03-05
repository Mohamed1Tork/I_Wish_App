// change class name from 'UserItems' to Items


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
public class Items implements Serializable {

    private int item_id;
    private String item_name;
    private float price;
    private String category;

    public Items(String item_name, int item_id, int price, String category) {
        this.item_name = item_name;
        this.item_id = item_id;
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

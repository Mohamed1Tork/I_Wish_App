
package i_wish_server;


import java.io.Serializable;


public class UserWishList implements Serializable {

    private String Product;
    private float price;
    private float Total;

    public UserWishList(String Product, float price, float Total) {
        this.Product = Product;
        this.price = price;
        this.Total = Total;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }

    
}

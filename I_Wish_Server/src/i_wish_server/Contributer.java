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
 * @author PC
 */
public class Contributer implements Serializable {

    private String email;
    private String firstname;
    private int total_contribution;

    public Contributer(String email, String firstname, int total_contribution) {
        this.email = email;
        this.firstname = firstname;
        this.total_contribution = total_contribution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getTotal_contribution() {
        return total_contribution;
    }

    public void setTotal_contribution(int total_contribution) {
        this.total_contribution = total_contribution;
    }

}

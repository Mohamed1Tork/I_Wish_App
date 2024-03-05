/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_wish_server;


import java.io.Serializable;
import java.io.IOException;

public class UserData implements Serializable {

    private String fname;
    private String lname;
    private String email;
    private String phone;
    private Integer current_balance;

    public UserData(String fname, String lname, String email, String user_mobile, Integer current_balance) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = user_mobile;
        this.current_balance = current_balance;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(Integer current_balance) {
        this.current_balance = current_balance;
    }

}
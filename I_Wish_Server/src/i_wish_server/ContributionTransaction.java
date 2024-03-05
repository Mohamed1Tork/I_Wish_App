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
public class ContributionTransaction {

    private int item_id;
    private int amount;
    private String sender_email;
    private String reciever_email;

    public ContributionTransaction(int item_id, int amount, String sender_email, String reciever_email) {
        this.item_id = item_id;
        this.amount = amount;
        this.sender_email = sender_email;
        this.reciever_email = reciever_email;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getReciever_email() {
        return reciever_email;
    }

    public void setReciever_email(String reciever_email) {
        this.reciever_email = reciever_email;
    }

}

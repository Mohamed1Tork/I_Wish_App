/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_wish_server;

/**
 *
 * @author Hassan Hosny
 */
public class Friend_request {
    private int request_id;
    private int from_user_id;
    private int to_user_id;
    private String status;

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Friend_request(int request_id, int from_user_id, int to_user_id, String status) {
        this.request_id = request_id;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        this.status = status;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_wish_server;

import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class I_wish_server {

    ServerSocket server_socket;
    Connection con;
    UsersDAL au;

    public I_wish_server() {
        try {
            server_socket = new ServerSocket(5005);
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/iwish", "root", "1234");
            while (true) {
                Socket s = server_socket.accept();
                new ClientHandler(s, con, server_socket);
            }
        } catch (IOException ex) {
            Logger.getLogger(I_wish_server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(I_wish_server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(I_wish_server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        // TODO code application logic here
        new I_wish_server();
    }

}

//////// clients handler to handle mutliple clients requests at the same time
class ClientHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    Connection con = null;
    Socket clientSocket;
    int cur_client;

    static Vector<ClientHandler> clients = new Vector<ClientHandler>();

    /// object from UsersDAL.java class to comuunicate with database through it
    UsersDAL authorUser;

    public ClientHandler(Socket s, Connection con, ServerSocket serSocket) {
        try {
            this.clientSocket = s;
            this.con = con;
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            clients.add(this);
            cur_client = clients.indexOf(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(I_wish_server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            try {
                String requestType = dis.readLine();
                System.out.println(requestType);

                //////////// this switch recieves every request as string and go through specific case to be executed
                switch (requestType) {
                    case "loginReq": ////////// login case -- recieved from scene1controller.java
                        String user = dis.readLine();
                        String pass = dis.readLine();
                        System.out.println(user);
                        System.out.println(pass);
                        authorUser = new UsersDAL(con);
                        if (authorUser.authenticateUser(user, pass)) {
                            System.out.println(user);
                            System.out.println(pass);
                            System.out.println(clients);
                            clients.get(cur_client).ps.println("Successfull Login");
                        } else {
                            clients.get(cur_client).ps.println("Fail to Login");
                            System.out.println("Failed Login");
                        }
                        break;
                    case "registerReq": ////////// login case -- to be recieved from regiteringscenecontroller.java
                        String fname = dis.readLine();
                        String Lname = dis.readLine();
                        String email = dis.readLine();
                        String number = dis.readLine();
                        String password = dis.readLine();
                        UsersDAL reg = new UsersDAL(con);
                        System.out.println(requestType);
                        switch (reg.addUser(fname, Lname, email, number, password)) {
                            case 1:
                                System.out.println(requestType);
                                clients.get(cur_client).ps.println("registerSuccess");
                                break;
                            case 2:
                                clients.get(cur_client).ps.println("wrongFormat");
                                break;
                            case 3:
                                clients.get(cur_client).ps.println("ExistedUser");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "myitems":
                        System.out.println("items");
                        email = dis.readLine();
                        System.out.println(email);
                        authorUser = new UsersDAL(con);
                        ArrayList<myItems> myItemsList = authorUser.getMyItems(email);
                        System.out.println(myItemsList.size());

                        // Convert ArrayList to JSON using Gson
                        Gson gson = new Gson();
                        String json = gson.toJson(myItemsList);

                        // Send JSON data to the client
                        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
                        pw.println(json);
                        pw.flush();

                        System.out.println("Data sent");
                        break;
                    case "mywish":
                        email = dis.readLine();
                        authorUser = new UsersDAL(con);
                        ArrayList<UserWishList> myWishLists = authorUser.getUserWishList(email);
                        System.out.println(myWishLists.size());

                        // Convert ArrayList to JSON using Gson
                        Gson gson1 = new Gson();
                        String json1 = gson1.toJson(myWishLists);

                        // Send JSON data to the client
                        PrintWriter pw1 = new PrintWriter(clientSocket.getOutputStream());
                        pw1.println(json1);
                        pw1.flush();

                        System.out.println("Data sent");
                        break;
                    case "allusers":
                        email = dis.readLine();
                        authorUser = new UsersDAL(con);
                        ArrayList<UserFriendList> UserFriendList = authorUser.getUserFriends(email);
                        System.out.println(UserFriendList.size());

                        // Convert ArrayList to JSON using Gson
                        Gson gson2 = new Gson();
                        String json2 = gson2.toJson(UserFriendList);

                        // Send JSON data to the client
                        PrintWriter pw2 = new PrintWriter(clientSocket.getOutputStream());
                        pw2.println(json2);
                        pw2.flush();

                        System.out.println("Data sent");
                        break;
                    case "userData":
                        email = dis.readLine();
                        authorUser = new UsersDAL(con);
                        ArrayList<UserData> userDataList = authorUser.getUserData(email);

                        // Convert ArrayList to JSON using Gson
                        Gson gsonUserData = new Gson();
                        String jsonUserData = gsonUserData.toJson(userDataList);

                        // Send JSON data to the client
                        PrintWriter pwUserData = new PrintWriter(clientSocket.getOutputStream());
                        pwUserData.println(jsonUserData);
                        pwUserData.flush();

                        System.out.println("Data sent");
                        break;
                    case "friendRequests": ///////////////////
                        email = dis.readLine();
                        System.out.println(email);
                        authorUser = new UsersDAL(con);
                        ArrayList<UserData> requests = authorUser.getFriendRequests(email);
                        System.out.println(requests.size());

                        // Use Gson streaming to write friend requests
                        Gson gson_2 = new Gson();
                        String json_2 = gson_2.toJson(requests);
                        PrintWriter pw_2 = new PrintWriter(clientSocket.getOutputStream());
                        pw_2.println(json_2);
                        pw_2.flush();

                        System.out.println("Data sent");
                        break;
                    case "users":
                        email = dis.readLine();
                        System.out.println(email);
                        authorUser = new UsersDAL(con);
                        ArrayList<UserFriendList> users = authorUser.getAllUsers(email);
                        System.out.println(users.size());

                        // Use Gson streaming to write friend requests
                        Gson gson_3 = new Gson();
                        String json_3 = gson_3.toJson(users);
                        PrintWriter pw_3 = new PrintWriter(clientSocket.getOutputStream());
                        pw_3.println(json_3);
                        pw_3.flush();
                        System.out.println("user sent");
                        break;
                    case "acceptrequest":
                        email = dis.readLine();
                        System.out.println(email);
                        String fr_email = dis.readLine();
                        authorUser = new UsersDAL(con);
                        int acc = authorUser.acceptFriendRequest(email, fr_email);
                        break;
                    case "declinerequest":
                        email = dis.readLine();
                        String fri_email = dis.readLine();
                        authorUser = new UsersDAL(con);
                        int dec = authorUser.declineFriendRequest(email, fri_email);
                        break;
                    case "addfriend":
                        email = dis.readLine();
                        String from_email = dis.readLine();
                        authorUser = new UsersDAL(con);
                        int des = authorUser.friendRequest(from_email, email);
                        break;
                    case "Recharge":
                        email = dis.readLine();
                        String amount = dis.readLine();
                        float balance = Float.parseFloat(amount);
                        authorUser = new UsersDAL(con);
                        int re = authorUser.rechargeBalance(email, balance);
                        break;
                    case "userwishlist":
                        email = dis.readLine();
                        authorUser = new UsersDAL(con);
                        ArrayList<UserWishList> UserWishList = authorUser.getUserWishList(email);
                        System.out.println(UserWishList.size());
                        System.out.println(UserWishList.size());

                        // Use Gson streaming to write friend requests
                        Gson gson_5 = new Gson();
                        String json_5 = gson_5.toJson(UserWishList);
                        PrintWriter pw_5 = new PrintWriter(clientSocket.getOutputStream());
                        pw_5.println(json_5);
                        System.out.println(json_5);
                        pw_5.flush();
                        System.out.println("wish user sent");

                        break;

                    case "MarketItems": ///////////////////
                        email = dis.readLine();
                        System.out.println(email);
                        authorUser = new UsersDAL(con);
                        ArrayList<UserItems> items = authorUser.getAllItems();
                        System.out.println(items.size());

                        // Use Gson streaming to write friend requests
                        Gson gson_6 = new Gson();
                        String json_6 = gson_6.toJson(items);
                        PrintWriter pw_6 = new PrintWriter(clientSocket.getOutputStream());
                        pw_6.println(json_6);
                        pw_6.flush();

                        System.out.println("Data sent");
                        break;

                    case "removefriend":
                        email = dis.readLine();
                        String friend_email = dis.readLine();
                        System.out.println(email);
                        System.out.println(friend_email);
                        authorUser = new UsersDAL(con);
                        int r = authorUser.removeFriend(email, friend_email);
                        System.out.println("test friend");
                        break;

                    case "wishitem":
                        email = dis.readLine();
                        String item_id_w = dis.readLine();
                        authorUser = new UsersDAL(con);
                        int z = authorUser.wishItem(email, item_id_w);
                        System.out.println(z);
                        System.out.println("Test wish");

                        break;
                    case "allNotifications":
                        email = dis.readLine();
                        System.out.println(email);
                        authorUser = new UsersDAL(con);
                        ArrayList<Notification> notifs = authorUser.getNotification(email);
                        System.out.println(notifs);

                        // Use Gson streaming to write friend requests
                        Gson gson_4 = new Gson();
                        String json_4 = gson_4.toJson(notifs);
                        PrintWriter pw_4 = new PrintWriter(clientSocket.getOutputStream());
                        pw_4.println(json_4);
                        pw_4.flush();
                        System.out.println("notifs sent");
                        break;

                    case "Contribute":
                        email = dis.readLine();
                        String f_email = dis.readLine();
                        String item_id = dis.readLine();
                        String c_amount = dis.readLine();
                        System.out.println(email);
                        System.out.println(f_email);
                        System.out.println(item_id);
                        System.out.println(c_amount);
                        authorUser = new UsersDAL(con);
                        int lol = authorUser.contribute(email, f_email, item_id, c_amount);
                        System.out.println(lol);
                        System.out.println("Test contribute");
                        break;

                    case "submitItem":
                        String name = dis.readLine();
                        String price = dis.readLine();
                        String category = dis.readLine();
                        System.out.println(name);
                        System.out.println(price);
                        System.out.println(category);
                        authorUser = new UsersDAL(con);
                        int o = authorUser.insertItem(name, price, category);
                        System.out.println(o);
                        System.out.println("added");
                        break;

                }
            } catch (SocketException se) {
                try {
                    dis.close();
                    ps.close();
                    this.stop();
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}

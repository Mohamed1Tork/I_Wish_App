/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i_wish_server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.Statement;

public class UsersDAL {

    private Connection connection;
    private ResultSet resultSet;

    public UsersDAL(Connection connection) {
        this.connection = connection;
    }

    public boolean authenticateUser(String userEmail, String userPassword) throws SQLException {
        String query = "SELECT * FROM users WHERE user_email=? AND pass=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, userPassword);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        }
    }

    public int addUser(String fname, String Lname, String email, String number, String password) {
        String query = "INSERT INTO USERS (user_email,fname,lname,pass,balance,phone) VALUES(?,?,?,?,0,?)";

        try (PreparedStatement insertst = connection.prepareStatement(query)) {
            insertst.setString(1, email);
            insertst.setString(2, fname);;
            insertst.setString(3, Lname);;
            insertst.setString(4, password);
            insertst.setString(5, number);
            insertst.executeUpdate();
            return 1;

        } catch (NumberFormatException nfex) {
            return 2;
        } catch (SQLIntegrityConstraintViolationException sicvex) {
            return 3;
        } catch (SQLException sex) {
            return 4;
        }
    }

    /*Connect pane*/
    public ArrayList<UserData> getFriendRequests(String email) throws SQLException, IOException {
        ArrayList<UserData> request = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select concat(u.fname,\" \",u.lname), fr.from_email\n"
                + "from friend_request fr\n"
                + "join users u on fr.from_email = u.user_email\n"
                + "where fr.to_email = ?");
        selectuser.setString(1, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            UserData obj = new UserData(resultSet.getString(1), null, resultSet.getString(2), null, 0);
            request.add(obj);
        }
        selectuser.close();
        resultSet.close();
        return request;
    }

    //this query is being updated 21-1-2024
    public ArrayList<UserFriendList> getAllUsers(String email) throws SQLException, IOException {
        ArrayList<UserFriendList> allUsers = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select fname , lname,users.user_email from users\n"
                + "where users.user_email not in(select friend_email from user_friends where user_email = ? \n"
                + "union\n"
                + "select user_email from user_friends where friend_email = ?\n"
                + "union\n"
                + "select to_email from friend_request where from_email = ? \n"
                + "union\n"
                + "select from_email from friend_request where to_email = ? )  and users.user_email != ?; ");
        selectuser.setString(1, email);
        selectuser.setString(2, email);
        selectuser.setString(3, email);
        selectuser.setString(4, email);
        selectuser.setString(5, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            UserFriendList obj = new UserFriendList(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            allUsers.add(obj);
        }
        selectuser.close();
        resultSet.close();
        return allUsers;
    }

    public int acceptFriendRequest(String user_email, String friend_email) {

        try {
            PreparedStatement insert = connection.prepareStatement("insert into user_friends (USER_EMAIL,friend_EMAIL) values(?,?)");
            insert.setString(1, user_email);
            insert.setString(2, friend_email);
            insert.executeUpdate();

            PreparedStatement deleteFromFriendRequest = connection.prepareStatement("delete from friend_request where friend_request.to_email = ? and friend_request.from_email = ?");
            deleteFromFriendRequest.setString(1, user_email);
            deleteFromFriendRequest.setString(2, friend_email);
            deleteFromFriendRequest.executeUpdate();
            insert.close();
            deleteFromFriendRequest.close();
            return 1;
        } catch (SQLException ex) {
            return 0;
        }

    }

    public int friendRequest(String to, String from) {

        try {
            PreparedStatement deletepst = connection.prepareStatement("insert into friend_request values (?,?,\"Pending\")");
            deletepst.setString(1, to);
            deletepst.setString(2, from);
            deletepst.executeUpdate();
            deletepst.close();
            return 1;
        } catch (SQLException ex) {
            return 0;
        }
    }

    public int declineFriendRequest(String user_email, String friend_email) {

        try {
            PreparedStatement decline = connection.prepareStatement("delete from friend_request where friend_request.to_email = ? and friend_request.from_email = ?");
            decline.setString(1, user_email);
            decline.setString(2, friend_email);
            decline.executeUpdate();
            decline.close();
            return 1;
        } catch (SQLException ex) {
            return 0;
        }

    }

    /*-----------------------------------------------------------------*/
 /*Profile pane*/
    public ArrayList<myItems> getMyItems(String email) throws SQLException {
        ArrayList<myItems> myItems = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select o.item_id, items.name, items.price\n"
                + "from user_owned_list o\n"
                + "join items on items.item_id = o.item_id\n"
                + "where o.user_email = ?");
        selectuser.setString(1, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            myItems obj = new myItems(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(3));
            myItems.add(obj);
        }
        return myItems;
    }

    public ArrayList<UserFriendList> getUserFriends(String email) throws SQLException {
        ArrayList<UserFriendList> user_friends = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select u.fname, u.lname, u.user_email\n"
                + "from users u\n"
                + "where u.user_email in (select friend_email\n"
                + "from user_friends where user_email = ?\n"
                + "union\n"
                + "select user_email from user_friends where friend_email = ?)");
        selectuser.setString(1, email);
        selectuser.setString(2, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            UserFriendList obj = new UserFriendList(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            user_friends.add(obj);
        }
        selectuser.close();
        resultSet.close();
        return user_friends;
    }

    public ArrayList<UserWishList> getUserWishList(String email) throws SQLException {
        ArrayList<UserWishList> wish_list = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select i.name ,  i.price, m.cont\n"
                + "from items i , my_wish_list m\n"
                + "where i.item_id = m.item_id\n"
                + "and  m.user_email = ?");
        selectuser.setString(1, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            UserWishList obj = new UserWishList(resultSet.getString(1), resultSet.getFloat(2), resultSet.getFloat(3));
            wish_list.add(obj);
        }
        return wish_list;
    }

    public ArrayList<UserData> getUserData(String email) throws SQLException {
        ArrayList<UserData> user_data = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select concat(fname,\" \",lname), phone, balance\n"
                + "from users\n"
                + "where user_email = ?");
        selectuser.setString(1, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            UserData obj = new UserData(resultSet.getString(1), null, null, resultSet.getString(2), resultSet.getInt(3));
            user_data.add(obj);
        }
        return user_data;
    }

    public int rechargeBalance(String email, float balance) {
        try {
            PreparedStatement recharge = connection.prepareStatement("update users set balance = balance +? where user_email = ?");
            recharge.setFloat(1, balance);
            recharge.setString(2, email);
            recharge.executeUpdate();
            recharge.close();
            return 1;
        } catch (SQLException ex) {
            return 0;
        }

    }

    /*-----------------------------------------------------------------*/
 /*Friends pane*/
    public ArrayList<UserFriendList> getFriends(String email) throws SQLException {
        ArrayList<UserFriendList> user_friends = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select u.fname, u.lname, f.friend_email\n"
                + "from user_friends f\n"
                + "join users u on f.friend_email = u.user_email\n"
                + "where f.user_email = ?");
        selectuser.setString(1, email);
        selectuser.setString(2, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            UserFriendList obj = new UserFriendList(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            user_friends.add(obj);
        }
        selectuser.close();
        resultSet.close();
        return user_friends;
    }

    public ArrayList<UserFriendList> getFriendsWishlist(String email) throws SQLException {
        ArrayList<UserFriendList> user_friends = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select i.name, i.price\n"
                + "from items i\n"
                + "join my_wish_list m on m.item_id = i.item_id\n"
                + "join user_friends f on m.user_email = f.friend_email\n"
                + "where f.user_email = ?");
        selectuser.setString(1, email);
        selectuser.setString(2, email);
        resultSet = selectuser.executeQuery();
        while (resultSet.next()) {
            UserFriendList obj = new UserFriendList(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            user_friends.add(obj);
        }
        selectuser.close();
        resultSet.close();
        return user_friends;
    }

    public int removeFriend(String user, String friend) {

        try {
            PreparedStatement deletepst = connection.prepareStatement("delete from user_friends where ( user_email=? or friend_email=?) and (friend_email =? or user_email=?)");
            deletepst.setString(1, user);
            deletepst.setString(2, user);
            deletepst.setString(3, friend);
            deletepst.setString(4, friend);
            deletepst.executeUpdate();
            deletepst.close();
            return 1;
        } catch (SQLException ex) {
            return 0;
        }

    }

    public int contribute(String user, String friend, String name, String amount) {

        try {
            PreparedStatement contributepst = connection.prepareStatement("insert into contributors \n"
                    + "(from_email, to_email, item_id, item_name, amount, on_date)\n"
                    + "values(?,?,(select item_id from items where name = ?),?,?,null);");
            PreparedStatement updatebal = connection.prepareStatement("update users \n"
                    + "set balance = balance - ?");
            contributepst.setString(1, user);
            contributepst.setString(2, friend);
            contributepst.setString(3, name);
            contributepst.setString(4, name);
            contributepst.setFloat(5, Float.valueOf(amount));
            contributepst.executeUpdate();
            updatebal.setFloat(1, Float.valueOf(amount));
            updatebal.executeUpdate();
            contributepst.close();
            return 1;
        } catch (SQLException ex) {
            return 0;
        }

    }

    /*-------------------------------------------------------------------------*/
 /*items pane*/
    public ArrayList<UserItems> getAllItems() throws SQLException {
        ArrayList<UserItems> user_items = new ArrayList<>();
        PreparedStatement selectuser = connection.prepareStatement("select * from items");
        //selectuser.setString(1,email);
        resultSet = selectuser.executeQuery();

        while (resultSet.next()) {
            UserItems obj = new UserItems(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4));
            user_items.add(obj);
        }
        return user_items;
    }

    public ArrayList<UserFriendList> getFriendsList(String userEmail) throws SQLException {
        ArrayList<UserFriendList> friendsList = new ArrayList<>();

        // TODO: Implement database query to fetch friends list based on the provided user_email
        // Use the appropriate SQL query and adapt it based on your database schema
        String query = "SELECT u.fname, u.lname, u.email AS friend_email FROM users u "
                + "JOIN user_friends uf ON u.email = uf.friend_email "
                + "WHERE uf.user_email = ?";

        try (java.sql.PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userEmail);

            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String friendFirstName = rs.getString("fname");
                    String friendLastName = rs.getString("lname");
                    String friendEmail = rs.getString("friend_email");

                    UserFriendList friend = new UserFriendList(friendFirstName, friendLastName, friendEmail);
                    friendsList.add(friend);
                }
            }

        }
        return friendsList;
    }

    public int wishItem(String user, String item_id) {

        try {
            PreparedStatement deletepst = connection.prepareStatement("insert into my_wish_list values (?,?,0)");
            deletepst.setString(1, user);
            deletepst.setInt(2, Integer.valueOf(item_id));
            deletepst.executeUpdate();
            deletepst.close();
            return 1;
        } catch (SQLException ex) {
            return 0;
        }
    }

    /*--------------------------------------------------------------------------*/
 /*notification pane*/
    public ArrayList<Notification> getNotification(String user) throws SQLException {
        ArrayList<Notification> notifications = new ArrayList<>();

        PreparedStatement getNotif = connection.prepareStatement("select n.content \n"
                + "from notifications n , contributors c\n"
                + "where n.to_email = c.to_email\n"
                + "and n.from_email = c.from_email\n"
                + "and ( n.to_email = ? or n.from_email = ? )");
        getNotif.setString(1, user);
        getNotif.setString(2, user);
        resultSet = getNotif.executeQuery();

        while (resultSet.next()) {
            Notification obj = new Notification(resultSet.getString(1));
            notifications.add(obj);
        }

        return notifications;
    }

    public int insertItem(String name, String price, String category) {
        try {
            PreparedStatement insert1 = connection.prepareStatement("INSERT INTO ITEMS (NAME, PRICE,CATEGORY) VALUES (? , ? , ?)");
            insert1.setString(1, name);
            insert1.setFloat(2, Float.valueOf(price));
            insert1.setString(3, category);
            insert1.executeUpdate();
            connection.commit();

            return 1;
        } catch (SQLException ex) {
            return 0;
        }
    }

}

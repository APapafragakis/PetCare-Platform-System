package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Message;

import javax.sound.midi.SysexMessage;

public class EditMessagesTable {

    public void addMessageFromJSON(String json) throws ClassNotFoundException {
        Message msg = jsonToMessage(json);
        createNewMessage(msg);
    }

    public Message jsonToMessage(String json) {
        Gson gson = new Gson();
        Message msg = gson.fromJson(json, Message.class);
        return msg;
    }

    public String reviewToJSON(Message msg) {
        Gson gson = new Gson();
        String json = gson.toJson(msg, Message.class);
        return json;
    }

    public ArrayList<Message> databaseToMessage(int booking_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Message> messages = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM messages WHERE booking_id= '" + booking_id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Message msg = gson.fromJson(json, Message.class);
                messages.add(msg);
            }
            return messages;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return null;
    }

    public void createMessageTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE messages "
                + "(message_id INTEGER not NULL AUTO_INCREMENT, "
                + "booking_id INTEGER not NULL, "
                + "message VARCHAR(500) not NULL, "
                + "sender VARCHAR(500) not NULL, "
                + "datetime DATETIME ,"
                + "FOREIGN KEY (booking_id) REFERENCES bookings(booking_id), "
                + "PRIMARY KEY ( message_id ))";

        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewMessage(Message msg) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            System.out.println(msg.getBooking_id());
            String insertQuery = "INSERT INTO "
                    + " messages (booking_id,message,sender,datetime) "
                    + " VALUES ("
                    + "'" + msg.getBooking_id() + "',"
                    + "'" + msg.getMessage() + "',"
                    + "'" + msg.getSender() + "',"
                    + "'" + msg.getDatetime() + "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The message was successfully added in the database.");

            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditMessagesTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createNewMessageWithVariables(int booking_id, String message, String sender, String datetime) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            String insertQuery = "INSERT INTO "
                    + " messages (booking_id,message,sender,datetime) "
                    + " VALUES ("
                    + "'" + booking_id + "',"
                    + "'" + message + "',"
                    + "'" + sender + "',"
                    + "'" + datetime + "'"
                    + ")";
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The message was successfully added in the database.");

            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditMessagesTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

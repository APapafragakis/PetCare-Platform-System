/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import mainClasses.Booking;
import database.DB_Connection;
import mainClasses.Message;
import mainClasses.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public class EditBookingsTable {

    public void addBookingFromJSON(String json) throws ClassNotFoundException {
        Booking r = jsonToBooking(json);
        System.out.println("Add bookings function!");
        System.out.println(r.getOwner_id());
        System.out.println(r.getKeeper_id());
        System.out.println(r.getPet_id());

        createNewBooking(r);
    }

    public void myAddBookingFromJSON(String json, int a, int b, int c) throws ClassNotFoundException {
        Booking r = jsonToBooking(json);
        r.setOwner_id(a);
        r.setKeeper_id(b);
        r.setPet_id(c);


        System.out.println("Add bookings function!");
        System.out.println(r.getOwner_id());
        System.out.println(r.getKeeper_id());
        System.out.println(r.getPet_id());

        createNewBooking(r);
    }

    public Booking databaseToBooking(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE booking_id= '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Booking bt = gson.fromJson(json, Booking.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }



    public ArrayList<Integer> getFinishedBookingIdsForOwner(String ownerId) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Integer> finishedBookingIds = new ArrayList<>();

        try {
            ResultSet rs = stmt.executeQuery("SELECT booking_id FROM bookings WHERE owner_id='" + ownerId + "' AND status='finished'");
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                finishedBookingIds.add(bookingId);
            }

            return finishedBookingIds;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            // Close resources
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return null;
    }

    public Booking jsonToBooking(String json) {
        Gson gson = new Gson();
        Booking r = gson.fromJson(json, Booking.class);
        return r;
    }

    public String bookingToJSON(Booking r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Booking.class);
        return json;
    }

    public void updateBooking(int bookingID,  String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE bookings SET status='"+status+"' WHERE booking_id= '"+bookingID+"'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }


    public List<Booking> getAllBookings() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Booking> bookingsList = new ArrayList<>();

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM bookings";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBorrowing_id(rs.getInt("booking_id"));
                booking.setOwner_id(rs.getInt("owner_id"));
                booking.setPet_id(rs.getInt("pet_id"));
                booking.setKeeper_id(rs.getInt("keeper_id"));
                booking.setFromDate(rs.getString("fromdate")); // Assuming "fromdate" is a string; adjust accordingly
                booking.setToDate(rs.getString("todate"));     // Assuming "todate" is a string; adjust accordingly
                booking.setStatus(rs.getString("status"));
                booking.setPrice(rs.getInt("price"));

                bookingsList.add(booking);
            }

        } finally {
            // Close resources
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return bookingsList;
    }


    public ArrayList<Booking> getBookingWithId(int booking_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Booking> bbs = new ArrayList<>();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM bookings WHERE booking_id = '" + booking_id + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Booking rev = gson.fromJson(json, Booking.class);
                bbs.add(rev);
                System.out.println(json);
            }
            bbs.get(0).setBookingId(booking_id);
        } finally {
            // Close resources
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return bbs;
    }


    public void createBookingTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE bookings "
                + "(booking_id INTEGER not NULL AUTO_INCREMENT, "
                + " owner_id INTEGER not NULL, "
                + "  pet_id VARCHAR(10) not NULL, "
                + " keeper_id INTEGER not NULL, "
                + " fromdate DATE not NULL, "
                + " todate DATE not NULL, "
                + " status VARCHAR(15) not NULL, "
                + " price INTEGER not NULL, "
                + "FOREIGN KEY (owner_id) REFERENCES petowners(owner_id), "
                + "FOREIGN KEY (pet_id) REFERENCES pets(pet_id), "
                + "FOREIGN KEY (keeper_id) REFERENCES petkeepers(keeper_id), "
                + " PRIMARY KEY (booking_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }


    public int getOwnerIDFromBookingID(int bookingID) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("SELECT owner_id FROM bookings WHERE booking_id='" + bookingID + "'");
            if (rs.next()) {
                return rs.getInt("owner_id");
            }
        } finally {
            // Close resources
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        // Return -1 or throw an exception based on your error handling logic
        return -1;
    }

    public int getBookingIDFromOwnerID(int ownerid) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("SELECT booking_id FROM bookings WHERE owner_id='" + ownerid + "'");
            if (rs.next()) {
                return rs.getInt("booking_id");
            }
        } finally {
            // Close resources
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        // Return -1 or throw an exception based on your error handling logic
        return -1;
    }

    public int getBookingIDFromKeeperID(int keeperID) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("SELECT booking_id FROM bookings WHERE keeper_id='" + keeperID + "'");
            if (rs.next()) {
                return rs.getInt("booking_id");
            }
        } finally {
            // Close resources
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        // Return -1 or throw an exception based on your error handling logic
        return -1;
    }


    public int getKeeperIDFromBookingID(int bookingID) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("SELECT keeper_id FROM bookings WHERE booking_id='" + bookingID + "'");
            if (rs.next()) {
                return rs.getInt("keeper_id");
            }
        } finally {
            // Close resources
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        // Return -1 or throw an exception based on your error handling logic
        return -1;
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewBooking(Booking bor) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();


            String insertQuery = "INSERT INTO "
                    + " bookings (owner_id,pet_id,keeper_id,fromDate,toDate,status,price)"
                    + " VALUES ("
                    + "'" + bor.getOwner_id() + "',"
                    + "'" + bor.getPet_id() + "',"
                     + "'" + bor.getKeeper_id()+ "',"
                    + "'" + bor.getFromDate() + "',"
                    + "'" + bor.getToDate() + "',"
                    + "'" + bor.getStatus() + "',"
                     + "'" + bor.getPrice() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The booking was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditBookingsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

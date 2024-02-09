/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import mainClasses.PetKeeper;
import database.DB_Connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Mike
 */
public class EditPetKeepersTable1 {

 
    public void addPetKeeperFromJSON(String json) throws ClassNotFoundException{
         PetKeeper user=jsonToPetKeeper(json);
         addNewPetKeeper(user);
    }
    
     public PetKeeper jsonToPetKeeper(String json){
         Gson gson = new Gson();

        PetKeeper user = gson.fromJson(json, PetKeeper.class);
        return user;
    }
    
    public String petKeeperToJSON(PetKeeper user){
         Gson gson = new Gson();

        String json = gson.toJson(user, PetKeeper.class);
        return json;
    }
    
   
    
    public void updatePetKeeper(String username,String personalpage) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE petkeepers SET personalpage='"+personalpage+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }

    public void updatePetKeeperMine(String username, String personalpage, String gender, String birthdate, String firstname, String lastname, String job, String telephone, String property, String catkeeper, String dogkeeper, int catprice, int dogprice, String propertydescription) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update;
        if (!personalpage.equals("")) {
            update = "UPDATE petkeepers SET personalpage='" + personalpage + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }

        if (!birthdate.equals("")) {
            update = "UPDATE petkeepers SET birthdate='" + birthdate + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }

        if (gender != null) {
            update = "UPDATE petkeepers SET gender='" + gender + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!firstname.equals("")) {
            update = "UPDATE petkeepers SET firstname='" + firstname + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!lastname.equals("")) {
            update = "UPDATE petkeepers SET lastname='" + lastname + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!job.equals("")) {
            update = "UPDATE petkeepers SET job='" + job + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!telephone.equals("")) {
            update = "UPDATE petkeepers SET telephone='" + telephone + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (property != null) {
            update = "UPDATE petkeepers SET property='" + property + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (catkeeper != null) {
            update = "UPDATE petkeepers SET catkeeper='" + catkeeper + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (dogkeeper != null) {
            update = "UPDATE petkeepers SET dogkeeper='" + dogkeeper + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        update = "UPDATE petkeepers SET catprice='" + catprice + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
        update = "UPDATE petkeepers SET dogprice='" + dogprice + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
        if (propertydescription != null) {
            update = "UPDATE petkeepers SET propertydescription='" + propertydescription + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }

    }
    public void printPetKeeperDetails(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='"+password+"'");
            while (rs.next()) {
                System.out.println("===Result===");
                DB_Connection.printResults(rs);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    
    public PetKeeper databaseToPetKeepers(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public PetKeeper databaseToPetKeepersUsernameEmail(String username, String email) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
       Statement stmt = con.createStatement();

       ResultSet rs;
       try {
           rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' OR email='"+email+"'");
           rs.next();
           String json=DB_Connection.getResultsToJSON(rs);
           Gson gson = new Gson();
           PetKeeper user = gson.fromJson(json, PetKeeper.class);
           return user;
       } catch (Exception e) {
           System.err.println("Got an exception! ");
           System.err.println(e.getMessage());
       }
       return null;
   }
    
    public PetKeeper databaseToPetKeepersUsernameOnly(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
       Statement stmt = con.createStatement();

       ResultSet rs;
       try {
           rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "'");
           rs.next();
           String json=DB_Connection.getResultsToJSON(rs);
           Gson gson = new Gson();
           PetKeeper user = gson.fromJson(json, PetKeeper.class);
           return user;
       } catch (Exception e) {
           System.err.println("Got an exception! ");
           System.err.println(e.getMessage());
       }
       return null;
   }

    
     public ArrayList<PetKeeper> getAvailableKeepers(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<PetKeeper>();
        ResultSet rs = null;
        try {
            //if(type=="catkeeper")


            if ("all".equals(type)) {
                rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE  `petKeepers`.`keeper_id` not in (select keeper_id "
                        + "from `bookings` where `status`='requested' or  `status`='accepted')\n" + "");
            } else if ("cat".equals(type)) {

                 rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE `petKeepers`.`catkeeper`='true' AND `petKeepers`.`keeper_id` not in (select keeper_id "
                        + "from `bookings` where `status`='requested' or  `status`='accepted')");
            } else if ("dog".equals(type)) {
                 rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE `petKeepers`.`dogkeeper`='true' AND `petKeepers`.`keeper_id` not in (select keeper_id "
                         + "from `bookings` where `status`='requested' or  `status`='accepted')");
            }

        
           
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                keepers.add(keeper);
            }
            return keepers;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    
    public ArrayList<PetKeeper> getKeepers(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<PetKeeper>();
        ResultSet rs = null;
        try {
            if("catkeeper".equals(type))
                 rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE catkeeper= '" + "true" + "'");
            else if ("dogkeeper".equals(type))
                  rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE dogkeeper= '" + "true" + "'");

           
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                keepers.add(keeper);
            }
            return keepers;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    
    public String databasePetKeeperToJSON(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    
    public List<String> databaseToPetKeepersUsernamesAll() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT username FROM petkeepers");

            List<String> usernameList = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString("username");
                usernameList.add(username);
            }

            return usernameList;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            // Close resources in the reverse order of their creation to avoid leaks
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return Collections.emptyList();
    }

    
    public void deletePetKeeper(String username) throws SQLException, ClassNotFoundException {
    Connection con = null;
    Statement stmt = null;

    try {
        con = DB_Connection.getConnection();
        stmt = con.createStatement();

        // Assuming 'petkeepers' table with a 'username' column
        String deleteQuery = "DELETE FROM petkeepers WHERE username='" + username + "'";
        stmt.executeUpdate(deleteQuery);
    } finally {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }
}

    public void deleteBookingsForPetOwner(int ownerId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        Statement stmt = null;

        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();

            // Assuming 'bookings' table with an 'owner_id' column
            String deleteQuery = "DELETE FROM bookings WHERE owner_id=" + ownerId;
            stmt.executeUpdate(deleteQuery);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    public static int countPetKeepers() throws SQLException, ClassNotFoundException {
    Connection con = DB_Connection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs;

    try {
        rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM petkeepers");

        if (rs.next()) {
            int count = rs.getInt("count");
            return count;
        }
    } catch (Exception e) {
        System.err.println("Got an exception! ");
        System.err.println(e.getMessage());
    } finally {
        // Close resources (ResultSet, Statement, Connection)
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    return 0; // Return 0 if there was an exception or no result
}

    public int getKeeperIdByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT keeper_id FROM petkeepers WHERE username = '" + username + "'");
            if (rs.next()) {
                return rs.getInt("keeper_id");
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            // Close resources in the reverse order of their creation to avoid leaks
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return -1; // Return -1 if no matching keeper_id found
    }

    public void deleteBookingsForPetKeeper(int keeperId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        Statement stmt = null;

        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();

            // Delete messages associated with the bookings
            String deleteMessagesQuery = "DELETE FROM messages WHERE booking_id IN (SELECT booking_id FROM bookings WHERE keeper_id=" + keeperId + ")";
            stmt.executeUpdate(deleteMessagesQuery);

            // Delete bookings for the specified keeper_id
            String deleteBookingsQuery = "DELETE FROM bookings WHERE keeper_id=" + keeperId;
            stmt.executeUpdate(deleteBookingsQuery);

        } finally {
            // Close resources in the reverse order of their creation to avoid leaks
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }



    public void createPetKeepersTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE petkeepers "
                + "(keeper_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(50) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(30) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    country VARCHAR(30) not null,"
                + "    city VARCHAR(50) not null,"
                + "    address VARCHAR(50) not null,"
                + "    personalpage VARCHAR(200) not null,"
                + "    job VARCHAR(200) not null,"
                + "    telephone VARCHAR(14),"
                + "    lat DOUBLE,"
                + "    lon DOUBLE,"
                + "    property VARCHAR(10) not null,"
                + "    propertydescription VARCHAR(200),"
                + "    catkeeper VARCHAR(10) not null,"
                + "    dogkeeper VARCHAR(10) not null,"
                + "    catprice INTEGER,"
                + "    dogprice INTEGER,"
                + " PRIMARY KEY (keeper_id))";
        stmt.execute(query);
        stmt.close();
    }


    public PetKeeper databaseToPetKeepersOnlyName2(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            username = username.substring(1, username.length() - 1);
            System.out.println("Inside " + username);

            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            System.out.println("Inside 2nd " + user.getUsername());

            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewPetKeeper(PetKeeper user) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " petkeepers (username,email,password,firstname,lastname,birthdate,gender,country,city,address,personalpage,"
                    + "job,telephone,lat,lon,property,propertydescription,catkeeper,dogkeeper,catprice,dogprice)"
                    + " VALUES ("
                    + "'" + user.getUsername() + "',"
                    + "'" + user.getEmail() + "',"
                    + "'" + user.getPassword() + "',"
                    + "'" + user.getFirstname() + "',"
                    + "'" + user.getLastname() + "',"
                    + "'" + user.getBirthdate() + "',"
                    + "'" + user.getGender() + "',"
                    + "'" + user.getCountry() + "',"
                    + "'" + user.getCity() + "',"
                    + "'" + user.getAddress() + "',"
                    + "'" + user.getPersonalpage() + "',"
                     + "'" + user.getJob() + "',"
                    + "'" + user.getTelephone() + "',"
                    + "'" + "69.69" + "',"
                    + "'" + "69.69" + "',"
                    + "'" + user.getProperty() + "',"
                    + "'" + user.getPropertydescription()+ "',"
                    + "'" + user.getCatkeeper() + "',"
                    + "'" + user.getDogkeeper() + "',"
                    + "'" + user.getCatprice() + "',"
                    + "'" + user.getDogprice() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The pet keeper was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            //Logger.getLogger(EditPetKeepersTable1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PetKeeper databaseToPetKeepersOnlyName(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {


            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);


            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

   

}

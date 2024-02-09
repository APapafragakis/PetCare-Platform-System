/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import mainClasses.PetOwner;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditPetOwnersTable {

 
    public void addPetOwnerFromJSON(String json) throws ClassNotFoundException{
         PetOwner user=jsonToPetOwner(json);
         addNewPetOwner(user);
    }
    
     public PetOwner jsonToPetOwner(String json){
         Gson gson = new Gson();

        PetOwner user = gson.fromJson(json, PetOwner.class);
        return user;
    }
    
    public String petOwnerToJSON(PetOwner user){
         Gson gson = new Gson();

        String json = gson.toJson(user, PetOwner.class);
        return json;
    }
    
   
    
    public void updatePetOwner(String username, String personalpage, String gender, String birthdate, String firstname, String lastname, String job, String telephone) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update;
        if (!personalpage.equals("")) {
            update = "UPDATE petowners SET personalpage='" + personalpage + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }

        if (!birthdate.equals("")) {
            update = "UPDATE petowners SET birthdate='" + birthdate + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }

        if (gender != null) {
            update = "UPDATE petowners SET gender='" + gender + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!firstname.equals("")) {
            update = "UPDATE petowners SET firstname='" + firstname + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!lastname.equals("")) {
            update = "UPDATE petowners SET lastname='" + lastname + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!job.equals("")) {
            update = "UPDATE petowners SET job='" + job + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }
        if (!telephone.equals("")) {
            update = "UPDATE petowners SET telephone='" + telephone + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(update);
        }


    }

   
    
    public PetOwner databaseToPetOwners(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetOwner user = gson.fromJson(json, PetOwner.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public PetOwner databaseToPetOwnersWithUsernameEmail(String username, String email) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
       Statement stmt = con.createStatement();

       ResultSet rs;
       try {
           rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' OR email ='"+email+"'");
           rs.next();
           String json=DB_Connection.getResultsToJSON(rs);
           Gson gson = new Gson();
           PetOwner user = gson.fromJson(json, PetOwner.class);
           return user;
       } catch (Exception e) {
           System.err.println("Got an exception! ");
           System.err.println(e.getMessage());
       }
       return null;
   }
    
    public PetOwner databaseToPetOwnersWithUsernameOnly(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
       Statement stmt = con.createStatement();

       ResultSet rs;
       try {
           rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "'");
           rs.next();
           String json=DB_Connection.getResultsToJSON(rs);
           Gson gson = new Gson();
           PetOwner user = gson.fromJson(json, PetOwner.class);
           return user;
       } catch (Exception e) {
           System.err.println("Got an exception loooool! ");
           System.err.println(e.getMessage());
       }
       return null;
   }
    
    public String databasePetOwnerToJSON(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

     public List<String> databaseToPetOwnersUsernamesAll() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT username FROM petowners");

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
    
     
     public void deletePetOwner(String username) throws SQLException, ClassNotFoundException {
    Connection con = null;
    Statement stmt = null;

    try {
        con = DB_Connection.getConnection();
        stmt = con.createStatement();

        // Assuming 'petowners' table with a 'username' column
        String deleteQuery = "DELETE FROM petowners WHERE username='" + username + "'";
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

    public void     deletePetsForPetOwner(int ownerId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        Statement stmt = null;

        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();

            // Assuming 'pets' table with an 'owner_id' column
            String deleteQuery = "DELETE FROM pets WHERE owner_id=" + ownerId;
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


    public static int countPetOwners() throws SQLException, ClassNotFoundException {
    Connection con = DB_Connection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs;

    try {
        rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM petowners");

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

    public String getPetTypeOfOwner(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT type FROM pets WHERE owner_id = (SELECT owner_id FROM petowners WHERE username = '" + username + "') LIMIT 1");

            if (rs.next()) {
                return rs.getString("type");
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

        return null;
    }

    public int getOwnerIdByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT owner_id FROM petowners WHERE username = '" + username + "'");
            if (rs.next()) {
                return rs.getInt("owner_id");
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

        return -1; // Return -1 if no matching owner_id found
    }



    public void createPetOwnersTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE petowners "
                + "(owner_id INTEGER not NULL AUTO_INCREMENT, "
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
                + " PRIMARY KEY (owner_id))";
        stmt.execute(query);
        stmt.close();
    }
     
    
    
    
    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewPetOwner(PetOwner user) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " petowners (username,email,password,firstname,lastname,birthdate,gender,country,city,address,personalpage,"
                    + "job,telephone,lat,lon)"
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
                    + "'" + "69.69" + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The pet owner was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditPetOwnersTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PetOwner databaseToPetOwnersOnlyName(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetOwner user = gson.fromJson(json, PetOwner.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }


   

}

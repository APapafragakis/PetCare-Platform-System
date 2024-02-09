/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.Pet;
import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditPetsTable {

    public void addPetFromJSON(String json) throws ClassNotFoundException, SQLException {
        Pet bt = jsonToPet(json);
        int id = bt.getOwner_id();
        //If not 0 update

        if (petOfOwner(String.valueOf(id)).getPet_id() != 0) {
            System.out.println("Is null");
            updatePetEverything(String.valueOf(bt.getOwner_id()), bt.getName(), bt.getType(), bt.getBreed(), bt.getGender(), bt.getBirthyear(), bt.getWeight(), bt.getDescription(), bt.getPhoto());

        } else {
            System.out.println("Is NOT null");

            createNewPet(bt);

        }



        //Logika efw tha kanei update an o owner id uparxei hdh sta pets
    }

    public Pet jsonToPet(String json) {
        Gson gson = new Gson();
        Pet btest = gson.fromJson(json, Pet.class);
        return btest;
    }

    public String petToJSON(Pet bt) {
        Gson gson = new Gson();

        String json = gson.toJson(bt, Pet.class);
        return json;
    }

    public ArrayList<Pet> databaseToPets() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Pet> pets = new ArrayList<Pet>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM pets");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Pet pet = gson.fromJson(json, Pet.class);
                pets.add(pet);
            }
            return pets;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }



    public Pet petOfOwner(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Pet pet = new Pet();

        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM pets WHERE owner_id= '" + id + "'");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                pet = gson.fromJson(json, Pet.class);
            }

            return pet;
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
        return null;
    }


    public ArrayList<Pet> databaseToPets(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Pet> pets = new ArrayList<Pet>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM pets WHERE type= '" + type + "'");
           
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Pet pet = gson.fromJson(json, Pet.class);
                pets.add(pet);
            }
            return pets;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public static int countPetsByType(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try {
            rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM pets WHERE type = '" + type + "'");

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

    public void updatePet(String id, String name) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Pet bt = new Pet();

        String update = "UPDATE pets SET name='" + name + "'" + "WHERE pet_id = '" + id + "'";
        //stmt.executeUpdate(update);
    }

    public void updatePetEverything(String id, String name, String type, String breed, String gender, int birthyear, double weight, String description, String photo) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Pet bt = new Pet();
        if (!name.equals("")) {
            String update = "UPDATE pets SET name='" + name + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }

        if (!type.equals("")) {

            String update = "UPDATE pets SET type='" + type + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }

        if (!breed.equals("")) {
            String update = "UPDATE pets SET breed='" + breed + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }

        if (!gender.equals("")) {
            String update = "UPDATE pets SET gender='" + gender + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }

        if (birthyear != 0) {
            String update = "UPDATE pets SET birthyear='" + birthyear + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }

        if (weight != 0) {
            String update = "UPDATE pets SET weight='" + weight + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }

        if (!description.equals("")) {
            String update = "UPDATE pets SET description='" + description + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }

        if (!photo.equals("")) {
            String update = "UPDATE pets SET photo='" + photo + "'" + "WHERE owner_id = '" + id + "'";
            stmt.executeUpdate(update);
        }


    }

    public void deletePet(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM pets WHERE pet_id='" + id + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();    
    }

    public String findPetTypeByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try {
            rs = stmt.executeQuery("SELECT p.type FROM pets p INNER JOIN petowners po ON p.owner_id = po.owner_id WHERE po.username = '" + username + "'");

            if (rs.next()) {
                return rs.getString("type");
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

        return null; // Return null if there was an exception or no result
    }

    public void createPetsTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE pets "
                + "(pet_id VARCHAR(10) not NULL unique, "
                + "owner_id INTEGER not null ,"
                + "name VARCHAR(30) not null,"
                + "type VARCHAR(3)  not null, "
                + "breed VARCHAR(30)  not null, "
                + "gender VARCHAR(7)  not null, "
                + "birthyear INTEGER not null , "
                + "weight DOUBLE not null , "
                + "description VARCHAR (500), "
                + "photo VARCHAR (300), "
                + "FOREIGN KEY (owner_id) REFERENCES petowners(owner_id), "
                + "PRIMARY KEY (pet_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewPet(Pet bt) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " pets (pet_id,owner_id,name,type,breed,gender,birthyear,weight,description,photo) "
                    + " VALUES ("
                    + "'" + bt.getPet_id() + "',"
                    + "'" + bt.getOwner_id() + "',"
                    + "'" + bt.getName() + "',"
                    + "'" + bt.getType()+ "',"
                    + "'" + bt.getBreed()+ "',"
                    + "'" + bt.getGender()+ "',"
                    + "'" + bt.getBirthyear()+ "',"
                    + "'" + bt.getWeight() + "',"
                    + "'" + bt.getDescription() + "',"
                    + "'" + bt.getPhoto() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The pet was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditPetsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.init;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import database.tables.EditPetsTable;
import static database.DB_Connection.getInitialConnection;
import database.tables.EditBookingsTable;
import database.tables.EditMessagesTable;

import database.tables.EditPetOwnersTable;
import database.tables.EditPetKeepersTable1;
import database.tables.EditReviewsTable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mainClasses.Message;
import mainClasses.Pet;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;
import mainClasses.Review;


/*
 *
 * @author micha
 */
public class InitDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();
        init.addToDatabaseExamples();
        init.updateRecords();
        init.databaseToJSON();

        //  init.dropDatabase();
        // init.deleteRecords();
    }

    public void dropDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        String sql = "DROP DATABASE  HY359_2023";
        stmt.executeUpdate(sql);
        System.out.println("Database dropped successfully...");
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE HY359_2023");			
        stmt.close();
        conn.close();
    }

    public void initTables() throws SQLException, ClassNotFoundException {
        EditPetOwnersTable eut = new EditPetOwnersTable();
        eut.createPetOwnersTable();

        EditPetKeepersTable1 editkeepers = new EditPetKeepersTable1();
        editkeepers.createPetKeepersTable();

        EditPetsTable editpets = new EditPetsTable();
        editpets.createPetsTable();

        EditBookingsTable editBookings = new EditBookingsTable();
        editBookings.createBookingTable();

        EditReviewsTable editRevs = new EditReviewsTable();
        editRevs.createReviewTable();

        EditMessagesTable editMsgs = new EditMessagesTable();
        editMsgs.createMessageTable();
    }

    public void addToDatabaseExamples() throws ClassNotFoundException, SQLException {
        //Users

        EditPetOwnersTable eut = new EditPetOwnersTable();
        eut.addPetOwnerFromJSON(Resources.petOwnerJSON);
        eut.addPetOwnerFromJSON(Resources.petOwner2JSON);
        eut.addPetOwnerFromJSON(Resources.petOwner3JSON);
        eut.addPetOwnerFromJSON(Resources.petOwner4JSON);

        EditPetKeepersTable1 editKeepers = new EditPetKeepersTable1();
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper1);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper2);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper3);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper4);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper5);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper6);

        EditPetsTable ebt = new EditPetsTable();
        ebt.addPetFromJSON(Resources.pet1);
        ebt.addPetFromJSON(Resources.pet2);
        ebt.addPetFromJSON(Resources.pet3);
        ebt.addPetFromJSON(Resources.pet4);

        EditBookingsTable editbookings = new EditBookingsTable();
        editbookings.addBookingFromJSON(Resources.booking1);
        editbookings.addBookingFromJSON(Resources.booking2);
        editbookings.addBookingFromJSON(Resources.booking3);

        EditMessagesTable editmessages = new EditMessagesTable();
        editmessages.addMessageFromJSON(Resources.message1);
        editmessages.addMessageFromJSON(Resources.message2);

        EditReviewsTable editRevs = new EditReviewsTable();
        editRevs.addReviewFromJSON(Resources.review1);
    }

    public void databaseToJSON() throws ClassNotFoundException, SQLException {
//       //Get info of Pet Owner
        EditPetOwnersTable eut = new EditPetOwnersTable();
        PetOwner su = eut.databaseToPetOwners("mountanton", "ab$12345");
        String json = eut.petOwnerToJSON(su);
        System.out.println("Pet Owner\n" + json + "\n");

        //Get Pet of Owner
        EditPetsTable editpets = new EditPetsTable();
        Pet pet = editpets.petOfOwner("4");
        String petjson = editpets.petToJSON(pet);
        System.out.println("Pet of Owner 4\n" + petjson + "\n");

        //Get Pets that are cats
        ArrayList<Pet> cats = new ArrayList<Pet>();
        cats = editpets.databaseToPets("cat");
        Gson gson1 = new Gson();
        JsonArray catsJSON = gson1.toJsonTree(cats).getAsJsonArray();
        System.out.println("Cats\n" + catsJSON + "\n");

        //Get info of Pet Keeper
        EditPetKeepersTable1 editkeepers = new EditPetKeepersTable1();
        PetKeeper Keeper = editkeepers.databaseToPetKeepers("catmary", "ab$111111");
        String keeperJSON = editkeepers.petKeeperToJSON(Keeper);
        System.out.println("Pet Keeper\n" + keeperJSON + "\n");

        //all catkeepers
        ArrayList<PetKeeper> catKeepers = new ArrayList<PetKeeper>();
        catKeepers = editkeepers.getKeepers("catkeeper");
        Gson gson2 = new Gson();
        JsonArray catKeepersJSON = gson2.toJsonTree(catKeepers).getAsJsonArray();
        System.out.println("Cat Keepers\n" + catKeepersJSON + "\n");

        //all dogkeepers
        ArrayList<PetKeeper> dogKeepers = new ArrayList<PetKeeper>();
        dogKeepers = editkeepers.getKeepers("dogkeeper");
        Gson gson3 = new Gson();
        JsonArray dogKeepersJSON = gson3.toJsonTree(dogKeepers).getAsJsonArray();
        System.out.println("Dog Keepers\n" + dogKeepersJSON + "\n");

        //all available Keepers
        ArrayList<PetKeeper> availableKeepers = new ArrayList<PetKeeper>();
        availableKeepers = editkeepers.getAvailableKeepers("all");
        Gson gson4 = new Gson();
        JsonArray availableKeepersJSON = gson4.toJsonTree(availableKeepers).getAsJsonArray();
        System.out.println("All available Keepers\n" + availableKeepersJSON + "\n");

        //all available catKeepers
        ArrayList<PetKeeper> availableCatKeepers = new ArrayList<PetKeeper>();
        availableCatKeepers = editkeepers.getAvailableKeepers("catKeepers");
        Gson gson5 = new Gson();
        JsonArray availableCatKeepersJSON = gson5.toJsonTree(availableCatKeepers).getAsJsonArray();
        System.out.println("All available CAT Keepers\n" + availableCatKeepersJSON + "\n");

        //all available DOG KEEPERS
        ArrayList<PetKeeper> availableDogKeepers = new ArrayList<PetKeeper>();
        availableDogKeepers = editkeepers.getAvailableKeepers("dogKeepers");
        Gson gson6 = new Gson();
        JsonArray availableDogKeepersJSON = gson6.toJsonTree(availableDogKeepers).getAsJsonArray();
        System.out.println("All available DOG Keepers\n" + availableDogKeepersJSON + "\n");

        // all messages of a booking
        EditMessagesTable editmessages = new EditMessagesTable();
        ArrayList<Message> messagesOfBooking = new ArrayList<Message>();
        messagesOfBooking = editmessages.databaseToMessage(1);
        Gson gson7 = new Gson();
        JsonArray messagesOfBookingJSON = gson7.toJsonTree(messagesOfBooking).getAsJsonArray();
        System.out.println("All MESSAGES OF Booking 1\n" + messagesOfBookingJSON + "\n");

//all reviews for a keeper
        EditReviewsTable ertab = new EditReviewsTable();
        ArrayList<Review> revs = ertab.databaseTokeeperReviews("1");
        Gson gson8 = new Gson();
        JsonArray jsonrevs = gson8.toJsonTree(revs).getAsJsonArray();
        System.out.println("Reviews for Keeper 1\n" + jsonrevs + "\n");
    }

    public void updateRecords() throws ClassNotFoundException, SQLException {
        EditPetOwnersTable es = new EditPetOwnersTable();
        //es.updatePetOwner("mountanton", "http://users.ics.forth/mountant");

    }

    public void deleteRecords() throws ClassNotFoundException, SQLException {

        EditPetsTable eb = new EditPetsTable();
        String pet_id = "1";
        //   eb.deletePet(pet_id);
    }

}

package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import mainClasses.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditReviewsTable {

    public String reviewsToJson(List<Review> reviews) {
        Gson gson = new Gson();
        String json = gson.toJson(reviews);
        return json;
    }


    public void addReviewFromJSON(String json) throws ClassNotFoundException {
        Review msg = jsonToReview(json);
        createNewReview(msg);
    }

    public Review jsonToReview(String json) {
        Gson gson = new Gson();
        Review msg = gson.fromJson(json, Review.class);
        return msg;
    }

    public String reviewToJSON(Review msg) {
        Gson gson = new Gson();
        String json = gson.toJson(msg, Review.class);
        return json;
    }

    public ArrayList<Review> databaseTokeeperReviews(String keeper_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Review> revs = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM reviews where keeper_id='" + keeper_id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Review rev = gson.fromJson(json, Review.class);
                revs.add(rev);
            }
            return revs;
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

    public void createNewReview(Review rev) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " reviews (owner_id, keeper_id, reviewText, reviewScore) "
                    + " VALUES ("
                    + "'" + rev.getOwner_id() + "',"
                    + "'" + rev.getKeeper_id() + "',"
                    + "'" + rev.getReviewText() + "',"
                    + "'" + rev.getReviewScore() + "'"
                    + ")";
            stmt.executeUpdate(insertQuery);
            System.out.println("# The review was successfully added in the database.");

            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditPetsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // New method to insert a review into the database
    public void insertReview(int owner_id, int keeper_id, String reviewText, int reviewScore) throws ClassNotFoundException {
        Review review = new Review();
        review.setOwner_id(owner_id);
        review.setKeeper_id(keeper_id);
        review.setReviewText(reviewText);
        review.setReviewScore(String.valueOf(reviewScore));

        createNewReview(review);
    }

    public void createReviewTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE reviews "
                + "(review_id INTEGER not NULL AUTO_INCREMENT, "
                + "owner_id INTEGER not null,"
                + "keeper_id INTEGER not null,"
                + "reviewText VARCHAR(2000) not null,"
                + "reviewScore INTEGER not null,"
                + "FOREIGN KEY (owner_id) REFERENCES petowners(owner_id), "
                + "FOREIGN KEY (keeper_id) REFERENCES petkeepers(keeper_id), "
                + "PRIMARY KEY ( review_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }
}

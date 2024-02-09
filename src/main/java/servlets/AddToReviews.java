package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainClasses.Review;
import database.tables.EditReviewsTable;
import database.tables.EditBookingsTable;

public class AddToReviews extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters from the request
        String bookingIdParam = request.getParameter("bookingId");
        String reviewText = request.getParameter("reviewText");

        // Check for null or empty parameters
        if (bookingIdParam == null || reviewText == null || bookingIdParam.isEmpty() || reviewText.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Parse parameters
        int bookingId;
        try {
            bookingId = Integer.parseInt(bookingIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Log received parameters
        System.out.println("Received parameters:");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Review Text: " + reviewText);

        try {
            // Create an instance of EditReviewsTable
            EditReviewsTable reviewsTable = new EditReviewsTable();
            EditBookingsTable bookingsTable = new EditBookingsTable();

            // Call methods to find owner ID and keeper ID
            int ownerID = bookingsTable.getOwnerIDFromBookingID(bookingId);
            int keeperID = bookingsTable.getKeeperIDFromBookingID(bookingId);

            // Check if IDs are valid (you may need to customize this part based on your logic)
            if (ownerID == -1 || keeperID == -1) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Log owner and keeper IDs
            System.out.println("Owner ID: " + ownerID);
            System.out.println("Keeper ID: " + keeperID);

            // Create a new Review object
            Review review = new Review();
            review.setOwner_id(ownerID);
            review.setKeeper_id(keeperID);
            review.setReviewText(reviewText);
            review.setReviewScore("0");

            // Call the method to add the review to the database
            reviewsTable.createNewReview(review);
            System.out.println("Review added to the database.");

            // Optionally, you can retrieve reviews for the keeper and send a response
            ArrayList<Review> keeperReviews = reviewsTable.databaseTokeeperReviews(String.valueOf(keeperID));

            // Send a JSON response based on the success of the insertion
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (keeperReviews != null && !keeperReviews.isEmpty()) {
                // Display the reviews for the keeper
                out.println("{\"status\": \"success\", \"message\": \"Review added successfully.\", \"reviews\": "
                        + reviewsTable.reviewsToJson(keeperReviews) + "}");
            } else {
                out.println("{\"status\": \"error\", \"message\": \"Error retrieving reviews. Please try again.\"}");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Log and handle database-related exceptions
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

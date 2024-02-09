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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import database.tables.EditPetOwnersTable;
import mainClasses.Booking;
import database.tables.EditBookingsTable;
import mainClasses.PetOwner;


public class ShowMyBooking extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set content type to JSON
        response.setContentType("application/json");

        // Get the username from the session attribute
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loggedIn");

        // Initialize necessary objects
        EditPetOwnersTable petOwnersTable = new EditPetOwnersTable();
        EditBookingsTable bookingsTable = new EditBookingsTable();
        ArrayList<Booking> bookingsList = new ArrayList<>();

        try {
            // Retrieve PetOwner based on the username
            PetOwner petOwner = petOwnersTable.databaseToPetOwnersWithUsernameOnly(username);

            // Get Booking ID based on PetOwner ID
            int bookingId = bookingsTable.getBookingIDFromOwnerID(petOwner.getOwnerId());

            // Get all Bookings associated with the Booking ID
            bookingsList = bookingsTable.getBookingWithId(bookingId);

            // Convert bookingsList to JSON
            Gson gson = new Gson();
            String json = gson.toJson(bookingsList);
            System.out.println(json);
            // Write JSON response
            PrintWriter out = response.getWriter();
            out.print(json);

            // Set HTTP status to OK
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions, log or return an error response
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

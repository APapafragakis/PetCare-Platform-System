package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import database.tables.EditPetOwnersTable;
import database.tables.EditBookingsTable;

public class ShowFinishedBookings extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the owner ID from the session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loggedIn");

        if (username == null) {
            // If the username is not found in the session, return an error message
            sendJsonResponse(response, "{ \"error\": \"User not logged in.\" }");
            return;
        }

        EditPetOwnersTable petOwnersTable = new EditPetOwnersTable();

        try {
            String ownerId = String.valueOf(petOwnersTable.getOwnerIdByUsername(username));

            if (ownerId == null) {
                // If owner ID is not found, return an error message
                sendJsonResponse(response, "{ \"error\": \"Owner ID not found for the logged-in user.\" }");
                return;
            }

            EditBookingsTable bookingsTable = new EditBookingsTable();

            // Call the method to get finished booking IDs for the owner
            ArrayList<Integer> finishedBookingIds = bookingsTable.getFinishedBookingIdsForOwner(ownerId);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (finishedBookingIds != null && !finishedBookingIds.isEmpty()) {
                // Convert finished booking IDs to JSON and send it to the client
                String json = bookingIdsToJSON(finishedBookingIds);
                sendJsonResponse(response, json);
            } else {
                // If no finished bookings, send a message
                sendJsonResponse(response, "{ \"message\": \"No finished bookings found.\" }");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            sendJsonResponse(response, "{ \"error\": \"" + e.getMessage() + "\" }");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, String jsonResponse) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(jsonResponse);

        // Print the response to the server's console
        System.out.println("Servlet Response: " + jsonResponse);
    }

    private String bookingIdsToJSON(ArrayList<Integer> bookingIds) {
        Gson gson = new Gson();
        return gson.toJson(bookingIds);
    }
}

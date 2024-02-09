package servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.tables.EditBookingsTable;


public class updateBookingStatus extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");

        try {
            // Create an instance of EditBookingsTable
            EditBookingsTable editBookingsTable = new EditBookingsTable();

            // Call the updateBooking method
            editBookingsTable.updateBooking(bookingId, status);

            // Optionally, you can send a success response if needed
            response.getWriter().write("Booking status updated successfully");
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions appropriately, e.g., log or send an error response
            e.printStackTrace();  // Log the exception stack trace
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating booking status");
        }
    }
}

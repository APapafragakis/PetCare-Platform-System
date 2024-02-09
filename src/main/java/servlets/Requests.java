package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.tables.EditBookingsTable;
import java.util.List;
import javax.servlet.ServletException;
import mainClasses.Booking;

@WebServlet("/GetBookingsServlet")
public class Requests extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            EditBookingsTable editBookingsTable = new EditBookingsTable();

            try {
                List<Booking> bookingsList = editBookingsTable.getAllBookings();

                // Convert bookingsList to JSON
                Gson gson = new Gson();
                String json = gson.toJson(bookingsList);

                // Send JSON response
                out.println(json);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                out.println("Error retrieving booking data.");
            }
        }
    }
}

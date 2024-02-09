package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.tables.EditPetsTable;

public class PetStatistics extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Fetch counts from the database based on pet types
            int catCount = EditPetsTable.countPetsByType("cat");
            int dogCount = EditPetsTable.countPetsByType("dog");

            // Set content type to JSON
            response.setContentType("application/json");

            // Create a JSON object
            String jsonData = String.format("{\"catCount\": %d, \"dogCount\": %d}", catCount, dogCount);

            // Send the JSON response
            PrintWriter out = response.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Internal Server Error\"}");
            out.flush();
        }
    }
}

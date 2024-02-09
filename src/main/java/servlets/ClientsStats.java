package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.tables.EditPetOwnersTable;
import database.tables.EditPetKeepersTable1;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientsStats extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        try {
            int petKeepersCount = EditPetKeepersTable1.countPetKeepers();
            int petOwnersCount = EditPetOwnersTable.countPetOwners();
            // Set content type to JSON
            response.setContentType("application/json");
            // Create a JSON object
            String jsonData = String.format("{\"petKeepers\": %d, \"petOwners\": %d}", petKeepersCount, petOwnersCount);
            // Send the JSON response
            PrintWriter out = response.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (SQLException ex) {
            Logger.getLogger(ClientsStats.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientsStats.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

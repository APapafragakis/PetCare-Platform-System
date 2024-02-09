package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.tables.EditPetKeepersTable1;
import database.tables.EditPetOwnersTable;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;

public class AvailableUsers extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EditPetOwnersTable listOwners = new EditPetOwnersTable();
        EditPetKeepersTable1 listKeepers = new EditPetKeepersTable1();

        List<String> ownerUsernames;
        List<String> keeperUsernames;

        try {
            ownerUsernames = listOwners.databaseToPetOwnersUsernamesAll();
            keeperUsernames = listKeepers.databaseToPetKeepersUsernamesAll();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvailableUsers.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving usernames");
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String ownerUsernamesJson = gson.toJson(ownerUsernames);
        String keeperUsernamesJson = gson.toJson(keeperUsernames);

        // Combine owner and keeper usernames JSON strings
        String jsonResponse = "{\"owner\":" + ownerUsernamesJson + ", \"keeper\":" + keeperUsernamesJson + "}";

        PrintWriter out = response.getWriter();
        out.write(jsonResponse);
        out.close();
    }
}

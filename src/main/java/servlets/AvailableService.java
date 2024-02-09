package servlets;

import com.google.gson.Gson;
import database.tables.EditPetKeepersTable1;
import database.tables.EditPetOwnersTable;
import database.tables.EditPetsTable;
import mainClasses.PetKeeper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AvailableService extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("loggedIn");
        String type = request.getParameter("type");
        EditPetOwnersTable owner = new EditPetOwnersTable();
        try {
            type = owner.getPetTypeOfOwner(username);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(type);
        List<PetKeeper> keepers = new ArrayList<PetKeeper>();
        EditPetKeepersTable1 available_petKeeper = new EditPetKeepersTable1();

        try {
            keepers = available_petKeeper.getAvailableKeepers(type);
        } catch (SQLException ex) {
            Logger.getLogger(AvailablePetKeepers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvailablePetKeepers.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<String> usernames = keepers.stream()
                .map(PetKeeper::getUsername)
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(usernames);

        PrintWriter out = response.getWriter();
        out.write(jsonResponse);
        out.close();
    }
}
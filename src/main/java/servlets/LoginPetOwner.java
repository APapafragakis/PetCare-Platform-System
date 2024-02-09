package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.tables.EditPetOwnersTable;
import java.io.PrintWriter;

/**
 * Servlet implementation class Login
 */
public class LoginPetOwner extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        EditPetOwnersTable tableO = new EditPetOwnersTable();
        HttpSession session = request.getSession();

        try {
            if (tableO.databaseToPetOwners(username, password) == null) {
                response.setStatus(403);
                response.getWriter().write("There is no pet owner with that username and password");
            } else {
                PrintWriter out = response.getWriter();

                out.println("PetOwnerGeneralInformation.html");
                session.setAttribute("loggedIn", username);
                response.setStatus(200);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

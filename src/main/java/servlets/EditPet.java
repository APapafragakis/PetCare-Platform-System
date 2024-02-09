/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import database.tables.EditPetOwnersTable;
import database.tables.EditPetsTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.JSONCONVERSION;
import mainClasses.Pet;
import mainClasses.PetOwner;

/**
 *
 * @author porok
 */
public class EditPet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditPet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditPet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        EditPetOwnersTable pot = new EditPetOwnersTable();

        Pet p = new Pet();
        EditPetsTable ept = new EditPetsTable();
        JSONCONVERSION jc = new JSONCONVERSION();
        BufferedReader inputJSONfromClient = request.getReader();
        String finalInput = jc.getJSONFromAjax(inputJSONfromClient);
        System.out.println("Final input: " + finalInput);
        p = ept.jsonToPet(finalInput);

        PetOwner po = null;
        try {
            po = pot.databaseToPetOwnersOnlyName(session.getAttribute("loggedIn").toString());
        } catch (SQLException ex) {
            Logger.getLogger(InsertPet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InsertPet.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.setOwner_id(po.getOwnerId());

        try {
            ept.updatePetEverything(String.valueOf(p.getOwner_id()), p.getName(), p.getType(), p.getBreed(), p.getGender(), p.getBirthyear(), p.getWeight(), p.getDescription(), p.getPhoto());

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(InsertPet.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintWriter out = response.getWriter();
        response.setStatus(200);
        response.getWriter().write("IHO");
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

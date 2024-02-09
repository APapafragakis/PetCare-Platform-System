/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import database.tables.EditBookingsTable;
import database.tables.EditPetKeepersTable1;
import database.tables.EditPetOwnersTable;
import database.tables.EditPetsTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.Booking;
import mainClasses.JSONCONVERSION;
import mainClasses.Pet;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;

/**
 *
 * @author porok
 */
public class AddToBookings extends HttpServlet {

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
            out.println("<title>Servlet AddToBookings</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddToBookings at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public String convertStringToMap(String data) {
        Map<String, String> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(data, ",");
        String returnValue = "";
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] keyValue = token.split(":");
            map.put(keyValue[0], keyValue[1]);
            returnValue = keyValue[1];
            return returnValue;

        }
        return returnValue;


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
        String username;

        HttpSession session = request.getSession();
        EditPetOwnersTable pot = new EditPetOwnersTable();
        EditPetKeepersTable1 epkt = new EditPetKeepersTable1();
        EditPetsTable ept = new EditPetsTable();
        PetOwner p = new PetOwner();
        PetKeeper pk = new PetKeeper();
        Pet pt = new Pet();

        JSONCONVERSION jc = new JSONCONVERSION();
        BufferedReader inputJSONfromClient = request.getReader();

        String finalInput = jc.getJSONFromAjax(inputJSONfromClient);
        System.out.println("FINAL INPUT " + finalInput);

        String resultMap = convertStringToMap(finalInput);
        System.out.println("PLSSS: " + resultMap);
        username = resultMap;
        System.out.println("Username: " + username);

        EditBookingsTable ebt = new EditBookingsTable();


        try {
            p = pot.databaseToPetOwnersOnlyName(session.getAttribute("loggedIn").toString());
            pk = epkt.databaseToPetKeepersOnlyName2(username);
            pt = ept.petOfOwner(Integer.toString(p.getOwnerId()));

            Booking b = new Booking();


            ebt.myAddBookingFromJSON(finalInput, p.getOwnerId(), pk.getKeeperId(), pt.getPet_id());
            PrintWriter out = response.getWriter();
            out.println("Request sent.");
            out.flush();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddToBookings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddToBookings.class.getName()).log(Level.SEVERE, null, ex);
        }
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

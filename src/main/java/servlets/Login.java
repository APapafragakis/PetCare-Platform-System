package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.tables.EditPetKeepersTable1;
import database.tables.EditPetOwnersTable;
import java.io.PrintWriter;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  HttpSession session=request.getSession();
		  EditPetOwnersTable tableO = new EditPetOwnersTable();
		  EditPetKeepersTable1 tableK = new EditPetKeepersTable1();
		  PetKeeper p = null;
		  PetOwner o = null;
	        if(session.getAttribute("loggedIn")!=null){
	           response.setStatus(200);
	           String username = session.getAttribute("loggedIn").toString();
	           
                    try {
                        PrintWriter out = response.getWriter();

				p = tableK.databaseToPetKeepersUsernameOnly(username);
				if(p==null) {
                                    o = tableO.databaseToPetOwnersWithUsernameOnly(username);
                                    out.println("PetOwnerGeneralInformation.html");
                                } else {
                                    out.println("PetKeeperGeneralInformation.html");

                        }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				if(p == null) {
                                    //response.getWriter().write(o.getUsername());
				}else {
                                    //response.getWriter().write(p.getUsername());
				}
	          
	        }
	        else{
	            response.setStatus(403);
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
        String password=request.getParameter("password");
        EditPetOwnersTable tableO = new EditPetOwnersTable();
		EditPetKeepersTable1 tableK = new EditPetKeepersTable1();
        HttpSession session=request.getSession();
        
        try {
			if(tableO.databaseToPetOwners(username, password)==null && tableK.databaseToPetKeepers(username, password)==null) {
				response.setStatus(403);
				response.getWriter().write("There is no pet keeper or pet owner with that username and password");
                        } else {

				session.setAttribute("loggedIn",username);
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

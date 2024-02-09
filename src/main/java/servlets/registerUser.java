package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import database.tables.EditPetKeepersTable1;
import database.tables.EditPetOwnersTable;
import mainClasses.JSONCONVERSION;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;


public class registerUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		PetKeeper k = new PetKeeper();
		PetOwner o = new PetOwner();
		EditPetOwnersTable tableO = new EditPetOwnersTable();
		EditPetKeepersTable1 tableK = new EditPetKeepersTable1();
            JSONCONVERSION jc = new JSONCONVERSION();
            BufferedReader inputJSONfromClient = request.getReader();
            String finalInput = jc.getJSONFromAjax(inputJSONfromClient);
            System.out.println("Final input: " + finalInput);

            k = tableK.jsonToPetKeeper(finalInput);
            String dummy = k.getType();
            if (dummy.equals("PetOwner")) {
                o = tableO.jsonToPetOwner(finalInput);
        }
        System.out.println(o);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
			if (tableK.databaseToPetKeepersUsernameEmail(k.getUsername(),k.getEmail()) != null || tableO.databaseToPetOwnersWithUsernameEmail(o.getUsername(),o.getEmail())!= null) {
			    response.setStatus(403);
			    Gson gson = new Gson();
			    JsonObject jo = new JsonObject();
			    response.setStatus(403);
			    jo.addProperty("error", "To onoma: "+ k.getUsername() + " h to email: "+ k.getEmail() +" einai kratimeno");
			    response.getWriter().write(jo.toString());
			} else {
				String JsonString = "";
                            if (dummy.equals("PetOwner")) {
					JsonString = jc.PetOwnerToJSON(o);
					try {
						tableO.addPetOwnerFromJSON(JsonString);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					JsonString = jc.PetKeeperToJSON(k);
					try {
						tableK.addPetKeeperFromJSON(JsonString);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			    PrintWriter out = response.getWriter();
			    response.setStatus(200);
			    response.getWriter().write(JsonString);
			    
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	/*String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String userType = request.getParameter("Type");
        boolean indoorAccommodation = Boolean.parseBoolean(request.getParameter("indoorAccommodation"));
        boolean outdoorAccommodation = Boolean.parseBoolean(request.getParameter("outdoorAccommodation"));
        boolean hostCat = Boolean.parseBoolean(request.getParameter("hostCat"));
        boolean hostDog = Boolean.parseBoolean(request.getParameter("hostDog"));
        String catPrice = request.getParameter("catPrice");
        String dogPrice = request.getParameter("dogPrice");
        String description = request.getParameter("propertyDescription");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String personalPage = request.getParameter("personalPage");
        String job = request.getParameter("job");
        String mobilePhone = request.getParameter("mobilePhone");
        boolean agreement = Boolean.parseBoolean(request.getParameter("agreement"));

        // Check for duplicates before attempting registration
        if (checkForDuplicates(username, email)) {
            // Respond to the client indicating duplicates exist
            response.getWriter().write("{\"duplicatesExist\": true}");
        } else {
            // If no duplicates, proceed with registration
            boolean registrationSuccessful = registerUser(username,email,password, confirmPassword, name, surname, dob, gender, userType, indoorAccommodation, outdoorAccommodation,
                     hostCat,  hostDog,  catPrice,  dogPrice,
                     description,  country,  city,  address,
                     personalPage,  job,  mobilePhone,  agreement);

            // Respond to the client
            response.getWriter().write("{\"registrationSuccessful\": " + registrationSuccessful + "}");
        }
    }

	private boolean checkForDuplicates(String username, String email) {
	    UserDao userDao = new UserDao(); 
	    try {
	        return userDao.checkUserExists(username, email);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


    private boolean registerUser(String username, String email, String password,
            String confirmPassword, String name, String surname, String dob, String gender,
            String userType, boolean indoorAccommodation, boolean outdoorAccommodation,
            boolean hostCat, boolean hostDog, String catPrice, String dogPrice,
            String description, String country, String city, String address,
            String personalPage, String job, String mobilePhone, boolean agreement) {
    	UserDao userDao = new UserDao();  
    	try {
            return userDao.registerUsers(username, email, password, name, surname, dob, gender,
                    userType, indoorAccommodation, outdoorAccommodation, hostCat, hostDog,
                    catPrice, dogPrice, description, country, city, address, personalPage,
                    job, mobilePhone, agreement);
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        } */
    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainClasses;

/**
 *
 * @author mountant
 */
public class PetKeeper {
    int keeper_id;
    String username,email,password,firstname,lastname,birthdate;
    String gender,job;
    String country,city,address;
    Double lat,lon;
    String telephone;
    String personalpage;
    String property,propertydescription;
    String catkeeper,dogkeeper;
    String type;
    int catprice, dogprice;
    public void setKeeperId(int keeper_id) {
        this.keeper_id = keeper_id;
    }

    public int getKeeperId() {
        return keeper_id;
    }
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertydescription() {
        return propertydescription;
    }

    public void setPropertydescription(String propertydescription) {
        this.propertydescription = propertydescription;
    }

    public String getCatkeeper() {
        return catkeeper;
    }

    public void setCatkeeper(String catkeeper) {
        this.catkeeper = catkeeper;
    }

    public String getDogkeeper() {
        return dogkeeper;
    }

    public void setDogkeeper(String dogkeeper) {
        this.dogkeeper = dogkeeper;
    }

    public int getCatprice() {
        return catprice;
    }

    public void setCatprice(int catprice) {
        this.catprice = catprice;
    }

    public int getDogprice() {
        return dogprice;
    }

    public void setDogprice(int dogprice) {
        this.dogprice = dogprice;
    }

    public String getPersonalpage() {
        return personalpage;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPersonalpage(String personalpage) {
        this.personalpage = personalpage;
    }
   
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    
    
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   
    
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthDate) {
        this.birthdate = birthDate;
    }

   
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

 
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

 
}

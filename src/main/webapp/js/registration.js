// registration.js

//Used for getting the data in table format
function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];

        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;

}

//Used at the register user tab
function registerUser() {
    let myForm = document.getElementById('myForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => {
		if(key === "indoorAccommodation"){
			return data["property"] = "Indoor"
		}else if(key === "outdoorAccommodation"){
			return data["property"] = "Outdoor"
		}
		
		
		
		return data[key] = value
	});

	console.log(formData);
	
    if ($("#country").val() === "Monaco") {
        alert("Banned due to Many Requests");
        return;
    }

    var jsonData = JSON.stringify(data);
    console.log(formData, myForm, jsonData);

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = JSON.parse(xhr.responseText);
			console.log(responseData);
           // $('#ajaxContent').html(createTableFromJSON(responseData));
           // setChoicesForRegisteredUser();
			document.getElementById('ajaxContent').style.display = "flex";
			document.getElementById('ajaxContent').style.color = "green";
			  	document.getElementById('ajaxContent')
                    .innerHTML = 'Request succeed. User: '+responseData.username+' is registered!';

        } else if (xhr.status !== 200) {
           
            const responseData = JSON.parse(xhr.responseText).error;
 			console.log(responseData);
         	document.getElementById('ajaxContent')
                    .innerHTML = 'Request failed. '+responseData;
			document.getElementById('ajaxContent').style.display = "flex";
        }
    };
    //alert(jsonData);
    xhr.open('POST', 'registerUser');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
}

function showLogin() {
   	document.getElementById('login-container').style.display = "flex";
	document.getElementById('registerForm').style.display = "none";
}

function showRegistrationForm() {
    document.getElementById('login-container').style.display = "none";
	document.getElementById('registerForm').style.display = "flex";
}

function loginPOST() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //setChoicesForLoggedUser();
           $("#outerContent").load(xhr.responseText);
		 	document.getElementById('login-container').style.display = "none";
			document.getElementById('registerButton').style.display = "none";
			document.getElementById('loginButton').style.display = "none";
			document.getElementById('logoutButton').style.display = "inline";
        } else if (xhr.status !== 200) {
              showLoginError();
            //('Request failed. Returned status of ' + xhr.status);
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'Login');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data);
}

function isLoggedIn() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			//setChoicesForLoggedUser();
                        
                        $("#outerContent").load(xhr.responseText, function (){
                            $('#ajaxContent').append("Welcome BACK!!!");
  
                        });
			//$("#outputContent").html("Welcome again "+xhr.responseText);
			//document.getElementById('registerForm').style.display = "none";
			//document.getElementById('registerButton').style.display = "none";
			//document.getElementById('loginButton').style.display = "none";
			//document.getElementById('logoutButton').style.display = "inline";
			
		} else if (xhr.status !== 200) {
			$("#outputContent").html("Login or register to continue");
			document.getElementById('registerButton').style.display = "inline";
			document.getElementById('loginButton').style.display = "inline";
			document.getElementById('logoutButton').style.display = "none";
			document.getElementById('registerForm').style.display = "inline";
		}
	};
	xhr.open('GET', 'Login');
	xhr.send();
}

function logout(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			                        
                        $("#outerContent").load("index.html", function (){
                            $('#ajaxContent').append("Succesfull Logout!");
                            
                            setTimeout(function() { $('#ajaxContent').hide(); }, 2000);

                            if($('#ajaxContent').is(":hidden")){
                                $('#ajaxContent').show();
                                setTimeout(function() { $('#ajaxContent').hide(); }, 2000);
                            }
                        });

		} else if (xhr.status !== 200) {
			alert('Request failed. Returned status of ' + xhr.status);
		}
	};
	xhr.open('POST', 'Logout');
	xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhr.send();
}

function showPetKeeperForm(){
    $("#outerContent").load("PetKeeper.html");
}

function showPetOwnerForm(){
    $("#outerContent").load("PetOwner.html");
}

//NEVER USED
function showPetOwnerRegistrationForm(){
    $("#outerContent").load("PetOwnerRegistrationForm.html", function (){
           setTimeout(function() { initializeMap(); }, 3000);
    });
}


function showPetKeeperRegistrationForm(){
    $("#outerContent").load("PetKeeperRegistrationForm.html", function (){
           setTimeout(function() { initializeMap(); }, 3000);
    });
}


function showLoginForm(){
    $("#outerContent").load("Login.html");
}

function showHomePage(){
    $("#outerContent").load("index.html");
}

//If login fails
function showLoginError(){
    document.getElementById('ajaxContent').style.color = "red";

    $('#ajaxContent').html("Wrong Credentials");
    setTimeout(function() { $('#ajaxContent').hide(); }, 2000);

    if($('#ajaxContent').is(":hidden")){
        $('#ajaxContent').show();
        setTimeout(function() { $('#ajaxContent').hide(); }, 2000);
    }
}

function showPetOwnerLoginForm(){
       $("#outerContent").load("LoginPetOwner.html"); 
}

function showPetKeeperLoginForm(){
        $("#outerContent").load("LoginPetKeeper.html"); 

}

//Login ajax for petOwner --> Login Pet Owner
function petOwnerloginPOST() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //setChoicesForLoggedUser();
           $("#outerContent").load(xhr.responseText);

        } else if (xhr.status !== 200) {
              showLoginError();
            //('Request failed. Returned status of ' + xhr.status);
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'LoginPetOwner');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data);
}

//Login ajax for petKeeper --> Login Pet Keeper
function petKeeperloginPOST(){
       var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //setChoicesForLoggedUser();
           $("#outerContent").load(xhr.responseText);

        } else if (xhr.status !== 200) {
              showLoginError();
            //('Request failed. Returned status of ' + xhr.status);
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'LoginPetKeeper');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data); 
}

//Shows pet owners Info
function showInfoPetOwner() {
        var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = JSON.parse(xhr.responseText);
                $('#ajaxContent').html("<h1>Your Data</h1>");
                $('#ajaxContent').append(createTableFromJSON(responseData));
            
            
            
            
            // $("#myForm").hide();
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };

    xhr.open('GET', 'PetOwnerInformation');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();

}

//Shows pet keepers info
function showInfoPetKeeper(){
    
            var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = JSON.parse(xhr.responseText);

                $('#ajaxContent').html("<h1>Your Data</h1>");
                $('#ajaxContent').append(createTableFromJSON(responseData));

        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };

    xhr.open('GET', 'PetKeeperInformation');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();

}

//Loads the form to update the pet owner's data
function showEditInfoPetOwner(){
    $("#ajaxContent").load("showEditInfoPetOwner.html"); 
}

function showEditInfoPetKeeper(){
    $("#ajaxContent").load("showEditInfoPetKeeper.html"); 

}

//Updates the pet owner's data
function updatePetOwnerData() {
    let myForm = document.getElementById('ChangeForm');
    let formData = new FormData(myForm);
    
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData = JSON.stringify(data);
    //console.log("Data is: ", jsonData);
    
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //const responseData = JSON.parse(xhr.responseText);
            $('#ajaxContent').html("Updated");
            setTimeout(function() { $('#ajaxContent').hide(); }, 3000);
            
            if($('#ajaxContent').is(":hidden")){
                $('#ajaxContent').show();
                setTimeout(function() { $('#ajaxContent').hide(); }, 3000);
            }
            
            //$('#ajaxContent2').html("<h1>Your Data</h1>");
            //$('#ajaxContent2').append(createTableFromJSON(responseData));
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
            //('Request failed. Returned status of ' + xhr.status);
        }
    };
    //var data = $('#loginForm').serialize();
    xhr.open('POST', 'UpdatePetOwnerData');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(jsonData);
    
    
}

function updatePetKeeperData() {
    let myForm = document.getElementById('ChangeForm');
    let formData = new FormData(myForm);
    
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData = JSON.stringify(data);
    //console.log("Data is: ", jsonData);
    
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //const responseData = JSON.parse(xhr.responseText);
            $('#ajaxContent').html("Updated");
            setTimeout(function() { $('#ajaxContent').hide(); }, 3000);
            
            if($('#ajaxContent').is(":hidden")){
                $('#ajaxContent').show();
                setTimeout(function() { $('#ajaxContent').hide(); }, 3000);
            }
            
            //$('#ajaxContent2').html("<h1>Your Data</h1>");
            //$('#ajaxContent2').append(createTableFromJSON(responseData));
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
            //('Request failed. Returned status of ' + xhr.status);
        }
    };
    //var data = $('#loginForm').serialize();
    xhr.open('POST', 'UpdatePetKeeperData');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(jsonData);
    
    
}

function show_chat_gpt_window(){
    $("#ajaxContent").load("chat_gpt_window.html"); 

}


function call_chatGPT(){

    //var input = document.getElementById("usergptinput").value;
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#responseGPT").html(xhr.responseText);
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
            //('Request failed. Returned status of ' + xhr.status);
        }
    };
    

   
    var data = $('#questiontogpt').serialize();

    xhr.open('POST', 'chatGPTQuestions');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data);
}

function showPetOwnerMessages(){
    $("#ajaxContent").load("PetOwnerMessages.html"); 
}

function showPetKeeperMessages(){
    $("#ajaxContent").load("PetKeeperMessages.html"); 
}

function showGuestForm(){
    $("#outerContent").load("GuestForm.html");
}

function showAdminForm(){
    $("#outerContent").load("adminLogin.html");
}

function showSparkForm(){
    $("#ajaxContent").load("SparkForm.html");
}

function showSparkFormEdit(){
    $("#ajaxContent").load("SparkFormEdit.html");
}

function adminLogin() {
    var username = document.getElementById("adminUsername").value;
    var password = document.getElementById("adminPassword").value;
    var messageContainer = document.getElementById("messageContainer");

    // Assuming your admin credentials are 'admin' and 'admin12*'
    if (username === "admin" && password === "admin12*") {
        // Successful login - display success message
        messageContainer.innerHTML = "<p class='success-message'>Successful admin login!</p>";

        // Redirect to adminPage.html
        window.location.href = "adminPage.html";
    } else {
        // Incorrect credentials - display error message
        messageContainer.innerHTML = "<p class='error-message'>Incorrect admin credentials. Please try again.</p>";
    }
}

function adminLogout() {
            window.location.href = "index.html";
}


$(document).ready(function(){
    isLoggedIn();
});

/*function showPetOwnerMessages() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            document.getElementById('ajaxContent').innerHTML = responseData;
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
}; 

    xhr.open('GET', 'PetOwnerMessages.html');
    xhr.send();
} */

/* function showPetKeeperMessages() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            document.getElementById('ajaxContent').innerHTML = responseData;
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
};

    xhr.open('GET', 'PetKeeperMessages.html');
    xhr.send();
} */

// chat.js

// Function to fetch new messages using AJAX




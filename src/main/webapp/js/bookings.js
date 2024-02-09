/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function showAvailableKeepers(){
    $("#ajaxContent").load("showBookings.html"); 

}

function AvailableKeepers() {  
    $.ajax({
        url: 'AvailablePetKeepers?type=all',
        type: 'GET',
        dataType: 'json',
        success: function (responseData) {
            if (responseData && responseData.length > 0) {
                $('#ajaxContent').html("<h1>Available Pet Keepers</h1>");
                $('#ajaxContent').append('<input type="text" id="deleteUserInput" placeholder="Username">');



                $('#ajaxContent').append(createTableFromJSON(responseData));
            } else {
                $('#ajaxContent').html("<p>No available pet keepers found.</p>");
            }
        },
        error: function (xhr, status, error) {
            console.error('AJAX Request Error:', status, error);

            // Log the response text for more details
            console.log('Response Text:', xhr.responseText);

            alert('AJAX Request Error. Check console for details.');
        }
    });
    
}

function sendRequest(){
    let myForm = document.getElementById('myForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData=JSON.stringify(data);
    
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
           document.getElementById('ajaxContent').innerHTML=JSON.stringify(xhr.responseText);
                      //document.getElementById('ajaxContent').innerHTML="<br>HEY</br>";

            
        } else if (xhr.status !== 200) {
            document.getElementById('msg')
                    .innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+
					JSON.stringify(xhr.responseText);
 
        }
    };
    xhr.open('POST', 'AddToBookings');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);

   /*
   var name = $("#username").val();
    
    $.ajax({
        url: 'AddToBookings',
        data: {
            jsonData,
            username : name
        },
        type: 'POST',
        dataType: 'json',
        success: function (responseData) {
            if (responseData && responseData.length > 0) {
                $('#ajaxContent').html("<h1>Available Pet Keepers</h1>");

            } else {
                $('#ajaxContent').html("<p>No available pet keepers found.</p>");
            }
        },
        error: function (xhr, status, error) {
            console.error('AJAX Request Error:', status, error);

            // Log the response text for more details
            console.log('Response Text:', xhr.responseText);

            alert('AJAX Request Error. Check console for details.');
        }
    });*/
}

// script.js

function ShowMyBooking() {
    // Make an AJAX call to the servlet
    $.ajax({
        url: 'ShowMyBooking',
        type: 'GET',
        dataType: 'json',
        success: function(responseData) {
            // Check if the responseData is an array and not empty
            if (Array.isArray(responseData) && responseData.length > 0) {
                $('#extra_field').html("<h1>Available Bookings</h1>");

                // Create a table header
                var table = '<table border="1"><tr><th>Booking ID</th><th>Owner ID</th><th>Pet ID</th><th>Keeper ID</th><th>Status</th><th>Price</th></tr>';

// Loop through each object in the array and create a table row
                for (var i = 0; i < responseData.length; i++) {
                    table += '<tr>';
                    table += '<td>' + responseData[i].bookingId + '</td>';
                    table += '<td>' + responseData[i].owner_id + '</td>';
                    table += '<td>' + responseData[i].pet_id + '</td>';
                    table += '<td>' + responseData[i].keeper_id + '</td>';
                    table += '<td>' + responseData[i].status + '</td>';
                    table += '<td>' + responseData[i].price + '</td>';
                    table += '</tr>';
                }
// Close the table tag
                table += '</table>';

                // Append the table to the HTML element with id 'extra_field'
                $('#extra_field').append(table);
            } else {
                // Display a message when no bookings are found
                $('#extra_field').html("<p>No available Bookings found.</p>");
            }
        },
        error: function(xhr, status, error) {
            // Handle errors, if any
            console.error('Error:', error);

            // Display an error message to the user
            $('#extra_field').html("<p>No available Bookings found.</p>");
        }
    });
}

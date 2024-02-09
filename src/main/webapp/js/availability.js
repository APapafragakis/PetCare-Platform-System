function formatUsers(data, category, value) {
    let resultHtml = '<div>';
    resultHtml += `<h2>${category}</h2>`;
    resultHtml += '<ul>';

    for (let i = 0; i < data.length; i++) {
        resultHtml += `<li>${value}: ${data[i]}</li>`;
    }

    resultHtml += '</ul>';
    resultHtml += '</div>';

    return resultHtml;
}

function showAvailability() {   
    
    $.ajax({
        url: 'AvailablePetKeepers?type=all',
        type: 'GET',
        dataType: 'json',
        success: function (responseData) {
            if (responseData && responseData.length > 0) {
                $('#ShowKeepers').html("<h1>Available Pet Keepers</h1>");
                $('#ShowKeepers').append(createTableFromJSON(responseData));
            } else {
                $('#ShowKeepers').html("<p>No available pet keepers found.</p>");
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

function showUsers() {
    console.log('Button pressed');

    $.ajax({
        url: 'AvailableUsers',
        type: 'GET',
        dataType: 'json',
        success: function (responseData) {
            console.log('Response Data:', responseData);

            if (responseData && responseData.owner && responseData.keeper) {
                $('#outerContent').html("<h1>List of users:</h1>");

                $('#outerContent').append('<input type="text" id="deleteUserInput" placeholder="Username">');
                $('#outerContent').append('<button onclick="deleteUser()">Delete</button>');
                
                $('#outerContent').append(formatUsers(responseData.owner, 'Pet Owners', 'Username'));
                $('#outerContent').append(formatUsers(responseData.keeper, 'Pet Keepers', 'Username'));
            } else {
                $('#outerContent').html("<p>No user found.</p>");
            }
        },
        error: function (xhr, status, error) {
            console.error('AJAX Request Error:', status, error);

            console.log('Response Text:', xhr.responseText);

            alert('AJAX Request Error. Check console for details.');
        }
    });
}

// Add a function to handle user deletion
function deleteUser() {
    const usernameToDelete = $('#deleteUserInput').val();
    if (usernameToDelete) {
        // Perform AJAX request to the DeleteUser servlet
        $.ajax({
            url: 'DeleteUser',  // Specify the URL of your DeleteUser servlet
            type: 'POST',
            data: { username: usernameToDelete },  // Send the username as data
            success: function (response) {
                console.log(response);  // Log the response from the servlet
                // You can perform additional actions based on the response if needed
            },
            error: function (xhr, status, error) {
                console.error('AJAX Request Error:', status, error);
                console.log('Response Text:', xhr.responseText);
                alert('AJAX Request Error. Check console for details.');
            }
        });

        // Clear the input after processing
        $('#deleteUserInput').val('');
    } else {
        alert('Please enter a username to delete.');
    }
}

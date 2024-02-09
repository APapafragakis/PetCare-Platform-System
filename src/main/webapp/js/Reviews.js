function submitReview() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "ShowFinishedBookings", true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);

                // Log the complete response array to the console
                console.log('Complete response array:', response);

                // Check if there are finished bookings
                if (response.hasOwnProperty("message")) {
                    // If no finished bookings, display the message
                    document.getElementById("ajaxContent").innerHTML = "<p>" + response.message + "</p>";
                } else if (Array.isArray(response)) {
                    // Log the structure of the first object in the response array
                    if (response.length > 0 && typeof response[0] === 'object') {
                        console.log('Structure of the first object:', Object.keys(response[0]));

                        // If the response is an array of objects, extract booking IDs
                        availableBookingIds = response.map(function (booking) {
                            return booking.bookingId;
                        });
                    } else {
                        // If the response is an array of integers, use them directly
                        availableBookingIds = response;
                    }

                    // Log the extracted booking IDs to the console
                    console.log('Extracted Booking IDs:', availableBookingIds);

                    // If there are finished bookings, display the review form
                    showReviewForm();
                } else {
                    // Handle unexpected response format
                    console.error('Unexpected response format:', response);
                    document.getElementById("ajaxContent").innerHTML = "<p>Error fetching finished bookings. Please try again.</p>";
                }
            } else {
                // Handle error
                console.error('Error fetching finished bookings. Status:', xhr.status);
                document.getElementById("ajaxContent").innerHTML = "<p>Error fetching finished bookings. Please try again.</p>";
            }
        }
    };

    xhr.send();
}


function showReviewForm() {
    // Display the review form
    var formHtml = '<form id="reviewForm">';
    formHtml += '<label for="bookingId">Select Booking ID:</label>';
    formHtml += '<select id="bookingId">';

    // Populate the dropdown with available booking IDs
    availableBookingIds.forEach(function (bookingId) {
        formHtml += '<option value="' + bookingId + '">' + bookingId + '</option>';
    });

    formHtml += '</select>';
    formHtml += '<br>';
    formHtml += '<label for="reviewText">Review:</label>';
    formHtml += '<textarea id="reviewText" rows="4" cols="50"></textarea>';
    formHtml += '<br>';
    formHtml += '<button type="button" onclick="submitReviewData()">Submit Review</button>';
    formHtml += '</form>';

    // Append the form to the container
    document.getElementById("ajaxContent").innerHTML = formHtml;

    // Print availableBookingIds to console for debugging
    console.log("Available Booking IDs:", availableBookingIds);
}

function submitReviewData() {
    var bookingIdSelect = document.getElementById('bookingId');
    var bookingId = bookingIdSelect.options[bookingIdSelect.selectedIndex].value;
    var reviewText = document.getElementById('reviewText').value;

    if (!bookingId) {
        console.error('Please select a Booking ID.');
        return;
    }

    // Perform AJAX request to submit the review for the selected bookingId
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "AddToReviews", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    // Send only the required parameters (bookingId and reviewText)
    var params = 'bookingId=' + encodeURIComponent(bookingId) + '&reviewText=' + encodeURIComponent(reviewText);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Handle success (you can customize this part based on your needs)
                console.log('Review submitted successfully.');
                // Optionally, you can hide the form after submission
                document.getElementById("ajaxContent").innerHTML = '';
            } else {
                // Handle error (you can customize this part based on your needs)
                console.error('Error submitting review.');
            }
        }
    };
    xhr.send(params);
}

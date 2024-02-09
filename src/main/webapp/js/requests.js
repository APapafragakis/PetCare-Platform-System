// requests.js

function showRequests() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                var bookings = JSON.parse(xhr.responseText);
                var requestsContainer = document.getElementById("ajaxContent");
                requestsContainer.innerHTML = "<h2>All Bookings</h2>";

                if (bookings.length > 0) {
                    var tableHtml = "<table border='1'>";
                    tableHtml += "<tr><th>Booking ID</th><th>Owner ID</th><th>Pet ID</th><th>Keeper ID</th><th>From Date</th><th>To Date</th><th>Status</th><th>Price</th><th>Action</th></tr>";

                    for (var i = 0; i < bookings.length; i++) {
                        tableHtml += "<tr>";
                        tableHtml += "<td>" + bookings[i].borrowing_id + "</td>";
                        tableHtml += "<td>" + bookings[i].owner_id + "</td>";
                        tableHtml += "<td>" + bookings[i].pet_id + "</td>";
                        tableHtml += "<td>" + bookings[i].keeper_id + "</td>";
                        tableHtml += "<td>" + bookings[i].fromDate + "</td>";
                        tableHtml += "<td>" + bookings[i].toDate + "</td>";
                        tableHtml += "<td>" + bookings[i].status + "</td>";
                        tableHtml += "<td>" + bookings[i].price + "</td>";
                        
                        if (bookings[i].status === 'requested') {
                            tableHtml += "<td>" +
                                "<button onclick='acceptBooking(" + bookings[i].borrowing_id + ")'>Accept</button> " +
                                "<button onclick='rejectBooking(" + bookings[i].borrowing_id + ")'>Reject</button>" +
                                "</td>";
                        } else if (bookings[i].status === 'accepted') {
                            tableHtml += "<td>" +
                                "<button onclick='finishBooking(" + bookings[i].borrowing_id + ")'>Finish</button>" +
                                "</td>";
                        } else {
                            tableHtml += "<td>Done</td>";
                        }

                        tableHtml += "</tr>";
                    }

                    tableHtml += "</table>";
                    requestsContainer.innerHTML += tableHtml;
                } else {
                    requestsContainer.innerHTML += "<p>No bookings available.</p>";
                }
            } else {
                console.error("Error fetching bookings. Status code: " + xhr.status);
            }
        }
    };

    xhr.open("GET", "Requests", true);
    xhr.send();
}

function acceptBooking(bookingId) {
    updateBookingStatus(bookingId, 'accepted');
}

function rejectBooking(bookingId) {
    updateBookingStatus(bookingId, 'rejected');
}

function finishBooking(bookingId) {
    updateBookingStatus(bookingId, 'finished');
}

function updateBookingStatus(bookingId, status) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                console.log("Booking status updated successfully.");
                showRequests();
            } else {
                console.error("Error updating booking status. Status code: " + xhr.status);
            }
        }
    };

    xhr.open("POST", "updateBookingStatus?bookingId=" + bookingId + "&status=" + status, true);
    xhr.send();
}


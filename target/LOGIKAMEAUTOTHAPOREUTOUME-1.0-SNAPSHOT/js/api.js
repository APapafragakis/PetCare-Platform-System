function verifyCity() {
    const country = document.getElementById("country").value;
    const city = document.getElementById("city").value;
    const addressName = document.getElementById("address").value;
    
    if (country !== "Greece" || !city.includes("Heraklion")) {
        document.getElementById("error-message").textContent = "Η υπηρεσία είναι διαθέσιμη μόνο στο Ηράκλειο αυτή τη στιγμή.";
        return;
    }else{
        document.getElementById("error-message").textContent = "";
    }

    const address = `${addressName} ${city} ${country}`;
    const apiUrl = "https://forward-reverse-geocoding.p.rapidapi.com/v1/forward/";


    const xhr = new XMLHttpRequest();
    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === this.DONE) {
            if (this.status === 200) {
                const obj = JSON.parse(xhr.responseText);
                if (obj.length > 0) {
                    // Η περιοχή υπάρχει - εμφανίστε τυχόν λεπτομέρειες
                    const firstResult = obj[0];
                    const lat = firstResult.lat;
                    const lon = firstResult.lon;
                    console.log("Περιοχή βρέθηκε:", firstResult);
                    console.log("Συντεταγμένες (lat, lon):", lat, lon);

					document.getElementById("lon").value = lon;
					document.getElementById("lat").value = lat;
                    setMarkers(obj);
                } else {
                    // Η περιοχή δεν βρέθηκε - εμφανίστε μήνυμα λάθους
                    document.getElementById("map-container").style.display = "none";
                    document.getElementById("error-message").textContent = "Η περιοχή δεν βρέθηκε.";
                    document.getElementById("error-message").textContent = "Η περιοχή δεν βρέθηκε.";
                }
            } else {
                // Σφάλμα κατά την ανάκτηση των δεδομένων
                console.error("Σφάλμα κατά την ανάκτηση των δεδομένων:", xhr.status);
            }
        }
    });

    xhr.open("GET", `${apiUrl}?q=${address}&accept-language=en&polygon_threshold=0.0`);
    xhr.setRequestHeader("x-rapidapi-host", "forward-reverse-geocoding.p.rapidapi.com");
    xhr.setRequestHeader("x-rapidapi-key", "41f6da7f09mshd8ae4ede73d6e43p1510f0jsne5d6e4b74c92"); 
    xhr.send();
}

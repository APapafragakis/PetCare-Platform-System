const userTypeDropdown = document.getElementById("Type");
const accommodationCheckboxes = document.querySelectorAll(".checkboxes");
const catPriceField = document.getElementById("catPriceInput");
const dogPriceField = document.getElementById("dogPriceInput");
const propertyDescriptionField = document.getElementById("Description");
const hostDog = document.getElementById("dogkeeper");
const hostCat = document.getElementById("catkeeper");
const outdoor = document.getElementById("outdoorAccommodation");
const indoor = document.getElementById("indoorAccommodation");
const catCheck = document.querySelector(".checkboxCat");


outdoor.addEventListener("change", function(){
    if(outdoor.checked && !indoor.checked){
        catCheck.style.display = "none";
    }else{
        catCheck.style.display = "block";
    }
})

hostDog.addEventListener("change", function(){
    if(hostDog.checked){
    dogPriceField.style.display = "block";
    }else{
        dogPriceField.style.display = "none";
    }
})

hostCat.addEventListener("change", function(){
    if(hostCat.checked){
    catPriceField.style.display = "block";
    }else{
        catPriceField.style.display = "none";
    }
})


userTypeDropdown.addEventListener("change", function () {
    const selectedUserType = userTypeDropdown.value;

    if (selectedUserType === "PetKeeper") {
        for (let checkbox of accommodationCheckboxes) {
            checkbox.style.display = "block";
        }
        propertyDescriptionField.style.display = "block";
        dogPriceField = "block"; 
        catPriceField = "block";
    } else {
        for (let checkbox of accommodationCheckboxes) {
            checkbox.style.display = "none";
        }
        catPriceField.style.display = "none";
        dogPriceField.style.display = "none";
        propertyDescriptionField.style.display = "none";
    }

    propertyDescriptionField.style.display = selectedUserType === "PetKeeper" ? "block" : "none";
    propertyDescriptionField.previousElementSibling.style.display = selectedUserType === "PetKeeper" ? "block" : "none";
});
    
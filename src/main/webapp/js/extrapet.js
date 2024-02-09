const userTypeDropdown1 = document.getElementById("Type");
const accommodationCheckboxes1 = document.querySelectorAll(".checkboxes");
const catPriceField1 = document.getElementById("catPriceInput");
const dogPriceField1 = document.getElementById("dogPriceInput");
const propertyDescriptionField1 = document.getElementById("Description");
const hostDog1 = document.getElementById("dogkeeper");
const hostCat1 = document.getElementById("catkeeper");
const outdoor1 = document.getElementById("outdoorAccommodation");
const indoor1 = document.getElementById("indoorAccommodation");
const catCheck1 = document.querySelector(".checkboxCat");


outdoor1.addEventListener("change", function(){
    if(outdoor1.checked && !indoor1.checked){
        catCheck1.style.display = "none";
    }else{
        catCheck1.style.display = "block";
    }
})

hostDog1.addEventListener("change", function(){
    if(hostDog1.checked){
    dogPriceField1.style.display = "block";
    }else{
        dogPriceField1.style.display = "none";
    }
})

hostCat1.addEventListener("change", function(){
    if(hostCat1.checked){
    catPriceField1.style.display = "block";
    }else{
        catPriceField1.style.display = "none";
    }
})


userTypeDropdown1.addEventListener("click", function () {
    const selectedUserType = userTypeDropdown1.value;

    if (selectedUserType === "PetKeeper") {
        for (let checkbox of accommodationCheckboxes1) {
            checkbox.style.display = "block";
        }
        propertyDescriptionField1.style.display = "block";
        dogPriceField1 = "block"; 
        catPriceField1 = "block";
    } else {
        for (let checkbox of accommodationCheckboxes1) {
            checkbox.style.display = "none";
        }
        catPriceField1.style.display = "none";
        dogPriceField1.style.display = "none";
        propertyDescriptionField1.style.display = "none";
    }

    propertyDescriptionField1.style.display = selectedUserType === "PetKeeper" ? "block" : "none";
    propertyDescriptionField1.previousElementSibling.style.display = selectedUserType === "PetKeeper" ? "block" : "none";
});
    



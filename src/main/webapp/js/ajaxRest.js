/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function addPet() {

    let myForm = document.getElementById('myForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData=JSON.stringify(data);
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
           document.getElementById('ajaxContent').innerHTML="Pet inserted.";
                      //document.getElementById('ajaxContent').innerHTML="<br>HEY</br>";

            
        } else if (xhr.status !== 200) {
            document.getElementById('msg')
                    .innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+
					JSON.stringify(xhr.responseText);
 
        }
    };
    xhr.open('POST', 'InsertPet');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
}

function editPet() {
    
    let myForm = document.getElementById('editpetform');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData=JSON.stringify(data);
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
           document.getElementById('ajaxContent2').innerHTML=JSON.stringify(xhr.responseText);
                      //document.getElementById('ajaxContent').innerHTML="<br>HEY</br>";

            
        } else if (xhr.status !== 200) {
            document.getElementById('ajaxContent2')
                    .innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"+
					JSON.stringify(xhr.responseText);
 
        }
    };
    xhr.open('POST', 'EditPet');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
}

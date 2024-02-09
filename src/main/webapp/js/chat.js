function gettime(){
    var currentdate = new Date(); 
    var datetime = currentdate.getFullYear() + "-"
                + (currentdate.getMonth()+1)  + "-" 
                + currentdate.getDate() + " "  
                + currentdate.getHours() + ":"  
                + currentdate.getMinutes() + ":" 
                + currentdate.getSeconds();
    var elem = document.getElementById("datetime");
    elem.value = datetime;
    console.log("Value is ", elem.value);
}

function sendChatAJAX() {
    gettime();
    let myForm = document.getElementById('myForm');
    let formData = new FormData(myForm);

    
    const data = {};

    formData.forEach((value, key) => {

		return data[key] = value;
	});
            var jsonData = JSON.stringify(data);
            console.log(jsonData);


    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            
                document.getElementById('ajaxContent1').innerHTML = xhr.responseText;
                console.log(xhr.responseText);
            } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };


    //alert(params);
    xhr.open('POST', 'Chat');
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(jsonData);
}


function getChatAJAX() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
           // alert(xhr.responseText);
            
            if(xhr.responseText!=="")
                document.getElementById('ajaxContent1').innerHTML = xhr.responseText;
            console.log("im here")
            setTimeout(getChatAJAX,1000);
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    var messages = document.getElementsByTagName("p").length;
    xhr.open('GET', 'Chat?lastID='+messages); 
     xhr.send();
}


setTimeout(getChatAJAX,1000);
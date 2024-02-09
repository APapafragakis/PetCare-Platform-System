function showAvailablePetKeepers() {
    var xhr = new XMLHttpRequest();
    var servletUrl = 'AvailableService';

    xhr.open('GET', servletUrl, true);
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            document.getElementById('ajaxContent').innerHTML = xhr.responseText;
        } else {
            console.error('Request failed with status:', xhr.status, xhr.statusText);
        }
    };

    xhr.send();
}
var map;
var markers;

function initializeMap() {
    map = new OpenLayers.Map("Map");
    var mapnik = new OpenLayers.Layer.OSM();
    var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
    var toProjection = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
    var position = new OpenLayers.LonLat(25.1442, 35.3387).transform(fromProjection, toProjection);
    var zoom = 5;

    map.addLayer(mapnik);
    map.setCenter(position, zoom);
    markers = new OpenLayers.Layer.Markers("Markers");
    map.addLayer(markers);
    setMarkers([{lon: 25.1442, lat: 35.3387}]); 
}

function setPosition(lon, lat) {
    var fromProjection = new OpenLayers.Projection("EPSG:4326");
    var toProjection = new OpenLayers.Projection("EPSG:900913");
    return new OpenLayers.LonLat(lon, lat).transform(fromProjection, toProjection);
}

function handler(position, message) {
    var popup = new OpenLayers.Popup.FramedCloud("Popup", position, null, message, null, true);
    map.addPopup(popup);
}

function setMarkers(array) {
    for (let marker of array) {
        console.log(marker);
        var position1 = setPosition(marker.lon, marker.lat);
        var marker1 = new OpenLayers.Marker(position1);
        markers.addMarker(marker1);
        marker1.events.register('mousedown', marker1, function (evt) {
            handler(position1, 'CSD: sxoli mike');
        });
    }
}

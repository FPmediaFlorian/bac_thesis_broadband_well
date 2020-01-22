//different function used all over the .jsp's
//mostly map regarded

function createMap(){
    map = L.map('map1').setView([48.20809,16.37156], 13);

    //add (c) Infos
    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox/streets-v11'
    }).addTo(map);
}


function addBBWmarkersWpopupsDynamic() {
    var greenIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/marker-icon-2x-green.png',
        shadowUrl: 'resources/leafletRM/markers/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    var BBWIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/marker-icon-2x-grey-wifi.png',
        shadowUrl: 'resources/leafletRM/markers/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });




}

function addBBWmarkersWpopups() {

    var normalIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/marker-icon-2x-grey-wifi.png',
        shadowUrl: 'resources/leafletRM/markers/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    var BBWIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/marker-icon-2x-grey-wifi.png',
        shadowUrl: 'resources/leafletRM/markers/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    //TODO Parse dynamicly from Javacode
    var markerWS29 = L.marker([48.2200482, 16.3562356],{icon: BBWIcon}).addTo(map); //WS 29
    var markerVKM = L.marker([48.2131498, 16.3505518],{icon: BBWIcon}).addTo(map); //VKM

    markerWS29.bindPopup('BBW in Währingerstraße 29 <br>Computer Science Institute <br>University of Vienna').openPopup;
    markerVKM.bindPopup('BBW in  Laudongasse 15–19<br>Volkskundemuseum Wien').openPopup;
}


function labelUpload() {
    document.getElementById('streamLabel').innerHTML ='Upload in MBit/s';
}
function labelDownload() {
    document.getElementById('streamLabel').innerHTML ='Download in MBit/s';


}

function hideSpeedform() {
    document.getElementById('streamLabel').style.display = 'none';
    document.getElementById('streamInput').style.display = 'none';
    document.getElementById('expertDownstreamHelp').style.display = 'none';

}

function showSpeedForm() {

    document.getElementById('streamLabel').style.display = 'block';
    document.getElementById('streamInput').style.display = 'block';
    document.getElementById('expertDownstreamHelp').style.display = 'block';

}

function drawRoute(startLat, startLng, destinationLat, destinationLng, APIkey, BBWIcon, greenIcon, vehicle) {
    L.Routing.control({
        waypoints: [
            L.latLng(startLat, startLng),
            L.latLng(destinationLat, destinationLng)
        ],
        createMarker: function (i, wp, nWps) {
            if (i === nWps - 1) {
                return L.marker(wp.latLng, {icon: BBWIcon});
            } else {
                return L.marker(wp.latLng, {icon: greenIcon});
            }
        },
        router: L.Routing.graphHopper(APIkey,{urlParameters: {vehicle: vehicle}})
    }).addTo(map);
}
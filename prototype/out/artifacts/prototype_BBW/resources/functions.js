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

function toogleSpeedForm() {
    if(document.getElementById('speedformRadio').checked){
        //show Speedinfo Form
        document.getElementById('expertUpstream').style.display = 'block';
        document.getElementById('expertUpstreamLabel').style.display = 'block';
        document.getElementById('expertUpstreamHelp').style.display = 'block';

        document.getElementById('expertDownstream').style.display = 'block';
        document.getElementById('expertDownstreamLabel').style.display = 'block';
        document.getElementById('expertDownstreamHelp').style.display = 'block';
    }else {
        //hide Speedinfo Form
        document.getElementById('expertUpstream').style.display = 'none';
        document.getElementById('expertUpstreamLabel').style.display = 'none';
        document.getElementById('expertUpstreamHelp').style.display = 'none';

        document.getElementById('expertDownstream').style.display = 'none';
        document.getElementById('expertDownstreamLabel').style.display = 'none';
        document.getElementById('expertDownstreamHelp').style.display = 'none';
    }
}

function drawRoutess(startLat, startLng, destinationLat, destinationLng, APIkey) {
    L.Routing.control({
        waypoints: [
            L.latLng(startLat,startLng),
            L.latLng(destinationLat,destinationLng)
        ],

        //TODO Endmarker ändern auf BBW icon

        router: L.Routing.graphHopper(APIkey)
    }).addTo(map);
}
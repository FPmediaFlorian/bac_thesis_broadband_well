//different function used all over the .jsp's
//mostly map regarded

function createMap() {
    map = L.map('map1').setView([48.20809, 16.37156], 13);

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


function labelUpload() {
    document.getElementById('streamLabel').innerHTML = 'Upload in MBit/s';
}

function labelDownload() {
    document.getElementById('streamLabel').innerHTML = 'Download in MBit/s';


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
        router: L.Routing.graphHopper(APIkey, {urlParameters: {vehicle: vehicle}})
    }).addTo(map);
}

function drawPublicRoute(startLat, startLng, stationAlat, stationAlng, stationBlat, stationBlng, destLat, destLng, orangeIcon, greenIcon, BBWIcon, APIkey) {
    //Current Location -> Station A
    L.Routing.control({
        waypoints: [
            L.latLng(startLat, startLng),
            L.latLng(stationAlat, stationAlng)
        ],
        createMarker: function (i, wp, nWps) {
            if (i === nWps - 1) {
                return L.marker(wp.latLng, {icon: orangeIcon});
            } else {
                return L.marker(wp.latLng, {icon: greenIcon});
            }
        },
        fitSelectedRoutes: false,
        router: L.Routing.graphHopper(APIkey, {urlParameters: {vehicle: 'FOOT'}})
    }).addTo(map);

    //Station A -> Station B

    pline = [[stationAlat, stationAlng], [stationBlat, stationBlng]];

    var polyline = L.polyline(pline, {color: 'orange'}).addTo(map);

    //Station B -> Destination
    L.Routing.control({
        waypoints: [
            L.latLng(stationBlat, stationBlng),
            L.latLng(destLat, destLng)
        ],
        createMarker: function (i, wp, nWps) {
            if (i === nWps - 1) {
                return L.marker(wp.latLng, {icon: BBWIcon});
            } else {
                return L.marker(wp.latLng, {icon: orangeIcon});
            }
        },
        fitSelectedRoutes: false,
        router: L.Routing.graphHopper(APIkey, {urlParameters: {vehicle: 'FOOT'}})
    }).addTo(map);

    map.fitBounds(polyline.getBounds());
}


function speedtestAndSubmitExpert() {
    var form = document.getElementById('expertForm');
    if (form.reportValidity()) {
        if (document.getElementById('speedtestRadio').checked) {
            document.getElementById('loadingAnimationExpert').style.display = "block";

            s = new Speedtest();
            //Upload or Download?
            if (document.getElementById("uploadRadio").checked) {
                //Upload
                s.setParameter("url_ul", "//st-be-bo1.infra.garr.it/empty.php");
                s.setParameter("test_order", "U");
                s.start();

                s.onupdate = function (data) {
                    document.getElementById('streamInput').value = data.ulStatus;
                }
            } else {
                //Download
                s.setParameter("url_dl", "//st-be-bo1.infra.garr.it/garbage.php");
                s.setParameter("test_order", "D");
                s.start();

                s.onupdate = function (data) {
                    document.getElementById('streamInput').value = data.dlStatus;
                }
            }

            s.onend = function (aborted) {
                if (!aborted) {
                    console.log('Test finished!');
                    document.getElementById("loadingTextExpert").innerHTML = "Calculating routes and downloadtimes!";
                    document.getElementById("expertForm").submit();

                } else {
                    console.log('Test aborted! Websiteadmin');
                }
            }
        } else {
            document.getElementById('loadingAnimationExpert').style.display = "block";
            document.getElementById("loadingTextExpert").innerHTML = "Calculating routes and downloadtimes!";
            document.getElementById("expertForm").submit();
        }
    }
}

function speedtestAndSubmitEasy() {
    var form = document.getElementById('easyForm');
    if (form.reportValidity()) {
        // show loading animation
        document.getElementById('loadingAnimation').style.display = "block";
        //Upload or Download?
        s = new Speedtest();
        var streamspeed;
        if (document.getElementById("easyUploadRadio").checked) {
            //Upload
            s.setParameter("url_ul", "//st-be-bo1.infra.garr.it/empty.php");
            s.setParameter("test_order", "U");
            s.start();

            s.onupdate = function (data) {
                document.getElementById('streamspeed').value = data.ulStatus;
            }
        } else {
            //Download
            s.setParameter("url_dl", "//st-be-bo1.infra.garr.it/garbage.php");
            s.setParameter("test_order", "D");
            s.start();

            s.onupdate = function (data) {
                document.getElementById('streamspeed').value = data.dlStatus;
            }
        }

        s.onend = function (aborted) {
            if (!aborted) {
                console.log('Test finished!');
                document.getElementById("loadingText").innerHTML = "Calculating routes and downloadtimes!";
                document.getElementById("easyForm").submit();
            } else {
                console.log('Test aborted! Contact the Websiteadmin!');
            }
        }

    }

}
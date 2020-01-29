<%--
  Created by IntelliJ IDEA.
  User: florianpichlmann
  Date: 23.01.20
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%-- Import Header File --%>
<%@ include file="templates/header.jsp" %>

<section>
    <div class="container-fluid h-75">
        <div class="row h-100">
            <!--<div class="col-md-3 overflow-auto"></div>-->


            <div class="col-md-12" id="map"></div>

            <div class='absolute fl my24 mx24 py24 px24 bg-gray-faint round'>
                <form id='params'>
                    <h4 class='txt-m txt-bold mb6'>Choose a BBW:</h4>
                    <div class='mb12 mr12 toggle-group align-center'>
                        <select class="form-control" name="bbwSelector">
                            ${bbwSelector}
                        </select>
                    </div>

                    <h4 class='txt-m txt-bold mb6'>Choose a travel mode:</h4>
                    <div class='mb12 mr12 toggle-group align-center'>
                        <label class='toggle-container'>
                            <input name='profile' type='radio' value='walking'>
                            <div class='toggle toggle--active-null toggle--null'>Walking</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='profile' type='radio' value='cycling' checked>
                            <div class='toggle toggle--active-null toggle--null'>Cycling</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='profile' type='radio' value='driving'>
                            <div class='toggle toggle--active-null toggle--null'>Driving</div>
                        </label>
                    </div>
                    <h4 class='txt-m txt-bold mb6'>Choose Downloadspeed Current Location</h4>
                    <div class='mb12 mr12 toggle-group align-center'>
                        <label class='toggle-container'>
                            <input name='streamspeed' type='radio' value='20'>
                            <div class='toggle toggle--active-null toggle--null'>20 MBit/s</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='streamspeed' type='radio' value='50'>
                            <div class='toggle toggle--active-null toggle--null'>50 MBit/s</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='streamspeed' type='radio' value='100' checked>
                            <div class='toggle toggle--active-null toggle--null'>100 MBit/s</div>
                        </label>
                    </div>
                    <br>
                    <div class='mb12 mr12 toggle-group align-center' style="display: none;" id="areaExceedMessage">
                        <div class="alert alert-warning" role="alert">
                            Sorry! Some Isochrone Lines exceeds the area limit.<br>
                            Not all Lines could be calculated!
                        </div>
                    </div>

                </form>
            </div>

            <div class='map-overlay' id="legend">

            </div>

        </div>
    </div>
</section>

<script>

    // // Create variables to use in getIso()
    var urlBase = 'https://api.mapbox.com/isochrone/v1/mapbox/';
    var lon = 16.356120;
    var lat = 48.220110;
    var profile = 'cycling';
    var minutes = [6, 9, 13, 16, 19, 26];
    var params = document.getElementById('params');
    var streamspeed = 100; //Mbit
    var filesizes = [10, 15, 20, 25, 30, 40];
    var colors = ['#9EEE00', '#00CC00', '#00999A', '#1141AC', '#3815AE', '#700BAB']

    // Add your Mapbox access token
    mapboxgl.accessToken = ${mapboxAPIKEY};
    var map = new mapboxgl.Map({
        container: 'map', // Specify the container ID
        style: 'mapbox://styles/mapbox/streets-v11', // Specify which map style to use
        center: [lon, lat], // Specify the starting position
        zoom: 13, // Specify the starting zoom

    });

    map.addControl(new mapboxgl.NavigationControl());


    // Create a function that sets up the Isochrone API query then makes an Ajax call
    function getIso() {
        //ISO1
        var query = urlBase + profile + '/' + lon + ',' + lat + '?contours_minutes=' + minutes[0] + '&polygons=true&access_token=' + mapboxgl.accessToken;
        $.ajax({
            method: 'GET',
            url: query
        }).done(function (data) {
            map.getSource('iso1').setData(data);
        })
        //ISO2
        query = urlBase + profile + '/' + lon + ',' + lat + '?contours_minutes=' + minutes[1] + '&polygons=true&access_token=' + mapboxgl.accessToken;
        $.ajax({
            method: 'GET',
            url: query
        }).done(function (data) {
            map.getSource('iso2').setData(data);
        })
        //ISO3
        query = urlBase + profile + '/' + lon + ',' + lat + '?contours_minutes=' + minutes[2] + '&polygons=true&access_token=' + mapboxgl.accessToken;
        $.ajax({
            method: 'GET',
            url: query
        }).done(function (data) {
            map.getSource('iso3').setData(data);
        })
        //ISO4
        query = urlBase + profile + '/' + lon + ',' + lat + '?contours_minutes=' + minutes[3] + '&polygons=true&access_token=' + mapboxgl.accessToken;
        $.ajax({
            method: 'GET',
            url: query
        }).done(function (data) {
            map.getSource('iso4').setData(data);
        })
        //ISO5
        query = urlBase + profile + '/' + lon + ',' + lat + '?contours_minutes=' + minutes[4] + '&polygons=true&access_token=' + mapboxgl.accessToken;
        $.ajax({
            method: 'GET',
            url: query
        }).done(function (data) {
            map.getSource('iso5').setData(data);
        })
        //ISO6
        query = urlBase + profile + '/' + lon + ',' + lat + '?contours_minutes=' + minutes[5] + '&polygons=true&access_token=' + mapboxgl.accessToken;
        $.ajax({
            method: 'GET',
            url: query
        }).done(function (data) {
            map.getSource('iso6').setData(data);
        })


    };


    map.on('load', function () {
        // When the map loads, add the source and layer
        //ISO6
        map.addSource('iso6', {
            type: 'geojson',
            data: {
                'type': 'FeatureCollection',
                'features': []
            }
        });
        map.addLayer({
            'id': 'isoLayer6',
            'type': 'fill',
            // Use "iso" as the data source for this layer
            'source': 'iso6',
            'layout': {},
            'paint': {
                // The fill color for the layer is set to a light purple
                'fill-color': '#700BAB',
                'fill-opacity': 0.3
            }
        }, "poi-label");

        //ISO5
        map.addSource('iso5', {
            type: 'geojson',
            data: {
                'type': 'FeatureCollection',
                'features': []
            }
        });
        map.addLayer({
            'id': 'isoLayer5',
            'type': 'fill',
            // Use "iso" as the data source for this layer
            'source': 'iso5',
            'layout': {},
            'paint': {
                // The fill color for the layer is set to a light purple
                'fill-color': '#3815ae',
                'fill-opacity': 0.3
            }
        }, "poi-label");

        //ISO4
        map.addSource('iso4', {
            type: 'geojson',
            data: {
                'type': 'FeatureCollection',
                'features': []
            }
        });
        map.addLayer({
            'id': 'isoLayer4',
            'type': 'fill',
            // Use "iso" as the data source for this layer
            'source': 'iso4',
            'layout': {},
            'paint': {
                // The fill color for the layer is set to a light purple
                'fill-color': '#1141ac',
                'fill-opacity': 0.3
            }
        }, "poi-label");

        //ISO3
        map.addSource('iso3', {
            type: 'geojson',
            data: {
                'type': 'FeatureCollection',
                'features': []
            }
        });
        map.addLayer({
            'id': 'isoLayer3',
            'type': 'fill',
            // Use "iso" as the data source for this layer
            'source': 'iso3',
            'layout': {},
            'paint': {
                // The fill color for the layer is set to a light purple
                'fill-color': '#00999a',
                'fill-opacity': 0.3
            }
        }, "poi-label");

        //ISO2
        map.addSource('iso2', {
            type: 'geojson',
            data: {
                'type': 'FeatureCollection',
                'features': []
            }
        });
        map.addLayer({
            'id': 'isoLayer2',
            'type': 'fill',
            // Use "iso" as the data source for this layer
            'source': 'iso2',
            'layout': {},
            'paint': {
                // The fill color for the layer is set to a light purple
                'fill-color': '#00cc00',
                'fill-opacity': 0.3
            }
        }, "poi-label");

        //ISO1
        map.addSource('iso1', {
            type: 'geojson',
            data: {
                'type': 'FeatureCollection',
                'features': []
            }
        });
        map.addLayer({
            'id': 'isoLayer1',
            'type': 'fill',
            // Use "iso" as the data source for this layer
            'source': 'iso1',
            'layout': {},
            'paint': {
                // The fill color for the layer is set to a light purple
                'fill-color': '#9eee00',
                'fill-opacity': 0.3
            }
        }, "poi-label");


        createLegend()


        refreshIsoAndMarker();
    });

    function createLegend() {
        var element = document.getElementById("legend");
        while (element.firstChild) {
            element.removeChild(element.firstChild);
        }

        var item = document.createElement('div');
        var value = document.createElement('span');
        value.innerHTML = '<strong>Downloadsizes<strong>';
        item.appendChild(value);
        legend.appendChild(item);

        for (i = 0; i < filesizes.length; i++) {
            var filesize = filesizes[i];
            var color = colors[i];
            var item = document.createElement('div');
            var key = document.createElement('span');
            key.className = 'legend-key';
            key.style.backgroundColor = color;

            var value = document.createElement('span');
            value.innerHTML = filesize + ' GB <small>(' + minutes[i] + 'min)</small>';
            item.appendChild(key);
            item.appendChild(value);
            legend.appendChild(item);
        }
    }


    params.addEventListener('change', function (e) {
        if (e.target.name === 'bbwSelector') {
            var hlp = e.target.value.split(',');
            lon = hlp[1];
            lat = hlp[0];
        } else {
            if (e.target.name === 'profile') {
                profile = e.target.value;

            } else {
                streamspeed = e.target.value;
                calculateMinutes();
            }
        }
        refreshIsoAndMarker();
    });

    function calculateMinutes() {
        var i;
        for (i = 0; i < minutes.length; i++) {
            var timeHome = (filesizes[i] * 8000) / streamspeed;
            var timeBBW = (filesizes[i] * 8) / 10;
            var Cminutes = Math.floor(((timeHome - timeBBW) / 60) / 2);
            console.log('Calculated Minutes:' + Cminutes);
            if (Cminutes > 60) {
                document.getElementById("areaExceedMessage").style.display = "block";
            } else {
                document.getElementById("areaExceedMessage").style.display = "none";
                minutes[i] = Cminutes;
            }
        }
        createLegend();
    }

    function refreshIsoAndMarker() {
        getIso();
        refreshMarker();
    }

    var lngLat = {
        lon: lon,
        lat: lat
    };
    var marker = new mapboxgl.Marker();

    function refreshMarker() {
        lngLat = {
            lon: lon,
            lat: lat
        };
        marker.setLngLat(lngLat).addTo(map);
    }
</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
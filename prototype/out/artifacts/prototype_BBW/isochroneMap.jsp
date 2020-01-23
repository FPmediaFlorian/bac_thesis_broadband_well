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
    <div class="container-fluid h-75"> <%--TODO: Höhe dynamisch anpassen --%>
        <div class="row h-100">
            <!--<div class="col-md-4 overflow-auto"></div>-->
            <div class="col-md-12" id="map"></div>

            <div class='absolute fl my24 mx24 py24 px24 bg-gray-faint round'>
                <form id='params'>
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
                    <h4 class='txt-m txt-bold mb6'>Choose Downloadspeed @Home</h4>
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
                    <h4 class='txt-m txt-bold mb6'>Choose Filesize</h4>
                    <div class='mb12 mr12 toggle-group align-center'>
                        <label class='toggle-container'>
                            <input name='filesize' type='radio' value='10'checked>
                            <div class='toggle toggle--active-null toggle--null'>10 GB</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='filesize' type='radio' value='15'>
                            <div class='toggle toggle--active-null toggle--null'>15 GB</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='filesize' type='radio' value='20'>
                            <div class='toggle toggle--active-null toggle--null'>20 GB</div>
                        </label>
                    </div>
                    <br>
                    <div class='mb12 mr12 toggle-group align-center'>
                        <label class='toggle-container'>
                            <input name='filesize' type='radio' value='25'>
                            <div class='toggle toggle--active-null toggle--null'>25 GB</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='filesize' type='radio' value='30'>
                            <div class='toggle toggle--active-null toggle--null'>30 GB</div>
                        </label>
                        <label class='toggle-container'>
                            <input name='filesize' type='radio' value='31'>
                            <div class='toggle toggle--active-null toggle--null'>>50 GB</div>
                        </label>
                    </div>
                    <br>
                    <div class='mb12 mr12 toggle-group align-center' style="display: none;" id="areaExceedMessage">
                        <div class="alert alert-warning" role="alert">
                            Sorry! The calculation exceeds the area limit.
                        </div>
                    </div>

                </form>
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
    var minutes = 13;
    var params = document.getElementById('params');
    var streamspeed = 100; //Mbit
    var filesize = 10; //TB

    // Add your Mapbox access token
    mapboxgl.accessToken = 'pk.eyJ1IjoiZmxvcmlhbnBpY2hsbWFubiIsImEiOiJjazVxaDR5OG8wMjdxM25wNnRibG02Ym1tIn0.NaT4DvNuBA0CRtei3_Wy1w';
    var map = new mapboxgl.Map({
        container: 'map', // Specify the container ID
        style: 'mapbox://styles/mapbox/streets-v11', // Specify which map style to use
        center: [lon,lat], // Specify the starting position
        zoom: 13, // Specify the starting zoom

    });

    map.addControl(new mapboxgl.NavigationControl());




    // Create a function that sets up the Isochrone API query then makes an Ajax call
    function getIso() {
        var query = urlBase + profile + '/' + lon + ',' + lat + '?contours_minutes=' + minutes + '&polygons=true&access_token=' + mapboxgl.accessToken;

        $.ajax({
            method: 'GET',
            url: query
        }).done(function(data) {
            map.getSource('iso').setData(data);
        })
    };

    var marker = new mapboxgl.Marker({
        'color': '#314ccd'
    });

    // Create a LngLat object to use in the marker initialization
    // https://docs.mapbox.com/mapbox-gl-js/api/#lnglat
    var lngLat = {
        lon: lon,
        lat: lat
    };

    map.on('load', function() {
        // When the map loads, add the source and layer
        map.addSource('iso', {
            type: 'geojson',
            data: {
                'type': 'FeatureCollection',
                'features': []
            }
        });

        map.addLayer({
            'id': 'isoLayer',
            'type': 'fill',
            // Use "iso" as the data source for this layer
            'source': 'iso',
            'layout': {},
            'paint': {
                // The fill color for the layer is set to a light purple
                'fill-color': '#5a3fc0',
                'fill-opacity': 0.3
            }
        }, "poi-label");

        // Initialize the marker at the query coordinates
        marker.setLngLat(lngLat).addTo(map);
        // Make the API call
        getIso();
    });

    // Call the getIso function
    // You will remove this later - it's just here so you can see the console.log results in this step



    params.addEventListener('change', function(e) {
        if (e.target.name === 'profile') {
            profile = e.target.value;

        } else {
            if (e.target.name === 'filesize') {
                filesize = e.target.value;

            }else {
                streamspeed=e.target.value;
            }
            calculateMinutes();
        }
        getIso();
    });

    function calculateMinutes() {
        var timeHome = (filesize*8000)/streamspeed;
        var timeBBW = (filesize*8)/10;
        var Cminutes = Math.floor((timeHome-timeBBW)/60);
        console.log('Calculated Minutes:'+Cminutes);
        if(Cminutes>60){
            document.getElementById("areaExceedMessage").style.display = "block";
        }
        else {
            document.getElementById("areaExceedMessage").style.display = "none";
            minutes=Cminutes;
        }

    }

</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
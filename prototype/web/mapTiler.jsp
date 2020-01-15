<%--
  Created by IntelliJ IDEA.
  User: florianpichlmann
  Date: 09.01.20
  Time: 09:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Import Header File --%>
<%@ include file="templates/header.jsp" %>

<section>
    <div id="map">

    </div>
</section>

<script>
    // create targomo client
    const client = new tgm.TargomoClient('westcentraleurope', '2J6E470Z6B586DREYFY3213645504');

    // Create a Leaflet map with basemap, set the center of the map to Portland, Oregon.
    const tilesUrl = 'https://api.maptiler.com/maps/positron/{z}/{x}/{y}@2x.png?key=oLLvZ9tFTwUt5TkR2lot';
    const tileLayer = L.tileLayer(tilesUrl, {
        tileSize: 512, zoomOffset: -1,
        minZoom: 1, crossOrigin: true
    });
    var map = L.map('map', {
        layers: [tileLayer],
        scrollWheelZoom: false
    }).setView([48.2094,16.3708], 13);

    // set the attribution
    const attributionText = `<a href='https://targomo.com/developers/resources/attribution/' target='_blank'>&copy; Targomo</a>`
    map.attributionControl.addAttribution(attributionText);



    // Define source and target locations which are passed into the Targomo route service.
    let targets = [{ id: 1, lat: 48.220266, lng: 16.356164 }]; //WS29
    let source = { id: 0, lat: 48.213231, lng: 16.350499 }; //VKM

    // create a target marker icon to be able to distinguish source and target markers
    const redIcon = L.icon({
        iconUrl: 'https://targomo.com/developers/images/code-example/marker-icon-red.png',
        shadowUrl: 'https://targomo.com/developers/images/code-example/marker-shadow.png',
        iconAnchor: [12, 45],
        popupAnchor: [0, -35]
    });

    // create a draggable source and two draggable target markers, add them to the map
    const sourceMarker = L.marker([source.lat, source.lng], { draggable: true }).addTo(map);
    const targetMarker1 = L.marker([targets[0].lat, targets[0].lng], { icon: redIcon, draggable: true }).addTo(map);
    //const targetMarker2 = L.marker([targets[1].lat, targets[1].lng], { icon: redIcon, draggable: true }).addTo(map);

    // Everytime a marker is dragged, update the location and request a new route.
    sourceMarker.on("dragend", markerDragged(source.id));
    targetMarker1.on("dragend", markerDragged(targets[0].id));
    //targetMarker2.on("dragend", markerDragged(targets[1].id));
    function markerDragged(id) {
        return (e) => {
            const position = e.target.getLatLng();
            if (id === source.id) {
                source = { id: source.id, lat: position.lat, lng: position.lng };
            } else {
                targets = targets.map(target => target.id === id ? { id: id, lat: position.lat, lng: position.lng } : target);
            }
            refreshRoutes();
        }
    }


    // The travel options used to determine which routes should be searched for
    const options = {
        travelType: 'transit',
        maxEdgeWeight: 3600,
        edgeWeight: 'time',
        pathSerializer: 'compact',
        transitFrameDate: 20190219,
        transitFrameTime: 36000,
        transitFrameDuration: 3600,
        transitMaxTransfers: 5
    };

    // This array is to keep track of all the leaflet layers which should be removed from the map each time new routes are fetched.
    let leafletLayers = [];

    // Requesting routs from the Targomo API.
    function refreshRoutes() {
        client.routes.fetch([source], targets, options).then(result => {

            // Remove all old layers
            leafletLayers.forEach(layer => {
                map.removeLayer(layer);
            });
            leafletLayers = [];

            // Go through the result, draw a dotted line for all parts of the route which are walked,
            // draw a solid line for the parts of the route which are traveled with transit
            // and finally draw circles for each transfer.
            let circlePoints = [];
            result.forEach((route) => {
                route.routeSegments.forEach(segment => {
                    const leafletPoints = segment.points.map(point => new L.LatLng(point.lat, point.lng));
                    if (segment.type === "WALK") {
                        drawDottedLine(leafletPoints);
                    } else if (segment.type === "TRANSIT") {
                        drawSolidLine(leafletPoints);
                    } else if (segment.type === "TRANSFER") {
                        circlePoints = circlePoints.concat(leafletPoints);
                    }
                })
            });
            // The circles are drawn after the lines so that they appear on top of the lines.
            drawCircles(circlePoints);
        });
    }

    function drawDottedLine(leafletPoints) {
        const line = new L.Polyline(leafletPoints, {
            color: '#3F51B5',
            weight: 5,
            opacity: 1,
            smoothFactor: 1,
            dashArray: '5, 10'
        });
        line.addTo(map);
        leafletLayers.push(line);
    }
    function drawSolidLine(leafletPoints) {
        const line = new L.Polyline(leafletPoints, {
            color: '#FF5722',
            weight: 5,
            opacity: 1,
            smoothFactor: 1
        });
        line.addTo(map);
        leafletLayers.push(line);
    }
    function drawCircles(leafletPoints) {
        leafletPoints.forEach(point => {
            const circle = L.circle(point, {
                radius: 1,
                weight: 15,
                color: '#8BC34A'
            });
            circle.addTo(map);
            leafletLayers.push(circle);
        });
    }

    // Request routes once immediately on page load.
    refreshRoutes();



</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>

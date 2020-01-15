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
    // Define source and target locations which are passed into the Targomo route service.
    let target = [ 48.220266, 16.356164 ]; //WS 29
    let source = [ 48.213231, 16.350499 ]; //VKM

    // define the map
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 10,
        center: new google.maps.LatLng(
            (target[0] + source[0]) / 2, (target[1] + source[1]) / 2,
        ),
        mapTypeId: 'roadmap'
    });

    const MAP_PATH = 'M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z';

    // init the source marker
    const sourceMarker = new google.maps.Marker({
        position: new google.maps.LatLng(source[0], source[1]),
        map: map,
        icon: {
            path: MAP_PATH, scale: 1.4, fillOpacity: 1,
            fillColor: '#ff8319', strokeOpacity: 0,
            anchor: { x: 12, y: 24 },
        }
    });

    // init the target marker
    const targetMarker = new google.maps.Marker({
        position: new google.maps.LatLng(target[0], target[1]),
        map: map,
        icon: {
            path: MAP_PATH, scale: 1.4, fillOpacity: 1,
            fillColor: '#ff8319', strokeOpacity: 0,
            anchor: { x: 12, y: 24 },
        }
    });

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

    // Requesting routs from the Targomo API.
    function refreshRoutes() {
        const s = {...sourceMarker.getPosition().toJSON(), ...{id: 's'}};
        const t = {...targetMarker.getPosition().toJSON(), ...{id: 't'}};
        client.routes.fetch([s], [t], options).then(result => {
            result.forEach((route) => {
                route.routeSegments.forEach(segment => {
                    if (segment.type === "WALK") {
                        const routePath = new google.maps.Polyline({
                            path: segment.points,
                            geodesic: true,
                            strokeOpacity: 0,
                            icons: [{
                                icon: {
                                    path: 'M 0,-1 0,1',
                                    strokeOpacity: 1,
                                    strokeColor: '#FF0000',
                                    scale: 2
                                },
                                offset: '0',
                                repeat: '10px'
                            }],
                            map: map
                        });
                    } else if (segment.type === "TRANSIT") {
                        const routePath = new google.maps.Polyline({
                            path: segment.points,
                            geodesic: true,
                            strokeColor: '#0000FF',
                            strokeOpacity: 1.0,
                            strokeWeight: 3,
                            map: map
                        });
                    } else if (segment.type === "TRANSFER") {
                        for (let transfer of segment.points) {
                            const transferCircle = new google.maps.Marker({
                                map: map,
                                position: transfer,
                                icon: {
                                    path: google.maps.SymbolPath.CIRCLE,
                                    scale: 3,
                                    strokeColor: '#00FF00'
                                }
                            });
                        }
                    }
                })
            });
        });

        let bounds = new google.maps.LatLngBounds();
        bounds.extend(sourceMarker.getPosition());
        bounds.extend(targetMarker.getPosition());

        map.fitBounds(bounds);
    }

    refreshRoutes();


</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>

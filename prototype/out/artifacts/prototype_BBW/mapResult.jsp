<%--
  Created by IntelliJ IDEA.
  User: florianpichlmann
  Date: 10.01.20
  Time: 09:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Import Header File --%>
<%@ include file="templates/header.jsp" %>
<section>
    <div class="container-fluid h-75"> <%--TODO: Höhe dynamisch anpassen --%>
        <div class="row h-100">
            <div class="col-lg-3 overflow-auto">
                <!-- Request result -->
                ${desicionResponse}

            </div>

            <div class="col-lg-9" id="map1"></div>

        </div>
    </div>
</section>

<script>

    var map;

    var BBWIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/marker-icon-2x-grey-wifi.png',
        shadowUrl: 'resources/leafletRM/markers/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });
    var greenIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/marker-icon-2x-green.png',
        shadowUrl: 'resources/leafletRM/markers/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    // Create map
    createMap();

    //Draw the route
    drawRoute(${latlngStart},${latlngDest},'${ghApiKey}',BBWIcon,greenIcon, '${vehicle}');


</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
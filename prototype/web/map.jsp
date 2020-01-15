<%--
  Created by IntelliJ IDEA.
  User: florianpichlmann
  Date: 10.01.20
  Time: 09:03
  To change this template use File | Settings | File Templates.
--%>

<html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Import Header File --%>
<%@ include file="templates/header.jsp" %>

<div id="map"></div>
<script>

    var map = L.map('map').setView([48.20809,16.37156], 13);

    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox/streets-v11'
    }).addTo(map);

    var bbwIcon = L.icon({
        iconUrl: 'resources/img/favicon.png',
        iconSize: [38, 95],
        iconAnchor: [22, 94],
        popupAnchor: [-3, -76],
        shadowSize: [68, 95],
        shadowAnchor: [22, 94]
    });


    var newRedIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/js/img/marker-icon-red.png',
        shadowUrl: 'resources/leafletRM/markers/js/img/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    L.marker([48.2200482, 16.3562356],{icon: newRedIcon}).addTo(map); //WS 29
    L.marker([48.2131498, 16.3505518],{icon: newRedIcon}).addTo(map); //VKM
    L.marker([48.1907465, 16.3302160]).addTo(map); //Reindorfgasse 42


    L.Routing.control({
        waypoints: [
            L.latLng(48.1907465,16.3302160),
            L.latLng(48.2200482,16.3562356)
        ],
        routeWhileDragging: true
    }).addTo(map);

 

</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
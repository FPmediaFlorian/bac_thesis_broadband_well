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
                <!--
                                <div class="alert alert-warning" role="alert">
                                    You should download your Files @Home!
                                </div>
                                <p>Your nearest BBW would be at the VKM (address)!</p>
                                <a class="btn btn-primary" data-toggle="collapse" href="#collapseBBWInfo" role="button" aria-expanded="false" aria-controls="collapseBBWInfo">Infos about the VKM</a>
                                <div class="collapse" id="collapseBBWInfo">
                                    <div class="card card-body">
                                        <p>Das Volkskundemuseum Wien ist eines der großen internationalen ethnographischen Museen mit
                                            umfangreichen Sammlungen zur Volkskunst sowie zu historischen und gegenwärtigen Alltagskulturen
                                            Europas. Wir zeigen Ausstellungen zu vielfältigen Themen des Zusammenlebens in einer sich ständig
                                            verändernden Welt.</p>
                                        <p><strong>Volkskundemuseum Wien</strong></p>
                                        Laudongasse 15–19, 1080 Wien<br>
                                        <strong>Öffnungszeiten</strong>
                                        <u>BBW:</u>
                                        24/7
                                        <u>Museum:</u>
                                        Di bis So, 10.00 bis 17.00 Uhr<br>
                                        Do, 10.00 bis 20.00 Uhr<br>
                                        <u>Bibliothek:</u>
                                        Di bis Fr, 9.00 bis 12.00 Uhr<br>
                                        <u>Hildebrandt Café:</u>
                                        Di bis So, 10.00 bis 18.00 Uhr<br>
                                        Do, 10.00 bis 20.00 Uhr<br>
                                        <u>Mostothek:</u>
                                        Di, ab 17.00 Uhr

                                    </div>
                                </div>
                                <br>
                                <br>
                                <h4>Detailed Info:</h4>
                                <br>
                                <div class="row">
                                    <div class="col-12">
                                        <table class="table table-borderless">
                                            <tbody>
                                                <tr>
                                                    <td><strong>Traveltime:</strong></td>
                                                    <td>20 min</td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Downoadtime:</strong></td>
                                                    <td>2 min</td>
                                                </tr>
                                                <tr class="table-danger">
                                                    <td>total Time @BBW</td>
                                                    <td>22 min</td>
                                                </tr>
                                                <tr class="table-success">
                                                    <td>total Time @Home:</td>
                                                    <td>.. min</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                                -->
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
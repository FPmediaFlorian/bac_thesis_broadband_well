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
    <div class="container-fluid h-75">
        <div class="row h-100">
            <div class="col-lg-3 overflow-auto">
                <!-- Request result -->
                ${desicionResponse}
                <!--
                <div class="pt-5 pb-3">
                    <div class="alert alert-success text-center ">
                        <strong>You should go to the Broadbandwell!</strong>
                    </div>
                </div>

                <div class="text-center pt-3 pb-3">
                    <p class="text-center pb-3">The nearest BBW is at <strong>VKM</strong></p>
                    <a class="btn btn-secondary " data-toggle="collapse" href="#collapseBBWInfo" role="button" aria-expanded="false" aria-controls="collapseBBWInfo">Detailed Infos</a>
                    <div class="collapse pt-2" id="collapseBBWInfo">
                        <div class="card card-body">
                            Info 123
                        </div>
                    </div>
                </div>

                <div class="text-center pt-3 pb-3">
                    <p class="text-center pb-3">Going to the BBW will save you <strong>1h 32 min</strong></p>
                    <a class="btn btn-primary" data-toggle="collapse" href="#collapseTimeinfo" role="button" aria-expanded="false" aria-controls="collapseTimeinfo">Learn more</a>
                    <div class="collapse pt-2" id="collapseTimeinfo">
                        <div class="card card-body">
                            <p>Time you need downloading @BBW</p>
                            <table class="table table-borderless">
                                <tbody>
                                    <tr>
                                        <td><strong>Traveltime:</strong></td>
                                        <td>2h 32m 2s</td>
                                    </tr>
                                    <tr>
                                        <td><strong>Downloadtime BBW:</strong></td>
                                        <td>0h 12m 4s</td>
                                    </tr>
                                    <tr class="table-success">
                                        <td><strong>total Time:</strong></td>
                                        <td>2h 44m 6s</td>
                                    </tr>
                                </tbody>
                            </table>
                            <p>Time you need downloading @current Location</p>
                            <table class="table table-borderless">
                                <tbody>
                                <tr class="table-warning">
                                    <td><strong>Downloadtime:</strong></td>
                                    <td>5h 44m 6s</td>
                                </tr>
                                </tbody>
                            </table>



                        </div>
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
    var orangeIcon = new L.Icon({
        iconUrl: 'resources/leafletRM/markers/marker-icon-2x-orange.png',
        shadowUrl: 'resources/leafletRM/markers/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    // Create map
    createMap();

    //Draw routes

    if ('${vehicle}' === 'PUBLIC') {
        drawPublicRoute(${latlngStart}, ${stationA}, ${stationB}, ${latlngDest}, orangeIcon, greenIcon, BBWIcon, '${ghApiKey}');
    } else {
        drawRoute(${latlngStart}, ${latlngDest}, '${ghApiKey}', BBWIcon, greenIcon, '${vehicle}');
    }


</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
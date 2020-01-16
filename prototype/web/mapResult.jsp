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
<section>
    <div class="container-fluid h-75"> <%--TODO: Höhe dynamisch anpassen --%>
        <div class="row h-100">
            <div class="col-lg-3 overflow-auto">
                <!-- Request result -->

            </div>

            <div class="col-lg-9" id="map1"></div>

        </div>
    </div>
</section>

<script>

    var map;

    // Create map
    createMap();

    //Draw the route
    drawRoutess(${latlngStart},${latlngDest},'${ghApiKey}');


</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: florianpichlmann
  Date: 07.01.20
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <title>BBW Prototype</title>

        <!-- Favicon Tag -->
        <link rel="icon" type="image/png" href="resources/img/favicon.png"/>

        <!-- BOOTSTRAP Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"/>

        <link rel="stylesheet" href="resources/styles.css"/>
        <script src="resources/functions.js"></script>


        <!-- Bootstrap JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

        <!-- Include Leaflet -->
        <link rel="stylesheet" href="resources/leaflet/leaflet.css" />
        <script src="resources/leaflet/leaflet.js"></script>

        <!-- Include Leaflet Routing Machine -->
        <link rel="stylesheet" href="resources/leafletRM/leaflet-routing-machine.css" />
        <script src="resources/leafletRM/leaflet-routing-machine.min.js"></script>

        <!-- Include Graphhopper -->
        <script src="resources/graphhopper/lrm-graphhopper-1.2.0.js"></script>

        <!-- Include Maptiler GEOCODER -->
        <script src="resources/maptiler-geocoder/maptiler-geocoder.js"></script>
        <link href="resources/maptiler-geocoder/maptiler-geocoder.css" rel="stylesheet" />

        <!-- Include Font Awesome Icons-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css" integrity="sha256-46qynGAkLSFpVbEBog43gvNhfrOj+BmwXdxFgVK/Kvc=" crossorigin="anonymous" />

        <!-- Include Speedtest Librespeed-->
        <script src="resources/speedtest/speedtest.js"></script>
        <script src="resources/speedtest/speedtest_worker.js"></script>

        <!-- Include Mapbox API-->
        <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v1.6.1/mapbox-gl.js'></script>
        <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v1.6.1/mapbox-gl.css' rel='stylesheet'/>
        <!-- Import Assembly -->
        <link href='https://api.mapbox.com/mapbox-assembly/v0.23.2/assembly.min.css' rel='stylesheet'/>
        <script src='https://api.mapbox.com/mapbox-assembly/v0.23.2/assembly.js'></script>
        <!-- Import jQuery -->
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>


    </head>

    <body>



        <!-- NAVBAR-->
        <nav class="navbar navbar-expand-xl  navbar-light bg-light shadow-sm">
            <div class="container">
                <a href="index.jsp" class="navbar-brand">
                    <!-- Logo Image -->
                    <img src="resources/img/BBW_Logo_trans.png" alt="" class="d-inline-block align-middle mr-2 logo-small">
                </a>

                <button type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler"><span class="navbar-toggler-icon"></span></button>

                <div id="navbarSupportedContent" class="collapse navbar-collapse">
                    <ul class="navbar-nav ml-auto">
                        <!-- TODO: add active atribute dynamicly -->
                        <li class="nav-item"><a href="index.jsp#about01" class="nav-link menu-font">About <span class="sr-only">(current)</span></a></li>
                        <li class="nav-item"><a href="index.jsp#figure01" class="nav-link menu-font">BBW in figures</a></li>
                        <li class="nav-item"><a href="index.jsp#use01" class="nav-link menu-font">How to use?</a></li>
                        <li class="nav-item"><a href="BuildMap" class="nav-link menu-font">Route planer</a></li>
                        <li class="nav-item"><a href="BuildIsochrone" class="nav-link menu-font">Isochrone Map</a></li>
                    </ul>
                </div>
            </div>
        </nav>
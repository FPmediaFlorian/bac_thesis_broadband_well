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
            <div class="col-md-4 overflow-auto">
                <ul class="nav nav-tabs nav-justified" role="tablist">
                    <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#easy">Easy Mode</a></li>
                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#expert">Expert Mode</a></li>
                </ul>
                <div class="tab-content ">
                    <div id="easy" class="container tab-pane active"><br>
                        <% if (request.getAttribute("error") != null) {%>
                        <div class="row pt-2">
                            <div class="alert alert-danger text-center" role="alert">
                                <p>${error}</p>
                            </div>
                        </div>
                        <%}%>
                        <form action="EasyMapRequest" method="post" accept-charset="ISO-8859-1" id="easyForm">
                            <div class="form-group">
                                <label for="easyCurrentLocation">Current Location</label>
                                <input type="text" class="form-control" id="easyCurrentLocation" name="CurrentLocation" placeholder="Current Location" required>
                                <small id="easyCurrentLocationHelp" class="form-text text-muted">Please fill in a street in Vienna!</small>
                            </div>

                            <div class="btn-group btn-group-toggle pb-3" role="group" data-toggle="buttons" aria-label="transport" required>
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="transport-option" value="BIKE" id="transport-bicycle" autocomplete="off" checked> <i class="fas fa-bicycle"></i> Bicycle
                                </label>
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="transport-option" value="PUBLIC" id="transport-public" autocomplete="off"> <i class="fas fa-bus"></i> Public Transport
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="transport-option" value="CAR" id="transport-car" autocomplete="off"> <i class="fas fa-car"></i> Car
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="transport-option" value="FOOT" id="transport-walk" autocomplete="off"> <i class="fas fa-walking"></i> Walk
                                </label>
                            </div>

                            <div class="form-group">
                                <label for="easyDownloadSize">Filesize</label>
                                <div class="input-group">
                                    <input type="number" class="form-control" id="easyDownloadSize" name="downloadSize" placeholder="Filesize eg. 100 GB" required>
                                    <div class="input-group-append">
                                        <select class="browser-default custom-select" name="sizeAppend">
                                            <option value="MB">MB</option>
                                            <option value="GB" selected>GB</option>
                                            <option value="TB">TB</option>
                                        </select>
                                    </div>
                                </div>
                                <!--https://getbootstrap.com/docs/4.0/components/input-group/#buttons-with-dropdowns-->
                                <small id="easyDownloadSizeHelp" class="form-text text-muted">Please fill in the size of file you want to download!</small>
                            </div>

                            <div class="btn-group btn-group-toggle pb-3" role="group" data-toggle="buttons" aria-label="transport"  id="updownloadRadio" required>
                                <label class="btn btn-secondary active">
                                    <input type="radio"  name="updownloadRadio" id="easyUploadRadio" value="upload" checkedautocomplete="off" checked> <i class="fas fa-upload"></i>Upload File
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="updownloadRadio" id="easyDownloadRadio" value="download"> <i class="fas fa-download"></i> Download File
                                </label>
                            </div>
                            <input type="hidden" id="streamspeed" name="streamspeed" value="">
                            <br/>
                            <button type="button" class="btn btn-primary" onclick="speedtestAndSubmitEasy();">Get my results!</button>
                        </form>
                        <div class="alert alert-info" id="loadingAnimation" style="display: none;">
                            <div class="spinner-border text-primary" role="status" >
                                <span class="sr-only">Performing Speedtest</span>
                            </div>
                            <b id="loadingText">Performing Speedtest</b>
                        </div>
                    </div>
                    <div id="expert" class="container tab-pane fade " style="height: 100%"><br>
                        <% if (request.getAttribute("error") != null) {%>
                        <div class="row pt-2">
                            <div class="alert alert-danger text-center" role="alert">
                                <p>${error}</p>
                            </div>
                        </div>
                        <%}%>
                        <form action="ExpertMapRequest" method="post" accept-charset="ISO-8859-1" id="expertForm">
                            <div class="form-group">
                                <label for="expertCurrentLocation">Current Location</label>
                                <input type="text" class="form-control" id="expertCurrentLocation" name="CurrentLocation" placeholder="Current Location" required>
                                <small id="expertCurrentLocationHelp" class="form-text text-muted">Please fill in your current location!</small>
                            </div>
                            <div class="btn-group btn-group-toggle pb-3" role="group" data-toggle="buttons" aria-label="transport" required>
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="transport-option" value="BIKE" id="transport-bicycle-expert" autocomplete="off" checked> <i class="fas fa-bicycle"></i> Bicycle
                                </label>
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="transport-option" value="PUBLIC" id="transport-public-expert" autocomplete="off"> <i class="fas fa-bus"></i> Public Transport
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="transport-option" value="CAR" id="transport-car-expert" autocomplete="off"> <i class="fas fa-car"></i> Car
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="transport-option" value="FOOT" id="transport-walk-expert" autocomplete="off"> <i class="fas fa-walking"></i> Walk
                                </label>
                            </div>

                            <div class="form-group">
                                <label for="easyDownloadSize">Filesize</label>
                                <div class="input-group">
                                    <input type="number" class="form-control" id="expertDownloadSize" name="downloadSize" placeholder="Filesize eg. 100 GB" required>
                                    <div class="input-group-append">
                                        <select class="browser-default custom-select" name="sizeAppend">
                                            <option value="MB">MB</option>
                                            <option value="GB" selected>GB</option>
                                            <option value="TB">TB</option>
                                        </select>
                                    </div>
                                </div>
                                <!--https://getbootstrap.com/docs/4.0/components/input-group/#buttons-with-dropdowns-->
                                <small id="expertDownloadSizeHelp" class="form-text text-muted">Please fill in the size of file you want to download!</small>
                            </div>

                            <div class="btn-group btn-group-toggle pb-3" role="group" data-toggle="buttons" aria-label="transport" required>
                                <label class="btn btn-secondary active">
                                    <input type="radio"  name="updownloadRadio" id="uploadRadio" value="upload" checkedautocomplete="off" onclick="labelUpload()" checked> <i class="fas fa-upload"></i>Upload File
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="updownloadRadio" id="downloadRadio" value="download" onclick="labelDownload()"> <i class="fas fa-download"></i> Download File
                                </label>
                            </div>

                            <div class="btn-group btn-group-toggle pb-3" role="group" data-toggle="buttons" aria-label="transport" required>
                                <label class="btn btn-secondary active">
                                    <input  type="radio" name="internetAccess" id="speedtestRadio" value="speedtest" onclick="hideSpeedform()" checked> Perform a Speedtest
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="internetAccess" id="manualInputSpeedRadio" value="speedform" onclick="showSpeedForm()">  i know my Up- & Downstreams
                                </label>
                            </div>

                            <div class="form-group pl-3" id="manualInputStream">
                                <label for="streamInput" id="streamLabel" style="display: none;">Upload in MBit/s</label>
                                <input type="number" class="form-control" id="streamInput" name="streamspeed" placeholder=" eg. 20 MBit/s" style="display: none;" required>
                                <small id="expertDownstreamHelp" class="form-text text-muted" style="display: none;">Please fill in your Stream in MBit/s!</small>
                            </div>


                            <div class="form-group">
                                <label for="expertRequestedBBW">Select your desired BBW</label>
                                <select class="form-control" id="expertRequestedBBW" name="desiredBBW">
                                    ${bbwOption}
                                </select>
                                <small id="expertRequestedBBWHelp" class="form-text text-muted">Please select your desiered BBW!</small>
                            </div>

                            <br/>
                            <button type="button" class="btn btn-primary" onclick="speedtestAndSubmitExpert()">Get my results!</button>

                        </form>
                        <div class="alert alert-info" id="loadingAnimationExpert" style="display: none;">
                            <div class="spinner-border text-primary" role="status" >
                                <span class="sr-only">Performing Speedtest</span>
                            </div>
                            <b id="loadingTextExpert">Performing Speedtest</b>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-8" id="map1"></div>

        </div>
    </div>
</section>

<script>

    /**
     * Scripts for map and routing
     */
    var map;

    // Create map
    createMap();

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

    //Creates BBW Marker dynamically
    ${createMarker}

    function selectBBW(e) {
        //TODO Set selected BBW

    }
</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
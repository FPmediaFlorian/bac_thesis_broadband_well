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

                <ul class="nav nav-tabs nav-justified" role="tablist">
                    <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#easy">Easy Mode</a></li>
                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#expert">Expert Mode</a></li>
                </ul>

                <div class="tab-content "> <!--TODO ValidationControl Clientside-->
                    <div id="easy" class="container tab-pane active"><br>
                        <form action="EasyMapRequest" method="post">
                            <div class="form-group">
                                <label for="easyCurrentLocation">Current Location</label>
                                <input type="text" class="form-control" id="easyCurrentLocation" name="easyCurrentLocation" placeholder="Current Location">
                                <small id="easyCurrentLocationHelp" class="form-text text-muted">Please fill in your current location!</small>
                            </div>
                            <div class="form-group">
                                <label for="easyInternetAccess">Select your internet connection</label>
                                <select class="form-control" id="easyInternetAccess" name="easyInternetAccess">
                                    <option>Mobile Connection</option>
                                    <option>Fixed Broadband Connection</option>
                                    <option>i dont know</option>
                                </select>
                                <small id="easyInternetAccessHelp" class="form-text text-muted">Please select your internet connection!</small>
                            </div>
                            <div class="form-group">
                                <label for="easyDownloadSize">Filesize in Gigabyte</label>
                                <input type="number" class="form-control" id="easyDownloadSize" name="easyDownloadSize" placeholder="Filesize eg. 100 GB">
                                <small id="easyDownloadSizeHelp" class="form-text text-muted">Please fill in the size of file to download in GB!</small>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="updownloadRadio" id="easyUploadRadio" value="upload" checked>
                                <label class="form-check-label" for="easyUploadRadio">
                                    Upload File
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="updownloadRadio" id="easyDownloadRadio" value="download">
                                <label class="form-check-label" for="easyDownloadRadio">
                                    Download File
                                </label>
                            </div>
                            <!--TODO implement option to Choose vehicle-->
                            <br/>
                            <button type="submit" class="btn btn-primary">Get my results!</button>
                        </form>
                    </div>
                    <div id="expert" class="container tab-pane fade " style="height: 100%"><br>
                        <form style="height: 100%">
                            <div class="form-group">
                                <label for="expertCurrentLocation">Current Location</label>
                                <input type="text" class="form-control" id="expertCurrentLocation" placeholder="Current Location">
                                <small id="expertCurrentLocationHelp" class="form-text text-muted">Please fill in your current location!</small>
                            </div>
                            <label for="speedtestRadio">Information about your internet connection</label>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="expertInternetAccess" id="speedtestRadio" value="speedtest" onclick="toogleSpeedForm()" checked>
                                <label class="form-check-label" for="speedtestRadio">
                                    perform Speedtest <small id="expertPreformSpeedtest" class="form-text text-muted">Takes about 10 seconds!</small>
                                    <!-- TODO Perform speedtest on submit-->
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="expertInternetAccess" id="speedformRadio" value="speedform" onclick="toogleSpeedForm()">
                                <label class="form-check-label" for="speedformRadio">
                                    i know my Up- & Downstreams <!--TODO Forms einrücken-->
                                </label>
                            </div>
                            <div class="form-group">
                                <label for="expertUpstream" id="expertUpstreamLabel" style="display: none;">Upstream in kb/s</label>
                                <input type="number" class="form-control" id="expertUpstream" placeholder="Upstream eg. 20 kb/s" style="display: none;">
                                <small id="expertUpstreamHelp" class="form-text text-muted" style="display: none;">Please fill in your Upstream in kb/s!</small>
                            </div>
                            <div class="form-group">
                                <label for="expertUpstream" id="expertDownstreamLabel" style="display: none;">Downstream in kb/s</label>
                                <input type="number" class="form-control" id="expertDownstream" placeholder="Downstream eg. 20 kb/s" style="display: none;">
                                <small id="expertDownstreamHelp" class="form-text text-muted" style="display: none;">Please fill in your Downstream in kb/s!</small>
                            </div>

                            <div class="form-group">
                                <label for="expertDownloadSize">Filesize in Gigabyte</label>
                                <input type="number" class="form-control" id="expertDownloadSize" placeholder="Filesize eg. 100 GB">
                                <small id="expertDownloadSizeHelp" class="form-text text-muted">Please fill in the size of file to download in GB!</small>
                            </div>
                            <div class="form-group">
                                <label for="expertRequestedBBW">Select your desired BBW</label>
                                <select class="form-control" id="expertRequestedBBW">
                                    <option>autoselect the nearest</option>
                                    <option>Währingerstraße 29, 1090 Wien</option>
                                    <option>Laudongasse 15,  1080 Wien</option>
                                </select>
                                <small id="expertRequestedBBWHelp" class="form-text text-muted">Please select your desiered BBW, for autoselect choose the nearest</small>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="updownloadRadio" id="expertUploadRadio" value="option1" checked>
                                <label class="form-check-label" for="expertUploadRadio">
                                    Upload File
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="updownloadRadio" id="expertDownloadRadio" value="option2">
                                <label class="form-check-label" for="expertDownloadRadio">
                                    Download File
                                </label>
                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary">Get my results!</button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-lg-9" id="map1"></div>

        </div>
    </div>
</section>

<script>
    var map;

    // Create map
    createMap();


    //Add markers to map
    addBBWmarkersWpopups();

</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
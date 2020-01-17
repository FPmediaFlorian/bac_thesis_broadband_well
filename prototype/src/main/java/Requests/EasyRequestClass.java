package Requests;

import Helper.*;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.graphhopper.directions.api.client.ApiClient;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.GeocodingApi;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.zip.DataFormatException;

public class EasyRequestClass {
    private String currentLocation;
    private EasyConnectionType connectionType;
    private double downloadSize;
    private boolean upload;
    private LatLng geocode;
    //Default static down and upstreams
    //Values are from netztest.at statistics
    //Period: 6 Months, visitet 17.1.2020
    private static double downMobile = 42 ;
    private static double downFixed = 27 ; //Value is not satisfying
    private static double downUnknown = 5;
    private static double upMobile = 13 ;
    private static double upFixed = 10 ; //Value is not satisfying
    private static double upUnknown = 5;



    public EasyRequestClass(String currentLocation, String connectionType, double downloadSize, boolean upload) {
        this.currentLocation = currentLocation;
        this.downloadSize = downloadSize;
        this.upload = upload;
        geocode = null;
        //Set connectiontype
        setConnectionType(connectionType);
    }


    /**
     * Loaded Constructor
     * @param currentLocation Adress on the current location
     * @param downloadSize Filesize to be downloaded in GB
     * @param upload Up- or Download, true -> upload, false -> download
     */
    public EasyRequestClass(String currentLocation, double downloadSize, boolean upload) {
        this.currentLocation = currentLocation;
        this.downloadSize = downloadSize;
        this.upload = upload;
        geocode = null;
    }

    /**
     * Empty Contructor
     */
    public EasyRequestClass() {
        geocode=null;
    }

    /**
     * Calculates the Donloadtime of the request with given Down- & Upstreams
     * @return Returns downloadtime in seconds
     * @throws DataFormatException
     */
    public double getDownloadtime() {
        DownloadCalculator downloadCalculator = new DownloadCalculator();
        downloadCalculator.setSize(downloadSize, SizeSuffix.GB);
        double downloadTime=0;
        //TODO in Downloadcalculator auslagern
        if(upload){
            //UPLOAD
            switch (connectionType){
                case MOBILE:
                    downloadCalculator.setStream(upMobile);
                    break;
                case FIXEDBB:
                    downloadCalculator.setStream(upFixed);
                    break;
                case UNKNOWN:
                    downloadCalculator.setStream(upUnknown);
                    break;
            }
        } else {
            //DOWNLOAD
            switch (connectionType){
                case MOBILE:
                    downloadCalculator.setStream(downMobile);
                    break;
                case FIXEDBB:
                    downloadCalculator.setStream(downFixed);
                    break;
                case UNKNOWN:
                    downloadCalculator.setStream(downUnknown);
                    break;
            }
        }
        return downloadCalculator.getDownloadtimeSec();
    }

    /**
     * Calculates the Donloadtime of the request with BBW Down- & Upstreams
     * @return Returns downloadtime in seconds
     */
    public double getBBWdownloadtime(){
        DownloadCalculator downloadCalculator = new DownloadCalculator();
        downloadCalculator.setSize(downloadSize,SizeSuffix.GB);
        return downloadCalculator.getBBWdownloadtimeSec();
    }

    /**
     * Matches the given Address with a Geolocationcode using Graphhopper API
     * @return Returns a GeocodingLocation object which contains lat & lng
     */
    public LatLng getGeolocation(){

        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(APIKeys.OPENCAGEAPI);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(getCurrentLocation());
        request.setRestrictToCountryCode("at"); // restrict results to a specific country
        //request.setBounds(48.4423, 16.8155, 47.9706, 15.8107); // restrict results to a geographic bounding box (southWestLng, southWestLat, northEastLng, northEastLat)

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result


        /*
        GeocodingApi geocoding = new GeocodingApi();
        ApiClient client=new ApiClient().setDebugging(false);
        client.setApiKey(APIKeys.GHAPI);
        geocoding.setApiClient(client);
        GeocodingLocation loc0;
        try {
            GeocodingResponse geocodingResponse = geocoding.getGeocode(getCurrentLocation(), "de", 5, false, false, "48.20809,16.37156", null);
            loc0 = geocodingResponse.getHits().get(0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        if(geocode==null){
            geocode = new LatLng(loc0.getPoint().getLat(),loc0.getPoint().getLng());
        }else {
            geocode.setLat(loc0.getPoint().getLat());
            geocode.setLng(loc0.getPoint().getLng());
        }


         */

        if(geocode==null){
            geocode = new LatLng(firstResultLatLng.getLat(),firstResultLatLng.getLng());
        }else {
            geocode.setLat(firstResultLatLng.getLat());
            geocode.setLng(firstResultLatLng.getLng());
        }


        return geocode;
    }


    public double getTraveltime() throws ApiException {
        BBW nearestBBW = null;
        double traveltime = 0;

        RoutingApi routing = new RoutingApi();
        ApiClient client = new ApiClient().setDebugging(true);
        client.setApiKey(APIKeys.GHAPI);
        if(geocode==null) geocode = getGeolocation();
        for(BBW bbw : BBWList.BBW_LIST){
            //Calculate time with Graphhopper APIkey
            RouteResponse rsp = routing.getRoute(Arrays.asList(geocode.getLat()+","+geocode.getLng(), bbw.getLatLngString()),
                    Arrays.<String>asList(), Arrays.<String>asList(),
                    VehicleProfileId.CAR, "en", true, Arrays.<String>asList(), false,
                    true, true, false, true, null, false,
                    "fastest", Collections.<Integer>emptyList(), null, null, null,
                    null, null, null, null, null,
                    null, null);
            if(nearestBBW==null) {
                traveltime = rsp.getPaths().get(0).getTime();
                nearestBBW = bbw;
            }else {
                if(traveltime > rsp.getPaths().get(0).getTime()){
                    traveltime = rsp.getPaths().get(0).getTime();
                    nearestBBW = bbw;
                }
            }

        }

        return traveltime;
    }


    /** Getter and Setter */
    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public EasyConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        if(connectionType.equals("Mobile Connection")){
            this.connectionType=EasyConnectionType.MOBILE;
        }else {
            if(connectionType.equals("Fixed Broadband Connection")){
                this.connectionType=EasyConnectionType.FIXEDBB;
            }else {
                this.connectionType=EasyConnectionType.UNKNOWN;
            }
        }
    }

    public double getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(double downloadSize) {
        this.downloadSize = downloadSize;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    @Override
    public String toString() {
        return "EasyRequest{" +
                "currentLocation='" + currentLocation + '\'' +
                ", connectionType=" + connectionType +
                ", downloadSize=" + downloadSize +
                ", upload=" + upload +
                '}';
    }
}

package Requests;

import Exceptions.InvalidAddressExeption;
import Helper.*;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.graphhopper.directions.api.client.ApiClient;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.*;

import org.apache.log4j.Logger;
import servlets.EasyMapRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class EasyRequestClass {
    private static Logger LOGGER = Logger.getLogger(EasyMapRequest.class.getName());


    private String currentLocation;
    private EasyConnectionType connectionType;
    private double downloadSize;
    private boolean upload;
    private LatLng geocode;
    private BBW nearestBBW;
    private VehicleProfileId transportForm;


    /**
     * Loaded Constructor
     * @param currentLocation Address on the current location
     * @param connectionType type of connection
     * @param downloadSize Filesize to be downloaded in GB
     * @param upload Up- or Download, true -> upload, false -> download
     */
    public EasyRequestClass(String currentLocation, String connectionType, double downloadSize, boolean upload, String transportOption) {
        this.currentLocation = currentLocation;
        this.downloadSize = downloadSize;
        this.upload = upload;
        geocode = null;
        nearestBBW=null;
        //Set connectiontype
        setConnectionType(connectionType);
        //Set Transportform
        setTransportFormFromString(transportOption);
    }


    /**
     * Calculates the Donloadtime of the request with given Down- & Upstreams
     * @return Returns downloadtime in seconds
     */
    public double getDownloadtime() {
        DownloadCalculator downloadCalculator = new DownloadCalculator();
        downloadCalculator.setSize(downloadSize, SizeSuffix.GB);
        if(upload){
            //UPLOAD
            double upMobile = 13;
            //Value is not satisfying
            double upFixed = 10;
            double upUnknown = 5;
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
            //Default static down and upstreams
            //Values are from netztest.at statistics
            //Period: 6 Months, visitet 17.1.2020
            double downMobile = 42;
            //Value is not satisfying
            double downFixed = 27;
            double downUnknown = 5;
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
     * Matches the given Address with a Geocode using OpenCage API
     * @return Returns LatLng object which contains lat & lng
     */
    public LatLng getGeolocation() throws InvalidAddressExeption {
        geocode = GeoCalculator.getGeocode(getCurrentLocation());
        if(geocode==null){
            throw new InvalidAddressExeption("The Addres could not be Found!");
            //TODO Error, invalid Address
        }
        return geocode;
    }

    /**
     * Calculates the Traveltime to the nearest BBW
     * @return Return Traveltime in seconds
     */
    public double getTravelTime(){
        if (geocode==null) {
            try {
                getGeolocation();
            } catch (InvalidAddressExeption invalidAddressExeption) {
                LOGGER.error(invalidAddressExeption.getMessage());
            }
        }
        nearestBBW = GeoCalculator.getTravetime(geocode, transportForm);
        return nearestBBW.getTravelTime();
    }

    /**
     * gets the nearest BBW
     * @return Returns the nearest BBW
     */
    public BBW getNearestBBW() {
        if(nearestBBW==null){
            getTravelTime();
        }
        return nearestBBW;
    }

    /**
     * Creates the Desicion based in various other calculations.
     * @return return s String with HTML input to be shown an the mapResult Page
     */
    public String getDesicionResponse() {
        //TODO Auslagern in eigene Klasse
        //TODO implement desicion response
        double downloadtimeHome;
        double downloadtimeBBW ;
        double totalTraveltime;
        double totalTimeForBBW;

        downloadtimeHome = getDownloadtime(); //seconds
        downloadtimeBBW = getBBWdownloadtime(); //seconds
        totalTraveltime = TimeUnit.MILLISECONDS.toSeconds(2*(long) nearestBBW.getTravelTime()); //seconds

        totalTimeForBBW = totalTraveltime+downloadtimeBBW;

        StringBuilder sb = new StringBuilder();
        if (totalTimeForBBW<downloadtimeHome){
            //Go to BBW
            sb.append(DesictionFeedbackHTML.getPositiveFeedback(nearestBBW,(long)totalTraveltime,(long) downloadtimeBBW,(long)totalTimeForBBW,(long)downloadtimeHome));
        }else{
            //Download @Home
            sb.append(DesictionFeedbackHTML.getNegativeFeedback(nearestBBW,(long)totalTraveltime,(long) downloadtimeBBW,(long)totalTimeForBBW,(long)downloadtimeHome));
            sb.append("Negative message to come");
        }
        return sb.toString();
    }


    /** Getter and Setter */
    /**
     * Returns Current Location
     * @return Current Location as a String
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the current Location
     * @param currentLocation String with a Streetname in Vienna
     */
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * sets the Connection Type based on String input
     * @param connectionType connectiontype as a String, Allowed: "Mobile Connection","Fixed Broadband Connection" and "Unknown"
     */
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


    /**
     * Sets the downloadsize in GB
     * @param downloadSize Downloadsize in GB
     */
    public void setDownloadSize(double downloadSize) {
        this.downloadSize = downloadSize;
    }

    /**
     * Sets the upload token
     * @param upload true -> Upload, false -> Download
     */
    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public VehicleProfileId getTransportForm() {
        return transportForm;
    }

    public void setTransportFormFromString(String transportOption) {
        if(transportOption.equals("bycicle")){
            transportForm=VehicleProfileId.BIKE;
        }else {
            if(transportOption.equals("car")){
                transportForm=VehicleProfileId.CAR;
            }else {
                if(transportOption.equals("walk")){
                    transportForm=VehicleProfileId.FOOT;
                }else{
                    transportForm= VehicleProfileId.CAR;
                }

            }
        }
    }
    public void setTransportForm(VehicleProfileId transportForm) {

        this.transportForm = transportForm;
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

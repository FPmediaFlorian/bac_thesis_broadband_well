package Requests;

import Exceptions.InvalidAddressExeption;
import Helper.*;
import com.graphhopper.directions.api.client.model.*;

import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class EasyRequestClass {
    private static Logger LOGGER = Logger.getLogger(EasyRequestClass.class.getName());


    private String currentLocation;
    private double downloadSize;
    private LatLng geocode;
    private BBW nearestBBW;
    private TransportForm transportForm;
    private double streamSpeed;
    private SizeSuffix sizeSuffix;



    /**
     * Loaded Constructor
     * @param currentLocation Address on the current location
     * @param streamSpeed speed of down or uploadspeed
     * @param downloadSize Filesize to be downloaded in GB
     */
    public EasyRequestClass(String currentLocation, double streamSpeed , double downloadSize, SizeSuffix sizeSuffix,  TransportForm transportOption) {
        this.currentLocation = currentLocation;
        this.downloadSize = downloadSize;
        this.streamSpeed =streamSpeed;
        this.sizeSuffix = sizeSuffix;
        geocode = null;
        nearestBBW=null;
        //Set Transportform
        transportForm=transportOption;
    }


    /**
     * Calculates the Donloadtime of the request with given Down- & Upstreams
     * @return Returns downloadtime in seconds
     */
    public double getDownloadtime() {
        DownloadCalculator downloadCalculator = new DownloadCalculator(downloadSize,streamSpeed,sizeSuffix);
        return downloadCalculator.getDownloadtimeSec();
    }

    /**
     * Calculates the Donloadtime of the request with BBW Down- & Upstreams
     * @return Returns downloadtime in seconds
     */
    public double getBBWdownloadtime(){
        DownloadCalculator downloadCalculator = new DownloadCalculator();
        downloadCalculator.setSize(downloadSize,sizeSuffix);
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
            //TODO Log Error, invalid Address
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
        nearestBBW = GeoCalculator.getNearestBBWsetTraveltime(geocode, transportForm);
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
        if(Math.abs(totalTimeForBBW-downloadtimeHome)<300){
            //Neutral, doesn't really matter
            sb.append(DesictionFeedbackHTML.getNeutralFeedback(nearestBBW,(long)totalTraveltime,(long) downloadtimeBBW,(long)totalTimeForBBW,(long)downloadtimeHome,streamSpeed,downloadSize,sizeSuffix));
        }else {
            if (totalTimeForBBW < downloadtimeHome) {
                //Go to BBW
                sb.append(DesictionFeedbackHTML.getPositiveFeedback(nearestBBW, (long) totalTraveltime, (long) downloadtimeBBW, (long) totalTimeForBBW, (long) downloadtimeHome, streamSpeed,downloadSize,sizeSuffix));
            } else {
                //Download @Home
                sb.append(DesictionFeedbackHTML.getNegativeFeedback(nearestBBW, (long) totalTraveltime, (long) downloadtimeBBW, (long) totalTimeForBBW, (long) downloadtimeHome,streamSpeed,downloadSize,sizeSuffix));
            }
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

    public TransportForm getTransportForm() {
        return transportForm;
    }







}

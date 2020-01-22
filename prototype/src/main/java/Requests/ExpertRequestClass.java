package Requests;

import Exceptions.InvalidAddressExeption;
import Helper.*;
import com.graphhopper.directions.api.client.model.VehicleProfileId;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class ExpertRequestClass {
    private static Logger LOGGER = Logger.getLogger(ExpertRequestClass.class.getName());
    private String currentLocation;
    private double downloadSize;
    private LatLng geocode;
    private BBW desiredBBW;
    private VehicleProfileId transportForm;
    private double streamSpeed;
    private SizeSuffix sizeSuffix;

    public ExpertRequestClass(String currentLocation, double streamSpeed , double downloadSize, SizeSuffix sizeSuffix, String transportOption, int desiredBBW) throws InvalidAddressExeption {
        this.currentLocation=currentLocation;
        this.downloadSize = downloadSize;
        this.streamSpeed =streamSpeed;
        this.sizeSuffix = sizeSuffix;
        geocode = null;
        //getGeolocation
        calculateGeocode();

        //Set Transportforf
        setTransportFormFromString(transportOption);
        if (desiredBBW==-1){
            findNearestBBW();
        } else {
            this.desiredBBW=BBW.BBW_LIST.get(desiredBBW);
            this.desiredBBW.setTravelTime(GeoCalculator.calculateTraveltime(geocode,transportForm,this.desiredBBW));
        }
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
    private LatLng calculateGeocode() throws InvalidAddressExeption {
        geocode = GeoCalculator.getGeocode(getCurrentLocation());
        if(geocode==null){
            throw new InvalidAddressExeption("The Addres could not be Found!");
            //TODO Error, invalid Address
        }
        return geocode;
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
        totalTraveltime = TimeUnit.MILLISECONDS.toSeconds(2*(long) desiredBBW.getTravelTime()); //seconds

        totalTimeForBBW = totalTraveltime+downloadtimeBBW;

        StringBuilder sb = new StringBuilder();
        if (totalTimeForBBW<downloadtimeHome){
            //Go to BBW
            sb.append(DesictionFeedbackHTML.getPositiveFeedback(desiredBBW,(long)totalTraveltime,(long) downloadtimeBBW,(long)totalTimeForBBW,(long)downloadtimeHome));
        }else{
            //Download @Home
            sb.append(DesictionFeedbackHTML.getNegativeFeedback(desiredBBW,(long)totalTraveltime,(long) downloadtimeBBW,(long)totalTimeForBBW,(long)downloadtimeHome));
            sb.append("Negative message to come");
        }
        return sb.toString();
    }

    private void findNearestBBW(){
        //TODO implement logic to find bbw
        desiredBBW = GeoCalculator.getNearestBBWsetTraveltime(geocode,transportForm);
    }

    /** Getter and Setter */
    /**
     * Returns Current Location
     * @return Current Location as a String
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    public VehicleProfileId getTransportForm() {
        return transportForm;
    }

    public LatLng getGeocode() {
        return geocode;
    }

    public BBW getDesiredBBW() {
        return desiredBBW;
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

}

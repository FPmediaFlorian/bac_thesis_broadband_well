package Requests;

import Exceptions.InvalidAddressExeption;
import Helper.*;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class ExpertRequestClass {
    private static Logger LOGGER = Logger.getLogger(ExpertRequestClass.class.getName());
    private String currentLocation;
    private double downloadSize;
    private LatLng geocode;
    private BBW desiredBBW;
    private TransportForm transportForm;
    private double streamSpeed;
    private SizeSuffix sizeSuffix;


    /**
     * Loaded constructor
     *
     * @param currentLocation Address of the current Location
     * @param streamSpeed     Streamspeed in MBit/s
     * @param downloadSize    Size of the File to download
     * @param sizeSuffix      Size suffix for size of File
     * @param transportOption form of transportation
     * @param desiredBBW      the desired BBW asl int in List if there is one, else -1, nearest BBW is calculated
     * @throws Exception Throws Exception if geocode or nearest BBW could not be calculated.
     */
    public ExpertRequestClass(String currentLocation, double streamSpeed, double downloadSize, SizeSuffix sizeSuffix, TransportForm transportOption, int desiredBBW) throws Exception {
        this.currentLocation = currentLocation;
        this.downloadSize = downloadSize;
        this.streamSpeed = streamSpeed;
        this.sizeSuffix = sizeSuffix;
        geocode = null;

        //getGeolocation
        calculateGeocode();

        //Set Transportforf
        transportForm = transportOption;
        //setTransportFormFromString(transportOption);
        if (desiredBBW == -1) {
            findNearestBBW();
        } else {
            this.desiredBBW = BBW.BBW_LIST.get(desiredBBW);
            this.desiredBBW.setTravelTime(GeoCalculator.calculateTraveltime(geocode, transportForm, this.desiredBBW));
        }
    }

    /**
     * Calculates the Donloadtime of the request with given Down- and Upstreams
     *
     * @return Returns downloadtime in seconds
     */
    public double getDownloadtime() {
        DownloadCalculator downloadCalculator = new DownloadCalculator(downloadSize, streamSpeed, sizeSuffix);
        return downloadCalculator.getDownloadtimeSec();
    }

    /**
     * Calculates the Donloadtime of the request with BBW Down- and Upstreams
     *
     * @return Returns downloadtime in seconds
     */
    public double getBBWdownloadtime() {
        DownloadCalculator downloadCalculator = new DownloadCalculator();
        downloadCalculator.setSize(downloadSize, sizeSuffix);
        return downloadCalculator.getBBWdownloadtimeSec();
    }

    /**
     * Matches the given Address with a Geocode using HERE map API
     *
     * @return Returns LatLng object which contains lat and lng
     */
    private LatLng calculateGeocode() throws InvalidAddressExeption {
        geocode = GeoCalculator.getGeocode(getCurrentLocation());
        if (geocode == null) {
            LOGGER.error("Address could not be found");
            throw new InvalidAddressExeption("The Address could not be Found!");
        }
        return geocode;
    }

    /**
     * Creates the Decision based in various other calculations.
     *
     * @return return s String with HTML input to be shown an the mapResult Page
     */
    public String getDesicionResponse() {
        double downloadtimeHome;
        double downloadtimeBBW;
        double totalTraveltime;
        double totalTimeForBBW;

        downloadtimeHome = getDownloadtime(); //seconds
        downloadtimeBBW = getBBWdownloadtime(); //seconds
        totalTraveltime = TimeUnit.MILLISECONDS.toSeconds(2 * (long) desiredBBW.getTravelTime()); //seconds

        totalTimeForBBW = totalTraveltime + downloadtimeBBW;

        StringBuilder sb = new StringBuilder();
        if (Math.abs(totalTimeForBBW - downloadtimeHome) < 300) {
            //Neutral, doesn't really matter
            sb.append(DesictionFeedbackHTML.getNeutralFeedback(desiredBBW, (long) totalTraveltime, (long) downloadtimeBBW, (long) totalTimeForBBW, (long) downloadtimeHome, streamSpeed, downloadSize, sizeSuffix));
        } else {
            if (totalTimeForBBW < downloadtimeHome) {
                //Go to BBW
                sb.append(DesictionFeedbackHTML.getPositiveFeedback(desiredBBW, (long) totalTraveltime, (long) downloadtimeBBW, (long) totalTimeForBBW, (long) downloadtimeHome, streamSpeed, downloadSize, sizeSuffix));
            } else {
                //Download @Home
                sb.append(DesictionFeedbackHTML.getNegativeFeedback(desiredBBW, (long) totalTraveltime, (long) downloadtimeBBW, (long) totalTimeForBBW, (long) downloadtimeHome, streamSpeed, downloadSize, sizeSuffix));
            }
        }
        return sb.toString();
    }

    /**
     * calculates nearest BBW if necessary
     */
    private void findNearestBBW() {
        desiredBBW = GeoCalculator.getNearestBBWsetTraveltime(geocode, transportForm);
    }

    /** Getter and Setter */
    /**
     * Returns Current Location
     *
     * @return Current Location as a String
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * returns form of Transport
     *
     * @return form of transport
     */
    public TransportForm getTransportForm() {
        return transportForm;
    }

    /**
     * returns geocode as LatLng
     *
     * @return geocode as LatLang
     */
    public LatLng getGeocode() {
        return geocode;
    }

    /**
     * returns desired BBW
     *
     * @return desired BBW
     */
    public BBW getDesiredBBW() {
        return desiredBBW;
    }


}

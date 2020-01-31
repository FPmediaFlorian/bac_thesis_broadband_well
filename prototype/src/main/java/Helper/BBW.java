package Helper;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BBW {
    public static List<BBW> BBW_LIST = null;
    private static Logger LOGGER = Logger.getLogger(BBW.class.getName());
    String name;
    LatLng latLng;
    String descMarker;
    String info;
    double travelTime;
    WLstation nearestPTStation;
    WLstation currentLocationPTstation;

    /**
     * Constructor for BBW
     * @param name Name of BBW
     * @param latLng coordinates of BBW
     * @param descMarker description for Map popup
     * @param info Info for Frontend
     * @param nearestPTStation nearest Public Transport station
     */
    public BBW(String name, LatLng latLng, String descMarker, String info, WLstation nearestPTStation) {
        this.name = name;
        this.latLng = latLng;
        this.descMarker = descMarker;
        this.info = info;
        this.nearestPTStation = nearestPTStation;

    }

    /**
     * Default constructor
     */
    public BBW() {
    }

    /**
     * Initializes the BBW List.
     * Reads all infos from BBW_config in config dir
     */
    public static void initBBWList() {

        BBW_LIST = new ArrayList<>();
        JSONParser parser = new JSONParser();
        BBW bbw;
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("/Users/florianpichlmann/projects/bac_thesis_broadband_well/prototype/src/config/BBW_config.json"));//"src/config/BBW_config.json"));




            for (Object o : a) {
                JSONObject bb = (JSONObject) o;
                JSONObject ll = (JSONObject) bb.get("latLng");
                JSONObject ptStation = (JSONObject) bb.get("nearestPTStation");
                bbw = new BBW(bb.get("name").toString(), new LatLng((double) ll.get("lat"), (double) ll.get("lng")), bb.get("descMarker").toString(), bb.get("info").toString(), new WLstation(ptStation.get("name").toString(), Integer.parseInt(ptStation.get("id").toString()), new LatLng((double)ptStation.get("lat"),(double)ptStation.get("lng"))));
                BBW_LIST.add(bbw);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //LOGGER.debug(BBW_LIST.toString());
    }

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name sets name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get latLng
     * @return latLng
     */
    public LatLng getLatLng() {
        return latLng;
    }

    /**
     * Set latLng
     * @param latLng sets latLng
     */
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    /**
     * Get descMarker
     * @return descMarker
     */
    public String getDescMarker() {
        return descMarker;
    }

    /**
     * Get info
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Set info
     * @param info sets info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Get String of latlang
     * @return String of latlang
     */
    public String getLatLngString() {
        return latLng.getLatLng();
    }

    /**
     * Get travelTime
     * @return travelTime
     */
    public double getTravelTime() {
        return travelTime;
    }

    /**
     * Set travelTime
     * @param travelTime sets travelTime
     */
    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    /**
     * Get nearestPTStation
     * @return nearestPTStation
     */
    public WLstation getNearestPTStation() {
        return nearestPTStation;
    }

    /**
     * Set nearestPTStation
     * @param nearestPTStation sets nearestPTStation
     */
    public void setNearestPTStation(WLstation nearestPTStation) {
        this.nearestPTStation = nearestPTStation;
    }

    /**
     * Get currentLocationPTstation
     * @return nacurrentLocationPTstationme
     */
    public WLstation getCurrentLocationPTstation() {
        return currentLocationPTstation;
    }

    /**
     * Set currentLocationPTstation
     * @param currentLocationPTstation sets currentLocationPTstation
     */
    public void setCurrentLocationPTstation(WLstation currentLocationPTstation) {
        this.currentLocationPTstation = currentLocationPTstation;
    }

    @Override
    public String toString() {
        return "BBW{" +
                "name='" + name + '\'' +
                ", latLng=" + latLng +
                ", descMarker='" + descMarker + '\'' +
                ", info='" + info + '\'' +
                ", travelTime=" + travelTime +
                ", nearestPTStation=" + nearestPTStation +
                '}';
    }
}

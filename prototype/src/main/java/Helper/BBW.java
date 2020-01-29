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
    String nearestPTStation;

    public BBW(String name, LatLng latLng, String descMarker, String info, String nearestPTStation) {
        this.name = name;
        this.latLng = latLng;
        this.descMarker = descMarker;
        this.info = info;
        this.nearestPTStation = nearestPTStation;

    }

    public BBW() {
    }

    public static void initBBWList() {

        BBW_LIST = new ArrayList<>();
        JSONParser parser = new JSONParser();
        BBW bbw;
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("/Users/florianpichlmann/projects/bac_thesis_broadband_well/prototype/src/config/BBW_config.json"));//"src/config/BBW_config.json"));


            for (Object o : a) {
                JSONObject bb = (JSONObject) o;
                JSONObject ll = (JSONObject) bb.get("latLng");
                bbw = new BBW(bb.get("name").toString(), new LatLng((double) ll.get("lat"), (double) ll.get("lng")), bb.get("descMarker").toString(), bb.get("info").toString(), bb.get("nearestPTStation").toString().replace("\"", ""));
                BBW_LIST.add(bbw);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //LOGGER.debug(BBW_LIST.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getDescMarker() {
        return descMarker;
    }

    public void setDescMarker(String descMarker) {
        this.descMarker = descMarker;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLatLngString() {
        return latLng.getLatLng();
    }

    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    public String getNearestPTStation() {
        return nearestPTStation;
    }

    public void setNearestPTStation(String nearestPTStation) {
        this.nearestPTStation = nearestPTStation;
    }

    @Override
    public String toString() {
        return "BBW{" +
                "name='" + name + '\'' +
                ", latLng=" + latLng +
                ", descMarker='" + descMarker + '\'' +
                ", info='" + info + '\'' +
                ", travelTime=" + travelTime +
                '}';
    }
}

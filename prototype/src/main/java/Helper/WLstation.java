package Helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WLstation {
    String Name;
    int ID;
    LatLng latLng;

    /**
     * Constructor for Wiener Linien station
     * @param name Name of the station
     * @param ID WL specific id
     * @param latLng Latitude and Longitude of the station
     */
    public WLstation(String name, int ID, LatLng latLng) {
        Name = name;
        this.ID = ID;
        this.latLng = latLng;
    }

    /**
     * initializes a List of the Stations from a csv
     * @return returns a List of Stations
     */
    public static List<WLstation> initWLstationList() {
        List<WLstation> retList = new ArrayList<>();

        String csvFile = "/Users/florianpichlmann/projects/bac_thesis_broadband_well/prototype/src/config/WLstops.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] station = line.split(cvsSplitBy);

                retList.add(new WLstation(station[1], Integer.valueOf(station[0]), new LatLng(Double.valueOf(station[2]), Double.valueOf(station[3]))));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return retList;
    }

    /**
     * returns Name of the station
     * @return Name of the station
     */
    public String getName() {
        return Name;
    }

    /**
     * sets Name of the station
     * @param name sets Name of the station
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * returns WL specific id
     * @return WL specific id
     */
    public int getID() {
        return ID;
    }

    /**
     * sets WL specific id
     * @param ID WL specific id
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * returns Latitude and Longitude of the station
     * @return Latitude and Longitude of the station
     */
    public LatLng getLatLng() {
        return latLng;
    }

    /**
     * sets Latitude and Longitude of the station
     * @param latLng Latitude and Longitude of the station
     */
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    /**
     * Builds readable string ob WLstation Object
     * @return readable string ob WLstation Object
     */
    @Override
    public String toString() {
        return "WLstation{" +
                "Name='" + Name + '\'' +
                ", ID=" + ID +
                ", latLng=" + latLng.toString() +
                '}';
    }
}

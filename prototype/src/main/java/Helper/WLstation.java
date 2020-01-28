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


    public WLstation(String name, int ID, LatLng latLng) {
        Name = name;
        this.ID = ID;
        this.latLng = latLng;
    }

    public static List<WLstation> initWLstationList(){
        List<WLstation> retList = new ArrayList<>();

        String csvFile = "/Users/florianpichlmann/projects/bac_thesis_broadband_well/prototype/src/main/java/WLstops.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] station = line.split(cvsSplitBy);

                retList.add(new WLstation(station[1], Integer.valueOf(station[0]),new LatLng(Double.valueOf(station[2]),Double.valueOf(station[3]))));

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "WLstation{" +
                "Name='" + Name + '\'' +
                ", ID=" + ID +
                ", latLng=" + latLng.toString() +
                '}';
    }
}

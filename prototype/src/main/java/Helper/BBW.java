package Helper;

import java.util.ArrayList;
import java.util.List;

public class BBW {
    public static final List<BBW> BBW_LIST = new ArrayList<BBW>(){{
        add(new BBW("Währingerstraße 29", new LatLng(48.220110, 16.356120),"BBW in Währingerstraße 29 <br>Computer Science Institute <br>University of Vienna", "Soon to come!"));
        add(new BBW("Volkskundemuseum", new LatLng(48.2131498, 16.3505518),"BBW in  Laudongasse 15–19<br>Volkskundemuseum Wien", "<p>Das Volkskundemuseum Wien ist eines der großen internationalen ethnographischen Museen mit\n" +
                "                            umfangreichen Sammlungen zur Volkskunst sowie zu historischen und gegenwärtigen Alltagskulturen\n" +
                "                            Europas. Wir zeigen Ausstellungen zu vielfältigen Themen des Zusammenlebens in einer sich ständig\n" +
                "                            verändernden Welt.</p>\n" +
                "                        <p><strong>Volkskundemuseum Wien</strong></p>\n" +
                "                        Laudongasse 15–19, 1080 Wien<br>\n" +
                "                        <strong>Öffnungszeiten</strong>\n" +
                "                        <u>BBW:</u>\n" +
                "                        24/7\n" +
                "                        <u>Museum:</u>\n" +
                "                        Di bis So, 10.00 bis 17.00 Uhr<br>\n" +
                "                        Do, 10.00 bis 20.00 Uhr<br>\n" +
                "                        <u>Bibliothek:</u>\n" +
                "                        Di bis Fr, 9.00 bis 12.00 Uhr<br>\n" +
                "                        <u>Hildebrandt Café:</u>\n" +
                "                        Di bis So, 10.00 bis 18.00 Uhr<br>\n" +
                "                        Do, 10.00 bis 20.00 Uhr<br>\n" +
                "                        <u>Mostothek:</u>\n" +
                "                        Di, ab 17.00 Uhr"));
        //add(new BBW("Reindorfgasse 42", new LatLng(48.190830,16.330270),"BBW in  Reindorfgasse 42<br>Wohnung Florian", "Soon to come!"));

    }};



    String name;
    LatLng latLng;
    String descMarker;
    String info;
    double travelTime;

    public BBW(String name, LatLng latLng, String descMarker, String info) {
        this.name = name;
        this.latLng = latLng;
        this.descMarker = descMarker;
        this.info = info;

    }



    public String getName() {
        return name;
    }

    public LatLng getLatLng(){
        return latLng;
    }

    public String getDescMarker() {
        return descMarker;
    }

    public String getInfo() { return info; }

    public String getLatLngString() {
        return latLng.getLatLng();
    }

    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }
}

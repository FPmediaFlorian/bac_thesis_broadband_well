package Helper;

public class BBW {
    String name;
    LatLng latLng;

    public BBW(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }



    public String getName() {
        return name;
    }

    public LatLng getLatLng(){
        return latLng;
    }

    public String getLatLngString() {
        return latLng.getLatLng();
    }
}

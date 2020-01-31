package Helper;

public class LatLng {
    double lat;
    double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng() {
    }

    public String getLatLng() {
        StringBuilder sb = new StringBuilder();
        sb.append(lat);
        sb.append(",");
        sb.append(lng);
        return sb.toString();
    }

    public double calculateDistance(LatLng latLng) {
        return Math.sqrt(Math.pow(this.getLat() - latLng.getLat(), 2) + Math.pow(this.getLng() - latLng.getLng(), 2));
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LatLng{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

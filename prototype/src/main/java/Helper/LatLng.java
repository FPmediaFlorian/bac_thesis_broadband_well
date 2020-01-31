package Helper;

public class LatLng {
    double lat;
    double lng;

    /**
     * loaded constructor
     * @param lat Latitude of coordinate
     * @param lng Logitude of coordinate
     */
    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * default constructor
     */
    public LatLng() {
    }

    /**
     * Returns the coordinates separated by a comma
     * @return coordinates separated by a comma
     */
    public String getLatLngAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(lat);
        sb.append(",");
        sb.append(lng);
        return sb.toString();
    }

    /**
     * calculates the distance between this LatLng object and the committed
     * @param latLng second Latlng object
     * @return distance between coordinates as double
     */
    public double calculateDistance(LatLng latLng) {
        return Math.sqrt(Math.pow(this.getLat() - latLng.getLat(), 2) + Math.pow(this.getLng() - latLng.getLng(), 2));
    }

    /**
     * get the Latitude
     * @return Latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * sets the Latitude
     * @param lat Latitude
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * get the Longitude
     * @return Longitude
     */
    public double getLng() {
        return lng;
    }

    /**
     * sets the longitude
     * @param lng longitude
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * Builds readable string ob LatLng Object
     * @return readable string ob WLstation Object
     */
    @Override
    public String toString() {
        return "LatLng{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

package Requests;

import Helper.APIKeys;
import com.graphhopper.directions.api.client.ApiClient;
import com.graphhopper.directions.api.client.api.GeocodingApi;
import com.graphhopper.directions.api.client.model.GeocodingLocation;
import com.graphhopper.directions.api.client.model.GeocodingResponse;

public class EasyRequest {
    private String currentLocation;
    private EasyConnectionType connectionType;
    private double downloadSize;
    private boolean upload;

    public EasyRequest(String currentLocation, EasyConnectionType connectionType, double downloadSize, boolean upload) {
        this.currentLocation = currentLocation;
        this.connectionType = connectionType;
        this.downloadSize = downloadSize;
        this.upload = upload;
    }


    public EasyRequest(String currentLocation, double downloadSize, boolean upload) {
        this.currentLocation = currentLocation;
        this.downloadSize = downloadSize;
        this.upload = upload;
    }

    public EasyRequest() {}


    public GeocodingLocation getGeolocation(){
        GeocodingApi geocoding = new GeocodingApi();
        ApiClient client=new ApiClient().setDebugging(false);
        client.setApiKey(APIKeys.GHAPI);
        geocoding.setApiClient(client);
        GeocodingLocation loc0;
        try {
            GeocodingResponse geocodingResponse = geocoding.getGeocode(getCurrentLocation(), "de", 5, false, false, "48.20809,16.37156", null);
            loc0 = geocodingResponse.getHits().get(0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return loc0;
    }


    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public EasyConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(EasyConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public double getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(double downloadSize) {
        this.downloadSize = downloadSize;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    @Override
    public String toString() {
        return "EasyRequest{" +
                "currentLocation='" + currentLocation + '\'' +
                ", connectionType=" + connectionType +
                ", downloadSize=" + downloadSize +
                ", upload=" + upload +
                '}';
    }
}

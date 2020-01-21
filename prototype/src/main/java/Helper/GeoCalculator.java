package Helper;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.graphhopper.directions.api.client.ApiClient;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.RouteResponse;
import com.graphhopper.directions.api.client.model.VehicleProfileId;
import org.apache.log4j.Logger;
import servlets.EasyMapRequest;

import java.util.Arrays;
import java.util.Collections;

public class GeoCalculator {
    private static Logger LOGGER = Logger.getLogger(EasyMapRequest.class.getName());


    public static LatLng getGeocode(String currentLocation){

        LatLng geocode = null;

        //Get geocode from API
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(APIKeys.OPENCAGEAPI);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(currentLocation);
        request.setRestrictToCountryCode("at"); // restrict results to a specific country
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result

        try {
            geocode=new LatLng(firstResultLatLng.getLat(),firstResultLatLng.getLng());
        } catch (NullPointerException e){
            LOGGER.error(e.getMessage());
        }


       return geocode;
    }

    public static BBW getTravetime(LatLng geocode, VehicleProfileId transportForm){
        //TODO Auslagern in eigene Klasse
        BBW nearestBBW= null ;
        double traveltime = 0;

        RoutingApi routing = new RoutingApi();
        routing.setApiClient(createClient());

        for (BBW bbw : BBW.BBW_LIST) {
            //Calculate time with Graphhopper APIkey
            RouteResponse rsp = null;
            try {
                rsp = routing.getRoute(Arrays.asList(geocode.getLat() + "," + geocode.getLng(), bbw.getLatLngString()),
                        Collections.<String>emptyList(), Collections.<String>emptyList(),
                        transportForm, "en", true, Collections.<String>emptyList(), false,
                        true, true, false, true, null, false,
                        "fastest", Collections.<Integer>emptyList(), null, null, null,
                        null, null, null, null, null,
                        null, null);
            } catch (ApiException e) {
                LOGGER.error(e.getMessage());
            }

            try {
                traveltime=rsp.getPaths().get(0).getTime();
            }catch (NullPointerException e){
                LOGGER.error(e.getMessage());
            }

            if (nearestBBW == null) {
                nearestBBW = bbw;
                nearestBBW.setTravelTime(traveltime);
            } else {
                if (nearestBBW.getTravelTime() > traveltime) {
                    nearestBBW = bbw;
                    nearestBBW.setTravelTime(traveltime);
                }
            }
        }
        return nearestBBW;
    }


    private static ApiClient createClient() {

        //TODO Auslagern in eigene Klasse
        ApiClient client = new ApiClient().setDebugging(true);
        client.setApiKey(System.getProperty("graphhopper.key", "5108091a-dea6-4eb3-88e3-f7049ad14659"));
        return client;
    }
}

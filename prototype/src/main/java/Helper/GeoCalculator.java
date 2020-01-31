package Helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.graphhopper.directions.api.client.ApiClient;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.RouteResponse;
import com.graphhopper.directions.api.client.model.VehicleProfileId;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import servlets.EasyMapRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

public class GeoCalculator {
    private static Logger LOGGER = Logger.getLogger(EasyMapRequest.class.getName());

    /**
     * Calculates the Latitude and Longitude from given Address by calling the HERE MAP API
     * @param currentLocation Address of the current location
     * @return Returns a LatLng Object with the coordinates of the given address
     */
    private static LatLng getLocation(String currentLocation) {
        //Parts of implementation from https://github.com/venkatramanm/common/blob/167e39dfa35b3b495dec90f0e2812f6296086b4d/src/main/java/com/venky/geo/GeoCoder.java
        try {
            Map<String, String> params = new HashMap<>();
            params.put("searchtext", currentLocation);
            params.put("mapview", "48.3752,16.1200;48.0163,16.6199");
            params.put("apiKey", "yWoJnTL5n9Lxjcjo-SOOrteMkQfC3clriQ8wGV6Mt1c");


            String url = "https://geocoder.ls.hereapi.com/6.2/geocode.json?";

            URL u = new URL(url+getParamsString(params));
            URLConnection connection = u.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            JSONObject doc = (JSONObject) JSONValue.parse(new InputStreamReader(connection.getInputStream()));
            JSONObject place = (JSONObject) doc.get("Response");
            JSONArray views = (JSONArray) place.get("View");
            JSONObject position = null;

            if (views != null && !views.isEmpty()) {
                JSONObject view = (JSONObject) views.get(0);
                JSONObject location = (JSONObject) ((JSONObject) ((JSONArray) view.get("Result")).get(0)).get("Location");
                position = (JSONObject) ((JSONArray)location.get("NavigationPosition")).get(0);
                }

            if (position != null) {
                LOGGER.info("URL:" + url);
                return new LatLng((Double) position.get("Latitude"), (Double) position.get("Longitude"));
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }


    /**
     * Calculates the Latitude and Longitude from given Address by calling the HERE MAP API
     * @param currentLocation Address of the current location
     * @return Returns a LatLng Object with the coordinates of the given address
     */
    public static LatLng getGeocode(String currentLocation) {
        //New implementation with here Maps API
        return getLocation(currentLocation);


        //Old implementation with OpenCageMaps
        /*
        //Get geocode from API
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(APIKeys.OPENCAGEAPI);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(currentLocation);
        request.setRestrictToCountryCode("at"); // restrict results to a specific country
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result

        try {
            geocode = new LatLng(firstResultLatLng.getLat(), firstResultLatLng.getLng());
        } catch (NullPointerException e) {
            LOGGER.error(e.getMessage());
        }

         */

    }

    /**
     * Calculates the Traveltime from the current Location (geocode) to the nearest BBW with given Transportform
     * @param geocode Geocode as LatLng Object from current Location
     * @param transportForm Form of transport as TransportForm Object (enum)
     * @param bbw nearest or desired BBW
     * @return returns the Traveltime as double
     * @throws Exception Throws exception if the Travel getPublicTransportTraveltime Mathod throws Error
     */
    public static double calculateTraveltime(LatLng geocode, TransportForm transportForm, BBW bbw) throws Exception {
        if(transportForm == TransportForm.PUBLIC){
            bbw.setCurrentLocationPTstation(getNearestPTStation(geocode));
            return getPublicTransportTraveltime(bbw);
        }else {
            double traveltime = 0;
            RouteResponse rsp = null;
            RoutingApi routing = new RoutingApi();
            routing.setApiClient(createGHClient());

            try {
                rsp = routing.getRoute(Arrays.asList(geocode.getLat() + "," + geocode.getLng(), bbw.getLatLngString()),
                        Collections.<String>emptyList(), Collections.<String>emptyList(),
                        VehicleProfileId.valueOf(transportForm.toString()), "en", true, Collections.<String>emptyList(), false,
                        true, true, false, true, null, false,
                        "fastest", Collections.<Integer>emptyList(), null, null, null,
                        null, null, null, null, null,
                        null, null);
            } catch (ApiException e) {
                LOGGER.error(e.getMessage());
            }

            try {
                traveltime = rsp.getPaths().get(0).getTime();
            } catch (NullPointerException e) {
                LOGGER.error(e.getMessage());
            }
            return traveltime;
        }
    }

    /**
     * Calculates the nearest BBW to given geocode in consideration of the transport form
     * @param geocode Geocode as LatLng Object from current Location
     * @param transportForm Form of transport as TransportForm Object (enum)
     * @return returns nearest BBW which has the Traveltime in it
     */
    public static BBW getNearestBBWsetTraveltime(LatLng geocode, TransportForm transportForm) {
        BBW nearestBBW = null;
        double traveltime = 0;
        if (transportForm != TransportForm.PUBLIC) {
            //Normal routing

            RoutingApi routing = new RoutingApi();
            routing.setApiClient(createGHClient());

            for (BBW bbw : BBW.BBW_LIST) {
                //Calculate time with Graphhopper APIkey
                RouteResponse rsp = null;
                try {
                    rsp = routing.getRoute(Arrays.asList(geocode.getLat() + "," + geocode.getLng(), bbw.getLatLngString()),
                            Collections.<String>emptyList(), Collections.<String>emptyList(),
                            VehicleProfileId.valueOf(transportForm.toString()), "en", true, Collections.<String>emptyList(), false,
                            true, true, false, true, null, false,
                            "fastest", Collections.<Integer>emptyList(), null, null, null,
                            null, null, null, null, null,
                            null, null);
                } catch (ApiException e) {
                    LOGGER.error(e.getMessage());
                }

                try {
                    traveltime = rsp.getPaths().get(0).getTime();
                } catch (NullPointerException e) {
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
        } else {
            //public Transport routing
            double distance = 0;
            //Get nearest BBW by distance
            for (BBW bbw : BBW.BBW_LIST) {
                if (nearestBBW == null) {
                    nearestBBW = bbw;
                    distance = bbw.latLng.calculateDistance(geocode);
                }
                if (distance > bbw.latLng.calculateDistance(geocode)) {
                    distance = bbw.latLng.calculateDistance(geocode);
                    nearestBBW = bbw;
                }
            }
            try {
                nearestBBW.setCurrentLocationPTstation(getNearestPTStation(geocode));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                traveltime = getPublicTransportTraveltime(nearestBBW);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //convert time to milliseconds (desicion response requires Milliseconds)
            nearestBBW.setTravelTime(traveltime * 1000);

        }


        return nearestBBW;
    }

    /**
     * Calculates the traveltime from Station A (nearest Station @current Location) to Station B (nearest Station @BBW) by calling the Wiener Linien API
     * @param bbw Nearest BBW
     * @return returns traveltime in second
     * @throws Exception
     */
    public static double getPublicTransportTraveltime(BBW bbw) throws Exception {
        double secRet = 0;

        URL url = new URL("http://www.wienerlinien.at/ogd_routing/XML_TRIP_REQUEST2?");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");


        Map<String, String> params = new HashMap<>();
        params.put("locationServerActive", "1");
        params.put("outputFormat", "JSON");
        params.put("locationServerActive", "1");
        params.put("type_origin", "any");
        params.put("name_origin", String.valueOf(bbw.getCurrentLocationPTstation().getID()));
        params.put("type_destination", "any");
        params.put("name_destination", Integer.toString(bbw.getNearestPTStation().getID()));

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream((con.getOutputStream()));
        out.writeBytes(getParamsString(params));
        out.flush();
        out.close();

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        //LOGGER.debug("Responsecode: "+1);
        //LOGGER.debug("Response: "+ content.toString());

        if (status == 200) {
            //Request OK
            JsonObject object = new JsonParser().parse(content.toString()).getAsJsonObject();
            //LOGGER.debug("Parsed JSON:" +object.toString());

            JsonArray trips = (JsonArray) object.get("trips");
            JsonObject trip1 = trips.get(0).getAsJsonObject().get("trip").getAsJsonObject();
            //LOGGER.debug("First Trip:" + trip1.toString());
            String time1 = trip1.get("duration").toString();

            String[] arr = time1.replace("\"", "").split(":");
            double hr = Double.valueOf(arr[0]);
            double min = Double.valueOf(arr[1]);

            //LOGGER.debug("Time First Trip: "+hr +"hr "+ min+"min ");

            secRet = hr * 3600 + min * 60;

        }
        return secRet;
    }

    /**
     * Finds nearest BBW station.
     * @param latLng Latitude & Longitude of current Location
     * @return Returns the nearest station to the given coordinates
     */
    public static WLstation getNearestPTStation(LatLng latLng) {
        List<WLstation> stationList = WLstation.initWLstationList();
        WLstation minStation = null;
        double minDist = 0;

        for (WLstation wlStation : stationList) {
            if (minStation == null) {
                minStation = wlStation;
                minDist = wlStation.latLng.calculateDistance(latLng);
            } else {
                if (minDist > wlStation.latLng.calculateDistance(latLng)) {
                    minDist = wlStation.latLng.calculateDistance(latLng);
                    minStation = wlStation;
                }
            }
        }
        return minStation;
    }


    /**
     *
     * @param params Map with all parameters for a REST request
     * @return Returns parameters streamlined & URL encoded as String
     * @throws UnsupportedEncodingException
     */
    private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * creates Graphhopper Client
     * @return returns created graphhopper Client
     */
    private static ApiClient createGHClient() {
        ApiClient client = new ApiClient().setDebugging(true);
        client.setApiKey(System.getProperty("graphhopper.key", "5108091a-dea6-4eb3-88e3-f7049ad14659"));
        return client;
    }
}

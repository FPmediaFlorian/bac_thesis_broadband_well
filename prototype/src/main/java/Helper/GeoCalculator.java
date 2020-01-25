package Helper;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.graphhopper.directions.api.client.ApiClient;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.RouteResponse;
import com.graphhopper.directions.api.client.model.VehicleProfileId;


import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import servlets.EasyMapRequest;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

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


    public static double calculateTraveltime(LatLng geocode, VehicleProfileId transportForm, BBW bbw){
        double traveltime = 0;
        RouteResponse rsp = null;
        RoutingApi routing = new RoutingApi();
        routing.setApiClient(createClient());

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
        return traveltime;
    }

    public static BBW getNearestBBWsetTraveltime(LatLng geocode, VehicleProfileId transportForm){
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

    public static double getPublicTransportTraveltime(LatLng geocode, BBW bbw) throws Exception{
        double secRet=0;

        URL url = new URL("http://www.wienerlinien.at/ogd_routing/XML_TRIP_REQUEST2?");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");



        Map<String,String> params = new HashMap<>();
        params.put("locationServerActive","1");
        params.put("outputFormat","JSON");
        params.put("locationServerActive","1");
        params.put("type_origin","any");
        params.put("name_origin",getNearestPTStation(geocode));
        params.put("type_destination","any");
        params.put("name_destination",bbw.getNearestPTStation());

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

        if (status==200){
            //Request OK
            JsonObject object = new JsonParser().parse(content.toString()).getAsJsonObject();
            //LOGGER.debug("Parsed JSON:" +object.toString());

            JsonArray trips = (JsonArray) object.get("trips");
            JsonObject trip1 =  trips.get(0).getAsJsonObject().get("trip").getAsJsonObject();
            //LOGGER.debug("First Trip:" + trip1.toString());
            String time1 = trip1.get("duration").toString();

            String[] arr =time1.replace("\"","").split(":");
            double hr = Double.valueOf(arr[0]);
            double min = Double.valueOf(arr[1]);

            //LOGGER.debug("Time First Trip: "+hr +"hr "+ min+"min ");

            secRet = hr*3600+min*60;

        }

        return secRet;
    }





    private static String getNearestPTStation(LatLng latLng) throws Exception{
        URL url = new URL("http://www.wienerlinien.at/ogd_routing/XML_TRIP_REQUEST2?");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");



        Map<String,String> params = new HashMap<>();
        params.put("type_origin","coord");
        params.put("name_origin",latLng.getLng()+":"+latLng.getLat()+":WGS84");

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

        //LOGGER.debug("Responsecode: "+status);
        //LOGGER.debug("Response: "+ content.toString());

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(content.toString())));

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        //itdRequest
        //itdTripRequest
        //itdOdv
        //itdOdv
        XPathExpression expr = xpath.compile("//itdRequest/itdTripRequest/itdOdv/itdOdvAssignedStops/@value");


        // Evaluate expression result on XML document
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        List<String> values=new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            values.add(nodes.item(i).getNodeValue());
        }

        for(String s : values){
            LOGGER.debug("String: "+s);
        }


        //LOGGER.debug("Element:"+doc.getElementsByTagName("time"));

        /*
        NodeList itdRequest = doc.getElementsByTagName("itdTripRequest");

        for(int i = 0; i< itdRequest.getLength();i++){
            LOGGER.debug("Node "+i+": "+itdRequest.item(i).toString());
        }

         */




        /*
        InputStream is = new ByteArrayInputStream(content.toString().getBytes());

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(is);

        Node rootElement = d.getDocumentElement();
        //LOGGER.debug(nodeToString(rootElement));

        NodeList nodeList = rootElement.getChildNodes();
        LOGGER.debug(nodeList.toString());
*/
        String ret = "60201288";
        return ret;
    }

    private static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception
    {
        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();

        // Create XPath object
        XPath xpath = xpathFactory.newXPath();

        List<String> values = new ArrayList<>();
        try
        {
            // Create XPathExpression object
            XPathExpression expr = xpath.compile(xpathExpression);


            // Evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getNodeValue());
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return values;
    }

    private static String nodeToString(Node node) throws Exception{
        StringWriter sw = new StringWriter();

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));

        return sw.toString();
    }

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

    private static ApiClient createClient() {

        //TODO Auslagern in eigene Klasse
        ApiClient client = new ApiClient().setDebugging(true);
        client.setApiKey(System.getProperty("graphhopper.key", "5108091a-dea6-4eb3-88e3-f7049ad14659"));
        return client;
    }
}

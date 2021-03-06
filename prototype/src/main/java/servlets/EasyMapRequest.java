package servlets;

import Exceptions.InvalidAddressExeption;
import Helper.APIKeys;
import Helper.BBW;
import Helper.SizeSuffix;
import Helper.TransportForm;
import Requests.EasyRequestClass;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EasyMapRequest extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(EasyMapRequest.class.getName());

    /**
     * Handles Post requests from the Easy form in the Frontend
     * Calculates all needed variables for a qualified decision Response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TransportForm transportOption = TransportForm.valueOf(request.getParameter("transport-option"));

        //Create request instance
        EasyRequestClass easyRequest = new EasyRequestClass(request.getParameter("CurrentLocation"),
                Double.valueOf(request.getParameter("streamspeed")),
                Double.valueOf(request.getParameter("downloadSize")),
                SizeSuffix.valueOf(request.getParameter("sizeAppend")),
                transportOption);

        //LOGGER.info("Request: " + easyRequest.toString());

        String geocode = null;
        try {
            geocode = easyRequest.getGeolocation().getLatLngAsString();
        } catch (InvalidAddressExeption ex) {
            request.setAttribute("error", "Your Address could not be found! Please try another spelling or address!");
            request.getRequestDispatcher("BuildMap").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.debug("Geocoding:" + geocode);
        LOGGER.debug("Downloadtime: " + easyRequest.getDownloadtime());
        LOGGER.debug("Downloadtime BBW: " + easyRequest.getBBWdownloadtime());


        BBW nearestBBW = easyRequest.getNearestBBW();

        //Set Parameters for Webform
        request.setAttribute("latlngStart", geocode);
        request.setAttribute("latlngDest", nearestBBW.getLatLng().getLatLngAsString());
        request.setAttribute("ghApiKey", APIKeys.GHAPI);
        request.setAttribute("vehicle", easyRequest.getTransportForm().toString());
        request.setAttribute("desicionResponse", easyRequest.getDesicionResponse());
        if (transportOption == TransportForm.PUBLIC) {
            request.setAttribute("stationA", nearestBBW.getCurrentLocationPTstation().getLatLng().getLatLngAsString());
            request.setAttribute("stationB", nearestBBW.getNearestPTStation().getLatLng().getLatLngAsString());
        } else {
            request.setAttribute("stationA", "not");
            request.setAttribute("stationB", "not");
        }


        request.getRequestDispatcher("/mapResult.jsp").forward(request, response);
    }

    /**
     * forwards every Get request to the Build map endpoint
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("BuildMap").forward(request, response);
    }
}

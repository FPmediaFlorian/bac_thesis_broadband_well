package servlets;

import Exceptions.InvalidAddressExeption;
import Helper.APIKeys;
import Helper.BBW;
import Helper.SizeSuffix;
import Helper.TransportForm;
import Requests.ExpertRequestClass;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpertMapRequest extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ExpertMapRequest.class.getName());

    /**
     * Handles Post requests from the Expert form in the Frontend
     * Calculates all needed variables for a qualified decision Response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpertRequestClass expertRequest = null;

        TransportForm transportOption = TransportForm.valueOf(request.getParameter("transport-option"));

        try {
            expertRequest = new ExpertRequestClass(request.getParameter("CurrentLocation"),
                    Double.valueOf(request.getParameter("streamspeed")),
                    Double.valueOf(request.getParameter("downloadSize")),
                    SizeSuffix.valueOf(request.getParameter("sizeAppend")),
                    transportOption,
                    Integer.valueOf(request.getParameter("desiredBBW")));

        } catch (InvalidAddressExeption ex) {
            request.setAttribute("error", "Your Address could not be found! Please try another spelling or address!");
            request.getRequestDispatcher("BuildMap").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.debug("Geocoding:" + expertRequest.getGeocode().getLatLngAsString());
        LOGGER.debug("Downloadtime: " + expertRequest.getDownloadtime());
        LOGGER.debug("Downloadtime BBW: " + expertRequest.getBBWdownloadtime());

        BBW nearestBBW = expertRequest.getDesiredBBW();


        //Set Parameters for Webform
        request.setAttribute("latlngStart", expertRequest.getGeocode().getLatLngAsString());
        request.setAttribute("latlngDest", nearestBBW.getLatLng().getLatLngAsString());
        request.setAttribute("ghApiKey", APIKeys.GHAPI);
        request.setAttribute("vehicle", expertRequest.getTransportForm().toString());
        request.setAttribute("desicionResponse", expertRequest.getDesicionResponse());
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
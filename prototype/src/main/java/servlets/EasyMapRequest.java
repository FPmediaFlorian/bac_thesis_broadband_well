package servlets;

import Helper.APIKeys;
import Helper.LatLng;
import Requests.EasyRequestClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class EasyMapRequest extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(EasyMapRequest.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean upload = false;
        //Check downloadmode
        if(request.getParameter("updownloadRadio").equals("upload")) upload=true;
        //Create request instance
        EasyRequestClass easyRequest = new EasyRequestClass(request.getParameter("easyCurrentLocation"),request.getParameter("easyInternetAccess"),Double.valueOf(request.getParameter("easyDownloadSize")),upload);

        //LOGGER.info("Request: " + easyRequest.toString());
        LOGGER.info("Geocoding:"+easyRequest.getGeolocation().getLatLng());
        LOGGER.info("Downloadtime: "+easyRequest.getDownloadtime());
        LOGGER.info("Downloadtime BBW: "+easyRequest.getBBWdownloadtime());


        LatLng returnGeolocation = easyRequest.getGeolocation();
        //Set Parameters for Webform
        request.setAttribute("latlngStart", returnGeolocation.getLatLng());
        request.setAttribute("latlngDest","48.2200482,16.3562356");
        request.setAttribute("route",true);
        request.setAttribute("ghApiKey", APIKeys.GHAPI);

        request.getRequestDispatcher("/mapResult.jsp").forward(request, response);
    }
}

package servlets;

import Exceptions.InvalidAddressExeption;
import Helper.APIKeys;
import Helper.LatLng;
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
            geocode = easyRequest.getGeolocation().getLatLng();
        } catch (InvalidAddressExeption ex){
            request.setAttribute("error","Your Address could not be found! Please try another spelling or address!");
            request.getRequestDispatcher("BuildMap").forward(request, response);
            return;
        }

        LOGGER.debug("Geocoding:"+geocode);
        LOGGER.debug("Downloadtime: "+easyRequest.getDownloadtime());
        LOGGER.debug("Downloadtime BBW: "+easyRequest.getBBWdownloadtime());


        //Set Parameters for Webform
        request.setAttribute("latlngStart", geocode);
        request.setAttribute("latlngDest",easyRequest.getNearestBBW().getLatLng().getLatLng());
        request.setAttribute("ghApiKey", APIKeys.GHAPI);
        request.setAttribute("vehicle", easyRequest.getTransportForm().toString());
        request.setAttribute("desicionResponse",easyRequest.getDesicionResponse());
        if(transportOption==TransportForm.PUBLIC){
            request.setAttribute("stationA","not"); // TODO get stationA coordinates
            request.setAttribute("stationB","not"); // TODO get stationB coordinates
        }else{
            request.setAttribute("stationA","not");
            request.setAttribute("stationB","not");
        }



        request.getRequestDispatcher("/mapResult.jsp").forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("BuildMap").forward(request, response);
    }
}

package servlets;

import Exceptions.InvalidAddressExeption;
import Helper.APIKeys;
import Helper.SizeSuffix;
import Requests.EasyRequestClass;
import Requests.ExpertRequestClass;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpertMapRequest extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ExpertMapRequest.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpertRequestClass expertRequest;
        try {
                expertRequest= new ExpertRequestClass(request.getParameter("CurrentLocation"),
                Double.valueOf(request.getParameter("streamspeed")),
                Double.valueOf(request.getParameter("downloadSize")),
                SizeSuffix.valueOf(request.getParameter("sizeAppend")),
                request.getParameter("transport-option"),
                Integer.valueOf(request.getParameter("desiredBBW")));
        } catch (InvalidAddressExeption ex){
            request.setAttribute("error","Your Address could not be found! Please try another spelling or address!");
            request.getRequestDispatcher("BuildMap").forward(request, response);
            return;
        }

        LOGGER.debug("Geocoding:"+expertRequest.getGeocode().getLatLng());
        LOGGER.debug("Downloadtime: "+expertRequest.getDownloadtime());
        LOGGER.debug("Downloadtime BBW: "+expertRequest.getBBWdownloadtime());


        //Set Parameters for Webform
        request.setAttribute("latlngStart", expertRequest.getGeocode().getLatLng());
        request.setAttribute("latlngDest",expertRequest.getDesiredBBW().getLatLng().getLatLng());
        request.setAttribute("ghApiKey", APIKeys.GHAPI);
        request.setAttribute("vehicle", expertRequest.getTransportForm().toString());
        request.setAttribute("desicionResponse",expertRequest.getDesicionResponse());
        request.setAttribute("stationA","not");
        request.setAttribute("stationB","not");



        request.getRequestDispatcher("/mapResult.jsp").forward(request, response);

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("BuildMap").forward(request, response);
    }

}
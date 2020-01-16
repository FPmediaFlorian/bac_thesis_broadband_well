package servlets;

import Helper.APIKeys;
import Requests.EasyConnectionType;
import Requests.EasyRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class EasyMapRequest extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(EasyMapRequest.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String connectionType = request.getParameter("easyInternetAccess");
        boolean upload = false;

        //Check downloadmode
        if(request.getParameter("updownloadRadio").equals("upload")) upload=true;

        //Create request instance
        EasyRequest easyRequest = new EasyRequest(request.getParameter("easyCurrentLocation"),Double.valueOf(request.getParameter("easyDownloadSize")),upload);

        //Set connectiontype
        if(connectionType.equals("Mobile Connection")){
            easyRequest.setConnectionType(EasyConnectionType.MOBILE);
        }else {
            if(connectionType.equals("Fixed Broadband Connection")){
                easyRequest.setConnectionType(EasyConnectionType.FIXEDBB);
            }else {
                easyRequest.setConnectionType(EasyConnectionType.UNKNOWN);
            }
        }

        LOGGER.info("Request: " + easyRequest.toString());

        LOGGER.info("Geocoding:"+easyRequest.getGeolocation().getPoint());

        request.setAttribute("latlngStart",easyRequest.getGeolocation().getPoint().getLat()+","+easyRequest.getGeolocation().getPoint().getLng());
        request.setAttribute("latlngDest","48.2200482,16.3562356");
        request.setAttribute("route",true);
        request.setAttribute("ghApiKey", APIKeys.GHAPI);

        request.getRequestDispatcher("/mapResult.jsp").forward(request, response);
    }
}

package servlets;

import Helper.APIKeys;
import Helper.BBW;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BuildMap extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(EasyMapRequest.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BBW.initBBWList();

        StringBuilder sb = new StringBuilder();

        int i = 1;
        for (BBW bbw:BBW.BBW_LIST){
            sb.append("var marker"+i+" = L.marker([");
            sb.append(bbw.getLatLng().getLatLng());
            sb.append("],{icon: BBWIcon}).addTo(map);");
            sb.append(System.getProperty("line.separator"));
            sb.append("marker"+i+".bindPopup('");
            sb.append(bbw.getDescMarker());
            sb.append("').openPopup;");
            sb.append(System.getProperty("line.separator"));
            i++;
        }


        request.setAttribute("createMarker",sb.toString());

        sb = new StringBuilder();
        i=0;
        sb.append("<option value=\"-1\">autoselect nearest</option>");

        for (BBW bbw:BBW.BBW_LIST){
            sb.append("<option value=\"");
            sb.append(i);
            sb.append("\">");
            sb.append(bbw.getName());
            sb.append("</option>");
            i++;
        }

        request.setAttribute("bbwOption",sb.toString());

        request.setAttribute("maptiperAPIKEY", "'"+APIKeys.MAPTILERAPI+"'");

        request.getRequestDispatcher("/map.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}

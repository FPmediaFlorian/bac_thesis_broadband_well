package servlets;

import Helper.APIKeys;
import Helper.BBW;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BuildIsochrone extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(BuildIsochrone.class.getName());

    /**
     * Gets called from clientside with an HTTP GET request.
     * Creates a List of BBWs for Frontend and passes it.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BBW.initBBWList();

        StringBuilder sb = new StringBuilder();

        for (BBW bbw : BBW.BBW_LIST) {
            sb.append("<option value=\"");
            sb.append(bbw.getLatLngString());
            sb.append("\">");
            sb.append(bbw.getName());
            sb.append("</option>");
        }

        request.setAttribute("bbwSelector", sb.toString());
        request.setAttribute("mapboxAPIKEY", "'" + APIKeys.MAPBOXAPI + "'");

        request.getRequestDispatcher("/isochroneMap.jsp").forward(request, response);
    }
}

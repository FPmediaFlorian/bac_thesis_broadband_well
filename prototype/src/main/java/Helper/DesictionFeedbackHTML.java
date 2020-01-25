package Helper;

import java.util.concurrent.TimeUnit;

public class DesictionFeedbackHTML {
    public static String getPositiveFeedback(BBW nearestBBW, long totalTraveltime, long downloadtimeBBW, long totalTimeForBBW, long downloadtimeHome){
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"alert alert-success\" role=\"alert\">\n" +
                "                    You should go to the Broadbandwell!\n" +
                "                </div> <p>The nearest BBW is at the");
        sb.append(nearestBBW.getName());
        sb.append("</p>\n" +
                "                <a class=\"btn btn-primary\" data-toggle=\"collapse\" href=\"#collapseBBWInfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseBBWInfo\">Infos about the VKM</a>\n" +
                "                <div class=\"collapse\" id=\"collapseBBWInfo\">\n" +
                "                    <div class=\"card card-body\">\n");
        sb.append(nearestBBW.getInfo());
        sb.append("                        <p>Das Volkskundemuseum Wien ist eines der großen internationalen ethnographischen Museen mit\n" +
                "                            umfangreichen Sammlungen zur Volkskunst sowie zu historischen und gegenwärtigen Alltagskulturen\n" +
                "                            Europas. Wir zeigen Ausstellungen zu vielfältigen Themen des Zusammenlebens in einer sich ständig\n" +
                "                            verändernden Welt.</p>\n" +
                "                        <p><strong>Volkskundemuseum Wien</strong></p>\n" +
                "                        Laudongasse 15–19, 1080 Wien<br>\n" +
                "                        <strong>Öffnungszeiten</strong>\n" +
                "                        <u>BBW:</u>\n" +
                "                        24/7\n" +
                "                        <u>Museum:</u>\n" +
                "                        Di bis So, 10.00 bis 17.00 Uhr<br>\n" +
                "                        Do, 10.00 bis 20.00 Uhr<br>\n" +
                "                        <u>Bibliothek:</u>\n" +
                "                        Di bis Fr, 9.00 bis 12.00 Uhr<br>\n" +
                "                        <u>Hildebrandt Café:</u>\n" +
                "                        Di bis So, 10.00 bis 18.00 Uhr<br>\n" +
                "                        Do, 10.00 bis 20.00 Uhr<br>\n" +
                "                        <u>Mostothek:</u>\n" +
                "                        Di, ab 17.00 Uhr\n" +
                "\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <br>\n" +
                "                <br>\n" +
                "                <h4>Detailed Info:</h4>\n" +
                "                <br>\n" +
                "                <div class=\"row\">\n");
        sb.append(" <table class=\"table table-borderless\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td><strong>Traveltime:</strong></td>");
        //TODO auf minuten runden, sonst passt es mit der map nicht zusammen
        sb.append("<td>" + String.format("%d min %d sec", TimeUnit.SECONDS.toMinutes(totalTraveltime), totalTraveltime - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalTraveltime))) +" (roundtrip)</td>");
        sb.append("</tr>\n" +
                "                                <tr><td><strong>Downoadtime:</strong></td>");
        sb.append("<td>"+ String.format("%d h %d min %d sec",TimeUnit.SECONDS.toHours(downloadtimeBBW),TimeUnit.SECONDS.toMinutes(downloadtimeBBW)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(downloadtimeBBW)),downloadtimeBBW-TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(downloadtimeBBW)))+"</td>");
        sb.append("</tr>\n" +
                "                                <tr class=\"table-success\">\n" +
                "                                    <td>total Time @BBW</td>");
        sb.append("<td>"+String.format("%d h %d min %d sec",TimeUnit.SECONDS.toHours(totalTimeForBBW), TimeUnit.SECONDS.toMinutes(totalTimeForBBW)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalTimeForBBW)), totalTimeForBBW - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalTimeForBBW)))+"</td>");
        sb.append("</tr>\n" +
                "                                <tr class=\"table-danger\">\n" +
                "                                    <td>total Time current Location:</td>");
        sb.append("<td>"+String.format("%d h %d min %d sec", TimeUnit.SECONDS.toHours(downloadtimeHome), TimeUnit.SECONDS.toMinutes(downloadtimeHome)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(downloadtimeHome)), downloadtimeHome - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(downloadtimeHome)))+"</td>");
        sb.append("</tr>\n" +
                "                            </tbody>\n" +
                "                        </table></div>");

        return sb.toString();
    }

    public static String getNegativeFeedback(BBW nearestBBW, long totalTraveltime, long downloadtimeBBW, long totalTimeForBBW, long downloadtimeHome){
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"alert alert-warning\" role=\"alert\">\n" +
                "                    You should download your Files @current Location!\n" +
                "                </div>\n" +
                "                <p>Your nearest BBW would be at the");
        sb.append(nearestBBW.getName());
        sb.append("</p>\n" +
                "                <a class=\"btn btn-primary\" data-toggle=\"collapse\" href=\"#collapseBBWInfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseBBWInfo\">Infos about the VKM</a>\n" +
                "                <div class=\"collapse\" id=\"collapseBBWInfo\">\n" +
                "                    <div class=\"card card-body\">");
        sb.append(nearestBBW.getInfo());
        sb.append("</div>\n" +
                "                </div>\n" +
                "                <br>\n" +
                "                <br>\n" +
                "                <h4>Detailed Info:</h4>\n" +
                "                <br>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"col-12\">\n" +
                "                        <table class=\"table table-borderless\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td><strong>Traveltime:</strong></td>");
        //TODO auf minuten runden, sonst passt es mit der map nicht zusammen
        sb.append("<td>" + String.format("%d min %d sec", TimeUnit.SECONDS.toMinutes(totalTraveltime), totalTraveltime - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalTraveltime))) +" (roundtrip)</td>");
        sb.append("</tr>\n" +
                "                                <tr><td><strong>Downoadtime:</strong></td>");
        sb.append("<td>"+ String.format("%d h %d min %d sec",TimeUnit.SECONDS.toHours(downloadtimeBBW),TimeUnit.SECONDS.toMinutes(downloadtimeBBW)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(downloadtimeBBW)),downloadtimeBBW-TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(downloadtimeBBW)))+"</td>");
        sb.append("</tr>\n" +
                "                                <tr class=\"table-danger\">\n" +
                "                                    <td>total Time @BBW</td>");
        sb.append("<td>"+String.format("%d h %d min %d sec",TimeUnit.SECONDS.toHours(totalTimeForBBW), TimeUnit.SECONDS.toMinutes(totalTimeForBBW)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalTimeForBBW)), totalTimeForBBW - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalTimeForBBW)))+"</td>");
        sb.append("</tr>\n" +
                "                                <tr class=\"table-success\">\n" +
                "                                    <td>total Time current Location:</td>");
        sb.append("<td>"+String.format("%d h %d min %d sec", TimeUnit.SECONDS.toHours(downloadtimeHome), TimeUnit.SECONDS.toMinutes(downloadtimeHome)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(downloadtimeHome)), downloadtimeHome - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(downloadtimeHome)))+"</td>");
        sb.append("</tr>\n" +
                "                            </tbody>\n" +
                "                        </table></div></div>");




        return sb.toString();
    }
}

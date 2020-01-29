package Helper;

import java.util.concurrent.TimeUnit;

public class DesictionFeedbackHTML {
    public static String getPositiveFeedback(BBW nearestBBW, long totalTraveltime, long downloadtimeBBW, long totalTimeForBBW, long downloadtimeHome, double streamSpeed, double downloadSize, SizeSuffix sizeSuffix){
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"pt-5 pb-3\">\n" +
                "                    <div class=\"alert alert-success text-center \">\n" +
                "                        <strong>You should go to the Broadbandwell!</strong>\n" +
                "                    </div>\n" +
                "                </div>");
        sb.append("<div class=\"text-center pt-3 pb-3\">\n" +
                "                    <p class=\"text-center pb-3\">The nearest BBW is at <strong>");
        sb.append(nearestBBW.getName());
        sb.append("</strong></p>\n" +
                "                    <a class=\"btn btn-secondary \" data-toggle=\"collapse\" href=\"#collapseBBWInfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseBBWInfo\">Detailed Infos</a>\n" +
                "                    <div class=\"collapse pt-2\" id=\"collapseBBWInfo\">\n" +
                "                        <div class=\"card card-body\">");
        sb.append(nearestBBW.getInfo());
        sb.append("</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"text-center pt-3 pb-3\">\n" +
                "                    <p class=\"text-center pb-3\">Going to the BBW will save you <strong>");
        sb.append(getStringfromMilliseconds(downloadtimeHome-totalTimeForBBW));
        sb.append("</strong></p>\n" +
                "                    <a class=\"btn btn-primary\" data-toggle=\"collapse\" href=\"#collapseTimeinfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseTimeinfo\">Learn more</a>\n" +
                "                    <div class=\"collapse pt-2\" id=\"collapseTimeinfo\">\n" +
                "                        <div class=\"card card-body\">\n" +
                "                           <p><strong>Filesize:</strong> ");
        sb.append(downloadSize+sizeSuffix.toString());
        sb.append("</p><p><strong>Streamspeed:</strong> ");
        sb.append(streamSpeed+"MBit/s</p><br>");
        sb.append("                            <p>Time you need downloading <strong>@BBW</strong></p>\n" +
                "                            <table class=\"table table-borderless\">\n" +
                "                                <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td><strong>Traveltime:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(totalTraveltime));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td><strong>Downloadtime BBW:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(downloadtimeBBW));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr class=\"table-success\">\n" +
                "                                        <td><strong>total Time:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(totalTimeForBBW));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                            <p>Time you need downloading <strong>@current Location</strong></p>\n" +
                "                            <table class=\"table table-borderless\">\n" +
                "                                <tbody>\n" +
                "                                <tr class=\"table-warning\">\n" +
                "                                    <td><strong>Downloadtime:</strong></td>\n" +
                "                                    <td>");
        sb.append(getStringfromMilliseconds(downloadtimeHome));
        sb.append("</td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>");









        /*
        sb.append("<div class=\"alert alert-success\" role=\"alert\">\n" +
                "                    You should go to the Broadbandwell!\n" +
                "                </div> <p>The nearest BBW is at the ");
        sb.append(nearestBBW.getName());
        sb.append("</p>\n" +
                "                <a class=\"btn btn-primary\" data-toggle=\"collapse\" href=\"#collapseBBWInfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseBBWInfo\">Infos about the BBW</a>\n" +
                "                <div class=\"collapse\" id=\"collapseBBWInfo\">\n" +
                "                    <div class=\"card card-body\">\n");
        sb.append(nearestBBW.getInfo());
        sb.append("                    </div>\n" +
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
        */
        return sb.toString();
    }

    public static String getNegativeFeedback(BBW nearestBBW, long totalTraveltime, long downloadtimeBBW, long totalTimeForBBW, long downloadtimeHome){
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"pt-5 pb-3\">\n" +
                "                    <div class=\"alert alert-warning text-center \">\n" +
                "                        <strong>You download your Files @current location!</strong>\n" +
                "                    </div>\n" +
                "                </div>");
        sb.append("<div class=\"text-center pt-3 pb-3\">\n" +
                "                    <p class=\"text-center pb-3\">The nearest BBW would be at <strong>");
        sb.append(nearestBBW.getName());
        sb.append("</strong></p>\n" +
                "                    <a class=\"btn btn-secondary \" data-toggle=\"collapse\" href=\"#collapseBBWInfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseBBWInfo\">Detailed Infos</a>\n" +
                "                    <div class=\"collapse pt-2\" id=\"collapseBBWInfo\">\n" +
                "                        <div class=\"card card-body\">");
        sb.append(nearestBBW.getInfo());
        sb.append("</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"text-center pt-3 pb-3\">\n" +
                "                    <p class=\"text-center pb-3\">Downloading your files @current Location will save you <strong>");
        sb.append(getStringfromMilliseconds(totalTimeForBBW-downloadtimeHome));
        sb.append("</strong></p>\n" +
                "                    <a class=\"btn btn-primary\" data-toggle=\"collapse\" href=\"#collapseTimeinfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseTimeinfo\">Learn more</a>\n" +
                "                    <div class=\"collapse pt-2\" id=\"collapseTimeinfo\">\n" +
                "                        <div class=\"card card-body\">\n" +
                "                         <p>Time you need downloading <strong>@current Location</strong></p>\n" +
                "                            <table class=\"table table-borderless\">\n" +
                "                                <tbody>\n" +
                "                                <tr class=\"table-success\">\n" +
                "                                    <td><strong>Downloadtime:</strong></td>\n" +
                "                                    <td>");
        sb.append(getStringfromMilliseconds(downloadtimeHome));
        sb.append("</td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                            <p>Time you need downloading <strong>@BBW</strong></p>\n" +
                "                            <table class=\"table table-borderless\">\n" +
                "                                <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td><strong>Traveltime:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(totalTraveltime));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td><strong>Downloadtime BBW:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(downloadtimeBBW));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr class=\"table-warning\">\n" +
                "                                        <td><strong>total Time:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(totalTimeForBBW));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>");

        /*
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

        */


        return sb.toString();
    }


    public static String getNeutralFeedback(BBW nearestBBW, long totalTraveltime, long downloadtimeBBW, long totalTimeForBBW, long downloadtimeHome){
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"pt-5 pb-3\">\n" +
                "                    <div class=\"alert alert-primary text-center \">\n" +
                "                        <strong>It does not really matter where you Download your Files!</strong>\n" +
                "                    </div>\n" +
                "                </div>");
        sb.append("<div class=\"text-center pt-3 pb-3\">\n" +
                "                    <p class=\"text-center pb-3\">The nearest BBW would be at <strong>");
        sb.append(nearestBBW.getName());
        sb.append("</strong></p>\n" +
                "                    <a class=\"btn btn-secondary \" data-toggle=\"collapse\" href=\"#collapseBBWInfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseBBWInfo\">Detailed Infos</a>\n" +
                "                    <div class=\"collapse pt-2\" id=\"collapseBBWInfo\">\n" +
                "                        <div class=\"card card-body\">");
        sb.append(nearestBBW.getInfo());
        sb.append("</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"text-center pt-3 pb-3\">\n" +
                "                    <p class=\"text-center pb-3\">The time saving for a BBW is under 5 minutes");

        sb.append("</p>\n" +
                "                    <a class=\"btn btn-primary\" data-toggle=\"collapse\" href=\"#collapseTimeinfo\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseTimeinfo\">Learn more</a>\n" +
                "                    <div class=\"collapse pt-2\" id=\"collapseTimeinfo\">\n" +
                "                        <div class=\"card card-body\">\n" +
                "                         <p>Time you need downloading <strong>@current Location</strong></p>\n" +
                "                            <table class=\"table table-borderless\">\n" +
                "                                <tbody>\n" +
                "                                <tr class=\"table-primary\">\n" +
                "                                    <td><strong>Downloadtime:</strong></td>\n" +
                "                                    <td>");
        sb.append(getStringfromMilliseconds(downloadtimeHome));
        sb.append("</td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                            <p>Time you need downloading <strong>@BBW</strong></p>\n" +
                "                            <table class=\"table table-borderless\">\n" +
                "                                <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td><strong>Traveltime:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(totalTraveltime));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td><strong>Downloadtime BBW:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(downloadtimeBBW));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr class=\"table-primary\">\n" +
                "                                        <td><strong>total Time:</strong></td>\n" +
                "                                        <td>");
        sb.append(getStringfromMilliseconds(totalTimeForBBW));
        sb.append("</td>\n" +
                "                                    </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>");


        return sb.toString();
    }


    private static String getStringfromMilliseconds(long time){
        return String.format("%d h %d min %d sec",TimeUnit.SECONDS.toHours(time),TimeUnit.SECONDS.toMinutes(time)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(time)),time-TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(time)));
    }
}

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

        return sb.toString();
    }

    public static String getNegativeFeedback(BBW nearestBBW, long totalTraveltime, long downloadtimeBBW, long totalTimeForBBW, long downloadtimeHome, double streamSpeed, double downloadSize, SizeSuffix sizeSuffix){
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
                "                        <div class=\"card card-body\">\n" );
        sb.append("        <p><strong>Filesize:</strong> ");
        sb.append(downloadSize+sizeSuffix.toString());
        sb.append("</p><p><strong>Streamspeed:</strong> ");
        sb.append(streamSpeed+"MBit/s</p><br>");
        sb.append("                         <p>Time you need downloading <strong>@current Location</strong></p>\n" +
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



        return sb.toString();
    }


    public static String getNeutralFeedback(BBW nearestBBW, long totalTraveltime, long downloadtimeBBW, long totalTimeForBBW, long downloadtimeHome, double streamSpeed, double downloadSize, SizeSuffix sizeSuffix){
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
                "                        <div class=\"card card-body\">\n" );
        sb.append("        <p><strong>Filesize:</strong> ");
        sb.append(downloadSize+sizeSuffix.toString());
        sb.append("</p><p><strong>Streamspeed:</strong> ");
        sb.append(streamSpeed+"MBit/s</p><br>");
        sb.append("                         <p>Time you need downloading <strong>@current Location</strong></p>\n" +
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

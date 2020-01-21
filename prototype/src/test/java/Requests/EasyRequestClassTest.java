package Requests;

import Exceptions.InvalidAddressExeption;
import Helper.BBW;
import com.graphhopper.directions.api.client.ApiException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class EasyRequestClassTest {
    private static Logger LOGGER = Logger.getLogger(EasyRequestClassTest.class.getName());

    @Test
    public void downloadtimeTest(){
        //Test Downloadtimes with different settings
        //Mobile Connection, Download Filesize: 100 GB, Download
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", "Mobile Connection", 52,false,"car");

        Assert.assertEquals(9904.761,easyRequestClass.getDownloadtime(),0.2);
        Assert.assertEquals(41.6,easyRequestClass.getBBWdownloadtime(),0.2);

        easyRequestClass.setConnectionType("Fixed Broadband Connection");
        easyRequestClass.setDownloadSize(19);
        easyRequestClass.setUpload(true);

        Assert.assertEquals(15200,easyRequestClass.getDownloadtime(),0.2);
        Assert.assertEquals(15.2,easyRequestClass.getBBWdownloadtime(),0.2);

        easyRequestClass.setConnectionType("unknown");
        easyRequestClass.setDownloadSize(35);
        easyRequestClass.setUpload(false);

        Assert.assertEquals(56000,easyRequestClass.getDownloadtime(),0.2);
        Assert.assertEquals(28,easyRequestClass.getBBWdownloadtime(),0.2);
    }

    @Test
    public void geolocationTest() throws InvalidAddressExeption {
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", "Mobile Connection", 52,false,"car");

        Assert.assertEquals(48.190830,easyRequestClass.getGeolocation().getLat(),0.001);
        Assert.assertEquals(16.330270,easyRequestClass.getGeolocation().getLng(),0.001);

        easyRequestClass.setCurrentLocation("Währingerstraße 29");

        Assert.assertEquals(BBW.BBW_LIST.get(0).getLatLng().getLat(),easyRequestClass.getGeolocation().getLat(),0.001);
        Assert.assertEquals(BBW.BBW_LIST.get(0).getLatLng().getLng(),easyRequestClass.getGeolocation().getLng(),0.001);


    }

    @Test
    public void routingTest() throws IOException, ApiException {
        //Reindorfgasse to nearest BBW (VKM)
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", "Mobile Connection", 52,false, "car");
        Assert.assertEquals(540000,easyRequestClass.getTravelTime(),60000);

        EasyRequestClass easyRequestClass2 = new EasyRequestClass("Biedergasse 5", "Mobile Connection", 52,false, "car");
        Assert.assertEquals(240000,easyRequestClass2.getTravelTime(),60000);

    }
}

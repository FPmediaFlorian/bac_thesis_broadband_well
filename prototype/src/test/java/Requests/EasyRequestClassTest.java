package Requests;

import Exceptions.InvalidAddressExeption;
import Helper.BBW;
import Helper.SizeSuffix;
import com.graphhopper.directions.api.client.ApiException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class EasyRequestClassTest {
    private static Logger LOGGER = Logger.getLogger(EasyRequestClassTest.class.getName());

    @Test
    public void downloadtimeTest(){
        BBW.initBBWList();
        //Test Downloadtimes
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", 42, 52, SizeSuffix.valueOf("GB"),"car");

        Assert.assertEquals(9904.761,easyRequestClass.getDownloadtime(),0.2);
        Assert.assertEquals(41.6,easyRequestClass.getBBWdownloadtime(),0.2);

    }

    @Test
    public void geolocationTest() throws InvalidAddressExeption {
        BBW.initBBWList();
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", 100, 52, SizeSuffix.valueOf("GB"),"car");

        Assert.assertEquals(48.190830,easyRequestClass.getGeolocation().getLat(),0.001);
        Assert.assertEquals(16.330270,easyRequestClass.getGeolocation().getLng(),0.001);

        easyRequestClass.setCurrentLocation("Währingerstraße 29");

        Assert.assertEquals(BBW.BBW_LIST.get(0).getLatLng().getLat(),easyRequestClass.getGeolocation().getLat(),0.001);
        Assert.assertEquals(BBW.BBW_LIST.get(0).getLatLng().getLng(),easyRequestClass.getGeolocation().getLng(),0.001);


    }

    @Test
    public void routingTest() {
        BBW.initBBWList();
        //Reindorfgasse to nearest BBW (VKM)
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", 100, 52, SizeSuffix.valueOf("GB"),"car");
        Assert.assertEquals(540000,easyRequestClass.getTravelTime(),60000);

        EasyRequestClass easyRequestClass2 = new EasyRequestClass("Biedergasse 5", 100, 52, SizeSuffix.valueOf("GB"),"car");
        Assert.assertEquals(240000,easyRequestClass2.getTravelTime(),60000);

    }
}

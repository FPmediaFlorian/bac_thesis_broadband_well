package Requests;

import Exceptions.InvalidAddressExeption;
import Helper.BBW;
import Helper.SizeSuffix;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ExpertRequestClassTest {
    private static Logger LOGGER = Logger.getLogger(ExpertRequestClassTest.class.getName());

    @Test
    public void downloadtimeTest() throws InvalidAddressExeption {
        BBW.initBBWList();
        ExpertRequestClass expertRequestClass = new ExpertRequestClass("Reindorfgasse 42", 42, 52, SizeSuffix.valueOf("GB"),"car", -1);

        Assert.assertEquals(9904.761,expertRequestClass.getDownloadtime(),0.2);
        Assert.assertEquals(41.6,expertRequestClass.getBBWdownloadtime(),0.2);
    }

    @Test
    public void geolocationTest() throws InvalidAddressExeption {
        BBW.initBBWList();
        ExpertRequestClass expertRequestClass = new ExpertRequestClass("Reindorfgasse 42", 42, 52, SizeSuffix.valueOf("GB"),"car", -1);

        Assert.assertEquals(48.190830,expertRequestClass.getGeocode().getLat(),0.001);
        Assert.assertEquals(16.330270,expertRequestClass.getGeocode().getLng(),0.001);

        ExpertRequestClass expertRequestClass2 = new ExpertRequestClass("Währingerstraße 29", 42, 52, SizeSuffix.valueOf("GB"),"car", -1);


        Assert.assertEquals(BBW.BBW_LIST.get(0).getLatLng().getLat(),expertRequestClass2.getGeocode().getLat(),0.001);
        Assert.assertEquals(BBW.BBW_LIST.get(0).getLatLng().getLng(),expertRequestClass2.getGeocode().getLng(),0.001);
    }

    @Test
    public void routingTest() throws InvalidAddressExeption {
        BBW.initBBWList();
        //Auto calculate nearest BBW
        ExpertRequestClass expertRequestClass = new ExpertRequestClass("Reindorfgasse 42", 100, 52, SizeSuffix.valueOf("GB"),"car",-1);
        Assert.assertEquals(540000,expertRequestClass.getDesiredBBW().getTravelTime(),60000);

        //VKM is desired BBW
        ExpertRequestClass expertRequestClass2 = new ExpertRequestClass("Reindorfgasse 42", 100, 52, SizeSuffix.valueOf("GB"),"car",1);
        Assert.assertEquals(540000,expertRequestClass2.getDesiredBBW().getTravelTime(),60000);

        //WS 29 is desired BBW
        ExpertRequestClass expertRequestClass3 = new ExpertRequestClass("Reindorfgasse 42", 100, 52, SizeSuffix.valueOf("GB"),"car",0);
        Assert.assertEquals(600000,expertRequestClass3.getDesiredBBW().getTravelTime(),60000);

    }
}

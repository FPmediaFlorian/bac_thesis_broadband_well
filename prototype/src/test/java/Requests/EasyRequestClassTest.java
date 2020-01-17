package Requests;

import Helper.BBWList;
import org.junit.Assert;
import org.junit.Test;

public class EasyRequestClassTest {

    @Test
    public void downloadtimeTest(){
        //Test Downloadtimes with different settings
        //Mobile Connection, Download Filesize: 100 GB, Download
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", "Mobile Connection", 52,false);

        Assert.assertEquals(9904.761,easyRequestClass.getDownloadtime(),0.2);
        Assert.assertEquals(41.6,easyRequestClass.getBBWdownloadtime(),0.2);


        easyRequestClass.setConnectionType("Fixed Broadband Connection");
        easyRequestClass.setDownloadSize(19);
        easyRequestClass.setUpload(true);

        Assert.assertEquals(15200,easyRequestClass.getDownloadtime(),0.2);
        Assert.assertEquals(15.2,easyRequestClass.getBBWdownloadtime(),0.2);

        //TODO test all possible combinations
    }

    @Test
    public void geolocationTest(){
        EasyRequestClass easyRequestClass = new EasyRequestClass("Reindorfgasse 42", "Mobile Connection", 52,false);


        Assert.assertEquals(48.190830,easyRequestClass.getGeolocation().getLat(),0.001);
        Assert.assertEquals(16.330270,easyRequestClass.getGeolocation().getLng(),0.001);

        easyRequestClass.setCurrentLocation("Währingerstraße 29");

        Assert.assertEquals(BBWList.BBW_LIST.get(0).getLatLng().getLat(),easyRequestClass.getGeolocation().getLat(),0.001);
        Assert.assertEquals(BBWList.BBW_LIST.get(0).getLatLng().getLng(),easyRequestClass.getGeolocation().getLng(),0.001);


    }
}

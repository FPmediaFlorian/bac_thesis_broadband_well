package Helper;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class GeoCalculatorTest {

    private static Logger LOGGER = Logger.getLogger(GeoCalculatorTest.class.getName());

    @Test
    public void getPublicTransportTest() throws Exception {
        BBW.initBBWList();
        BBW.BBW_LIST.get(0).setCurrentLocationPTstation(GeoCalculator.getNearestPTStation(new LatLng(48.190830, 16.330270)));
        double time = GeoCalculator.getPublicTransportTraveltime(BBW.BBW_LIST.get(0));
        LOGGER.debug("Traveltime: " + time);
        //Can not be asserted, changes depending on public transport situation
        //Assert.assertEquals(1560.0, time,1);
    }

    @Test
    public void getNearestPTstation() throws Exception {

        WLstation station = GeoCalculator.getNearestPTStation(new LatLng(48.2514764999, 16.4801149100));

        LOGGER.debug("Nearest Station" + station.toString());
        Assert.assertEquals(60259903, station.getID());

        station = GeoCalculator.getNearestPTStation(new LatLng(48.190830, 16.330270));

        LOGGER.debug("Nearest Station" + station.toString());

        Assert.assertEquals(60200712, station.getID());
    }


    @Test
    public void getTraveltimeTest() {
        BBW.initBBWList();
        BBW bbw = GeoCalculator.getNearestBBWsetTraveltime(new LatLng(48.190830, 16.330270), TransportForm.PUBLIC);

        LOGGER.debug("Nearst BBW incl. Traveltime" + bbw.toString());

        //Can not be asserted, changes depending on traffic and public transport situation
    }

    @Test
    public void getGeocodeTest() throws Exception {
        LatLng latLng = GeoCalculator.getGeocode("Reindorfgasse 42");

        Assert.assertEquals(48.19084, latLng.getLat(), 0.0001);
        Assert.assertEquals(16.33009, latLng.getLng(), 0.0001);

        latLng = GeoCalculator.getGeocode("Währingerstraße 29");

        Assert.assertEquals(48.22009, latLng.getLat(), 0.0001);
        Assert.assertEquals(16.35641, latLng.getLng(), 0.0001);

        latLng = GeoCalculator.getGeocode("adsfadfa23");

        Assert.assertNull(latLng);

    }


}

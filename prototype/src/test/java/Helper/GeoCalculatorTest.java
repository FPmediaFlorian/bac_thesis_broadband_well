package Helper;

import org.junit.Assert;
import org.junit.Test;

public class GeoCalculatorTest {
   @Test
   public void getPublicTransportTest() throws Exception {
       BBW.initBBWList();
       double time = GeoCalculator.getPublicTransportTraveltime(new LatLng(48.220548,16.32937), BBW.BBW_LIST.get(1));
       Assert.assertEquals(300, time,1);
   }
}

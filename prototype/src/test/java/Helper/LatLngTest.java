package Helper;

import org.junit.Assert;
import org.junit.Test;

public class LatLngTest {
    @Test
    public void testLatLng() {
        LatLng latLng = new LatLng(13.13, 14.14);

        Assert.assertEquals("13.13,14.14", latLng.getLatLng());
    }

    @Test
    public void testCalculateDistance() {
        LatLng latLng1 = new LatLng(11, 6);
        LatLng latLng2 = new LatLng(8.14, 7.2);

        Assert.assertEquals(3.1015, latLng1.calculateDistance(latLng2), 0.0001);
    }
}


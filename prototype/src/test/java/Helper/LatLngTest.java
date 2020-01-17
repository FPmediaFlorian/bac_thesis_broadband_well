package Helper;

import org.junit.Assert;
import org.junit.Test;

public class LatLngTest {
    @Test
    public void testLatLng(){
        LatLng latLng=new LatLng(13.13,14.14);

        Assert.assertEquals("13.13,14.14",latLng.getLatLng());
    }
}

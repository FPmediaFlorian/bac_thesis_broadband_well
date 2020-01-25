package Helper;

import org.junit.Assert;
import org.junit.Test;

public class DownloadCalculatorTest {

    @Test
    public void TestDownloadCalc(){
        BBW.initBBWList();
        DownloadCalculator downloadCalculator = new DownloadCalculator(100,30,SizeSuffix.GB);

        //Test Download Time Calcualtor
        Assert.assertEquals(80, downloadCalculator.getBBWdownloadtimeSec(),2);
        Assert.assertEquals(26666.66, downloadCalculator.getDownloadtimeSec() , 2);

        //Test Conversion from GB to MBit
        downloadCalculator.setSize(20,SizeSuffix.GB);

        Assert.assertEquals(160000,downloadCalculator.getSize(),2);

    }

}

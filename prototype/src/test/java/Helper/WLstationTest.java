package Helper;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

public class WLstationTest {

    private static Logger LOGGER = Logger.getLogger(WLstationTest.class.getName());

    @Test
    public void csvImportTest(){
        List<WLstation> list= WLstation.initWLstationList();
        LOGGER.debug(list);
        LOGGER.debug("Listsize: "+list.size());
    }
}

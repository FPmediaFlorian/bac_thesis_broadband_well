package Helper;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class BBWtest {

    private static Logger LOGGER = Logger.getLogger(BBWtest.class.getName());

    @Test
    public void mapToJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {

            // Java objects to JSON file
            mapper.writeValue(new File("src/config/BBW_config.json"), BBW.BBW_LIST);

            // Java objects to JSON string - compact-print
            String jsonString = mapper.writeValueAsString(BBW.BBW_LIST);

            LOGGER.debug(jsonString);

            // Java objects to JSON string - pretty-print
            String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(BBW.BBW_LIST);

            LOGGER.debug(jsonInString2);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void mapFromJson() {

        BBW.initBBWList();

        LOGGER.debug("BBW List:" + BBW.BBW_LIST.toString());
    }
}

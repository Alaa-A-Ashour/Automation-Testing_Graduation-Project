package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonReader {
    public static String getValue(String section, String key) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File("src/test/resources/testdata.json"));
            return root.get(section).get(key).asText();
        } catch (Exception e) { return ""; }
    }
}
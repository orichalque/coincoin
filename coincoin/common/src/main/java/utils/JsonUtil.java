package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by E104607D on 27/09/16.
 * Json util methods
 */
public class JsonUtil {
    private JsonUtil() {
    }

    /**
     * Deserialize a String into a list of the Class in parameter
     * @param file the file to deserialize
     * @param clazz the Class
     * @param <T> Class template
     * @return the newly created List
     * @throws IOException
     */
    public static <T> List<T> deserializeListFromString(String file, final Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
    }
}

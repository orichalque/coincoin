package com.alma.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by E104607D on 27/09/16.
 * Json util methods
 */
public class JsonUtil {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static <C> List<C> deserializeListFromString(String file, Class C) {
        List<C> itemList = new ArrayList();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            itemList = objectMapper.readValue(file, new TypeReference<List<C>>(){});
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "The database can not be load", e);
        }

        return itemList;
    }
}

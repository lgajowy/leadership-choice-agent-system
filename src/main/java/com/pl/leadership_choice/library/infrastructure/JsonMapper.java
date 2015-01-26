package com.pl.leadership_choice.library.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lukasz on 25.01.15.
 */
public class JsonMapper {

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    public static String createJsonFromObject(Object pojo) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            logger.error("Unable to map Object to json!");
            e.printStackTrace();
        }
        return null;
    }

    public static Object mapJsonStringToObject(String jsonString, Class clazz) {
        Object result = null;
        try {
            result = new ObjectMapper().readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("Unable to parse json String!");
            e.printStackTrace();
        }
        return result;

    }
}

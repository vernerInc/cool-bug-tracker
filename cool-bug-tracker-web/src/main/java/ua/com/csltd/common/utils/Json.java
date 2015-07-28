package ua.com.csltd.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Json {
    private ObjectMapper jsonMapper = new ObjectMapper();

    public String objectToString(Object o) throws IOException {
        return jsonMapper.writeValueAsString(o);
    }

    public <T> T stringToObject(String s, Class<T> clazz) throws IOException {
        return jsonMapper.readValue(s, clazz);
    }
}

package ua.com.csltd.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : verner
 * @since : 31.07.2015
 */
public class CustomDateDeSerialization extends JsonDeserializer<Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd.MM.yyyy");

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String str = p.getText().trim();
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}


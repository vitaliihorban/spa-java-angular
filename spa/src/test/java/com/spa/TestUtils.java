package com.spa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spa.service.dto.EmployeeExtendedDTO;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    private static ObjectMapper objectMapper = createObjectMapper();

    public static <T> List<T> mapContentToObjectList(MvcResult result, Class<T> clazz) throws IOException {
        List list = new ArrayList();
        JsonNode rootNode = objectMapper.readTree(objectMapper.getFactory().createParser(result.getResponse().getContentAsString()));
        rootNode.elements().forEachRemaining(list::add);
        JavaType valueType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return (List) objectMapper.readValue(list.toString(), valueType);
    }

    public static byte[] writeBytes(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(value);
    }

    public static <T> T  mapObject(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }


    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


}

package com.example.tests.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

public class JsonReader {
    public static List<Map<String, Object>> readJsonArray(String path) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), List.class);
    }
}

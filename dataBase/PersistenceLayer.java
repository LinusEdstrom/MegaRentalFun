package com.Edstrom.dataBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PersistenceLayer {

    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
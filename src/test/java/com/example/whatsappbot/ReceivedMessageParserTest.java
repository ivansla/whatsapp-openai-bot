package com.example.whatsappbot;

import com.example.whatsappbot.model.received.ReceivedMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ReceivedMessageParserTest {

    @Test
    void given_when_then() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("samples/received/received_message.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String jsonString = reader.lines().collect(Collectors.joining());

        ObjectMapper mapper = new ObjectMapper();
        ReceivedMessage message = mapper.readValue(jsonString, ReceivedMessage.class);

        System.out.println(message);
    }
}

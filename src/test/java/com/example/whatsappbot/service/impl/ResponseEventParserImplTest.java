package com.example.whatsappbot.service.impl;

import com.example.whatsappbot.model.WhatsappMessageWrapper;
import com.example.whatsappbot.model.delivered.DeliveredMessage;
import com.example.whatsappbot.model.received.ReceivedMessage;
import com.example.whatsappbot.model.sent.SentMessage;
import com.example.whatsappbot.service.ResponseEventParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResponseEventParserImplTest {

    private ObjectMapper mapper = new ObjectMapper();
    private ResponseEventParser sut = new ResponseEventParserImpl();

//    @Test
    void givenValidReceivedMessage_whenParseEvent_thenSuccess() throws Exception {
        String jsonString = loadJsonToString("samples/received/received_message.json");
        ReceivedMessage expectedMessage = mapper.readValue(jsonString, ReceivedMessage.class);

        WhatsappMessageWrapper result = sut.parseEvent(jsonString).get();

        result.getMessage();
        MatcherAssert.assertThat(result.getMessage(), Matchers.is(expectedMessage));
    }

    @Test
    void givenValidDeliveredMessage_whenParseEvent_thenSuccess() throws Exception {
        String jsonString = loadJsonToString("samples/delivered/delivered_message.json");
        DeliveredMessage expectedMessage = mapper.readValue(jsonString, DeliveredMessage.class);

        WhatsappMessageWrapper result = sut.parseEvent(jsonString).get();

        result.getMessage();
        MatcherAssert.assertThat(result.getMessage(), Matchers.is(expectedMessage));
    }

//    @Test
    void givenValidSentMessage_whenParseEvent_thenSuccess() throws Exception {
        String jsonString = loadJsonToString("samples/sent/sent_message.json");
        SentMessage expectedMessage = mapper.readValue(jsonString, SentMessage.class);

        WhatsappMessageWrapper result = sut.parseEvent(jsonString).get();

        result.getMessage();
        MatcherAssert.assertThat(result.getMessage(), Matchers.is(expectedMessage));
    }

//    @Test
    void givenInvalidDeliveredMessage_whenParseEvent_thenSuccess() throws Exception {
        String jsonString = loadJsonToString("examples/unparsable_message.json");
        MatcherAssert.assertThat(sut.parseEvent(jsonString), Matchers.is(Optional.empty()));
    }

    private String loadJsonToString(String path) {
        try(InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.example.whatsappbot.service.impl;

import com.example.whatsappbot.model.GenericMessageWrapper;
import com.example.whatsappbot.model.WhatsappMessageWrapper;
import com.example.whatsappbot.model.delivered.DeliveredMessage;
import com.example.whatsappbot.model.received.ReceivedMessage;
import com.example.whatsappbot.model.sent.SentMessage;
import com.example.whatsappbot.service.ResponseEventParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ResponseEventParserImpl implements ResponseEventParser {
    private static final Logger log = LoggerFactory.getLogger(ResponseEventParserImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Optional<WhatsappMessageWrapper> parseEvent(String event) {
        log.info("Processing Message: {}", event);
        return parseMessage(event, ReceivedMessage.class)
                .or(() -> parseMessage(event, DeliveredMessage.class))
                .or(() -> parseMessage(event, SentMessage.class))
                .or(() ->  {
                    log.warn("Encountered unparsable event: {}", event);
                    return Optional.empty();
                });
    }

    private <T> Optional<WhatsappMessageWrapper> parseMessage(String event, Class<T> t) {
        try {
            WhatsappMessageWrapper<T> wrapper = new GenericMessageWrapper(mapper.readValue(event, t));
            return Optional.of(wrapper);
        } catch (UnrecognizedPropertyException e) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

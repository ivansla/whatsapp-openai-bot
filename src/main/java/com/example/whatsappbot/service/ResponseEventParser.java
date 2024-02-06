package com.example.whatsappbot.service;

import com.example.whatsappbot.model.WhatsappMessageWrapper;

import java.util.Optional;

public interface ResponseEventParser {

    Optional<WhatsappMessageWrapper> parseEvent(String event);
}

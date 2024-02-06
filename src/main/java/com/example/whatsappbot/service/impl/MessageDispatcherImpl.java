package com.example.whatsappbot.service.impl;

import com.example.whatsappbot.model.WhatsappMessageWrapper;
import com.example.whatsappbot.model.delivered.DeliveredMessage;
import com.example.whatsappbot.model.received.ReceivedMessage;
import com.example.whatsappbot.model.sent.SentMessage;
import com.example.whatsappbot.service.MessageDispatcher;
import com.example.whatsappbot.service.MessageProcessor;
import com.example.whatsappbot.service.VerificationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDispatcherImpl implements MessageDispatcher {
    private static final Logger log = LoggerFactory.getLogger(MessageDispatcherImpl.class);
    private final MessageProcessor<ReceivedMessage> receivedMessageMessageProcessor;

    public MessageDispatcherImpl(MessageProcessor<ReceivedMessage> receivedMessageMessageProcessor) {
        this.receivedMessageMessageProcessor = receivedMessageMessageProcessor;
    }

    @Override
    public void dispatchMessage(WhatsappMessageWrapper whatsappMessageWrapper) {
        if(whatsappMessageWrapper.getMessage() instanceof ReceivedMessage) {
            log.info("Logging ReceivedMessage: {}", whatsappMessageWrapper.getMessage());
            receivedMessageMessageProcessor.processMessage((ReceivedMessage) whatsappMessageWrapper.getMessage());
        } else if(whatsappMessageWrapper.getMessage() instanceof DeliveredMessage) {
            log.info("Logging DeliveredMessage: {}", whatsappMessageWrapper.getMessage());
        } else if(whatsappMessageWrapper.getMessage() instanceof SentMessage) {
            log.info("Logging SentMessage: {}", whatsappMessageWrapper.getMessage());
        } else {
            log.error("Unimplemented event type");
        }
    }
}

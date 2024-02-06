package com.example.whatsappbot.service;

import com.example.whatsappbot.model.WhatsappMessageWrapper;
import com.example.whatsappbot.model.delivered.DeliveredMessage;
import com.example.whatsappbot.model.received.*;
import com.example.whatsappbot.model.sent.SentMessage;
import com.example.whatsappbot.service.impl.MessageDispatcherImpl;
import com.example.whatsappbot.service.impl.ReceivedMessageProcessor;
import com.example.whatsappbot.service.impl.ResponseEventParserImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("whatsapp-bot")
public class VerificationController {

    private static final Logger log = LoggerFactory.getLogger(VerificationController.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEventParser responseEventParser = new ResponseEventParserImpl();
    private final MessageDispatcher messageDispatcher = new MessageDispatcherImpl(new ReceivedMessageProcessor());

    @GetMapping(path = "/ping", produces = "text/plain")
    public String ping() {
        log.info("Successful Ping");
        return "Successful Ping";
    }

    @GetMapping(path = "/webhooks", produces = "application/json")
    public int register(@RequestParam(name = "hub.mode") String hubMode,
                          @RequestParam(name = "hub.challenge") int hubChallenge,
                          @RequestParam(name = "hub.verify_token") String hubVerifyToken) {

        if(!hubVerifyToken.equals("TEST")) {
            throw new IllegalArgumentException("Invalid verify token");
        }

        log.info("Hello World! {}, {}, {}", hubMode, hubChallenge, hubVerifyToken);
        return hubChallenge;
    }
    @PostMapping(value ="/webhooks", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void processEvent(@RequestBody String event) {

        Optional<WhatsappMessageWrapper> maybeWhatsappMessageWrapper = responseEventParser.parseEvent(event);
        if(maybeWhatsappMessageWrapper.isEmpty()) {
            return;
        }
        messageDispatcher.dispatchMessage(maybeWhatsappMessageWrapper.get());
    }
}

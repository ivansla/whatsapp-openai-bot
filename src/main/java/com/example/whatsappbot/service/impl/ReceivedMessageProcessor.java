package com.example.whatsappbot.service.impl;

import com.example.whatsappbot.model.received.*;
import com.example.whatsappbot.service.MessageProcessor;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReceivedMessageProcessor implements MessageProcessor<ReceivedMessage> {
    private static final Logger log = LoggerFactory.getLogger(ReceivedMessageProcessor.class);
    private final WebClient whatsappClient = WebClient.create("https://graph.facebook.com");
    private final OpenAiService openAiService = new OpenAiService("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");
    @Override
    public void processMessage(ReceivedMessage message) {
        String phoneNumber = updateBrazilianPhoneNumber(getContactPhoneNumber(message));
        echoResponse(phoneNumber, message);
    }

    private String updateBrazilianPhoneNumber(String phoneNumber) {
        String number = phoneNumber.substring(4);
        String countryAndStateCode = phoneNumber.substring(0, 4);
        return countryAndStateCode + 9 + number;
    }

    private Optional<Entry> findFirstEntry(ReceivedMessage receivedMessage) {
        if(receivedMessage.getEntry() == null) {
            return Optional.empty();
        }
        return receivedMessage.getEntry().stream().findFirst();
    }

    private Optional<Change> findFirstChange(Entry entry) {
        if(entry.getChanges() == null) {
            return Optional.empty();
        }
        return entry.getChanges().stream().findFirst();
    }

    private Optional<Value> findValue(Change change) {
        return Optional.ofNullable(change.getValue());
    }

    private Optional<Contact> findFirstContact(Value value) {
        if(value.getContacts() == null) {
            return Optional.empty();
        }
        return value.getContacts().stream().findFirst();
    }

    private Optional<Message> findFirstMessage(Value value) {
        if(value.getMessages() == null) {
            return Optional.empty();
        }
        return value.getMessages().stream().findFirst();
    }

    private String getContactPhoneNumber(ReceivedMessage receivedMessage) {
        return findFirstEntry(receivedMessage)
                .flatMap(this::findFirstChange)
                .flatMap(this::findValue)
                .flatMap(this::findFirstContact)
                .map(Contact::getWaId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to retrieve contact phone number. ReceivedMessage: %s", receivedMessage)));
    }

    private void echoResponse(String phoneNumber, ReceivedMessage receivedMessage) {
        String receivedMessageText = findFirstEntry(receivedMessage)
                .flatMap(this::findFirstChange)
                .flatMap(this::findValue)
                .flatMap(this::findFirstMessage)
                .map(message -> message.getText()).map(text -> text.getBody()).orElse("Failed to retrieve text from received message. Please ignore this message.");

        log.info("Received message text: {}", receivedMessageText);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole("user");
        chatMessage.setContent(receivedMessageText);

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(Arrays.asList(chatMessage))
                .model("gpt-3.5-turbo")
                .build();
        List<ChatCompletionChoice> completionChoiceList = openAiService.createChatCompletion(completionRequest).getChoices();
        completionChoiceList.forEach(System.out::println);

        for(ChatCompletionChoice choice : completionChoiceList) {
            String response = prepareResponseFromOpenAi(phoneNumber, choice.getMessage().getContent());
            log.info("echoResponseRequest: {}", response);
            sendResponse(response);
        }
    }

    private void sendResponse(String echoResponseRequest) {
        WebClient.RequestHeadersSpec<?> headersSpec = whatsappClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v16.0/102199822777733/messages")
                        .build())
                .bodyValue(echoResponseRequest)
                .header("Authorization", "Bearer EAABh27OagwkBAIHaD0LFVG3OMIvMQtcCjZBMaW7HbA9oF2Tllnm7nlSZAFZCKbrauzyi90GcufKZCzgBXGd2MJuX4GRSaxfV72xq5I9fyipwXrZA8YKrsZCByZB76ujN8O30al2BLBXOEKyIKuTc61ZCPo2G7gcBz4QVZA1Lv8poKOxxZAbZCM1capk1Pd58hgOwnKDK8ZA2IZB4TJAZDZD")
                .header("Content-Type", "application/json");

        Mono<String> response1 = headersSpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return response.bodyToMono(String.class);
            } else {
                return response.createException()
                        .flatMap(Mono::error);
            }
        });

        log.info("echoResponse: {}", response1.block());
    }

    private String prepareResponseFromOpenAi(String phoneNumber, String body) {
        return "{\n" +
                "    \"messaging_product\": \"whatsapp\",\n" +
                "    \"to\": " + phoneNumber + ",\n" +
                "    \"type\": \"text\",\n" +
                "    \"text\": {\n" +
                "        \"preview_url\": \"false\",\n" +
                "        \"body\": \"" + body + "\"\n" +
                "    }\n" +
                "}";
    }

    private String prepareEchoResponse(String phoneNumber) {
        return "{\n" +
                "    \"messaging_product\": \"whatsapp\",\n" +
                "    \"to\": " + phoneNumber + ",\n" +
                "    \"type\": \"text\",\n" +
                "    \"text\": {\n" +
                "        \"preview_url\": \"false\",\n" +
                "        \"body\": \"Ahoj, ako sa mas? Estou esrevendo do meu Java aplicativo \\ud83d\\ude09\"\n" +
                "    }\n" +
                "}";
    }
}

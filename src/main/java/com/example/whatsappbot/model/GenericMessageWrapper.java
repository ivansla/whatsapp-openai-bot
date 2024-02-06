package com.example.whatsappbot.model;

import com.example.whatsappbot.model.received.ReceivedMessage;

public class GenericMessageWrapper<T> implements WhatsappMessageWrapper<T> {

    private final T message;

    public GenericMessageWrapper(T message) {
        this.message = message;
    }

    @Override
    public T getMessage() {
        return this.message;
    }
}

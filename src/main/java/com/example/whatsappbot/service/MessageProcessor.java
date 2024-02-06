package com.example.whatsappbot.service;

public interface MessageProcessor<T> {

    void processMessage(T t);
}

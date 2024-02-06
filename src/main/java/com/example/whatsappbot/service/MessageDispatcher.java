package com.example.whatsappbot.service;

import com.example.whatsappbot.model.WhatsappMessageWrapper;

public interface MessageDispatcher {

    void dispatchMessage(WhatsappMessageWrapper messageWrapper);
}

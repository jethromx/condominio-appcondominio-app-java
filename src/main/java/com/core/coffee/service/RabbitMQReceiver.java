package com.core.coffee.service;

import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}
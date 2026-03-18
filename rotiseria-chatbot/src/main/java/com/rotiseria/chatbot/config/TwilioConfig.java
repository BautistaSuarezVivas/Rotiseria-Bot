package com.rotiseria.chatbot.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TwilioConfig {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.whatsapp-number}")
    private String whatsappNumber;

    // Se ejecuta una sola vez al levantar el contexto de Spring
    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }
}
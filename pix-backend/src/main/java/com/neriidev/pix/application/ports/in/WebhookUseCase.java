package com.neriidev.pix.application.ports.in;

public interface WebhookUseCase {
    void processWebhook(String eventId, String endToEndId, String status);
}

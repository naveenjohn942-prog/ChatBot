package org.example.aibackend.service.impl;

import org.example.aibackend.service.AIservice;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class AIserviceImpl implements AIservice {

    private final ChatClient chatClient;

    public AIserviceImpl(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @Override
    public Flux<String> streamResponse(String question) {
        return this.chatClient.prompt()
                .user(question)
                .stream()
                .content()
                .map(this::cleanAndFormat)
                .filter(text -> !text.isEmpty())
                .publishOn(Schedulers.boundedElastic());
    }


    private String cleanAndFormat(String text) {
        text = text.replaceAll("(?s)<think>.*?</think>", "");
        text = text.replaceAll("<think>", "").replaceAll("</think>", "");
        text = text.replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>");
        text = text.replaceAll("\\s*(\\d+\\s*\\.)", "<br>$1");
        text = text.replaceAll("\n", "<br>");
        text = text.replaceAll("^(<br>)+", "").trim();

        return text;
    }
}

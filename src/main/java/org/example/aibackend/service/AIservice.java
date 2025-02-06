package org.example.aibackend.service;

import reactor.core.publisher.Flux;

public interface AIservice {
    Flux<String> streamResponse(String question);
}

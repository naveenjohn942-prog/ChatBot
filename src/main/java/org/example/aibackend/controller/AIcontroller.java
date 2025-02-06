package org.example.aibackend.controller;

import org.example.aibackend.service.AIservice;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api")
public class AIcontroller {
    private final AIservice aIservice;

    public AIcontroller(AIservice aIservice) {
        this.aIservice = aIservice;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamAi(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        if (message.trim().isEmpty()) {
            return Flux.error(new IllegalArgumentException("Message must not be empty"));
        }
        return aIservice.streamResponse(message);
    }
}

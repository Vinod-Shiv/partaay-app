package com.partaay.api.event;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse create(@Valid @RequestBody EventRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<EventResponse> listPublicEvents() {
        return service.listPublicEvents();
    }

    @GetMapping("/{id}")
    public EventResponse getPublicEvent(@PathVariable UUID id) {
        return service.getPublicEvent(id);
    }

    @PutMapping("/{id}")
    public EventResponse update(@PathVariable UUID id,
                                @Valid @RequestBody EventRequest request) {
        return service.update(id, request);
    }
}

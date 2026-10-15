package com.partaay.api.event;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public EventResponse create(EventRequest request) {
        validateDates(request.startsAt(), request.endsAt());
        EventEntity event = new EventEntity(
                request.hostId(), request.title().trim(), request.description(),
                request.startsAt(), request.endsAt(), request.venueName(),
                request.city(), request.visibility(), request.capacity(), request.imageUrl()
        );
        return EventResponse.from(repository.save(event));
    }

    public List<EventResponse> listPublicEvents() {
        return repository.findByVisibilityAndStatusOrderByStartsAtAsc(
                        EventVisibility.PUBLIC, EventStatus.SCHEDULED)
                .stream().map(EventResponse::from).toList();
    }

    public EventResponse getPublicEvent(UUID id) {
        EventEntity event = repository.findByIdAndVisibilityAndStatus(
                        id, EventVisibility.PUBLIC, EventStatus.SCHEDULED)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event not found"));
        return EventResponse.from(event);
    }

    public EventResponse update(UUID id, EventRequest request) {
        validateDates(request.startsAt(), request.endsAt());
        EventEntity event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event not found"));
        event.update(
                request.title().trim(), request.description(),
                request.startsAt(), request.endsAt(), request.venueName(),
                request.city(), request.visibility(), request.capacity(), request.imageUrl()
        );
        return EventResponse.from(repository.save(event));
    }

    private void validateDates(OffsetDateTime startsAt, OffsetDateTime endsAt) {
        if (!endsAt.isAfter(startsAt)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "endsAt must be after startsAt");
        }
    }
}

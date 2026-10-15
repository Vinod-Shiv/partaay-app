package com.partaay.api.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EventResponse(
        UUID id,
        UUID hostId,
        String title,
        String description,
        OffsetDateTime startsAt,
        OffsetDateTime endsAt,
        String venueName,
        String city,
        EventVisibility visibility,
        Integer capacity,
        EventStatus status
) {
    public static EventResponse from(EventEntity event) {
        return new EventResponse(
                event.getId(), event.getHostId(), event.getTitle(),
                event.getDescription(), event.getStartsAt(), event.getEndsAt(),
                event.getVenueName(), event.getCity(), event.getVisibility(),
                event.getCapacity(), event.getStatus()
        );
    }
}

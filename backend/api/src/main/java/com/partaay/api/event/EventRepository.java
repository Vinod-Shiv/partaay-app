package com.partaay.api.event;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    List<EventEntity> findByVisibilityAndStatusOrderByStartsAtAsc(
            EventVisibility visibility, EventStatus status);

    Optional<EventEntity> findByIdAndVisibilityAndStatus(
            UUID id, EventVisibility visibility, EventStatus status);
}

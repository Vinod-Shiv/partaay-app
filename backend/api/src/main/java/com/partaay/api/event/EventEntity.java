package com.partaay.api.event;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "host_id", nullable = false)
    private UUID hostId;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 4000)
    private String description;

    @Column(name = "starts_at", nullable = false)
    private OffsetDateTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private OffsetDateTime endsAt;

    @Column(name = "venue_name", length = 200)
    private String venueName;

    @Column(name = "city", length = 120)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EventVisibility visibility;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EventStatus status = EventStatus.SCHEDULED;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected EventEntity() {}

    public EventEntity(UUID hostId, String title, String description,
                       OffsetDateTime startsAt, OffsetDateTime endsAt,
                       String venueName, String city,
                       EventVisibility visibility, Integer capacity) {
        this.hostId = hostId;
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.venueName = venueName;
        this.city = city;
        this.visibility = visibility;
        this.capacity = capacity;
        this.status = EventStatus.SCHEDULED;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public UUID getId() { return id; }
    public UUID getHostId() { return hostId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public OffsetDateTime getStartsAt() { return startsAt; }
    public OffsetDateTime getEndsAt() { return endsAt; }
    public String getVenueName() { return venueName; }
    public String getCity() { return city; }
    public EventVisibility getVisibility() { return visibility; }
    public Integer getCapacity() { return capacity; }
    public EventStatus getStatus() { return status; }

    public void update(String title, String description,
                       OffsetDateTime startsAt, OffsetDateTime endsAt,
                       String venueName, String city,
                       EventVisibility visibility, Integer capacity) {
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.venueName = venueName;
        this.city = city;
        this.visibility = visibility;
        this.capacity = capacity;
    }
}

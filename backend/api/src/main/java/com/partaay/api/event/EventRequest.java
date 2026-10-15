package com.partaay.api.event;

import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.UUID;

public record EventRequest(
        @NotNull UUID hostId,
        @NotBlank @Size(max = 120) String title,
        @Size(max = 4000) String description,
        @NotNull OffsetDateTime startsAt,
        @NotNull OffsetDateTime endsAt,
        @Size(max = 200) String venueName,
        @Size(max = 120) String city,
        @NotNull EventVisibility visibility,
        @NotNull @Min(1) @Max(100000) Integer capacity,
        @Size(max = 1000) String imageUrl
) {}

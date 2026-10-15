CREATE TABLE users (
    id UUID PRIMARY KEY,
    display_name VARCHAR(120) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events (
    id UUID PRIMARY KEY,
    host_id UUID NOT NULL,
    title VARCHAR(120) NOT NULL,
    description VARCHAR(4000),
    starts_at TIMESTAMP WITH TIME ZONE NOT NULL,
    ends_at TIMESTAMP WITH TIME ZONE NOT NULL,
    venue_name VARCHAR(200),
    city VARCHAR(120),
    visibility VARCHAR(20) NOT NULL,
    capacity INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_events_host FOREIGN KEY (host_id) REFERENCES users(id),
    CONSTRAINT chk_event_visibility CHECK (visibility IN ('PUBLIC', 'PRIVATE')),
    CONSTRAINT chk_event_status CHECK (status IN ('SCHEDULED', 'CANCELLED')),
    CONSTRAINT chk_event_capacity CHECK (capacity >= 1),
    CONSTRAINT chk_event_dates CHECK (ends_at > starts_at)
);

CREATE INDEX idx_events_visibility_starts_at
    ON events (visibility, starts_at);

CREATE INDEX idx_events_host_id
    ON events (host_id);

package com.partaay.api.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.partaay.api.PartaayApiApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PartaayApiApplication.class)
@AutoConfigureMockMvc
class EventApiIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired JdbcTemplate jdbcTemplate;

    private final UUID hostId = UUID.fromString("11111111-1111-4111-8111-111111111111");

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM events");
        jdbcTemplate.update("DELETE FROM users");
        jdbcTemplate.update(
                "INSERT INTO users (id, display_name, email, password_hash) VALUES (?, ?, ?, 'test-hash')",
                hostId, "Demo Host", "host@partaay.example");
    }

    private String eventJson(String title, String visibility, int capacity) throws Exception {
        OffsetDateTime start = OffsetDateTime.now(ZoneOffset.UTC).plusDays(7);
        OffsetDateTime end = start.plusHours(3);
        return objectMapper.writeValueAsString(new EventRequest(
                hostId, title, "Partaay test event", start, end,
                "Community Hall", "Charlotte", EventVisibility.valueOf(visibility), capacity
        ));
    }

    @Test
    void createPublicEventReturns201AndPersistsIt() throws Exception {
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson("Saturday Party", "PUBLIC", 40)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(emptyOrNullString())))
                .andExpect(jsonPath("$.title").value("Saturday Party"))
                .andExpect(jsonPath("$.visibility").value("PUBLIC"))
                .andExpect(jsonPath("$.capacity").value(40));

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM events WHERE title = ?", Integer.class, "Saturday Party");
        org.junit.jupiter.api.Assertions.assertEquals(1, count);
    }

    @Test
    void publicListDoesNotReturnPrivateEvents() throws Exception {
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON)
                .content(eventJson("Public Party", "PUBLIC", 20)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON)
                .content(eventJson("Private Party", "PRIVATE", 10)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", hasItem("Public Party")))
                .andExpect(jsonPath("$[*].title", not(hasItem("Private Party"))));
    }

    @Test
    void privateEventCannotBeFetchedThroughPublicDetailsEndpoint() throws Exception {
        String response = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson("Private Dinner", "PRIVATE", 8)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonNode node = objectMapper.readTree(response);
        String id = node.get("id").asText();

        mockMvc.perform(get("/api/events/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsEndTimeBeforeStartTime() throws Exception {
        OffsetDateTime start = OffsetDateTime.now(ZoneOffset.UTC).plusDays(2);
        EventRequest request = new EventRequest(
                hostId, "Invalid Date Event", null, start, start.minusHours(1),
                null, null, EventVisibility.PUBLIC, 10
        );

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsBlankTitle() throws Exception {
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson("   ", "PUBLIC", 10)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsZeroCapacity() throws Exception {
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson("No Capacity", "PUBLIC", 0)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void unknownPublicEventReturns404() throws Exception {
        mockMvc.perform(get("/api/events/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}

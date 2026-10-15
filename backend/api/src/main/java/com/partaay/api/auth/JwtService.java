package com.partaay.api.auth;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.*; import java.util.*;
@Service
public class JwtService {
  private final SecretKey key; private final Duration ttl=Duration.ofDays(7);
  public JwtService(@Value("${app.jwt.secret}") String secret){ this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); }
  public String create(UserEntity u){ Instant now=Instant.now(); return Jwts.builder().subject(u.getId().toString()).claim("email",u.getEmail()).issuedAt(Date.from(now)).expiration(Date.from(now.plus(ttl))).signWith(key).compact(); }
  public UUID parseUserId(String token){ return UUID.fromString(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject()); }
}

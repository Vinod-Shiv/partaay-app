package com.partaay.api.auth;

import jakarta.servlet.http.*; import jakarta.validation.Valid;
import org.springframework.http.*; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.web.bind.annotation.*;
import java.time.*; import java.util.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
  private final UserRepository users; private final JwtService jwt; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
  public AuthController(UserRepository users, JwtService jwt){this.users=users;this.jwt=jwt;}

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r, HttpServletResponse response){
    String email=r.email().trim().toLowerCase(Locale.ROOT);
    if(users.existsByEmailIgnoreCase(email)) return ResponseEntity.status(409).body(Map.of("message","An account with this email already exists."));
    UserEntity u=users.save(new UserEntity(UUID.randomUUID(),r.name().trim(),email,encoder.encode(r.password()),OffsetDateTime.now()));
    setCookie(response,jwt.create(u)); return ResponseEntity.status(201).body(AuthResponse.from(u));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest r, HttpServletResponse response){
    var found=users.findByEmailIgnoreCase(r.email().trim());
    if(found.isEmpty() || !encoder.matches(r.password(),found.get().getPasswordHash())) return ResponseEntity.status(401).body(Map.of("message","Invalid email or password."));
    setCookie(response,jwt.create(found.get())); return ResponseEntity.ok(AuthResponse.from(found.get()));
  }

  @GetMapping("/me")
  public ResponseEntity<?> me(@CookieValue(name="partaay_session", required=false) String token){
    if(token==null) return ResponseEntity.status(401).body(Map.of("message","Not signed in."));
    try { return users.findById(jwt.parseUserId(token)).<ResponseEntity<?>>map(u->ResponseEntity.ok(AuthResponse.from(u))).orElseGet(()->ResponseEntity.status(401).build()); }
    catch(Exception e){ return ResponseEntity.status(401).body(Map.of("message","Session expired or invalid.")); }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response){ ResponseCookie c=ResponseCookie.from("partaay_session","").httpOnly(true).sameSite("Lax").path("/").maxAge(0).build(); response.addHeader(HttpHeaders.SET_COOKIE,c.toString()); return ResponseEntity.noContent().build(); }

  private void setCookie(HttpServletResponse response,String token){ ResponseCookie c=ResponseCookie.from("partaay_session",token).httpOnly(true).sameSite("Lax").path("/").maxAge(Duration.ofDays(7)).build(); response.addHeader(HttpHeaders.SET_COOKIE,c.toString()); }
}

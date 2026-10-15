package com.partaay.api.auth;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id private UUID id;
  @Column(name="display_name", nullable=false, length=120) private String displayName;
  @Column(nullable=false, unique=true, length=320) private String email;
  @Column(name="password_hash", nullable=false, length=100) private String passwordHash;
  @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;
  protected UserEntity() {}
  public UserEntity(UUID id, String displayName, String email, String passwordHash, OffsetDateTime createdAt) { this.id=id; this.displayName=displayName; this.email=email; this.passwordHash=passwordHash; this.createdAt=createdAt; }
  public UUID getId(){return id;} public String getDisplayName(){return displayName;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;} public OffsetDateTime getCreatedAt(){return createdAt;}
}

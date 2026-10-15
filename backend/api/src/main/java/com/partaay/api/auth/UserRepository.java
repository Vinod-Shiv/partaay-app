package com.partaay.api.auth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepository extends JpaRepository<UserEntity, UUID> { Optional<UserEntity> findByEmailIgnoreCase(String email); boolean existsByEmailIgnoreCase(String email); }

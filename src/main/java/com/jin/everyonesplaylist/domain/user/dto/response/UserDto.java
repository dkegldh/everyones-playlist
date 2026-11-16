package com.jin.everyonesplaylist.domain.user.dto.response;

import com.jin.everyonesplaylist.domain.user.entity.Role;
import com.jin.everyonesplaylist.domain.user.entity.User;
import java.time.Instant;
import java.util.UUID;

public record UserDto (
    UUID id,
    Instant createdAt,
    String email,
    String username,
    String profileImageUrl,
    Role role,
    boolean locked
) {
  public static UserDto from(User user) {
    return new UserDto(
        user.getId(),
        user.getCreatedAt(),
        user.getEmail(),
        user.getUsername(),
        user.getProfileImageUrl(),
        user.getRole(),
        user.isLocked()
    );
  }
}

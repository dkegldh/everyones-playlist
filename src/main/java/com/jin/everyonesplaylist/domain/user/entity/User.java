package com.jin.everyonesplaylist.domain.user.entity;

import com.jin.everyonesplaylist.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

  @Column(nullable = false)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  private String profileImageUrl;

  @Column(nullable = false)
  private boolean locked;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  private User(String username, String email, String password, Role role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.locked = false;
  }

  public static User createUser(String username, String email, String password) {
    return new User(username, email, password, Role.USER);
  }

  public void updateProfiles(String username, String profileImageUrl) {
    if(username != null) {
      this.username = username;
    }

    if(profileImageUrl != null) {
      this.profileImageUrl = profileImageUrl;
    }
  }

  public void changeRole(Role role) {
    this.role = role;
  }

  public void lock() {
    this.locked = true;
  }

  public void unlock() {
    this.locked = false;
  }
}

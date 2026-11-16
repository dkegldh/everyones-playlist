package com.jin.everyonesplaylist.domain.user.service;

import com.jin.everyonesplaylist.domain.user.dto.request.UserCreateRequest;
import com.jin.everyonesplaylist.domain.user.dto.request.UserUpdateRequest;
import com.jin.everyonesplaylist.domain.user.dto.response.UserDto;
import com.jin.everyonesplaylist.domain.user.entity.User;
import com.jin.everyonesplaylist.domain.user.exception.DuplicateEmailException;
import com.jin.everyonesplaylist.domain.user.exception.UserNotFoundException;
import com.jin.everyonesplaylist.domain.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UserDto signUp(UserCreateRequest request) {
    // TODO: 이메일 중복 확인 후 예외처리 여부 추가 예정
    String encodedPassword = passwordEncoder.encode(request.password());
    User user = User.createUser(request.username(), request.email(), encodedPassword);
    User saved = userRepository.save(user);
    return UserDto.from(saved);
  }

  public UserDto getUser(UUID userId) {
    User user = findUserOrThrow(userId);
    return UserDto.from(user);
  }

  @Transactional
  public UserDto updateProfile(UUID targetUserId, UserUpdateRequest request, MultipartFile image) {
    User user = findUserOrThrow(targetUserId);

    String imageUrl = user.getProfileImageUrl();
    final String oldImageUrl = user.getProfileImageUrl();

    if(imageUrl != null && !image.isEmpty()) {
      // TODO: 새로운 이미지 업로드 수행
    }

    user.updateProfiles(request.username(), imageUrl);

    return UserDto.from(user);
  }

  private User findUserOrThrow(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> ); // TODO: 예외처리 추가 후 수정예정
  }
}

package com.jin.everyonesplaylist.domain.user.dto.request;

import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    @Size(min = 1, max = 20)
    String username
) {

}

package com.hanghae.settlement_system.dto.user;

import com.hanghae.settlement_system.domain.user.Role;
import com.hanghae.settlement_system.domain.user.User;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private Role role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
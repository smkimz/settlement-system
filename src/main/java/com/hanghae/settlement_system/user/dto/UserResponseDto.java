package com.hanghae.settlement_system.user.dto;

import com.hanghae.settlement_system.user.domain.Role;
import com.hanghae.settlement_system.user.domain.User;
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
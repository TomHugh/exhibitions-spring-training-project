package com.sliwinski.exhibitions.dto;

import com.sliwinski.exhibitions.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private long id;
    private String username;
    private Role role;

}

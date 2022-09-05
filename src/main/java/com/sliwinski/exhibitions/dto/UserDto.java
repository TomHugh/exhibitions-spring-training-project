package com.sliwinski.exhibitions.dto;

import com.sliwinski.exhibitions.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private long id;
    private String username;
    private Role role;

}

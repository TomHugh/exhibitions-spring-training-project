package com.sliwinski.exhibitions.dto.mapper;

import com.sliwinski.exhibitions.dto.UserDto;
import com.sliwinski.exhibitions.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

//    public User toUser(UserDto userDto) {
//        return new User();
//    }
}

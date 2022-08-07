package com.sliwinski.exhibitions.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
public class UserDto {
    private long id;
    private String username;

}

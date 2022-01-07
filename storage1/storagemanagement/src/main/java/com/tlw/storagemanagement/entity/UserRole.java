package com.tlw.storagemanagement.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRole {
    private Integer id;
    private String userId;
    private String roleId;

    private User user;
    private Role role;
}

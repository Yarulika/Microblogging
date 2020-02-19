package com.sda.microblogging.common;

public enum RoleTitle {
    ADMIN,
    USER;

    // Returns RoleTitle from String case insensitive
    public static RoleTitle deserialize(String name) {
        return RoleTitle.valueOf(name.toUpperCase());
    }
}

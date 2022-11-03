package ru.anastasia.springcourse.models;

public enum Permission {
    READ("users:read"),
    WRITE("users:write");

    private final String permission;
    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

package main.model;

public enum Permission {
    USER("user:write"),
    MODERATE("user:moderate");

    private final String permission;

    Permission(final String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

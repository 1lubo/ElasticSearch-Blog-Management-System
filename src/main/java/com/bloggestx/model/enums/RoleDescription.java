package com.bloggestx.model.enums;

public enum RoleDescription {
    ADMIN("Admin of Blog"),
    USER("User of Blog");

    public final String label;

    RoleDescription(String label){
        this.label = label;
    }
}

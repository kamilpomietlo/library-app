package com.kamilpomietlo.libraryapp.model;

public enum CoverType {
    SOFT("soft"),
    HARD("hard");

    private final String displayName;

    CoverType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

package com.kamilpomietlo.libraryapp.model;

public enum CoverType {
    SOFT("Soft"),
    HARD("Hard");

    private final String displayName;

    CoverType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

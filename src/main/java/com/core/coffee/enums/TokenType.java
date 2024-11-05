package com.core.coffee.enums;

public enum TokenType {
    BEARER("Bearer"),
    REFRESH("Refresh");

    private String value;

    private TokenType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
    
}

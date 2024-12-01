package com.scoinone.core.common.type;

import lombok.Getter;

@Getter
public enum PostType {
    QNA("QUESTION"),
    ANNOUNCEMENT("ANNOUNCEMENT");

    private final String value;

    PostType(String value) {
        this.value = value;
    }
}

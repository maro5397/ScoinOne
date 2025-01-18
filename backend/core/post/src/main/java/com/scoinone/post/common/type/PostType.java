package com.scoinone.post.common.type;

import lombok.Getter;

@Getter
public enum PostType {
    QNA("question"),
    ANNOUNCEMENT("announcement"),;

    private final String value;

    PostType(String value) {
        this.value = value;
    }

    public static PostType from(String value) {
        for (PostType type : PostType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PostType value: " + value);
    }
}

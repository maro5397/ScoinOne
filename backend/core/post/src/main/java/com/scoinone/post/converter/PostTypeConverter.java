package com.scoinone.post.converter;

import com.scoinone.post.common.type.PostType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostTypeConverter implements Converter<String, PostType> {

    @Override
    public PostType convert(String source) {
        return PostType.from(source);
    }
}

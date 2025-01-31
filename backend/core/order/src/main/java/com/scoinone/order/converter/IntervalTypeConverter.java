package com.scoinone.order.converter;

import com.scoinone.order.common.status.IntervalType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IntervalTypeConverter implements Converter<String, IntervalType> {

    @Override
    public IntervalType convert(String source) {
        return IntervalType.from(source);
    }
}

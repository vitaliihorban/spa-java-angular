package com.spa.domain;

import com.spa.domain.enumeration.Direction;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Sort {
    private String field;
    private Direction direction;

    public static Sort of(String field, String direction) {
        return Sort.builder().field(field)
                .direction(direction.toUpperCase().equals(Direction.DESC.name()) ? Direction.DESC : Direction.ASC)
                .build();
    }
}

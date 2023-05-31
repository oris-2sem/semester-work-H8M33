package com.example.orisimpl.model.enums;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVATION_WAITING("ACTIVATION_WAITING"),
    ACTIVATED("ACTIVATED"),
    BANNED("BANNED");

    private final String value;
}

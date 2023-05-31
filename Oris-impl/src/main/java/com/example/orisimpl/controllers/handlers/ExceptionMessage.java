package com.example.orisimpl.controllers.handlers;

import lombok.Builder;
import lombok.Data;

/**
 * Сообщение описывающее возникшую исключительную ситуацию.
 */
@Data
@Builder
public class ExceptionMessage {

    /** Сообщение исключения */
    private String message;

    /** Наименование исключения */
    private String exceptionName;
}

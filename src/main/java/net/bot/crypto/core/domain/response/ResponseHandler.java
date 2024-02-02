package net.bot.crypto.core.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public record ResponseHandler<T>(
        HttpStatus statusCode,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data) {

    public static final String MESSAGE_SUCCESS = "success";
    public static final String MESSAGE_FAIL = "fail";

    public static <T> ResponseEntity<ResponseHandler<T>> createResponse(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status)
                .body(
                        ResponseHandler.<T>builder()
                                .statusCode(status)
                                .message(message)
                                .data(data)
                                .build()
                );
    }

}

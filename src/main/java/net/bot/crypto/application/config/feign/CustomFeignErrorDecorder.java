package net.bot.crypto.application.config.feign;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import net.bot.crypto.application.aop.exception.ExternalApiException;

import java.io.IOException;
import java.io.InputStream;

import static net.bot.crypto.application.aop.exception.ExceptionStatus.*;

@Slf4j
public class CustomFeignErrorDecorder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (isServerError(response)) {
            loggingError(response);
            handleRetryAfterHeader(response);
            throw new ExternalApiException(EXTERNAL_API_ERROR_5XX);
        }
        if (isClientError(response)) {
            loggingError(response);
            throw new ExternalApiException(EXTERNAL_API_ERROR_4XX);
        }
        loggingError(response);
        throw new ExternalApiException(EXTERNAL_API_ERROR_UNKOWN);
    }

    private boolean isClientError(Response response) {
        return response.status() >= 400 && response.status() <= 499;
    }

    private boolean isServerError(Response response) {
        return response.status() >= 500 && response.status() <= 599;
    }

    private void handleRetryAfterHeader(Response response) {
        if (response.headers().containsKey("Retry-After")) {
            String retryAfter = response.headers().get("Retry-After").iterator().next();
            throw new RetryableException(response.status(), response.reason(), response.request().httpMethod(), Long.parseLong(retryAfter), response.request());
        }
    }

    private void loggingError(Response response) {
        log.error("Feign Error: status={}, reason={}, body={}",
                response.status(), response.reason(), new String(getResponseBody(response)));
    }

    private byte[] getResponseBody(Response response) {
        try (InputStream is = response.body().asInputStream()) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read response body", e);
        }
    }

}

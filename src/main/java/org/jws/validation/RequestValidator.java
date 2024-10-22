/* (C)2024 */
package org.jws.validation;

import lombok.extern.log4j.Log4j2;
import org.jws.exception.ValidationException;
import org.jws.model.GetPageRequest;
import org.jws.model.WritePageRequest;

import static org.jws.exception.Messages.FIELD_MUST_NOT_BE_BLANK;

@Log4j2
public final class RequestValidator {
    private RequestValidator() {}

    public static void validateGetPageRequest(final GetPageRequest request) {
        validateNotBlank(request.user(), "User");
        validateNotBlank(request.namespace(), "Namespace");
        validateNotBlank(request.name(), "Name");
    }

    public static void validateWritePageRequest(final WritePageRequest request) {
        validateNotBlank(request.user(), "User");
        validateNotBlank(request.namespace(), "Namespace");
        validateNotBlank(request.name(), "Name");
        validateNotBlank(request.content(), "Content");
    }

    private static void validateNotBlank(final String fieldValue, final String fieldName) {
        if (fieldValue == null || fieldValue.isBlank()) {
            log.info(FIELD_MUST_NOT_BE_BLANK + " [{}]", fieldName);
            throw new ValidationException(FIELD_MUST_NOT_BE_BLANK + " ["+fieldName+"]");
        }
    }
}

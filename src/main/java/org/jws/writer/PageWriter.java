/* (C)2024 */
package org.jws.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jws.PagePathResolver;
import org.jws.exception.ValidationException;
import org.jws.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.jws.exception.Messages.OVERWRITE_EXISTING_EXCEPTION;

public class PageWriter {
    private final PagePathResolver pagePathResolver;
    private final ObjectMapper objectMapper;
    public PageWriter(final PagePathResolver pagePathResolver, final ObjectMapper objectMapper) {
        this.pagePathResolver = pagePathResolver;
        this.objectMapper = objectMapper;
    }
    public WritePageResponse write(final WritePageRequest writePageRequest) {
        final Path pathToWriteTo = pagePathResolver.writeRequestToFilePath(writePageRequest);
        try {
            if (new File(pathToWriteTo.toString()).exists() && !writePageRequest.overwriteExisting()) {
                throw new ValidationException(OVERWRITE_EXISTING_EXCEPTION);
            }
            // Ensure directory to write page to exist
            Files.createDirectories(pathToWriteTo.getParent());

            try (final FileWriter fileWriter = new FileWriter(pathToWriteTo.toString())) {
                final Page pageToWrite = Mapper.toPage(writePageRequest);
                fileWriter.write(objectMapper.writeValueAsString(pageToWrite));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return ImmutableWritePageResponse.builder()
                .user(writePageRequest.user())
                .namespace(writePageRequest.namespace())
                .name(writePageRequest.name())
                .build();
    }
}

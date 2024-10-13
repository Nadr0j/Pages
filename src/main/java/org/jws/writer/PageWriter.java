package org.jws.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jws.Utils;
import org.jws.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PageWriter {
    public WritePageResponse write(final WritePageRequest writePageRequest, final ObjectMapper objectMapper) {
        final Path pathToWriteTo = Utils.writeRequestToFilePath(writePageRequest);
        try {
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

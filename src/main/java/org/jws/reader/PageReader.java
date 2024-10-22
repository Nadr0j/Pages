/* (C)2024 */
package org.jws.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.jws.PagePathResolver;
import org.jws.PagesServer;
import org.jws.exception.ValidationException;
import org.jws.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static org.jws.exception.Messages.PAGE_DOES_NOT_EXIST;

@Log4j2
public class PageReader {
    private final PagePathResolver pagePathResolver;
    public PageReader(final PagePathResolver pagePathResolver) {
        this.pagePathResolver = pagePathResolver;
    }

    public GetPageResponse read(
            final GetPageRequest getPageRequest,
            final ObjectMapper objectMapper
    ) {
        final Path pathToReadFileFrom = pagePathResolver.getRequestToFilePath(getPageRequest);
        try {
            final String pageContents = Files.readString(pathToReadFileFrom);
            final Page page = objectMapper.readValue(pageContents, Page.class);
            return Mapper.toGetPageResponse(page);
        } catch (final FileNotFoundException | NoSuchFileException e) {
            log.info("Read file does not exist. {}", e.toString());
            throw new ValidationException(PAGE_DOES_NOT_EXIST);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}

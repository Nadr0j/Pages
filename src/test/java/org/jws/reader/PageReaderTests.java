/* (C)2024 */
package org.jws.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.jws.PagePathResolver;
import org.jws.exception.ValidationException;
import org.jws.model.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.jws.TestConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PageReaderTests {
    @Mock
    private PagePathResolver pagePathResolver;
    @Mock
    private ObjectMapper objectMapper;
    @TempDir
    private Path tempDir;

    @InjectMocks
    private PageReader pageReader;

    @Test
    public void read_readsFileFromCorrectLocation_whenCalled() throws IOException {
        final Path testFile = tempDir.resolve(TEST_PATH);
        // Ensure directory exist before we write contents
        Files.createDirectories(testFile.getParent());
        Files.writeString(testFile, CONTENT);

        when(pagePathResolver.getRequestToFilePath(GET_PAGE_REQUEST)).thenReturn(testFile);
        when(objectMapper.readValue(CONTENT, Page.class)).thenReturn(PAGE);

        final GetPageResponse getPageResponse = pageReader.read(GET_PAGE_REQUEST);

        assertNotNull(getPageResponse);
        assertEquals(GET_PAGE_RESPONSE, getPageResponse);
    }

    @Test
    public void read_throwsValidationException_whenFileDoesNotExist() {
        final GetPageRequest badGetPageRequest = mock(GetPageRequest.class);
        when(pagePathResolver.getRequestToFilePath(badGetPageRequest)).thenReturn(Path.of("doesn't-exist"));
        assertThrows(ValidationException.class, () -> pageReader.read(badGetPageRequest));
    }
}

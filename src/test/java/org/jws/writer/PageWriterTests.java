/* (C)2024 */
package org.jws.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.jws.PagePathResolver;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jws.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PageWriterTests {
    @Mock
    private PagePathResolver pagePathResolver;
    @Mock
    private ObjectMapper objectMapper;
    @TempDir
    private Path tempDir;

    @InjectMocks
    private PageWriter pageWriter;

    @Test
    public void write_writesFileToCorrectLocation_whenCalled() throws JsonProcessingException {
        final Path expectedPath = tempDir.resolve(TEST_PATH);
        assertFalse(Files.exists(expectedPath));

        when(pagePathResolver.writeRequestToFilePath(any())).thenReturn(expectedPath);
        when(objectMapper.writeValueAsString(any())).thenReturn(CONTENT);

        pageWriter.write(WRITE_PAGE_REQUEST);

        assertTrue(Files.exists(expectedPath));

    }
}

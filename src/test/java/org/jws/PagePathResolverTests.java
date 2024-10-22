/* (C)2024 */
package org.jws;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.jws.TestConstants.*;

public class PagePathResolverTests {
    final private PagePathResolver pagePathResolver = new PagePathResolver();
    @Test
    public void getRequestToFilePath_returnsExpectedPath_whenCalled() {
        assertEquals(TEST_PATH, pagePathResolver.getRequestToFilePath(GET_PAGE_REQUEST));
    }

    @Test
    public void writeRequestToFilePath_returnsExpectedPath_whenCalled() {
        assertEquals(TEST_PATH, pagePathResolver.writeRequestToFilePath(WRITE_PAGE_REQUEST));
    }
}

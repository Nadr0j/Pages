/* (C)2024 */
package org.jws.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.jws.model.WritePageRequest;
import org.jws.model.WritePageResponse;
import org.jws.writer.PageWriter;

import javax.inject.Inject;

@Log4j2
public class WritePageActivity {
    private final PageWriter pageWriter;
    private final ObjectMapper objectMapper;

    @Inject
    public WritePageActivity(final PageWriter pageWriter, final ObjectMapper objectMapper) {
        this.pageWriter = pageWriter;
        this.objectMapper = objectMapper;
    }

    public WritePageResponse write(final WritePageRequest request) {
        log.info("Start WritePageActivity");
        try {
            return pageWriter.write(request, objectMapper);
        } finally {
            log.info("End WritePageActivity");
        }
    }
}

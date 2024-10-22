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

    @Inject
    public WritePageActivity(final PageWriter pageWriter) {
        this.pageWriter = pageWriter;
    }

    public WritePageResponse write(final WritePageRequest request) {
        log.info("Start WritePageActivity");
        try {
            return pageWriter.write(request);
        } finally {
            log.info("End WritePageActivity");
        }
    }
}

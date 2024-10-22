/* (C)2024 */
package org.jws.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.jws.model.GetPageRequest;
import org.jws.model.GetPageResponse;
import org.jws.reader.PageReader;

import javax.inject.Inject;
import java.io.FileNotFoundException;

@Log4j2
public class GetPageActivity {
    private final PageReader pageReader;

    @Inject
    public GetPageActivity(final PageReader pageReader) {
        this.pageReader = pageReader;
    }

    public GetPageResponse get(final GetPageRequest request) throws FileNotFoundException {
        log.info("Start GetPageActivity");
        try {
            return pageReader.read(request);
        } finally {
            log.info("End GetPageActivity");
        }
    }
}

package org.jws;

import org.jws.model.GetPageRequest;
import org.jws.model.Page;
import org.jws.model.WritePageRequest;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    private static final String PATH_PREFIX = "Pages";
    public static Path getRequestToFilePath(final GetPageRequest request) {
        return Paths.get(
                PATH_PREFIX,
                request.user(),
                request.namespace(),
                request.name()
        );
    }

    public static Path writeRequestToFilePath(final WritePageRequest request) {
        return Paths.get(
                PATH_PREFIX,
                request.user(),
                request.namespace(),
                request.name()
        );
    }
}

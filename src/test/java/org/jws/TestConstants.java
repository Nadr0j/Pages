package org.jws;

import org.jws.model.GetPageRequest;
import org.jws.model.ImmutableGetPageRequest;
import org.jws.model.ImmutableWritePageRequest;
import org.jws.model.WritePageRequest;

import java.nio.file.Path;

public class TestConstants {
    public static final String PATH_PREFIX = "Pages";
    public static final String USER = "USER";
    public static final String NAMESPACE = "NAMESPACE";
    public static final String NAME = "NAME";
    public static final String CONTENT = "CONTENT";

    public static final GetPageRequest GET_PAGE_REQUEST = ImmutableGetPageRequest.builder()
            .user(USER)
            .namespace(NAMESPACE)
            .name(NAME)
            .build();

    public static final WritePageRequest WRITE_PAGE_REQUEST = ImmutableWritePageRequest.builder()
            .user(USER)
            .namespace(NAMESPACE)
            .name(NAME)
            .content(CONTENT)
            .build();

    public static final Path TEST_PATH = Path.of(PATH_PREFIX, USER, NAMESPACE, NAME);
}

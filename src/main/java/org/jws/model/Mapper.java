package org.jws.model;

public final class Mapper {
    private Mapper() {}

    public static Page toPage(final WritePageRequest request) {
        return ImmutablePage.builder()
                .user(request.user())
                .namespace(request.namespace())
                .name(request.name())
                .content(request.content())
                .build();
    }

    public static GetPageResponse toGetPageResponse(final Page page) {
        return ImmutableGetPageResponse.builder()
                .page(page)
                .build();
    }
}

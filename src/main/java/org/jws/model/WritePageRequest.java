package org.jws.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableWritePageRequest.class)
public abstract class WritePageRequest {
    public abstract String user();
    public abstract String namespace();
    public abstract String name();
    public abstract String content();
}

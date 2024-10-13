package org.jws.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableWritePageResponse.class)
public abstract class WritePageResponse extends ReturnableModel {
    public abstract String user();
    public abstract String namespace();
    public abstract String name();
}

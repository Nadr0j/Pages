package org.jws.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGetPageRequest.class)
public abstract class GetPageResponse extends ReturnableModel {
    public abstract Page page();
}

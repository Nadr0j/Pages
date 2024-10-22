/* (C)2024 */
package org.jws.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGetPageRequest.class)
public abstract class GetPageRequest {
    public abstract String user();
    public abstract String namespace();
    public abstract String name();
}

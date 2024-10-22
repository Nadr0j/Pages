/* (C)2024 */
package org.jws.dagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import org.jws.PagePathResolver;
import org.jws.reader.PageReader;
import org.jws.writer.PageWriter;

import javax.inject.Singleton;

@Module
public class PageModules {
    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public PageWriter providePageWriter(final PagePathResolver pagePathResolver, final ObjectMapper objectMapper) {
        return new PageWriter(pagePathResolver, objectMapper);
    }

    @Provides
    @Singleton
    public PageReader providerPageReader(final PagePathResolver pagePathResolver, final ObjectMapper objectMapper) {
        return new PageReader(pagePathResolver, objectMapper);
    }

    @Provides
    @Singleton
    public PagePathResolver providePagePathResolver() {
        return new PagePathResolver();
    }
}

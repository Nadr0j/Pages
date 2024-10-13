package org.jws.dagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
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
    public PageWriter providePageWriter() {
        return new PageWriter();
    }

    @Provides
    @Singleton
    public PageReader providerPageReader() {
        return new PageReader();
    }
}

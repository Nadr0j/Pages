/* (C)2024 */
package org.jws.dagger;

import dagger.Component;
import org.jws.PagesServer;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PageModules.class})
public interface PagesServerComponent {
    PagesServer pagesServer();
}

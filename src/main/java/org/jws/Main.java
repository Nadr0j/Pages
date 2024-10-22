/* (C)2024 */
package org.jws;


import org.jws.dagger.DaggerPagesServerComponent;
import org.jws.dagger.PagesServerComponent;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        final PagesServerComponent pagesServerComponent = DaggerPagesServerComponent.create();
        final PagesServer server = pagesServerComponent.pagesServer();
        server.start();
    }
}

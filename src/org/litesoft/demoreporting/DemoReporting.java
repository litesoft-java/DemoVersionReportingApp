package org.litesoft.demoreporting;

import org.litesoft.jetty.*;

public class DemoReporting {
    private static final String[] ADDITIONAL_MIME_TYPES = {
            //    "txt", "text/plain",
            //    "bin", "application/octet-stream",
    };

    public static void main( String[] args )
            throws Exception {
        System.exit(
                new EmbeddedJetty(
                        new PortServlets( 8888, new ReportingServlet(), // Servlet!
                                          "/*", ADDITIONAL_MIME_TYPES ) ).run() );
    }


}

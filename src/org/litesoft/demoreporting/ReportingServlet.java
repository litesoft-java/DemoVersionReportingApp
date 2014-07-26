package org.litesoft.demoreporting;

import org.litesoft.server.http.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ReportingServlet extends HttpServlet {
    @Override
    protected void doGet( HttpServletRequest pRequest, HttpServletResponse pResponse )
            throws ServletException, IOException {

        CacheHeaders.never( pResponse );

        pResponse.setContentType( "text/plain" );

        PrintWriter zWriter = pResponse.getWriter();

        zWriter.println( pRequest.getPathInfo() );
        zWriter.println();
        zWriter.println();
        zWriter.println("Yo Dude");
    }

}

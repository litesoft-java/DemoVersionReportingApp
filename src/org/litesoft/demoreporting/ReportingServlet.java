package org.litesoft.demoreporting;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.server.http.*;

import java8.util.function.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ReportingServlet extends HttpServlet {
    private final String mPrefix;
    private final Supplier<Report> mReportSupplier;

    public ReportingServlet( String pPrefix, Supplier<Report> pReportSupplier ) {
        mPrefix = ConstrainTo.notNull( pPrefix );
        mReportSupplier = Confirm.isNotNull( "ReportSupplier", pReportSupplier );
    }

    @Override
    protected void doGet( HttpServletRequest pRequest, HttpServletResponse pResponse )
            throws ServletException, IOException {
        String zPath = ConstrainTo.notNull( pRequest.getPathInfo(), "/" );

        CacheHeaders.never( pResponse );

        pResponse.setContentType( "text/plain" );

        PrintWriter zWriter = pResponse.getWriter();

        zWriter.println( mPrefix + zPath + "\n" + mReportSupplier.get() );
    }
}

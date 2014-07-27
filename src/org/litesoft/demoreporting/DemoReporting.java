package org.litesoft.demoreporting;

import org.litesoft.jetty.*;
import org.litesoft.jetty.Shutdown;
import org.litesoft.linuxversioneddirupdater.*;

import java8.util.function.*;

public class DemoReporting extends AppBackgroundUpdater implements Supplier<Report> {
    private static final String[] ADDITIONAL_MIME_TYPES = {
            //    "txt", "text/plain",
            //    "bin", "application/octet-stream",
    };

    public static void main( String[] args )
            throws Exception {
        System.out.println( "DemoReporting vs 0.9" );
        if ( args.length != 3 ) { // validate that we have 3 params
            System.out.println( "Please provide three parameters: URL, DeploymentVersion, CSVTargets" );
            System.exit( 1 );
        }
        String zURL = args[0];
        String zDGroup = args[1];
        String zTargets = args[2];
        System.out.println( "    URL  . : " + zURL );
        System.out.println( "    DGroup : " + zDGroup );
        System.out.println( "    Targets: " + zTargets );
        DemoReporting zReporting = new DemoReporting( zURL, zDGroup, zTargets );

        System.exit(
                new EmbeddedJetty(
                        new PortServlets( 8888, new ReportingServlet( zReporting ), // Servlet!
                                          "/*", ADDITIONAL_MIME_TYPES ) ).run() );
    }

    public DemoReporting( String pURL, String pDeploymentVersion, String pCSVTargets ) {
        super( new Updater( pURL, pDeploymentVersion, pCSVTargets ), true );
    }

    @Override
    protected void statsReady() {
        System.out.println( status() );
        if ( areCriticalUpdates() || areNonCriticalUpdates() ) {
            Shutdown.Instance.get().request( 0 );
            return;
        }
        try {
            Thread.sleep( 5000 );
        }
        catch ( InterruptedException e ) {
            // Whatever!
        }
        runAgain();
    }

    private String status() {
        StringBuilder sb = new StringBuilder();
        add( sb, areNotStarted(), "NotStarted" );
        add( sb, areFailed(), "Failed" );
        add( sb, areNonCriticalUpdates(), "Non-Critical Updates" );
        add( sb, areCriticalUpdates(), "Critical Updates" );
        add( sb, areUnfinished(), "Unfinished" );
        return "Status: " + sb;
    }

    private void add( StringBuilder sb, boolean pAdd, String pText ) {
        if ( pAdd ) {
            if ( sb.length() != 0 ) {
                sb.append( ", " );
            }
            sb.append( pText );
        }
    }

    @Override
    public Report get() {
        State zState = getState();
        return new Report( status() + "\n\n" + zState.toString() );
    }
}

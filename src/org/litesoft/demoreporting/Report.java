package org.litesoft.demoreporting;

public class Report {
    private final String mState;

    public Report( String pState ) {
        mState = pState;
    }

    public String getState() {
        return mState;
    }

    @Override
    public String toString() {
        return getState();
    }
}

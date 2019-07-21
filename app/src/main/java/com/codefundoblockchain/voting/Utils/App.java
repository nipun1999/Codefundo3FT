package com.codefundoblockchain.voting.Utils;

import android.app.Application;

public class App extends Application {
    private static App instance;

    private SessionManager sessionManager;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sessionManager = new SessionManager(this);

    }
    public static App getInstance(){
        return instance;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}

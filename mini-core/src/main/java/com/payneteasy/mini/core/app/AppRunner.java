package com.payneteasy.mini.core.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class AppRunner {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    public interface IApp {
        void run(AppContext aContext) throws Exception;
    }

    public static void runApp(String[] args, IApp aApp) {
        Logger log = LoggerFactory.getLogger(aApp.getClass());

        try {
            AppContext context = AppContext.builder()
                    .logger ( log  )
                    .args   ( args )
                    .build();

            aApp.run(context);

        } catch (Exception e) {
            log.error("Cannot start server app", e);
            System.exit(1);
        }

    }
}
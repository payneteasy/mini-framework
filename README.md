# mini framework

The simplest micro service framework

Features:
* small footprint
* fast startup time

## How to add it into your app

### Maven


```xml
<repositories>
    <repository>
        <id>pne</id>
        <name>payneteasy repo</name>
        <url>https://maven.pne.io</url>
    </repository>
</repositories>
  
<dependency>
    <groupId>com.payneteasy.mini-framework</groupId>
    <artifactId>mini-dao</artifactId>
    <version>1.0-1</version>
</dependency>
```

### Java code

## SimpleApplication.java

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payneteasy.apiservlet.GsonJettyContextHandler;
import com.payneteasy.jetty.util.*;
import com.payneteasy.mini.core.app.AppContext;
import com.payneteasy.mini.core.error.handler.ApiExceptionHandler;
import com.payneteasy.mini.core.error.handler.ApiRequestValidator;
import org.eclipse.jetty.servlet.ServletContextHandler;

import static com.payneteasy.mini.core.app.AppRunner.runApp;
import static com.payneteasy.startup.parameters.StartupParametersFactory.getStartupParameters;

public class SimpleApplication {

    public static void main(String[] args) {
        runApp(args, FxRateHttpProxyApplication::run);
    }

    private static void run(AppContext aContext) {
        IStartupConfig config = getStartupParameters(IStartupConfig.class);

        JettyServer jetty = new JettyServerBuilder()
                .startupParameters(config)
                .contextOption(JettyContextOption.SESSIONS)

                .filter("/*", new PreventStackTraceFilter())
                .servlet("/health", new HealthServlet())

                .contextListener(servletContextHandler -> configureApi(servletContextHandler, config))
                .build();

        jetty.startJetty();

    }

    private static void configureApi(ServletContextHandler aContext, IStartupConfig aConfig) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        GsonJettyContextHandler gsonHandler = new GsonJettyContextHandler(
                aContext
                , gson
                , new ApiExceptionHandler()
                , new ApiRequestValidator()
        );

        ISimpleService simpleService = new SimpleServiceImpl();
        gsonHandler.addApi("/api/simple/list"     , simpleService::list, ListRequest.class);
    }
}
```
### IStartupConfig.java

```java
import com.payneteasy.jetty.util.IJettyStartupParameters;
import com.payneteasy.startup.parameters.AStartupParameter;

public interface IStartupConfig extends IJettyStartupParameters {

    @Override
    @AStartupParameter(name = "JETTY_CONTEXT", value = "/service")
    String getJettyContext();

    @Override
    @AStartupParameter(name = "JETTY_PORT", value = "1803")
    int getJettyPort();

}
```

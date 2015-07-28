package ua.com.csltd.web.coolbt.config;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.com.csltd.web.coolbt.filters.NoCacheFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : verner
 * @since : 21.07.2015
 */
public class AppInit implements WebApplicationInitializer {


    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));

        FilterRegistration.Dynamic noCacheFilter = servletContext.addFilter("noCacheFilter", NoCacheFilter.class);
        noCacheFilter.addMappingForUrlPatterns(null, false, "/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("cachedExts", ".css,.png,.gif,.js");
        initParams.put("cachedParams", "Cache-Control|max-age=86400, must-revalidate");
        initParams.put("excludeList", "");
        initParams.put("unCachedExts", "");
        initParams.put("unCachedParams", "Cache-Control|no-store, max-age=0, must-revalidate;Pragma|no-cache;Expires|Fri, 01 Jan 1990 00:00:00 GMT");
        noCacheFilter.setInitParameters(initParams);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("cool-bt-servlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("ua.com.csltd.web.coolbt.config");
        context.register(WebConfig.class);
        return context;
    }
}

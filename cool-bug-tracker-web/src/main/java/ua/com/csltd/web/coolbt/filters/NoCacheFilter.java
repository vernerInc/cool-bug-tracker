package ua.com.csltd.web.coolbt.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : verner
 * @since : 14.03.14 12:36
 */
public class NoCacheFilter implements Filter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private List<CacheExtContainer> cacheExtContainer = new ArrayList<>();
    private List<String> excludeList;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        for (CacheExtContainer extContainer : cacheExtContainer) {
            if (extContainer.getHeaders() == null) continue;
            if (requestURI.endsWith(extContainer.getExt())) {
                String page = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.length() - extContainer.getExt().length());
                if (excludeList.contains(page)) break;

                for (Header header : extContainer.getHeaders()) {
                    httpResponse.setHeader(header.getName(), header.getValue());
                }
                break;
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        excludeList = Collections.unmodifiableList(Arrays.asList(config.getInitParameter("excludeList").split(";")));
        List unCachedExts = Collections.unmodifiableList(Arrays.asList(config.getInitParameter("unCachedExts").split(",")));
        List cachedExts = Collections.unmodifiableList(Arrays.asList(config.getInitParameter("cachedExts").split(",")));

        logger.info("HTTP-headers for unCachedExts : " + unCachedExts);
        List<Header> unCachedHeaders = parseParam(config.getInitParameter("unCachedParams"));

        logger.info("HTTP-headers for cachedExts : " + cachedExts);
        List<Header> cachedHeaders = parseParam(config.getInitParameter("cachedParams"));

        for (Object o : unCachedExts) {
            if (unCachedHeaders == null || unCachedHeaders.isEmpty() || "".equals(String.valueOf(o))) continue;
            String unCachedExt = String.valueOf(o);
            cacheExtContainer.add(new CacheExtContainer(unCachedExt, unCachedHeaders));
        }

        for (Object o : cachedExts) {
            if (cachedHeaders == null || cachedHeaders.isEmpty() || "".equals(String.valueOf(o))) continue;
            String cachedExt = String.valueOf(o);
            cacheExtContainer.add(new CacheExtContainer(cachedExt, cachedHeaders));
        }

    }

    public void destroy() {
    }

    private List<Header> parseParam(String param) {
        List<Header> headers = new ArrayList<>();
        if (param == null) return headers;
        String[] arrParam = param.split(";");
        if (arrParam.length > 0) {
            for (String s : arrParam) {
                String[] arr_s = s.split("\\|");
                String name = arr_s[0];
                String value = arr_s[1];
                logger.info("HTTP-header registred - {" + name + "}:{" + value + "}");
                headers.add(new Header(name, value));
            }
            return headers;
        } else {
            return headers;
        }
    }

    private class CacheExtContainer {
        private String ext;
        private List<Header> headers;

        private CacheExtContainer(String ext, List<Header> headers) {
            this.ext = ext;
            this.headers = headers;
        }

        private String getExt() {
            return ext;
        }

        private List<Header> getHeaders() {
            return headers;
        }

    }

    private class Header {
        private String name;
        private String value;

        private Header(String name, String value) {
            this.name = name;
            this.value = value;
        }

        private String getName() {
            return name;
        }

        private String getValue() {
            return value;
        }

    }
}

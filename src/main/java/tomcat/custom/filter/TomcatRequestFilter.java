package tomcat.custom.filter;


import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class TomcatRequestFilter implements Filter {

    private FilterConfig filterConfig = null;

    private static final Log log = LogFactory.getLog(TomcatRequestFilter.class);

    public void destroy() {
        this.filterConfig = null;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        if (filterConfig == null) {
            return;
        }


        MultiReadHttpServletRequest requestWrapper = new MultiReadHttpServletRequest((HttpServletRequest) servletRequest);

        if(requestWrapper.getRequestURI()!=null) {
            if(requestWrapper.getRequestURI().toLowerCase().contains("apikeyvalidation")) {

                Enumeration names = requestWrapper.getParameterNames();
                StringBuilder data = new StringBuilder();
                data.append("============ Custom Log ===========" + "\n");
                while (names.hasMoreElements()) {
                    String name = (String) names.nextElement();
                    data.append("Param - ").append(name).append(": ");
                    String values[] = servletRequest.getParameterValues(name);
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0) data.append(", ");
                        data.append(values[i]);
                    }
                    data.append("\n");
                }

                Enumeration<String> headerNames = requestWrapper.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        data.append("Header - ").append(name).append(": ").append(requestWrapper.getHeader(name)).append("\n");
                    }
                }
                data.append("Payload - ").append(IOUtils.toString(requestWrapper.getInputStream())).append("\n");
                data.append("============ Custom Log ===========" + "\n");
                log.debug(data.toString());

            }

        }else{
            log.debug("getRequestURI() is null");
        }

        filterChain.doFilter(requestWrapper, servletResponse);
    }

}

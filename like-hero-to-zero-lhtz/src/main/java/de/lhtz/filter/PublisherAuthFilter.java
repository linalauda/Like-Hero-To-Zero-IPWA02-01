package de.lhtz.filter;

import de.lhtz.bean.PublisherAuthBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/publisher/*")
public class PublisherAuthFilter implements Filter {

    @Inject
    private PublisherAuthBean publisherAuthBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (publisherAuthBean != null && publisherAuthBean.isLoggedIn()) {
            chain.doFilter(request, response);
        } else {
            String contextPath = httpRequest.getContextPath();
            httpResponse.sendRedirect(contextPath + "/publisher-login.xhtml.xhtml");
        }
    }
}
package de.lhtz.filter;

import de.lhtz.bean.AuthBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin/*")
public class AuthFilter implements Filter {

    @Inject
    private AuthBean authBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (authBean != null && authBean.isLoggedIn()) {
            chain.doFilter(request, response);
        } else {
            String contextPath = httpRequest.getContextPath();
            httpResponse.sendRedirect(contextPath + "/login.xhtml");
        }
    }
}
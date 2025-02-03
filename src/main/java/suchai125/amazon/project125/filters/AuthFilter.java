package suchai125.amazon.project125.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        Object user = request.getSession().getAttribute("user");
        Object cart = request.getSession().getAttribute("cart");
        // Protect route
        if (url.startsWith("/cart/") && (user == null || cart == null)) {
            // API route
            response.sendError(400,"Not logged in");
        }
        else if ((user == null || cart == null) && (url.startsWith("/cart") || url.startsWith("/delete"))) {
            response.sendRedirect("/login");
        }
        else if (url.startsWith("/login") && (user != null || cart != null)) {
            response.sendRedirect("/");
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
//        System.out.println("url : "+ url);
//        System.out.println("user : " + user + ", cart : " + cart + " , TIME : "+ (new Date()).getTime());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static util.SessionAttributeNames.AUTHENTICATED_USER;
import static util.WebPaths.*;

@WebFilter(filterName = "AuthFilter", value = {
    SIGN_IN,
    CREATE_POST,
    UPDATE_POST,
    DELETE_POST,
    PROFILE
})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException
        , IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isSignedIn = session != null && session.getAttribute(AUTHENTICATED_USER) != null;
        boolean isSignInRequest = httpRequest.getRequestURI().equals(SIGN_IN);

        if (isSignedIn || isSignInRequest) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect(SIGN_IN);
        }
    }
}

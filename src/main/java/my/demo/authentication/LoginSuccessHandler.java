package my.demo.authentication;

import my.demo.vo.UserPassport;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserPassport passport = (UserPassport) authentication.getPrincipal();
            String username = passport.getUsername();
            String password = passport.getPassword();
            System.out.println("username:" + username + " password:" + password);
            httpServletResponse.getWriter().append("{\"result\": \"success\"}");
            //response.sendRedirect("/");
        }
    }
}

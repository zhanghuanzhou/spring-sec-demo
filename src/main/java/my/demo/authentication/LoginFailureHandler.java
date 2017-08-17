package my.demo.authentication;

import my.demo.utils.PasswordUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhzho on 2017/6/13.
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String error = "";
        if(e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
            error = "用户名/密码无效";
        }else if(e instanceof AccountExpiredException){
            error = "账户过期";
        }else if(e instanceof LockedException){
            error = "账户锁定";
        }else if(e instanceof DisabledException){
            error = "账户不可用";
        }else if(e instanceof CredentialsExpiredException){
            error = "证书过期";
        }
        httpServletResponse.getWriter().append("{\"result\": \"failure\", \"error\": \"" + error + "\"}");
    }
}

package my.demo.authentication;



import my.demo.service.LoginService;
import my.demo.utils.PasswordUtil;
import my.demo.vo.UserPassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * 用户登录验证器
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private final LoginService loginService;

    public UserAuthenticationProvider(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    	String username = token.getName();
    	UserPassport passport = null;
    	if (username != null) {
    		UserDetails userDetails = loginService.loadUserByUsername(username);
    		passport = userDetails != null ? (UserPassport)userDetails : null;
    	}
    	if (passport == null) {
    		throw new UsernameNotFoundException("用户名/密码无效");
    	}else if (!passport.isEnabled()){
    		throw new DisabledException("用户已被禁用");
    	}else if (!passport.isAccountNonExpired()) {
    		throw new AccountExpiredException("账号已过期");
    	}else if (!passport.isAccountNonLocked()) {
    		throw new LockedException("账号已被锁定");
    	}else if (!passport.isCredentialsNonExpired()) {
    		throw new LockedException("凭证已过期");
    	}
        String encodedPassword = passport.getEncodedPassword();
        String passwordKey = passport.getPasswordKey();
    	String password = token.getCredentials().toString();
        PasswordUtil passwordUtil = new PasswordUtil();
        if (!passwordUtil.isPasswordValid(encodedPassword, password, passwordKey)) {
            throw new BadCredentialsException("用户名/密码无效");
        }

    	return new UsernamePasswordAuthenticationToken(passport, password,passport.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}

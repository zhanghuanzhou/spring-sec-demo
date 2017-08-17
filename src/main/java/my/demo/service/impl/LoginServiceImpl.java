package my.demo.service.impl;


import my.demo.dao.UserDAO;
import my.demo.entity.User;
import my.demo.service.LoginService;
import my.demo.vo.UserPassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userDAO.findByUserName(username);
    	UserPassport passport = null;
    	if (user != null) {
    		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    		/*List<Power> list = powerDAO.searchByRoleId(admin.getRole().getRoleId());
    		for(Power power : list){
    			StringBuffer userRole = new StringBuffer();
                userRole.append("ROLE_").append(power.getCode());
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.toString());
        		authorities.add(simpleGrantedAuthority);
    		}*/
    		// 添加权限
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    		passport = new UserPassport(user, authorities);
    	}else{
			throw new UsernameNotFoundException("Username not found");
		}
    	return passport;
    }
}

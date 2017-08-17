package my.demo.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    //@Autowired AdminService adminService;

    /**
     * 进入登录页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "/WEB-INF/login.jsp";
    }

    @RequestMapping(value = "/test")
    String test() {
        return "redirect:/";
    }
}

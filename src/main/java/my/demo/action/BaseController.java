package my.demo.action;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class BaseController {

	/**
	 * 注入的HttpServletRequest
	 */
	@Resource
	protected HttpServletRequest request;

	/**
	 * 注入的HttpServletResponse
	 */
	@Resource
	protected HttpServletResponse response;

	/**
	 * 注入的HttpSession
	 */
	@Resource
	protected HttpSession httpSession;

	/**
	 * 注入的ServletContext
	 */
	@Resource
	protected ServletContext servletContext;
}

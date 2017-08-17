package my.demo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 */
public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    /**
     * 加载Spring 配置类
     *
     * @return
     */
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class, SecurityConfig.class};
    }

    /**
     * 加载Spring MVC 配置类
     *
     * @return
     */
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DispatcherConfig.class};
    }

    /**
     * 映射Spring MVC 路径
     *
     * @return
     */
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

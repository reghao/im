package cn.reghao.im.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author reghao
 * @date 2021-12-30 12:34:26
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    private final String baseDir = "/home/reghao/opt/file/group0/";

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceLocation = String.format("file:%s/", baseDir);
        String pathPattern = "/group0/node0/**";
        registry.addResourceHandler(pathPattern).addResourceLocations(resourceLocation);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}

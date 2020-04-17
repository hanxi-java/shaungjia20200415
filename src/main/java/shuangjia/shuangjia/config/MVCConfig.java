package shuangjia.shuangjia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import shuangjia.shuangjia.interceptor.TokenInterceptor;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送 /atguigu 请求来到success
        registry.addViewController("/in").setViewName("index");

    }

    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

        };
        return adapter;
    }

    @Bean
    public HandlerInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

            //注册拦截器
     @Override
     public void addInterceptors(InterceptorRegistry registry) {
                //拦截对所有页面的请求（/**）
                registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**");
                super.addInterceptors(registry);
            }




}

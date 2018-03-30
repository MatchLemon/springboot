package com;

import com.hm.user.utils.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {


    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new ReturnHandler());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor())
                .excludePathPatterns("/user/login")
                .addPathPatterns("/**")
                .excludePathPatterns("/error");
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }
//
//    @Override
//    public void configureContentNegotiation(
//            ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false);
//    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            HttpSession session = request.getSession();
            if(session.getAttribute(Constant.SESSION_USER_KEY) != null) {
                return true;
            }
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().write("{\"code\" :401, \"msg\":\"没有登录\"}");
            response.flushBuffer();
            return false;
        }

//        @Override
//        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//
//            super.postHandle(request, response, handler, modelAndView);
//
//        }
    }
    private class ReturnHandler implements HandlerMethodReturnValueHandler {

        @Override
        public boolean supportsReturnType(MethodParameter methodParameter) {
            System.out.println(methodParameter.getParameterType());
            return true;
        }

        @Override
        public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
            System.out.println(o.toString());
        }
    }

}

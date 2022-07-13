package com.example.provider.config;

import com.example.provider.filter.UUIDFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Resource
    private UUIDFilter uuidFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自己的拦截器,并设置拦截的请求路径
        //addPathPatterns为拦截此请求路径的请求
        //excludePathPatterns为不拦截此路径的请求
        //如请求：http://www.localhost:8080/Vehicle/getUserVehicle?user_name=33
        //      /Vehicle/则为路径
        registry.addInterceptor(uuidFilter)
                .addPathPatterns("/Vehicle/*","/Order/*","/Parking/get_parking_lot")
                .excludePathPatterns("/Order/getParkingOrder",
                        "/Order/setStatus_in",
                        "/Order/setStatus_out",
                        "/Order/parking_cancellation_Order");
    }

}

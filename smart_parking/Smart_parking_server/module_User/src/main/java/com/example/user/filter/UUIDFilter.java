package com.example.user.filter;

import com.example.user.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Slf4j
@Component
public class UUIDFilter implements HandlerInterceptor {



    @Resource
    private UserServiceImpl userService;

    /**
     * 目标方法执行前
     * 该方法在控制器处理请求方法前执行，其返回值表示是否中断后续操作
     * 返回 true 表示继续向下执行，返回 false 表示中断后续操作
     */
    @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user_name = request.getParameter("user_name");
        String UUID = request.getParameter("UUID");
        if (user_name==null||UUID==null) {
            return false;
        }
        boolean b = userService.check_UUID(UUID, user_name);
        if (b) {
            return true;
        }else {
            String data = "401：请重新登陆";
            OutputStream outputStream = response.getOutputStream();// 获取输出流
            // 通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
            response.setHeader("content-type", "text/html;charset=UTF-8");
            // 将字符转换成字节数组，指定以UTF-8编码进行转换
            byte[] dataByteArr = data.getBytes("UTF-8");
            //使用OutputStream流向客户端输出字节数组
            outputStream.write(dataByteArr);
            outputStream.flush();
            outputStream.close();
            return false;
        }
    }



    /**
     * 目标方法执行后
     * 该方法在控制器处理请求方法调用之后、解析视图之前执行
     * 可以通过此方法对请求域中的模型和视图做进一步修改
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }



    /**
     * 页面渲染后
     * 该方法在视图渲染结束后执行
     * 可以通过此方法实现资源清理、记录日志信息等工作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

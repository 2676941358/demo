package com.example.filter;

import com.alibaba.fastjson.JSON;
import com.example.common.R;
import com.example.common.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Mapper
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest sq1 = (HttpServletRequest) servletRequest;
        HttpServletResponse sp1 = (HttpServletResponse) servletResponse;
        String requestURI = sq1.getRequestURI();
        log.info("拦截到请求：{}",requestURI);
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
     boolean check=check(urls,requestURI);
     if (check){
         log.info("无需处理");
         filterChain.doFilter(sq1, sp1);
         return;
    }
         if(sq1.getSession().getAttribute("employee") != null){
        log.info("用户已登录，用户id为：{}",sq1.getSession().getAttribute("employee"));
        Long empId = (Long) sq1.getSession().getAttribute("employee");
        BaseContext.setCurrentId(empId);

        filterChain.doFilter(sq1,sp1);
        return;
    }
         if(sq1.getSession().getAttribute("user") != null){
             log.info("用户已登录，用户id为：{}",sq1.getSession().getAttribute("user"));

             Long userId = (Long) sq1.getSession().getAttribute("user");
             BaseContext.setCurrentId(userId);

             filterChain.doFilter(sq1,sp1);
             return;
         }
            log.info("用户未登录");

            //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
            sp1.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return;

        }


    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
             }
        return false;

    }

}

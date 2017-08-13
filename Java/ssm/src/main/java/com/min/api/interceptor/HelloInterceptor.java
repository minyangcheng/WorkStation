package com.min.api.interceptor;

import com.min.api.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

/**
 * Created by minyangcheng on 2017/6/25.
 */
public class HelloInterceptor implements HandlerInterceptor{

    Logger logger= LoggerFactory.getLogger(HelloInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String name=request.getParameter("name");
        logger.debug("HelloInterceptor preHandle name="+name);
        if(name.equals("root")){
            ResponseUtils.renderText(response,"root is interceptor");
            return false;
        }
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
        logger.debug("HelloInterceptor postHandle");
		if(modelAndView!=null){
			logger.debug("HelloInterceptor "+modelAndView.getViewName());
		}
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
        logger.debug("HelloInterceptor afterCompletion");
	}

}

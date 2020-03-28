package com.csmaxwell.srent.admin.config;

import com.csmaxwell.srent.core.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maxwell on 2019/8/15
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {

    private final Log logger = LogFactory.getLog(ShiroExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public Object unauthenticatedHandler(AuthenticationException e) {
        logger.warn(e.getMessage(), e);
        return ResponseUtil.unlogin();
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Object unauthorizedHandler(AuthorizationException e) {
        logger.warn(e.getMessage(), e);
        return ResponseUtil.unauthz();
    }
}

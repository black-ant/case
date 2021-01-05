package com.macro.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname OAuthSuccess
 * @Description TODO
 * @Date 2020/2/23 19:43
 * @Created by zengzg
 */
@Component
public class OAuthSuccess extends SimpleUrlAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String LOCAL_SERVER_URL = "www.baidu.com";

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            System.out.println("savedRequest is null ");
            //用户判断是否要使用上次通过session里缓存的回调URL地址
            int flag = 0;
            //通过提交登录请求传递需要回调的URL callCustomRediretUrl
            if (request.getSession().getAttribute("callCustomRediretUrl") != null && !"".equals(request.getSession().getAttribute("callCustomRediretUrl"))) {
                String url = String.valueOf(request.getSession().getAttribute("callCustomRediretUrl"));
                //若session 存在则需要使用自定义回调的URL 而不是缓存的URL
                super.setDefaultTargetUrl(url);
                super.setAlwaysUseDefaultTargetUrl(true);
                flag = 1;
                request.getSession().setAttribute("callCustomRediretUrl", "");
            }
            //重设置默认URL为主页地址
            if (flag == 0) {
                super.setDefaultTargetUrl(LOCAL_SERVER_URL);
            }
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        //targetUrlParameter 是否存在
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.setAlwaysUseDefaultTargetUrl(false);
            super.setDefaultTargetUrl("/");
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        //清除属性
        clearAuthenticationAttributes(request);
        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        if (targetUrl != null && "".equals(targetUrl)) {
            targetUrl = LOCAL_SERVER_URL;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

}

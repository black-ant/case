package com.gang.comgangcasedwr.service;

import com.gang.comgangcasedwr.to.TextNotifyMessage;
import com.gang.comgangcasedwr.utils.DwrScriptSessionManagerUtil;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;

/**
 * @Classname Demo1Service
 * @Description 后端 Server 调用前端 Web
 * @Date 2021/4/8
 * @Created by zengzg
 */
@Service
@RemoteProxy
public class Demo1Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //保存scriptSession ， 这个方法需要在页面刚已加载的时候调用，为了前端和后端建立连接。
    @RemoteMethod
    public void onPageLoad(String tag) {
        logger.info("------> ask web client <-------");
        //获取当前的ScriptSession
        try {
            ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
            if (scriptSession != null) {
                scriptSession.setAttribute("tag", tag);
            }
            DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();
            dwrScriptSessionManagerUtil.init("tag", tag);
        } catch (Exception e) {

        }
        System.out.println("onPageLoad 被调用  ：" + tag);
    }

    /**
     * on msg 生效需要前端点击 onPageLoad
     *
     * @param message
     */
    public void onMessage(TextNotifyMessage message) {
        // Step 1 : 创建一个 Listern
//        DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();
//        try {
//            dwrScriptSessionManagerUtil.init();
//        } catch (ServletException e) {
//            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
//        }

        Browser.withAllSessions(new Runnable() {
            public void run() {
                logger.info("-------> NoticeMsg run() : Call Web Auto Login");
                ScriptSessions.addFunctionCall("getmessage", message.getData());
            }
        });


    }
}

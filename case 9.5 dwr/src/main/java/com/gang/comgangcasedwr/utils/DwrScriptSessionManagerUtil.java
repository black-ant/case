package com.gang.comgangcasedwr.utils;

import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 * @Classname DwrScriptSessionManagerUtil
 * @Description TODO
 * @Date 2020/9/18 11:27
 * @Created by zengzg
 */
public class DwrScriptSessionManagerUtil extends DwrServlet {
    public void init() throws ServletException {

        Container container = ServerContextFactory.get().getContainer();
        ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
        ScriptSessionListener listener = new ScriptSessionListener() {

            public void sessionCreated(ScriptSessionEvent ev) {
                HttpSession session = WebContextFactory.get().getSession();
                String userId = (String) session.getAttribute("userId");
                System.out.println("a ScriptSession is created!");
                ev.getSession().setAttribute("userId", userId);
            }

            public void sessionDestroyed(ScriptSessionEvent ev) {
                System.out.println("a ScriptSession is distroyed");
            }
        };
        manager.addScriptSessionListener(listener);
    }
}


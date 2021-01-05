package com.gang.study.myfilter.demo.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class MyServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        logger.info("------> doget <-------");
        logger.info("------> doget getContextPath {}<-------",req.getContextPath());
        logger.info("------> doget getHeaderNames {}<-------",req.getHeaderNames());
        logger.info("------> doget getPathInfo {}<-------",req.getPathInfo());
        logger.info("------> doget getSession {}<-------",req.getSession());
        logger.info("------> doget getUserPrincipal {}<-------",req.getUserPrincipal());
        logger.info("------> doget getRequestURI {}<-------",req.getRequestURI());
        logger.info("------> doget getContextPath {}<-------",req.getContextPath());

        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("------> doPost <-------");
    }
}

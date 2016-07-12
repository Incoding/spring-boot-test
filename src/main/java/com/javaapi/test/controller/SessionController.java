package com.javaapi.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class SessionController {

    @RequestMapping("/setSession")
    public void setSession(HttpServletRequest request,HttpServletResponse resp) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("nihao", request.getParameter("nihao"));
        request.setAttribute("nihao", request.getParameter("nihao"));
//        resp.sendRedirect(request.getContextPath() + "/getSession");
    }

    @RequestMapping("/getSession")
    @ResponseBody
    public String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nihao = (String) session.getAttribute("nihao");
        Object attrib = request.getAttribute("nihao");
        return nihao+":"+attrib;
    }

    @RequestMapping("/attr")
    @ResponseBody
    public String getAttr(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nihao = (String) session.getAttribute("nihao");
        Object attrib = request.getAttribute("nihao");
        return nihao+":"+attrib;
    }




}

package com.matrix.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

import com.matrix.thread.MessageSender;

public class PushServlet extends HttpServlet implements CometProcessor  {
	private static final long serialVersionUID = 1L;
	
	public static MessageSender ms = null;
	
    public PushServlet() {
        super();
    }
    
    @Override
    public void destroy() {
    	ms.stop();
    	ms = null;
    }

    @Override
    public void init() throws ServletException {
    	ms = new MessageSender();
    	Thread messageSender  = new Thread(ms);
    	messageSender.setDaemon(true);
    	messageSender.start();
    }
    
    @Override
    public void event(final CometEvent event) throws IOException, ServletException {
        HttpServletRequest request = event.getHttpServletRequest();
        HttpServletResponse response = event.getHttpServletResponse();
        
        String user = request.getParameter("user");
        String isReceive = request.getParameter("isReceive");
        System.out.println("#####################################");
        if (event.getEventType() == CometEvent.EventType.BEGIN) {
            request.setAttribute("org.apache.tomcat.comet.timeout", 60*60*1000);
            if ("".equals(isReceive) || null == isReceive) {
            	isReceive = "0";
            }
            ms.addResponse(user, response, isReceive);
            
        } else if (event.getEventType() == CometEvent.EventType.ERROR) {
            event.close();
        } else if (event.getEventType() == CometEvent.EventType.END) {
        	ms.removeResponse(user);
            event.close();
        } else if (event.getEventType() == CometEvent.EventType.READ) {
        	System.out.println("read");
        }
    }
}

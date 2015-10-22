package com.matrix.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//public static boolean flag = true;
       
    public SendMessageServlet() {
        super();
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = request.getParameter("txtMessage");
		String deviceId = request.getParameter("user").trim();
		PushServlet.ms.send(deviceId,msg);
		
		//PushServlet.ms.send(msg);
		response.sendRedirect("SendMessage.jsp");
	}
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

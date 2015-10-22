package com.like.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.JSONParser;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.matrix.restful.TestRestful;

@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String collapseKey = "collapseKey";
	private final boolean delayWhileIdle = true;
	private final boolean dryRun = true;
	private final String restrictedPackageName = "com.like.gcm_client_test";
	private final int retries = 42;
	private final int ttl = 108;
	private final String authKey = "275699305826"; //project number
	private final JSONParser jsonParser = new JSONParser();

	public SendMessageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("enter");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		String strMessage = request.getParameter("txtMessage");
		String regId = TestRestful.regIds.get(0);
		String strMessage = "cysb";
		Message message = new Message.Builder()
				.collapseKey(collapseKey).delayWhileIdle(delayWhileIdle)
				.dryRun(dryRun).restrictedPackageName(restrictedPackageName)
				.timeToLive(ttl)
				.addData("message", "cysb")
				.build();
		//api key
		Result result = new Sender("AIzaSyBKGNolhj28O9oAa1JfByA77A-AFJPUD5U").send(message, regId, 10);
		System.out.println("result:" + result.getCanonicalRegistrationId() + "   " + result.getErrorCodeName() + "   " + result.getMessageId());
	}

}

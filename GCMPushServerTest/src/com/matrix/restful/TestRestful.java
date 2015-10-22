package com.matrix.restful;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestRestful {
	public static List<String> regIds = new ArrayList<String>();
	@Path("/uploadregid")
	@POST
	@Produces(value=MediaType.TEXT_PLAIN)
	public String uploadRegId(@QueryParam("regId") String regId){
		regIds.add(regId);
		System.out.println("get " + regId);
		return "success";
	}

}

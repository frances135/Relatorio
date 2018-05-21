package br.com.relatorio.rest;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.com.relatorio.bo.Controller;

import com.google.gson.Gson;

@Path("/relatorios")
public class Relatorios implements Serializable {

	private static final long serialVersionUID = -8064800976365126037L;
	
	private static final String encoding  = "UTF-8";
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + encoding)
	public Response getLogs(@Context SecurityContext sc, 
			@QueryParam("ipCliente") String ipCliente, 
			@QueryParam("dateDe") String dateDe, 
			@QueryParam("dateAte") String dateAte,
			@QueryParam("httpMethod") String httpMethod, 
			@QueryParam("resource") String resource, 
			@QueryParam("status") String status, 
			@QueryParam("bytes") String bytes, 
			@QueryParam("principalRequest") String principalRequest, 
			@QueryParam("userAgent") String userAgent) {
		
		Controller controller = new Controller();

		return Response.status(200).entity(new Gson().toJson(controller.getLogs(ipCliente, dateDe, dateAte, httpMethod, 
			resource, status, bytes, principalRequest, userAgent))).header(
			"Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").header(
			"Pragma", "No-cache").header("Cache-Control", "no-cache,no-store,max-age=0").header("Expires", 1).build();
	}
	
	@GET
	@Path("/coletar")
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + encoding)
	public Response coletarLogs(@Context SecurityContext sc) {
		
		Controller controller = new Controller();
		controller.coletarLogsApache();

		return Response.status(200).entity(new Gson().toJson("OK")).header(
			"Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").header(
			"Pragma", "No-cache").header("Cache-Control", "no-cache,no-store,max-age=0").header("Expires", 1).build();
	}
}

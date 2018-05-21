package br.com.relatorio.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.relatorio.bo.Controller;

public class Teste extends HttpServlet {
	
	private static final long serialVersionUID = 3182306516822833534L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Controller controller = new Controller();
		
		response.getOutputStream().print(controller.getLogs(null, null, null,
			null, null, null, null, null, null).toString());
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
		
	}
}
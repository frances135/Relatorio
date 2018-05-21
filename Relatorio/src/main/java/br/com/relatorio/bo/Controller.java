package br.com.relatorio.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.relatorio.util.ConexaoMariaDB;
import br.com.relatorio.vo.Log;

public class Controller {
	
	public List<Log> getLogs(String ipCliente, 
			String dateDe, 
			String dateAte,
			String httpMethod, 
			String resource, 
			String status, 
			String bytes, 
			String principalRequest, 
			String userAgent) {
		
		String includeIpCliente = "";
		String includeDate = "";
		String includeHttpMethod = "";
		String includeResource = "";
		String includeStatus = "";
		String includeBytes = "";
		String includePrincipalRequest = "";
		String includeUserAgent = "";
		
		if (ipCliente != null && !"".equals(ipCliente)) {
			includeIpCliente = "IPV4_CLIENTE = '" + ipCliente + "' AND ";
		}
		
		if (dateDe != null && !"NaN".equals(dateDe) && dateAte != null && !"NaN".equals(dateAte)) {
			includeDate = "(DATA BETWEEN '" + new Timestamp(Long.parseLong(dateDe)) + 
				"' AND '" + new Timestamp(Long.parseLong(dateAte)) + "') AND ";
		
		} else if (dateDe != null && !"NaN".equals(dateDe)) {
			includeDate = "DATA >= '" + new Timestamp(Long.parseLong(dateDe)) + "' AND ";
		
		} else if (dateAte != null && !"NaN".equals(dateAte)) {
			includeDate = "DATA <= '" + new Timestamp(Long.parseLong(dateAte)) + "' AND ";
		}
		
		if (httpMethod != null && !"".equals(httpMethod)) {
			includeHttpMethod = "HTTP_METHOD = '" + httpMethod + "' AND ";
		}
		
		if (resource != null && !"".equals(resource)) {
			includeResource = "LOWER(RECURSO) LIKE LOWER('" + resource + "') AND ";
		}
		
		if (status != null && !"".equals(status)) {
			includeStatus = "STATUS = '" + status + "' AND ";
		}
		
		if (bytes != null && !"".equals(bytes)) {
			includeBytes = "QUANTIDADE_BYTES >= '" + bytes + "' AND ";
		}
		
		if (principalRequest != null && !"false".equals(principalRequest)) {
			includePrincipalRequest = "REQUEST_PRINCIPAL = '" + principalRequest + "'  AND ";
		}
		
		if (userAgent != null && !"".equals(userAgent)) {
			includeUserAgent = "LOWER(USER_AGENT) LIKE LOWER('" + userAgent + "') AND ";
		}
		
		List<Log> logs = new ArrayList<Log>();
		
		Connection conexao = null;
		
		try {
			conexao = ConexaoMariaDB.getDatasource().getConnection();
			
            String sql = "SELECT * FROM log";

			if (!"".equals(includeIpCliente) || 
				!"".equals(includeDate) ||
				!"".equals(includeHttpMethod) ||
				!"".equals(includeResource) ||
				!"".equals(includeStatus) ||
				!"".equals(includeBytes) ||
				!"".equals(includePrincipalRequest) ||
				!"".equals(includeUserAgent)) {
				
				sql += (" WHERE " + includeIpCliente + includeDate + includeHttpMethod + 
					includeResource + includeStatus + includeBytes + includePrincipalRequest + 
					includeUserAgent);
				
				sql = sql.substring(0, sql.length() - 5);
			}
			
			sql += " LIMIT 50";
			
			System.out.println(sql);
            
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			stmt.execute();
			
			ResultSet result = stmt.getResultSet();
			
			while(result.next()) {
				Log log = new Log();
				
				log.setIpCliente(result.getString("IPV4_CLIENTE").trim());
				log.setDate(result.getTimestamp("DATA"));
				log.setHttpMethod(result.getString("HTTP_METHOD").trim());
				log.setResource(result.getString("RECURSO").trim());
				log.setProtocol(result.getString("PROTOCOLO").trim());
				log.setStatus(result.getInt("STATUS"));
				log.setBytes(result.getInt("QUANTIDADE_BYTES"));
				log.setDomain(result.getString("DOMINIO").trim());
				log.setPrincipalRequest(result.getBoolean("REQUEST_PRINCIPAL"));
				log.setUserAgent(result.getString("USER_AGENT").trim());
				
				logs.add(log);
			}
			
			result.close();
            stmt.close();
		
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				
				} catch (Exception ignore) {}
			}
		}
		
		return logs;
	}
	
	public Date getLastLog() {
		
		Date lastLog = new Date(0);
		
		Connection conexao = null;
		
		try {
			conexao = ConexaoMariaDB.getDatasource().getConnection();
            
            String sql = "SELECT MAX(DATA) AS DATA FROM log";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			stmt.execute();
			
			ResultSet result = stmt.getResultSet();
			
			if (result.next()) {
				lastLog = new Date(result.getTimestamp("DATA").getTime());
			}
			
			result.close();
            stmt.close();
		
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				
				} catch (Exception ignore) {}
			}
		}
		
		return lastLog;
	}
	
	public void coletarLogsApache() {
		LogRead read = new LogRead();
		
		Date lastLog = getLastLog();

		for (Log log : read.leitorLog(lastLog)) {
			if (log.getDate().after(lastLog)) {
				inserirLogDB(log);
			}
		}
	}
	
	public void inserirLogDB(Log log) {
		
		Connection conexao = null;
		
		try {
			conexao = ConexaoMariaDB.getDatasource().getConnection();
            
            String sql = "INSERT INTO log (IPV4_CLIENTE, DATA, HTTP_METHOD, RECURSO, "
            	+ "PROTOCOLO, STATUS, QUANTIDADE_BYTES, DOMINIO, REQUEST_PRINCIPAL, USER_AGENT) "
            	+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			stmt.setString(1, log.getIpCliente());
			stmt.setTimestamp(2, new Timestamp(log.getDate().getTime()));
			stmt.setString(3, log.getHttpMethod());
			stmt.setString(4, log.getResource());
			stmt.setString(5, log.getProtocol());
			stmt.setInt(6, log.getStatus());
			stmt.setInt(7, log.getBytes());
			stmt.setString(8, log.getDomain());
			stmt.setBoolean(9, log.isPrincipalRequest());
			stmt.setString(10, log.getUserAgent());
			
			stmt.execute();
			
            stmt.close();
		
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				
				} catch (Exception ignore) {}
			}
		}
		
	}
}

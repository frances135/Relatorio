package br.com.relatorio.bo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Date;

import br.com.relatorio.vo.Log;

public class LogRead {
	
	private static final String logAccess = "C:\\xampp\\apache\\logs\\access.log";

	public List<Log> leitorLog(Date lastLog) {
		
		List<Log> logs = new ArrayList<Log>();
		
		try {
			
			BufferedReader buffRead = new BufferedReader(new FileReader(logAccess));
			
			String lineLog = buffRead.readLine();
			
			while (true) {
				if (lineLog != null) {
					
					Log log = new Log();
					
					log.setIpCliente(lineLog.substring(0, lineLog.indexOf(" ")));
					
					String dataLog = lineLog.substring(lineLog.indexOf("[") + 1, lineLog.lastIndexOf("]"));
					
					SimpleDateFormat formato = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
					
					Date date = null;
					try {
						date = formato.parse(dataLog);
					
					} catch (ParseException e) {}
					
					log.setDate(date);
					
					lineLog = lineLog.substring(lineLog.indexOf("\"") + 1);
					
					String request = lineLog.substring(0, lineLog.indexOf("\""));
					String[] requestInfos = request.split(" ");

					String httpMethod = "";
					String resource = "";
					String protocol = "";
					
					if (requestInfos != null && requestInfos.length == 3) {

						httpMethod = requestInfos[0];
						resource = requestInfos[1];
						protocol = requestInfos[2];
						
					}
					
					log.setHttpMethod(httpMethod);
					log.setResource(resource);
					log.setProtocol(protocol);
					
					lineLog = lineLog.substring(request.length() + 2);
					
					String statusLog = lineLog.substring(0, lineLog.indexOf(" "));
					
					int status = 0;
					
					try {
						status = Integer.parseInt(statusLog);
					} catch (Exception e) {}
					
					log.setStatus(status);
					
					String bytesLog = lineLog.substring(statusLog.length() + 1);
					bytesLog = bytesLog.substring(0, bytesLog.indexOf(" "));
					
					lineLog = lineLog.substring(lineLog.indexOf(bytesLog) + bytesLog.length() + 2);
					
					int bytes = 0;
					
					try {
						bytes = Integer.parseInt(bytesLog);
						
					} catch (Exception e) {}
					
					log.setBytes(bytes);
					
					String domain = lineLog.substring(0, lineLog.indexOf("\" "));
					
					boolean principalRequest = false;
					
					if ("-".equals(domain)) {
						principalRequest = true;
						domain = "";
					}
					
					log.setDomain(domain);
					
					log.setPrincipalRequest(principalRequest);
					
					String userAgent = lineLog.substring(lineLog.indexOf(" \"") + 2, lineLog.length() - 1);
					
					log.setUserAgent(userAgent);
					
					if (log.getDate().after(lastLog)) {
						logs.add(log);
					}
					
				} else {
					break;
				}
				
				lineLog = buffRead.readLine();
			}
			buffRead.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return logs;
	}
}
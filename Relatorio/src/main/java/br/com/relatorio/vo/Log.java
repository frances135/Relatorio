package br.com.relatorio.vo;

import java.util.Date;

public class Log {
	
	private String ipCliente;
	private Date date;
	private String httpMethod;
	private String resource;
	private String protocol;
	private int status;
	private int bytes;
	private String domain;
	private boolean principalRequest;
	private String userAgent;

	public String getIpCliente() {
		return ipCliente;
	}

	public void setIpCliente(String ipCliente) {
		this.ipCliente = ipCliente;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBytes() {
		return bytes;
	}

	public void setBytes(int bytes) {
		this.bytes = bytes;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean isPrincipalRequest() {
		return principalRequest;
	}

	public void setPrincipalRequest(boolean principalRequest) {
		this.principalRequest = principalRequest;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}

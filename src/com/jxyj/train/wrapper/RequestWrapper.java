package com.jxyj.train.wrapper;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	private String ENCODING;
	public RequestWrapper(HttpServletRequest request, String ENCODING) {
		super(request);
		this.ENCODING=ENCODING;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value=getRequest().getParameter(name);
		// TODO Auto-generated method stub
		if(value !=null){
			byte[] b;
			try {
				b = value.getBytes("ISO-8859-1");
				value=new String(b,ENCODING);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			
		}
		return value;
	}
}

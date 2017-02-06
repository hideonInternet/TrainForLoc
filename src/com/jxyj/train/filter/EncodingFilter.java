package com.jxyj.train.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.wrapper.RequestWrapper;


/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter(urlPatterns={"/*"},
initParams={
		   @WebInitParam(name="ENCODING",value="UTF-8")
})
public class EncodingFilter implements Filter {
	private String ENCODING;
    /**
     * Default constructor. 
     */
    public EncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req=(HttpServletRequest)request;
		if("GET".equalsIgnoreCase(req.getMethod())){
			req=new RequestWrapper(req, ENCODING);
		}else{
			req.setCharacterEncoding(ENCODING);
		}
		// pass the request along the filter chain
		HttpServletResponse res=(HttpServletResponse)response;
		res.setContentType("text/html; charset=UTF-8");
		chain.doFilter(req, response);
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		 ENCODING = fConfig.getInitParameter("ENCODING");
	}

}

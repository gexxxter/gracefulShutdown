package edu.slaxxx.tools;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@WebFilter
@Component
public class ConnectionHeaderFilter implements Filter {

	private boolean closeConnections = false;
	
	@Autowired
	private Environment env;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		if (servletResponse.getHeaderNames().contains("Connection")){
			chain.doFilter(request, servletResponse);
			return;
		}
		if (closeConnections) {
			servletResponse.addHeader("Connection", "close");
		} else {
			servletResponse.addHeader("Connection", "keep-alive");
			servletResponse.addHeader("Keep-Alive", "60"); // TODO: read from server props
		}
		chain.doFilter(request, servletResponse);
	}

	public void closeConnetions() {
		closeConnections = true;
	}
}

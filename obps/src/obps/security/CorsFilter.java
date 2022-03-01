package obps.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {
	@Autowired
	Environment environment;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", environment.getRequiredProperty("host_server"));
		response.setHeader("Set-Cookie", "HttpOnly; SameSite=Strict; secure; domain="
				+ environment.getRequiredProperty("host_server") + " ;path=/obps/");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");

		if (request.getHeader("Referer") != null
				&& !request.getHeader("Referer").contains(environment.getRequiredProperty("host_server").toString())) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else if (request.getHeader("Origin") != null
				&& !request.getHeader("Origin").contains(environment.getRequiredProperty("host_server").toString())) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			filterChain.doFilter(request, response);
		}
	}
}

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
//		if (request.getHeader(ORIGIN).equals("null")) {
			response.setHeader("Access-Control-Allow-Origin", environment.getRequiredProperty("host_server"));
			response.setHeader("Set-Cookie", "HttpOnly; SameSite=Strict; secure; domain="+environment.getRequiredProperty("host_server")+" ;path=/obps/");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST");
//		}
			if(!request.getHeader("Referer").contains(environment.getRequiredProperty("host_server").toString())){
				response.getWriter().print("OK");
				response.getWriter().flush();
			}
//		if (request.getMethod().equals("OPTIONS")) {
//			try {
//				response.getWriter().print("OK");
//				response.getWriter().flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
			filterChain.doFilter(request, response);
//		}
	}
}

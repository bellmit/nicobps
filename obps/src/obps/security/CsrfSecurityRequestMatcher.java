/*
 * KAUSHIK DEB NATH
 * 
 */
package obps.security;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CsrfSecurityRequestMatcher implements RequestMatcher {

	@Autowired
	private Environment env;

	private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
	private ArrayList<String> csrfUnprotectedList = new ArrayList<>();

	@Override
	public boolean matches(HttpServletRequest request) {
		try {
			csrfUnprotectedList=(new ObjectMapper()).readValue(env.getProperty("csrfUnprotected"),new TypeReference<ArrayList<String>>(){});
		} catch (Exception e) {
			System.out.println("Malformed enviroment variable \"csrfUnprotected\" list ");
		}
		for (String url : csrfUnprotectedList) {
			if ((new RegexRequestMatcher(url, null)).matches(request)) {
				return false;
			}
		}
		if (allowedMethods.matcher(request.getMethod()).matches()) {
			return false;
		}
		return true;
	}
}
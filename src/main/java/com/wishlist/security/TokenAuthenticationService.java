package com.wishlist.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wishlist.model.AuthToken;
import com.wishlist.security.impl.AuthTokenGeneratorService;
import com.wishlist.security.impl.AuthTokenService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@PropertySource("classpath:application.yml")
public class TokenAuthenticationService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String HEADER_SECURITY_TOKEN = "X-AuthToken";

	@Value("auth.success.url")
	private String defaultTargetUrl;

	@Autowired
	private AuthTokenGeneratorService authTokenGeneratorService;
	
	@Autowired
	private AuthTokenService authTokenService;
	
	@Autowired
	@Qualifier("CustomUserDetailsService")
    private UserDetailsService userDetailsService;	

	
	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
	
		final String authToken = authTokenGeneratorService.generateToken(authentication);
		
		response.addHeader(HEADER_SECURITY_TOKEN, authToken);
		//request.getRequestDispatcher(defaultTargetUrl).forward(request, response);
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		
		final String token = request.getHeader(HEADER_SECURITY_TOKEN);
		AbstractAuthenticationToken userAuthenticationToken = null;
		userAuthenticationToken = authenticateByToken(token);
		
		if (userAuthenticationToken != null) {
			
			if (userAuthenticationToken.getDetails() instanceof UserContext) {
				
				final UserContext user = (UserContext) userAuthenticationToken.getDetails();
				if (user != null) {
					return new UserAuthentication(user);
				}
			}
			
		}
		return null;

	}
	
	
	/**
	 * authenticate the user based on token
	 * 
	 * @return
	 */
	private AbstractAuthenticationToken authenticateByToken(String token) {
		if (null == token) {
			
			System.out.println("\n\n DEBUG....in get auth token:  NULL");
			return null;
		}

		AbstractAuthenticationToken authToken = null;

		try {
			String[] tokens = authTokenGeneratorService.decode(token);

			AuthToken tokenEntry = authTokenService.findUserByTokenAndSeries(
					tokens[0], tokens[1]);
			if (null == tokenEntry) {
				return null;
			}

			UserContext securityUser = new UserContext(
					tokenEntry.getUser());

			System.out.println("auth token - contxt user: " + securityUser.getUsername()  + " & password: " +  securityUser.getPassword() );
			
/*			authToken = new UsernamePasswordAuthenticationToken(
					securityUser.getUsername(), "",
					securityUser.getAuthorities());*/
			authToken = new UsernamePasswordAuthenticationToken(
					securityUser.getUsername(), securityUser.getPassword(), securityUser.getAuthorities() );
			
			authToken.setDetails(securityUser);
			
		} catch (Exception ex) {
			logger.error("Failed to authenticate user for token" + token
					+ "{ }", ex);
		}

		return authToken;
	}
	
	
	
}

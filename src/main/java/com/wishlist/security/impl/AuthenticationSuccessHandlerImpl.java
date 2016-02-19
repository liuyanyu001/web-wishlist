package com.wishlist.security.impl;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wishlist.security.UserAuthentication;
import com.wishlist.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@PropertySource("classpath:application.yml")
public class AuthenticationSuccessHandlerImpl extends
		SimpleUrlAuthenticationSuccessHandler {
	
	private static final String HEADER_SECURITY_TOKEN = "X-AuthToken";

	@Value("auth.success.url")
	private String defaultTargetUrl;

	@Autowired
	private AuthTokenGeneratorService authTokenGeneratorService;
	
	@Autowired
	@Qualifier("CustomUserDetailsService")
	private UserDetailsService userDetailsService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
	    final UserContext authenticatedUser = (UserContext) userDetailsService.loadUserByUsername(authentication.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		final String authToken = authTokenGeneratorService
				.generateToken(userAuthentication);
		
		response.addHeader(HEADER_SECURITY_TOKEN, authToken);

		//todo removed?
		request.getSession().setAttribute("userBean", authenticatedUser.getUser());
		request.getSession().setMaxInactiveInterval((int) TimeUnit.HOURS.toSeconds(48));

		request.getRequestDispatcher(defaultTargetUrl).forward(request,
				response);

	}

}


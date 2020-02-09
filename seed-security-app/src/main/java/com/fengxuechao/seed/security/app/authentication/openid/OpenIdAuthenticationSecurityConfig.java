package com.fengxuechao.seed.security.app.authentication.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author fengxuechao
 * @date 2020-02-09
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private AuthenticationSuccessHandler seedAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler seedAuthenticationFailureHandler;
	
	@Autowired
	private SocialUserDetailsService userDetailsService;
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		OpenIdAuthenticationFilter OpenIdAuthenticationFilter = new OpenIdAuthenticationFilter();
		OpenIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		OpenIdAuthenticationFilter.setAuthenticationSuccessHandler(seedAuthenticationSuccessHandler);
		OpenIdAuthenticationFilter.setAuthenticationFailureHandler(seedAuthenticationFailureHandler);
		
		OpenIdAuthenticationProvider OpenIdAuthenticationProvider = new OpenIdAuthenticationProvider();
		OpenIdAuthenticationProvider.setUserDetailsService(userDetailsService);
		OpenIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);
		
		http.authenticationProvider(OpenIdAuthenticationProvider)
			.addFilterAfter(OpenIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

}

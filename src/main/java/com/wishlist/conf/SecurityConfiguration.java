package com.wishlist.conf;

import com.wishlist.service.IUserService;
import com.wishlist.util.auth.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ComponentScan("com.wishlist")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired IUserService userService;

    @Autowired AuthUtils authUtils;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Type");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/api/auth/login").permitAll();
        http.authorizeRequests().antMatchers("/api/auth/registration").permitAll();
        http.authorizeRequests().antMatchers("/api/auth/logout").permitAll();
        http.authorizeRequests().antMatchers("/api/auth/isNickFree").permitAll();
        http.authorizeRequests().antMatchers("/api/auth/isEmailFree").permitAll();


        http.authorizeRequests().antMatchers("/api/*").authenticated()
                .and()
                .addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
                .csrf().disable();

    }

    @Autowired
    @Qualifier("UserDetailsService")
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}

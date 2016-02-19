package com.wishlist.conf;

import com.wishlist.security.impl.AuthenticationFailureHandlerImpl;
import com.wishlist.security.impl.AuthenticationSuccessHandlerImpl;
import com.wishlist.security.impl.LogoutSuccessHandlerImpl;
import com.wishlist.service.impl.MongoAuthProvider;
import com.wishlist.service.impl.UserDetailService;
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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ComponentScan("com.wishlist")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MongoAuthProvider mongoAuthProvider;

    @Autowired
    @Qualifier("CustomUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.addFilterBefore(new MyUsernamePasswordAuthenticationFilter(), MyUsernamePasswordAuthenticationFilter.class);
        http.csrf().disable().authorizeRequests()
                .antMatchers("/test","/", "/index", "/login", "/js/**", "/css/**", "/html/**")
                .permitAll().anyRequest().authenticated();

        http.formLogin()
                .failureHandler(authenticationFailureHandler())
                .successHandler(authenticationSuccessHandler())
                .loginPage("/login")
                .and().logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();

       /*
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth*//**").permitAll()
                .antMatchers("/password*//**").permitAll()
                .and();

        http.formLogin()
                .failureUrl("/?error")
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .successHandler(getAuthenticationSuccess())
                .permitAll()
                .and()

                .authorizeRequests()
                .antMatchers("/profile/me/").authenticated()
                .anyRequest().authenticated()
                .and();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll().and();

        http.rememberMe()
                .authenticationSuccessHandler(getAuthenticationSuccess())
                .key("MY_BLABLA_KEY")
                .rememberMeServices(rememberMeServices())
                .and();
         http.headers().xssProtection();

         */


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(mongoAuthProvider);
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandlerImpl();
    }

 /*   @Bean
    public RememberMeServices rememberMeServices() {
        // Key must be equal to rememberMe().key()
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices("MY_BLABLA_KEY",getUserDetailsService());
        rememberMeServices.setCookieName("remember_me_cookie");
        rememberMeServices.setParameter("remember_me_checkbox");
        rememberMeServices.setTokenValiditySeconds(2678400); // 1month
        return rememberMeServices;
    }*/


    /*@Bean
    public SavedRequestAwareAuthenticationSuccessHandler getAuthenticationSuccess() {
        AuthenticationSuccessFilter authenticationSuccess = new AuthenticationSuccessFilter();
        authenticationSuccess.setDefaultTargetUrl("/profile/me/");
        return authenticationSuccess;
    }*/
}

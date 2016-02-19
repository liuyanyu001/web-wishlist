package com.wishlist.conf;

import com.wishlist.conf.filter.AuthFilter;
import com.wishlist.conf.filter.AuthenticationSuccessFilter;
import com.wishlist.service.impl.MongoAuthProvider;
import com.wishlist.service.impl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
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
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ComponentScan("com.wishlist")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MongoAuthProvider mongoAuthProvider;

    @Autowired
    @Qualifier("UserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers( "/index", "/auth/**", "/js/**", "/css/**", "/html/**")
                .permitAll().anyRequest().authenticated();

      /*  http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth*//**").permitAll()
                .antMatchers("/password*//**").permitAll()
                .and();*/

        /*http.formLogin()
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
                .permitAll().and();*/

      /*  http.rememberMe()
                .authenticationSuccessHandler(getAuthenticationSuccess())
                .key("MY_BLABLA_KEY")
                .rememberMeServices(rememberMeServices())
                .and();*/


        //http.headers().xssProtection();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(mongoAuthProvider);
        auth.userDetailsService(userDetailsService);
    }

  /*  @Bean
    public RememberMeServices rememberMeServices() {
        // Key must be equal to rememberMe().key()
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices("MY_BLABLA_KEY",getUserDetailsService());
        rememberMeServices.setCookieName("remember_me_cookie");
        rememberMeServices.setParameter("remember_me_checkbox");
        rememberMeServices.setTokenValiditySeconds(2678400); // 1month
        return rememberMeServices;
    }*/

    @Bean
    public FilterRegistrationBean shallowEtagHeaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean UserDetailsService getUserDetailsService(){
        return new UserDetailService();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler getAuthenticationSuccess() {
        AuthenticationSuccessFilter authenticationSuccess = new AuthenticationSuccessFilter();
        authenticationSuccess.setDefaultTargetUrl("/profile/me/");
        return authenticationSuccess;
    }
}

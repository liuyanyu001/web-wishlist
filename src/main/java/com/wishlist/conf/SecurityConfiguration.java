package com.wishlist.conf;

import com.wishlist.conf.filter.AuthenticationSuccessFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .and();

        http.formLogin()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .successHandler(getAuthenticationSuccess())
                .permitAll()
                .and()

                .authorizeRequests()
                .antMatchers("/me/").authenticated()
                .anyRequest().authenticated()
                .and();
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
        http.headers().xssProtection();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler getAuthenticationSuccess() {
        AuthenticationSuccessFilter authenticationSuccess = new AuthenticationSuccessFilter();
        authenticationSuccess.setDefaultTargetUrl("/me/");
        return authenticationSuccess;
    }
}

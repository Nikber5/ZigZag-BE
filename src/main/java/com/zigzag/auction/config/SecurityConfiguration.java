package com.zigzag.auction.config;

import com.zigzag.auction.jwt.JwtConfigurer;
import com.zigzag.auction.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(PasswordEncoder passwordEncoder,
                                 UserDetailsService userDetailsService,
                                 JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/by-email", "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/index", "/lots", "/lots/*", "/users/{\\d+}").permitAll()
                .antMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/products").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/bids/*", "/lots", "/products").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/users").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .headers().frameOptions().disable();
    }
}

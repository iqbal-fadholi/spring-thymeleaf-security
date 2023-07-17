package com.iqbal.springthymeleafsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

        @Bean
        public InMemoryUserDetailsManager inmemoryUserDetailsManager() {
                UserDetails admin = User.builder()
                                .username("admin")
                                .password("{noop}admin")
                                .roles("ADMIN")
                                .build();

                UserDetails iqbal = User.builder()
                                .username("iqbal")
                                .password("{noop}iqbal")
                                .roles("ADMIN", "USER")
                                .build();

                UserDetails john = User.builder()
                                .username("john")
                                .password("{noop}john")
                                .roles("USER")
                                .build();

                return new InMemoryUserDetailsManager(admin, iqbal, john);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(configurer -> configurer
                                                .requestMatchers("/adminPage/**").hasRole("ADMIN")
                                                .requestMatchers("/userPage/**").hasRole("USER")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/showMyLoginPage")
                                                .loginProcessingUrl("/authenticateTheUser")
                                                .permitAll())
                                .logout(logout -> logout.permitAll())
                                .exceptionHandling(exception -> exception.accessDeniedPage("/accessDenied"));

                return http.build();
        }

}

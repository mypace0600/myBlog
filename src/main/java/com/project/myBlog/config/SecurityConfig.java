package com.project.myBlog.config;

import com.project.myBlog.config.oauth.CustomOAuth2AuthenticationSuccessHandler;
import com.project.myBlog.config.oauth.CustomOAuth2UserService;
import com.project.myBlog.entity.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalDetailService principalDetailService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/img/**", "/thymeleaf/**").permitAll()
                        .requestMatchers("/post/write").hasAnyRole(RoleType.ADMIN.getKey())
                        .requestMatchers("/post/edit/**").hasAnyRole(RoleType.ADMIN.getKey())
                        .requestMatchers("/post/delete").hasAnyRole(RoleType.ADMIN.getKey())
                        .requestMatchers("/post/**").permitAll()
                        .requestMatchers("/oauth/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/auth/loginForm")
                        .loginProcessingUrl("/auth/loginProc")
                        .usernameParameter("email")
                        .failureUrl("/auth/login/error")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .logoutSuccessUrl("/")
                )
                .oauth2Login(oauth2->oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/login"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(new CustomOAuth2AuthenticationSuccessHandler()))
                .exceptionHandling(exception->exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }


}

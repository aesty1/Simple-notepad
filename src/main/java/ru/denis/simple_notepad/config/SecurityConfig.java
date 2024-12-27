package ru.denis.simple_notepad.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ru.denis.simple_notepad.jwt.JwtFilter;
import ru.denis.simple_notepad.service.PeopleService;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.denis.simple_notepad")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final PeopleService service;
    private final JwtFilter filter;

    @Autowired
    public SecurityConfig(@Qualifier("peopleService") PeopleService service, JwtFilter filter) {
        this.service = service;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "auth/**", "/authenticate", "/register").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                        .build();


    }

    @Bean
    public UserDetailsService userDetailsService() {
        return service;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
     return new ProviderManager(provider());
    }

    @Bean
    public DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(service);

        return provider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }
}

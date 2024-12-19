package ru.denis.simple_notepad.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ru.denis.simple_notepad.jwt.JwtFilter;
import ru.denis.simple_notepad.service.PeopleService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final PeopleService service;
    private final JwtFilter filter;

    public SecurityConfig(@Qualifier("peopleService") PeopleService service, JwtFilter filter) {
        this.service = service;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(provider())
                .csrf(csrf -> csrf.disable())
                .build();


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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

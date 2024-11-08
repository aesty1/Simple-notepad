//package ru.denis.simple_notepad.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//    private final JwtFilter filter;
//
//    @Autowired
//    public JwtConfigurer(JwtFilter filter) {
//        this.filter = filter;
//    }
//
//    @Override
//    public void configure(HttpSecurity builder) throws Exception {
//        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//    }
//}

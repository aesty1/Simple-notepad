//package ru.denis.simple_notepad.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import ru.denis.simple_notepad.jwt.JwtProvider;
//import ru.denis.simple_notepad.rest.JwtAuthenticationResponse;
//import ru.denis.simple_notepad.rest.SignInRequest;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//    private final JwtProvider jwtProvider;
//    private final PeopleService peopleService;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//
//    public JwtAuthenticationResponse signIn(SignInRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                request.getUsername(),
//                request.getPassword()
//        ));
//
//        var user = peopleService
//                .userDetailsService()
//                .loadUserByUsername(request.getUsername());
//
//        var jwt = jwtProvider.createToken(user);
//
//        return new JwtAuthenticationResponse(jwt);
//    }
//
//}

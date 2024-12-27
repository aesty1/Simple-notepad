//package ru.denis.simple_notepad.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import ru.denis.simple_notepad.model.Person;
//import ru.denis.simple_notepad.model.Status;
//
//import java.util.Collection;
//import java.util.List;
//
//public class PersonSecurity implements UserDetails {
//    private final String username;
//    private final String password;
//
//    private final List<GrantedAuthority> authorities;
//
//    public PersonSecurity(String username, String password, List<GrantedAuthority> authorities) {
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public static UserDetails fromPerson(Person person) {
//        return new org.springframework.security.core.userdetails.User(
//                person.getUsername(), person.getPassword(),
//                person.getStatus().equals(Status.ACTIVE),
//                person.getStatus().equals(Status.ACTIVE),
//                person.getStatus().equals(Status.ACTIVE),
//                person.getStatus().equals(Status.ACTIVE),
//                person.getRole().getAuthorities()
//        );
//    }
//}

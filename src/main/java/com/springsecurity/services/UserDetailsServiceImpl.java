package com.springsecurity.services;

import com.springsecurity.entity.User;
import com.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User myUser = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Usuario no encontrado"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority role = new SimpleGrantedAuthority(myUser.getRole().name());
        authorities.add(role);

        return new org.springframework.security.core.userdetails
                .User(myUser.getUsername(), myUser.getPassword(), authorities);
    }
}

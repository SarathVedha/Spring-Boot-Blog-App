package com.vedha.blog.security;

import com.vedha.blog.entity.UserEntity;
import com.vedha.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmailOrName(username, username).orElseThrow(() -> new UsernameNotFoundException("User Not Found : " + username));

        Set<GrantedAuthority> grantedAuthorities = userEntity.getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toSet());

        return new User(userEntity.getEmail(), userEntity.getPassword(), grantedAuthorities);
    }
}

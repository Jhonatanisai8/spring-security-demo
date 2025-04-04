package com.isai.spring_security_demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isai.spring_security_demo.persistens.entitys.UserEntity;
import com.isai.spring_security_demo.persistens.entitys.repository.UserRepository;

@Service
public class UserDetailsServiceImp
        implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = repository.findUserEntityByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario: " + username + " no existe."));
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // obtencion de los roles
        userEntity.getRoles()
                .forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRolEnum().name())));
                });
        // obtecion de los permisos de los roles
        userEntity.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permisiion -> {
                    authorities.add(new SimpleGrantedAuthority(permisiion.getName()));
                });
        return new User(
                userEntity.getUserName(),
                userEntity.getUserPassword(),
                userEntity.getIsEnabled(),
                userEntity.getAccountNoExpire(),
                userEntity.getCredentialsNoExpire(),
                userEntity.getAccountNoLoked(),
                authorities);
    }
}

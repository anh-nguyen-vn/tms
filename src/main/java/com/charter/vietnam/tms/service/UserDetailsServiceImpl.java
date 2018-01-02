package com.charter.vietnam.tms.service;

import com.charter.vietnam.tms.repository.SystemUserRepository;
import com.charter.vietnam.tms.repository.entity.SystemUserEntity;
import com.charter.vietnam.tms.repository.entity.UserRoleEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Load user by Username [{}]", username);
        SystemUserEntity systemUserEntity = systemUserRepository.findByUsername(username);
        if (systemUserEntity == null || systemUserEntity.getDeleted()) {
            LOGGER.error("Can not find system user by username [{}]", username);
            throw new UsernameNotFoundException("User not found");
        }

        UserRoleEntity userRoleEntity = systemUserEntity.getUserRole();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userRoleEntity.getName()));

        UserDetails userDetails = new User(systemUserEntity.getUsername(), systemUserEntity.getPassword(), grantedAuthorities);
        LOGGER.info("Found a system user by username");

        return userDetails;
    }
}

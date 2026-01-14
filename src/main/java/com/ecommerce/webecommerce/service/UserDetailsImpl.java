package com.ecommerce.webecommerce.service;

import java.util.List;

import com.ecommerce.webecommerce.common.errors.UserNotFoundException;
import com.ecommerce.webecommerce.entity.Role;
import com.ecommerce.webecommerce.entity.User;
import com.ecommerce.webecommerce.model.UserInfo;
import com.ecommerce.webecommerce.repository.RoleRepository;
import com.ecommerce.webecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByKeyword(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        List<Role> roles = roleRepository.findByUserId(user.getUserId());

        return UserInfo.builder()
                .roles(roles)
                .user(user)
                .build();
    }
}
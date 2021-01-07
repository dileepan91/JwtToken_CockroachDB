package com.tracker.security;

import com.tracker.db.UserDetailsRepository;
import com.tracker.db.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Users> trackerUserDetailsOptional = userDetailsRepository.findById(userName);
        if (trackerUserDetailsOptional.isPresent()) {
            final Users trackerUserDetails = trackerUserDetailsOptional.get();
            return new User(trackerUserDetails.getUsername(), passwordEncoder.encode(trackerUserDetails.getPassword()), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }
    }
}

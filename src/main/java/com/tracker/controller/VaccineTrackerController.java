package com.tracker.controller;

import com.tracker.db.UserDetailsRepository;
import com.tracker.jwt.JwtTokenUtils;
import com.tracker.model.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VaccineTrackerController {

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public String getToken(@RequestBody TokenRequest tokenRequest) throws Exception {
        authenticate(tokenRequest.getUsername(),tokenRequest.getPassword());
        Map<String, Object> claims = new HashMap<>();
        return jwtTokenUtils.generateToken(claims,tokenRequest.getUsername());
    }

    @RequestMapping(value = "/getDetails", method = RequestMethod.GET)
    public String getDetails() throws Exception {
        return "Hello World";
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

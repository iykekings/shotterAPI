package dev.ikeze.Shotter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.util.JwtUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestHelper {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  static String generateToken(Owner owner) {
        JwtUtil jwtUtil = new JwtUtil();
        final UserDetails userDetails = new User(owner.getEmail(), owner.getPassword(), new ArrayList<>());
        Map<String, Object> claims = new HashMap<>();
        claims.put("Id", owner.getOwnerid());
        return jwtUtil.generateToken(userDetails, claims);
    }
}

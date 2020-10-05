package com.pyruz.rest.secured.security;

import com.pyruz.rest.secured.configuration.ApplicationProperties;
import com.pyruz.rest.secured.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private String secretKey;
    final UsersDetails usersDetails;
    final ApplicationProperties applicationProperties;

    public JwtTokenProvider(UsersDetails usersDetails, ApplicationProperties applicationProperties) {
        this.usersDetails = usersDetails;
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString("my-secret-key".getBytes());
    }

    public String createToken(String username, User user) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        user.getAccesses().forEach(i -> i.getApis().forEach(j -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(j.getRole().equalsIgnoreCase("") ? "-" : j.role))));
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", simpleGrantedAuthorities);

        Date now = new Date();
        Date validity = new Date(now.getTime() + Long.parseLong(applicationProperties.getProperty("expire-length")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = usersDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return true;
    }

}

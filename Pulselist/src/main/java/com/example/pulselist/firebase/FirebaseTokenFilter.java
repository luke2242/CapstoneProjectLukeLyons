package com.example.pulselist.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter{

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        // Gets our header so we can authorize user
        String header = req.getHeader("Authorization");


        if(header != null && header.startsWith("Bearer ")){
            // Retreives the token from the header
            String token = header.substring(7);

            try{
                FirebaseToken decodeToken = firebaseAuth.verifyIdToken(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        decodeToken.getUid(), null, List.of()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception err){
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Firebase Token is Invalid!");
                return;
            }

            filterChain.doFilter(req, res);
        }




    }

}
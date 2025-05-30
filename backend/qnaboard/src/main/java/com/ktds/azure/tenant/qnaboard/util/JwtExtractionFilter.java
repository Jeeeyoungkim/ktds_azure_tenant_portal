package com.ktds.azure.tenant.qnaboard.util;
import com.ktds.azure.tenant.qnaboard.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
@Component
public class JwtExtractionFilter extends OncePerRequestFilter {
    private static final String AUTH_COOKIE_NAME = "Authorization";
    private String JWT_SECRET = null;

    public JwtExtractionFilter(@Value("${JWT_SECRET}") String jwtSecret) {
        this.JWT_SECRET = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractTokenFromCookies(request);

       if (token != null) {
           try {
               // Extract claims from token
               Claims claims = Jwts.parser()
                       .setSigningKey(JWT_SECRET.getBytes())
                       .parseClaimsJws(token)
                       .getBody();

               // Extract user information
               String name = claims.get("name", String.class);
               String email = claims.get("email", String.class);
               String role = claims.get("role", String.class);

               // Store in session
               HttpSession session = request.getSession();
               session.setAttribute("userName", name);
               session.setAttribute("userEmail", email);
               session.setAttribute("userRole", role);

               // Create a user object if you prefer
               UserInfo userInfo = new UserInfo(name, email, role);
               session.setAttribute("userInfo", userInfo);

           } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                    SignatureException | IllegalArgumentException e) {
               // Return error response
               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               response.getWriter().write("{\"success\": false, \"message\": \"Invalid token\"}");
               response.setContentType("application/json");
               return;
           }
       } else {
           // No token found, you can either continue or return error based on your requirements
           // Create a user object if you prefer
//            System.out.println("생겼다.");
//            HttpSession session = request.getSession();
//            UserInfo userInfo = new UserInfo("ㅁㅁㅁ", "ㅁㅁㅁ@kt.com", "ROLE_USER");
//            session.setAttribute("userInfo", userInfo);

           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.getWriter().write("{\"success\": false, \"message\": \"No token provided\"}");
           response.setContentType("application/json");
           return;
       }

        HttpSession session = request.getSession();
        UserInfo userInfo = new UserInfo("김지영(Cloud사업2팀)", "jeeeyoung.kim@kt.com", "ROLE_USER");
        session.setAttribute("userInfo", userInfo);

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (AUTH_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue(); // Return JWT token from cookie
                }
            }
        }
        return null; // No valid cookie found
    }
}
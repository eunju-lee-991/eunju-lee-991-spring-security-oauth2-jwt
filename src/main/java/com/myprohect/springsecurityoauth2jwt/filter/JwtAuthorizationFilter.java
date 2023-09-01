package com.myprohect.springsecurityoauth2jwt.filter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myprohect.springsecurityoauth2jwt.authorization.PrincipalDetails;
import com.myprohect.springsecurityoauth2jwt.constant.JwtConstant;
import com.myprohect.springsecurityoauth2jwt.model.User;
import com.myprohect.springsecurityoauth2jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JWTVerifier jwtVerifier;

    private UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JWTVerifier jwtVerifier, UserService userService) {
        super(authenticationManager);
        this.jwtVerifier = jwtVerifier;
        this.userService = userService;

        System.out.println("JwtAuthorizationFilter created");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
        System.out.println("JwtAuthorizationFilter  doFilterInternal");

        String authHeader = request.getHeader(JwtConstant.HEADER_AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(JwtConstant.TOKEN_PREFIX)) {
            String accessToken = authHeader.replace(JwtConstant.TOKEN_PREFIX, "");

            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            Integer id = Integer.parseInt(decodedJWT.getSubject()) ;

            User user = userService.findUser(id);
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}

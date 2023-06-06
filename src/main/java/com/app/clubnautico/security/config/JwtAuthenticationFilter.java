package com.app.clubnautico.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Para lograr que esta clase se active cada vez que se realiza un request
 * necesita extender de OncePerRequestFilter o  implementar GenericFilterBean
 */
@Component //le indica a spring que esta clase sera de tipo Bean
@RequiredArgsConstructor //crea un constructor usando cualquier campo de tipo final que se declare dentro de la clase
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //clase para manipular el JWT
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        //el token siempre se pasa por el header de la request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        //Jwt service para extraer el nombre usaurio
        final String userEmail;

        //check JWT token, siempre comienza con la palabara 'Bearer'
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            //pasar el request, response hacia el proximo filter
            filterChain.doFilter(request, response);
            return;
        }

        //extraer el token del authHeader despues del 'Bearer '
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); //extract the userEmail from JWT token;
        //comprobar si no es nulo y que user aun no esta autenticado
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //comprobar si el token es valido
            if(jwtService.isTokenValid(jwt,userDetails)){
                //este objeto es necesitado po el securityContextHolder para actualizar el context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //actualizar el securityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request,response);

    }
}

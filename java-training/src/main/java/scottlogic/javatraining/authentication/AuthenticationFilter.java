package scottlogic.javatraining.authentication;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import scottlogic.javatraining.services.UserService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final AuthTokenUtils tokenUtils;

    public AuthenticationFilter(UserService userService, AuthTokenUtils tokenUtils) {
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String bearer = "Bearer ";

        String username = null;
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(bearer)) {
            token = authorizationHeader.substring(bearer.length());
            username = tokenUtils.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            if (tokenUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePwdAuthToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                usernamePwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePwdAuthToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

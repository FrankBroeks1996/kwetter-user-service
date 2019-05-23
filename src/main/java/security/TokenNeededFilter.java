package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import models.User;
import services.UserService;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;

@Provider
@TokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class TokenNeededFilter implements ContainerRequestFilter {

    @Inject
    private IKeyGenerator keyGenerator;

    @EJB
    private UserService userService;


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isValidTokenString(authorizationHeader)) {
            stopUnauthorizedRequest(containerRequestContext);
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            final String username = validateToken(token);

            final SecurityContext currentSecurityContext = containerRequestContext.getSecurityContext();
            containerRequestContext.setSecurityContext(new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return () -> username;
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public boolean isUserInRole(String role) {
                    User user = userService.getUserByName(username);
                    return user.getRole().toString().equals(role);
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });
        } catch (Exception e) {
            stopUnauthorizedRequest(containerRequestContext);
        }
    }

    private boolean isValidTokenString(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith("Bearer".toLowerCase() + " ");
    }

    private void stopUnauthorizedRequest(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                "Bearer")
                        .build());
    }

    private String validateToken(String token) {
        Key key = keyGenerator.generateKey();
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return jws.getBody().getSubject();
    }
}

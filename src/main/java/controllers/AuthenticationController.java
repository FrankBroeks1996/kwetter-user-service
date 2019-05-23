package controllers;

import dtos.RegisterDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;
import security.KwetterKeyGenerator;
import services.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("authentication")
public class AuthenticationController {

    @Inject
    private KwetterKeyGenerator keyGenerator;

    @Inject
    private UserService userService;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(RegisterDTO registerDTO){
        try{
            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(registerDTO.getPassword());
            if(userService.login(user)) {
                String token = issueToken(user.getUsername());

                return Response.ok().header(AUTHORIZATION, "Bearer " + token).header("Access-Control-Expose-Headers", "Authorization").build();
            }else{
                return Response.status(UNAUTHORIZED).build();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterDTO registerDTO){
        try {
            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(registerDTO.getPassword());
            userService.addUser(user);

            String token = issueToken(user.getUsername());
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).header("Access-Control-Expose-Headers", "Authorization").build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private String issueToken(String username){
        Key key = keyGenerator.generateKey();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date newDate = calendar.getTime();

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(newDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }
}

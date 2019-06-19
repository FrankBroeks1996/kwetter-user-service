package controllers;

import dtos.UserDTO;
import models.User;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import security.TokenNeeded;
import services.UserService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.*;

@Path("user")
public class UserController {

    @EJB
    UserService userService;

    @Context
    SecurityContext context;

    public UserController(){}

    @GET
    @Path("getUserById/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") String userId){
        return Response.ok().entity(new UserDTO(userService.getUserById(UUID.fromString(userId)))).build();
    }

    @GET
    @Path("getUserByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUsername(@PathParam("username") String username){
        return Response.ok().entity(new UserDTO(userService.getUserByUsername(username))).build();
    }

    @PATCH
    @Path("editUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TokenNeeded
    public Response editUser(UserDTO userDTO){
        try {
            User user = userService.getUserById(UUID.fromString(userDTO.getUsername()));
            user.setBio(userDTO.getBio());
            user.setLocation(userDTO.getLocation());
            user.setWebsite(userDTO.getWebsite());
            userService.editUser(user);
            userDTO = new UserDTO(user);
            return Response.ok().entity(userDTO).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    @POST
    @Path("followUser/{userId}")
    @TokenNeeded
    public Response followUser(@PathParam("userId") UUID userId){
        Principal principal = context.getUserPrincipal();
        User currentUser = userService.getUserById(UUID.fromString(principal.getName()));
        User userToBeFollowed = userService.getUserById(userId);

        userService.followUser(currentUser, userToBeFollowed);

        return Response.ok().build();
    }

    @POST
    @Path("unFollowUser/{userId}")
    @TokenNeeded
    @Transactional
    public Response unFollowUser(@PathParam("userId") UUID userId){
        Principal principal = context.getUserPrincipal();
        User currentUser = userService.getUserById(UUID.fromString(principal.getName()));
        User userToBeUnfollowed = userService.getUserById(userId);

        userService.unFollowUser(currentUser, userToBeUnfollowed);

        return Response.ok().build();
    }

    @GET
    @Path("isFollowing/{userId}")
    @TokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response isFollowing(@PathParam("userId") String userId){
        Principal principal = context.getUserPrincipal();
        User currentUser = userService.getUserById(UUID.fromString(principal.getName()));
        User checkUser = userService.getUserById(UUID.fromString(userId));

        return Response.ok().entity(userService.isFollowing(currentUser, checkUser)).build();
    }

    @POST
    @Path("editUserRole")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUserRole(UserDTO userDTO){
        User user = new User(userDTO);
        userService.editUser(user);

        return Response.ok().build();
    }

    @GET
    @Path("getFollowing/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFollowing(@PathParam("userId") UUID userId){
        User currentUser = userService.getUserById(userId);

        List<UserDTO> usersFollowingDtoList = new ArrayList<>();
        for (User user : userService.getAllFollowing(currentUser)){
            usersFollowingDtoList.add(new UserDTO(user));
        }

        return Response.ok().entity(usersFollowingDtoList).build();
    }

    @GET
    @Path("getFollowers/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFollowers(@PathParam("username") String username){
        User currentUser = userService.getUserById(UUID.fromString(username));

        List<UserDTO> usersFollowerDtoList = new ArrayList<>();
        for (User user : userService.getAllFollowers(currentUser)){
            usersFollowerDtoList.add(new UserDTO(user));
        }

        return Response.ok().entity(usersFollowerDtoList).build();
    }

    @POST
    @Path("getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUsers(List<UUID> authors){
        List<UserDTO> userDtos = new ArrayList<>();
        for (User user : userService.getUsers(authors)){
            userDtos.add(new UserDTO(user));
        }

        return Response.ok().entity(userDtos).build();
    }

    @GET
    @Path("search/{searchQuery}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("searchQuery") String searchQuery, @QueryParam("resultPage") int resultPage, @QueryParam("resultSize") int resultSize){
        List<UserDTO> searchResult = new ArrayList<>();

        List<User> users = userService.getSearchResult(searchQuery, resultPage, resultSize);

        for (User user : users){
            searchResult.add(new UserDTO(user));
        }

        return Response.ok().entity(searchResult).build();
    }
}

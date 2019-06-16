package controllers;

import dtos.UserDTO;
import models.User;
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
    public UserDTO getUserById(@PathParam("userId") String userId){
        return new UserDTO(userService.getUserById(UUID.fromString(userId)));
    }

    @GET
    @Path("getUserByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getUserByUsername(@PathParam("username") String username){
        return new UserDTO(userService.getUserByUsername(username));
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
    public void followUser(@PathParam("userId") UUID userId){
        Principal principal = context.getUserPrincipal();
        User currentUser = userService.getUserById(UUID.fromString(principal.getName()));
        User userToBeFollowed = userService.getUserById(userId);

        userService.followUser(currentUser, userToBeFollowed);
    }

    @POST
    @Path("unFollowUser/{userId}")
    @TokenNeeded
    public void unFollowUser(@PathParam("userId") UUID userId){
        Principal principal = context.getUserPrincipal();
        User currentUser = userService.getUserById(UUID.fromString(principal.getName()));
        User userToBeUnfollowed = userService.getUserById(userId);

        userService.unFollowUser(currentUser, userToBeUnfollowed);
    }

    @GET
    @Path("isFollowing/{userId}")
    @TokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public boolean isFollowing(@PathParam("userId") String userId){
        Principal principal = context.getUserPrincipal();
        User currentUser = userService.getUserById(UUID.fromString(principal.getName()));
        User checkUser = userService.getUserById(UUID.fromString(userId));

        return userService.isFollowing(currentUser, checkUser);
    }

    @POST
    @Path("editUserRole")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editUserRole(UserDTO userDTO){
        User user = new User(userDTO);
        userService.editUser(user);
    }

    @GET
    @Path("getFollowing/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllFollowing(@PathParam("userId") UUID userId){
        User currentUser = userService.getUserById(userId);

        List<UserDTO> usersFollowingDtoList = new ArrayList<>();
        for (User user : userService.getAllFollowing(currentUser)){
            usersFollowingDtoList.add(new UserDTO(user));
        }

        return usersFollowingDtoList;
    }

    @GET
    @Path("getFollowers/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllFollowers(@PathParam("username") String username){
        User currentUser = userService.getUserById(UUID.fromString(username));

        List<UserDTO> usersFollowerDtoList = new ArrayList<>();
        for (User user : userService.getAllFollowers(currentUser)){
            usersFollowerDtoList.add(new UserDTO(user));
        }

        return usersFollowerDtoList;
    }

    @POST
    @Path("getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<UserDTO> getUsers(List<UUID> authors){
        List<UserDTO> userDtos = new ArrayList<>();
        for (User user : userService.getUsers(authors)){
            userDtos.add(new UserDTO(user));
        }

        return userDtos;
    }

    @GET
    @Path("search/{searchQuery}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> search(@PathParam("searchQuery") String searchQuery, @QueryParam("resultPage") int resultPage, @QueryParam("resultSize") int resultSize){
        List<UserDTO> searchResult = new ArrayList<>();

        List<User> users = userService.getSearchResult(searchQuery, resultPage, resultSize);

        for (User user : users){
            searchResult.add(new UserDTO(user));
        }

        return searchResult;
    }
}

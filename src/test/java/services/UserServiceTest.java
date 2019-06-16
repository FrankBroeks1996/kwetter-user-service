package services;

import daos.UserDAOMemoryImpl;
import database.memory.InMemoryDatabase;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService _userService;

    @Before
    public void init(){
        this._userService = new UserService();
        this._userService.setUserDAO(new UserDAOMemoryImpl());
    }

    @After
    public void cleanUp(){
        InMemoryDatabase.getInMemoryDatabase().cleanUp();
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("User");
        _userService.addUser(user);

        assertEquals(1, _userService.getAllUsers().size());
    }

    @Test
    public void getUserById(){
        User user = new User();
        user.setUsername("User");
        _userService.addUser(user);

        User user2 = _userService.getUserById(user.getId());
        assertEquals("User", user2.getUsername());
        assertEquals(user.getId(), user2.getId());
    }

    @Test
    public void getUserByUsername() {
        User user = new User();
        user.setUsername("User");
        _userService.addUser(user);

        assertEquals("User", _userService.getUserByUsername("User").getUsername());
    }

    @Test
    public void editUser() {
        User user = new User();
        user.setUsername("User");
        user.setBio("Bio");
        _userService.addUser(user);

        user = _userService.getUserByUsername("User");
        user.setBio("New bio");
        _userService.editUser(user);

        assertEquals("New bio", _userService.getUserByUsername("User").getBio());
    }

    @Test
    public void login() {
        User user = new User();
        user.setUsername("User");
        user.setPassword("Pass");
        _userService.addUser(user);

        assertNotNull(_userService.login("User", "Pass"));
    }

    @Test
    public void getAllFollowing() {
        User user1 = new User();
        user1.setUsername("User1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("User2");
        _userService.addUser(user2);

        assertEquals(0, _userService.getUserByUsername("User1").getFollowing().size());

        _userService.followUser(user1, user2);

        assertEquals(1, _userService.getUserByUsername("User1").getFollowing().size());
    }

    @Test
    public void getAllFollowers() {
        User user1 = new User();
        user1.setUsername("User1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("User2");
        _userService.addUser(user2);

        assertEquals(0, _userService.getUserByUsername("User2").getFollowers().size());

        _userService.followUser(user1, user2);

        assertEquals(1, _userService.getUserByUsername("User2").getFollowers().size());
    }

    @Test
    public void followUser() {
        User user1 = new User();
        user1.setUsername("User1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("User2");
        _userService.addUser(user2);

        assertEquals(0, _userService.getUserByUsername("User1").getFollowing().size());
        assertEquals(0, _userService.getUserByUsername("User2").getFollowers().size());

        _userService.followUser(user1, user2);

        assertEquals(1, _userService.getUserByUsername("User1").getFollowing().size());
        assertEquals(1, _userService.getUserByUsername("User2").getFollowers().size());
    }

    @Test
    public void unFollowUser(){
        User user1 = new User();
        user1.setUsername("User1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("User2");
        _userService.addUser(user2);

        assertEquals(0, _userService.getUserByUsername("User1").getFollowing().size());
        assertEquals(0, _userService.getUserByUsername("User2").getFollowers().size());

        _userService.followUser(user1, user2);

        assertEquals(1, _userService.getUserByUsername("User1").getFollowing().size());
        assertEquals(1, _userService.getUserByUsername("User2").getFollowers().size());

        _userService.unFollowUser(user1, user2);

        assertEquals(0, _userService.getUserByUsername("User1").getFollowing().size());
        assertEquals(0, _userService.getUserByUsername("User2").getFollowers().size());
    }

    @Test
    public void getAllUsers(){
        User user1 = new User();
        user1.setUsername("User1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("User2");
        _userService.addUser(user2);

        User user3 = new User();
        user2.setUsername("User3");
        _userService.addUser(user3);

        assertEquals(3, _userService.getAllUsers().size());
    }

    @Test
    public void isFollowing(){
        User user1 = new User();
        user1.setUsername("User1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("User2");
        _userService.addUser(user2);

        assertFalse(_userService.isFollowing(user1, user2));

        _userService.followUser(user1, user2);

        assertTrue(_userService.isFollowing(user1, user2));
    }

    @Test
    public void getUsers(){
        User user1 = new User();
        user1.setUsername("User1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("User2");
        _userService.addUser(user2);

        User user3 = new User();
        user2.setUsername("User3");
        _userService.addUser(user3);

        List<UUID> users = new ArrayList<>();
        users.add(user1.getId());
        users.add(user2.getId());

        assertEquals(2, _userService.getUsers(users).size());
    }

    @Test
    public void getSearchResult(){
        User user1 = new User();
        user1.setUsername("TestUser1");
        _userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("TestUser2");
        _userService.addUser(user2);

        User user3 = new User();
        user3.setUsername("Test3");
        _userService.addUser(user3);

        assertEquals(2, _userService.getSearchResult("User", 1, 10).size());
    }
}

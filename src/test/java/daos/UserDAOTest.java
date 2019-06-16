package daos;

import models.User;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;

import javax.ejb.EJB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserDAOTest {

    @EJB
    private IUserDAO _userDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void addUser() {
        User user = new User();
        user.setUsername("Username1");
        user.setBio("Bio");
        _userDAO.addUser(user);

        assertEquals("Username1", _userDAO.getUserById(user.getId()).getUsername());
        assertEquals("Bio", _userDAO.getUserById(user.getId()).getBio());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void editUser() {
        User user = new User();
        user.setUsername("Username2");
        user.setBio("Bio");
        _userDAO.addUser(user);

        user.setUsername("NewUsername");
        _userDAO.editUser(user);

        assertEquals("NewUsername", _userDAO.getUserById(user.getId()).getUsername());
        assertEquals("Bio", _userDAO.getUserById(user.getId()).getBio());

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getUserByUsername() {
        User user = new User();
        user.setUsername("Username3");
        _userDAO.addUser(user);

        User user2 = _userDAO.getUserByUsername(user.getUsername());

        assertEquals("Username3", user2.getUsername());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getUserById(){
        User user = new User();
        user.setUsername("Username");
        _userDAO.addUser(user);

        User user2 = _userDAO.getUserById(user.getId());

        assertEquals(user.getId(), user2.getId());
        assertEquals("Username", user2.getUsername());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void followUser() {
        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        _userDAO.addUser(user1);
        _userDAO.addUser(user2);

        _userDAO.followUser(user1, user2);

        assertEquals(1, user2.getFollowers().size());
        assertEquals(0, user1.getFollowers().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void unFollowUser(){
        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        _userDAO.addUser(user1);
        _userDAO.addUser(user2);

        _userDAO.followUser(user1, user2);

        assertEquals(1, user2.getFollowers().size());
        assertEquals(0, user1.getFollowers().size());

        _userDAO.unFollowUser(user1, user2);

        assertEquals(0, user2.getFollowers().size());
        assertEquals(0, user1.getFollowers().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllFollowers() {
        User user1 = new User();
        user1.setUsername("User3");

        User user2 = new User();
        user2.setUsername("User4");

        _userDAO.addUser(user1);
        _userDAO.addUser(user2);

        _userDAO.followUser(user1, user2);

        assertEquals(1, _userDAO.getAllFollowers(user2).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllFollowing() {
        User user1 = new User();
        user1.setUsername("User5");

        User user2 = new User();
        user2.setUsername("User6");

        _userDAO.addUser(user1);
        _userDAO.addUser(user2);

        _userDAO.followUser(user1, user2);

        assertEquals(1, _userDAO.getAllFollowing(user1).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllUsers(){
        User user1 = new User();
        user1.setUsername("User7");

        User user2 = new User();
        user2.setUsername("User8");

        _userDAO.addUser(user1);
        _userDAO.addUser(user2);

        List<User> users = _userDAO.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void isFollowing(){
        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        _userDAO.addUser(user1);
        _userDAO.addUser(user2);

        assertFalse(_userDAO.isFollowing(user1, user2));

        _userDAO.followUser(user1, user2);

        assertTrue(_userDAO.isFollowing(user1, user2));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void login() {
        User user1 = new User();
        user1.setUsername("user");
        user1.setPassword("pass");
        _userDAO.addUser(user1);

        assertNotNull(_userDAO.login(user1.getUsername(), user1.getPassword()));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getUsers(){
        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        User user3 = new User();
        user2.setUsername("User2");

        _userDAO.addUser(user1);
        _userDAO.addUser(user2);
        _userDAO.addUser(user3);

        List<UUID> users = new ArrayList<>();
        users.add(user1.getId());
        users.add(user2.getId());

        assertEquals(2, _userDAO.getUsers(users).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getSearchResult(){
        User user1 = new User();
        user1.setUsername("TestUser1");
        _userDAO.addUser(user1);

        User user2 = new User();
        user2.setUsername("TestUser2");
        _userDAO.addUser(user2);

        User user3 = new User();
        user3.setUsername("Test3");
        _userDAO.addUser(user3);

        assertEquals(2, _userDAO.getSearchResult("User", 1, 10).size());
    }
}

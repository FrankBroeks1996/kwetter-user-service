package daos;

import models.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IUserDAO {
    void addUser(User user);

    void editUser(User user);

    User getUserById(UUID userId);

    User getUserByUsername(String username);

    void followUser(User currentUser, User userToBeFollowed);

    void unFollowUser(User currentUser, User userToBeUnFollowed);

    Set<User> getAllFollowers(User user);

    Set<User> getAllFollowing(User user);

    List<User> getAllUsers();

    boolean isFollowing(User currentUser, User checkUser);

    User login(String username, String password);

    List<User> getUsers(List<UUID> authors);
}

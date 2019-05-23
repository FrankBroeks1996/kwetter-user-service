package daos;

import models.User;

import java.util.List;
import java.util.Set;

public interface IUserDAO {
    void addUser(User user);

    void editUser(User user);

    User getUserByName(String name);

    void followUser(User currentUser, User userToBeFollowed);

    void unFollowUser(User currentUser, User userToBeUnFollowed);

    Set<User> getAllFollowers(User user);

    Set<User> getAllFollowing(User user);

    List<User> getAllUsers();

    boolean isFollowing(User currentUser, User checkUser);

    boolean login(User user);
}

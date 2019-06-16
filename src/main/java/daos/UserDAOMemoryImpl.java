package daos;

import database.memory.InMemoryDatabase;
import models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
@Alternative
public class UserDAOMemoryImpl implements IUserDAO {

    @Override
    public void addUser(User user) {
        InMemoryDatabase.getInMemoryDatabase().addUser(user);
    }

    @Override
    public void editUser(User user) {
        User savedUser = InMemoryDatabase.getInMemoryDatabase().getUserById(user.getId());
        savedUser.setBio(user.getBio());
        savedUser.setLocation(user.getLocation());
        savedUser.setWebsite(user.getWebsite());
        savedUser.setRole(user.getRole());
        savedUser.setProfilePicturePath(user.getProfilePicturePath());
    }

    @Override
    public User getUserById(UUID userId) {
        return InMemoryDatabase.getInMemoryDatabase().getUserById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return InMemoryDatabase.getInMemoryDatabase().getUserByUsername(username);
    }

    @Override
    public void followUser(User currentUser, User userToBeFollowed) {
        User toBeFollowed = InMemoryDatabase.getInMemoryDatabase().getUserById(userToBeFollowed.getId());
        User current = InMemoryDatabase.getInMemoryDatabase().getUserById(currentUser.getId());
        toBeFollowed.getFollowers().add(current);
        current.getFollowing().add(toBeFollowed);
    }

    @Override
    public void unFollowUser(User currentUser, User userToBeUnFollowed) {
        User toBeUnFollowed = InMemoryDatabase.getInMemoryDatabase().getUserById(userToBeUnFollowed.getId());
        User current = InMemoryDatabase.getInMemoryDatabase().getUserById(currentUser.getId());
        toBeUnFollowed.getFollowers().removeIf(u -> u.getId() == current.getId());
        current.getFollowing().removeIf(u -> u.getId() == toBeUnFollowed.getId());
    }

    @Override
    public Set<User> getAllFollowers(User user) {
        return InMemoryDatabase.getInMemoryDatabase().getUserById(user.getId()).getFollowers();
    }

    @Override
    public Set<User> getAllFollowing(User user) {
        return InMemoryDatabase.getInMemoryDatabase().getUserById(user.getId()).getFollowing();
    }

    @Override
    public List<User> getAllUsers() {
        return InMemoryDatabase.getInMemoryDatabase().getUsers();
    }

    @Override
    public boolean isFollowing(User currentUser, User checkUser) {
        User check = InMemoryDatabase.getInMemoryDatabase().getUserById(checkUser.getId());
        return check.getFollowers().stream().anyMatch(u -> u.getId() == currentUser.getId());
    }

    @Override
    public User login(String username, String password) {
        return InMemoryDatabase.getInMemoryDatabase()
                .getUsers()
                .stream()
                .filter(u -> u.getUsername() == username && u.getPassword() == password)
                .findAny()
                .orElse(null);
    }

    @Override
    public List<User> getUsers(List<UUID> authors) {
        return InMemoryDatabase.getInMemoryDatabase()
                .getUsers()
                .stream()
                .filter(u -> authors.stream().anyMatch(a -> a == u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getSearchResult(String searchQuery, int resultPage, int resultSize) {
        return InMemoryDatabase.getInMemoryDatabase()
                .getUsers()
                .stream()
                .filter(u -> u.getUsername().contains(searchQuery))
                .collect(Collectors.toList());
    }
}

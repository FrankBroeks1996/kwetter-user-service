package services;

import daos.IUserDAO;
import models.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Stateless
public class UserService {

    @EJB(beanName = "UserDAOImpl")
    private IUserDAO userDAO;

    public void addUser(User user){
        userDAO.addUser(user);
    }

    public User getUserById(UUID userId){
        return userDAO.getUserById(userId);
    }

    public void editUser(User user){
        userDAO.editUser(user);
    }

    public boolean login(User user){return userDAO.login(user);}

    public Set<User> getAllFollowing(User user){
        return userDAO.getAllFollowing(user);
    }

    public Set<User> getAllFollowers(User user){
        return userDAO.getAllFollowers(user);
    }

    public void followUser(User currentUser, User userToBeFollowed){
        userDAO.followUser(currentUser, userToBeFollowed);
    }

    public void unFollowUser(User currentUser, User userToBeUnFollowed){
        userDAO.unFollowUser(currentUser, userToBeUnFollowed);
    }

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public boolean isFollowing(User currentUser, User checkUser){
        return userDAO.isFollowing(currentUser, checkUser);
    }
}

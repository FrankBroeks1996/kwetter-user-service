package services;

import daos.IUserDAO;
import models.User;
import org.jboss.arquillian.transaction.api.annotation.Transactional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Stateless
public class UserService {

    @EJB(beanName =  "UserDAOImpl")
    private IUserDAO userDAO;

    public void addUser(User user){
        userDAO.addUser(user);
    }

    public User getUserById(UUID userId){
        return userDAO.getUserById(userId);
    }

    public User getUserByUsername(String username) { return  userDAO.getUserByUsername(username); }

    public void editUser(User user){
        userDAO.editUser(user);
    }

    public User login(String username, String password){return userDAO.login(username, password);}

    public Set<User> getAllFollowing(User user){
        return userDAO.getAllFollowing(user);
    }

    public Set<User> getAllFollowers(User user){
        return userDAO.getAllFollowers(user);
    }

    public void followUser(User currentUser, User userToBeFollowed){
        userDAO.followUser(currentUser, userToBeFollowed);
    }

    @Transactional
    public void unFollowUser(User currentUser, User userToBeUnFollowed){
        userDAO.unFollowUser(currentUser, userToBeUnFollowed);
    }

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public boolean isFollowing(User currentUser, User checkUser){
        return userDAO.isFollowing(currentUser, checkUser);
    }

    public List<User> getUsers(List<UUID> authors){ return userDAO.getUsers(authors); }

    public List<User> getSearchResult(String searchQuery, int resultPage, int resultSize){return userDAO.getSearchResult(searchQuery, resultPage, resultSize);}

    public void setUserDAO(IUserDAO userDAO){
        this.userDAO = userDAO;
    }
}

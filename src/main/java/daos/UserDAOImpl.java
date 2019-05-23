package daos;

import models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless
@Default
@Named("UserDAOImpl")
public class UserDAOImpl implements IUserDAO {

    @PersistenceContext(name = "primary")
    private EntityManager em;

    public UserDAOImpl(){}

    public void addUser(User user){
        em.persist(user);
    }

    public void editUser(User user){
        em.merge(user);
    }

    public User getUserByName(String name){
        return em.createNamedQuery("User.getUserByName", User.class).setParameter("name", name).getSingleResult();
    }

    public void followUser(User currentUser, User userToBeFollowed){
        userToBeFollowed.getFollowers().add(currentUser);
        em.merge(userToBeFollowed);
    }

    public void unFollowUser(User currentUser, User userToBeUnFollowed) {
        userToBeUnFollowed.getFollowers().removeIf( f -> f.getId() == currentUser.getId() );
        em.merge(userToBeUnFollowed);
    }

    public Set<User> getAllFollowers(User user){
        return user.getFollowers();
    }

    public Set<User> getAllFollowing(User user){
        return user.getFollowing();
    }

    public List<User> getAllUsers() {
        return em.createNamedQuery("User.getAllUsers", User.class).getResultList();
    }

    public boolean isFollowing(User currentUser, User checkUser){
        try {
            User user = em.createNamedQuery("User.isFollowing", User.class).setParameter("username", currentUser.getUsername()).setParameter("checkUser", checkUser).getSingleResult();
            if (user == null) {
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(User user){
        try {
            User u = em.createNamedQuery("User.login", User.class).setParameter("username", user.getUsername()).setParameter("password", user.getPassword()).getSingleResult();
            if (u == null) {
                return false;
            } else {
                return true;
            }
        }
        catch (Exception ex){
            return false;
        }
    }
}

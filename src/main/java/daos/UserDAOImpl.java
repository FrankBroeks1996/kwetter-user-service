package daos;

import database.memory.InMemoryDatabase;
import models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    public User getUserById(UUID userId){
        return em.createNamedQuery("User.getUserById", User.class).setParameter("id", userId).getSingleResult();
    }

    public User getUserByUsername(String username){
        return em.createNamedQuery("User.getUserByUsername", User.class).setParameter("username", username).getSingleResult();
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
        return new HashSet<>(em.createNamedQuery("User.getAllFollowers", User.class).setParameter("current", user).getResultList());
    }

    public Set<User> getAllFollowing(User user){
        return new HashSet<>(em.createNamedQuery("User.getAllFollowing", User.class).setParameter("current", user).getResultList());
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

    public User login(String username, String password){
        try {
            return em.createNamedQuery("User.login", User.class).setParameter("username", username).setParameter("password", password).getSingleResult();
        }
        catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<User> getUsers(List<UUID> authors) {
        return em.createNamedQuery("User.getUsers", User.class).setParameter("authors", authors).getResultList();
    }
}

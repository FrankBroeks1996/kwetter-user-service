package database.memory;

import models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class InMemoryDatabase {

    private static InMemoryDatabase inMemoryDatabase;

    protected List<User> users;

    public InMemoryDatabase(){
        cleanUp();
    }

    public static InMemoryDatabase getInMemoryDatabase(){
        if(inMemoryDatabase == null){
            inMemoryDatabase = new InMemoryDatabase();
        }
        return inMemoryDatabase;
    }

    public void cleanUp(){
        this.users = new ArrayList<>();
    }

    public List<User> getUsers(){
        return users;
    }

    public User getUserById(UUID userId){
        return users.stream().filter(u -> u.getId() == userId).findAny().orElse(null);
    }

    public User getUserByUsername(String username){
        return users.stream().filter(u -> u.getUsername() == username).findAny().orElse(null);
    }

    public User addUser(User user){
        user.setId(UUID.randomUUID());
        user.setFollowers(new HashSet<>());
        user.setFollowing(new HashSet<>());
        users.add(user);
        return user;
    }
}

package dataaccess;

import entity.User;
import usecase.profile.ProfileUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccess implements UserDataAccessInterface,
        ProfileUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}

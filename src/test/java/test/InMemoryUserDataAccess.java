package test;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccess implements
        LoginUserDataAccessInterface,
        SignupUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;

    public InMemoryUserDataAccess() {
        users.put("bob", new User("bob", 0, 0, 8, "123"));
    }

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }
}